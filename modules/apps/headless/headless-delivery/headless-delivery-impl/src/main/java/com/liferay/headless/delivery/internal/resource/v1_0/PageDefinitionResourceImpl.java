/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.headless.delivery.dto.v1_0.PageDefinition;
import com.liferay.headless.delivery.resource.v1_0.PageDefinitionResource;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.ServicePreAction;
import com.liferay.portal.events.ThemeServicePreAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.DummyHttpServletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Pavel Savinov
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/page-definition.properties",
	scope = ServiceScope.PROTOTYPE, service = PageDefinitionResource.class
)
public class PageDefinitionResourceImpl extends BasePageDefinitionResourceImpl {

	@Override
	public Response postSitePageDefinitionPreview(
			Long siteId, PageDefinition pageDefinition)
		throws Exception {

		Map<Locale, String> nameMap = Collections.singletonMap(
			contextAcceptLanguage.getPreferredLocale(), StringUtil.randomId());

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			contextHttpServletRequest);

		Layout layout = _layoutService.addLayout(
			siteId, false, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			_portal.getClassNameId(PageDefinition.class), 0, nameMap, nameMap,
			Collections.emptyMap(), Collections.emptyMap(),
			Collections.emptyMap(), LayoutConstants.TYPE_CONTENT,
			StringPool.BLANK, true, true, Collections.emptyMap(), 0,
			serviceContext);

		LayoutStructure layoutStructure = new LayoutStructure();

		layoutStructure.addRootLayoutStructureItem();

		_fragmentEntryLinkLocalService.
			deleteLayoutPageTemplateEntryFragmentEntryLinks(
				siteId, _portal.getClassNameId(Layout.class), layout.getPlid());

		SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();

		FilterProvider filterProvider = simpleFilterProvider.addFilter(
			"Liferay.Vulcan", SimpleBeanPropertyFilter.serializeAll());

		ObjectWriter objectWriter = _objectMapper.writer(filterProvider);

		try {
			_layoutPageTemplatesImporter.importPageElement(
				layout, layoutStructure, layoutStructure.getMainItemId(),
				objectWriter.writeValueAsString(
					pageDefinition.getPageElement()),
				0);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to import page element", exception);
			}

			return Response.serverError(
			).status(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR
			).entity(
				"Unable to generate page definition preview"
			).build();
		}

		contextHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(layout));

		layout.includeLayoutContent(
			contextHttpServletRequest, contextHttpServletResponse);

		_layoutService.deleteLayout(layout.getPlid(), serviceContext);

		StringBundler sb =
			(StringBundler)contextHttpServletRequest.getAttribute(
				WebKeys.LAYOUT_CONTENT);

		if (sb == null) {
			return Response.noContent(
			).build();
		}

		return Response.ok(
			sb.toString()
		).build();
	}

	private ThemeDisplay _getThemeDisplay(Layout layout) throws Exception {
		ServicePreAction servicePreAction = new ServicePreAction();

		DummyHttpServletResponse dummyHttpServletResponse =
			new DummyHttpServletResponse();

		servicePreAction.servicePre(
			contextHttpServletRequest, dummyHttpServletResponse, false);

		ThemeServicePreAction themeServicePreAction =
			new ThemeServicePreAction();

		themeServicePreAction.run(
			contextHttpServletRequest, dummyHttpServletResponse);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)contextHttpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		themeDisplay.setLayout(layout);
		themeDisplay.setScopeGroupId(layout.getGroupId());
		themeDisplay.setSiteGroupId(layout.getGroupId());

		return themeDisplay;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PageDefinitionResourceImpl.class);

	private static final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
			configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
			setDateFormat(new ISO8601DateFormat());
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
			setVisibility(
				PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
			setVisibility(
				PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
		}
	};

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private LayoutPageTemplatesImporter _layoutPageTemplatesImporter;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private Portal _portal;

}