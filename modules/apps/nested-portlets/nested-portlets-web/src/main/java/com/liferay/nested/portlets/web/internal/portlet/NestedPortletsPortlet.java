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

package com.liferay.nested.portlets.web.internal.portlet;

import com.liferay.nested.portlets.web.internal.constants.NestedPortletsPortletKeys;
import com.liferay.nested.portlets.web.internal.constants.NestedPortletsWebKeys;
import com.liferay.nested.portlets.web.internal.display.context.NestedPortletsDisplayContext;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTemplate;
import com.liferay.portal.kernel.model.LayoutTemplateConstants;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutTemplateLocalService;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-nested-portlets",
		"com.liferay.portlet.display-category=category.cms",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=1",
		"com.liferay.portlet.single-page-application=false",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Nested Portlets",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + NestedPortletsPortletKeys.NESTED_PORTLETS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user"
	},
	service = Portlet.class
)
public class NestedPortletsPortlet extends MVCPortlet {

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String layoutTemplateId = StringPool.BLANK;

		try {
			NestedPortletsDisplayContext nestedPortletsDisplayContext =
				new NestedPortletsDisplayContext(
					_portal.getHttpServletRequest(renderRequest));

			layoutTemplateId =
				nestedPortletsDisplayContext.getLayoutTemplateId();
		}
		catch (ConfigurationException ce) {
			if (_log.isWarnEnabled()) {
				_log.warn(ce, ce);
			}
		}

		String templateId = StringPool.BLANK;
		String templateContent = StringPool.BLANK;

		Map<String, String> columnIds = new HashMap<>();

		if (Validator.isNotNull(layoutTemplateId)) {
			Theme theme = themeDisplay.getTheme();

			LayoutTemplate layoutTemplate =
				_layoutTemplateLocalService.getLayoutTemplate(
					layoutTemplateId, false, theme.getThemeId());

			String content = layoutTemplate.getContent();

			Matcher processColumnMatcher = _processColumnPattern.matcher(
				content);

			while (processColumnMatcher.find()) {
				String columnId = processColumnMatcher.group(2);

				if (Validator.isNotNull(columnId)) {
					columnIds.put(
						columnId,
						renderResponse.getNamespace() + StringPool.UNDERLINE +
							columnId);
				}
			}

			processColumnMatcher.reset();

			StringBundler sb = new StringBundler(4);

			sb.append(theme.getThemeId());
			sb.append(LayoutTemplateConstants.CUSTOM_SEPARATOR);
			sb.append(renderResponse.getNamespace());
			sb.append(layoutTemplateId);

			templateId = sb.toString();

			content = processColumnMatcher.replaceAll(
				"$1" + renderResponse.getNamespace() + "_$2$3");

			Matcher columnIdMatcher = _columnIdPattern.matcher(content);

			templateContent = columnIdMatcher.replaceAll(
				"$1" + renderResponse.getNamespace() + "_$2$3");
		}

		checkLayout(themeDisplay.getLayout(), columnIds.values());

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		renderRequest.setAttribute(
			NestedPortletsWebKeys.TEMPLATE_ID + portletDisplay.getId(),
			templateId);
		renderRequest.setAttribute(
			NestedPortletsWebKeys.TEMPLATE_CONTENT + portletDisplay.getId(),
			templateContent);

		Map<String, Object> vmVariables =
			(Map<String, Object>)renderRequest.getAttribute(
				WebKeys.FTL_VARIABLES + portletDisplay.getId());

		if (vmVariables != null) {
			vmVariables.putAll(columnIds);
		}
		else {
			renderRequest.setAttribute(
				WebKeys.FTL_VARIABLES + portletDisplay.getId(), columnIds);
		}

		super.include(viewTemplate, renderRequest, renderResponse);
	}

	protected void checkLayout(Layout layout, Collection<String> columnIds) {
		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		String[] layoutColumnIds = StringUtil.split(
			typeSettingsProperties.get(
				LayoutTypePortletConstants.NESTED_COLUMN_IDS));

		boolean updateColumnIds = false;

		for (String columnId : columnIds) {
			String portletIds = typeSettingsProperties.getProperty(columnId);

			if (Validator.isNotNull(portletIds) &&
				!ArrayUtil.contains(layoutColumnIds, columnId)) {

				layoutColumnIds = ArrayUtil.append(layoutColumnIds, columnId);

				updateColumnIds = true;
			}
		}

		if (updateColumnIds) {
			typeSettingsProperties.setProperty(
				LayoutTypePortletConstants.NESTED_COLUMN_IDS,
				StringUtil.merge(layoutColumnIds));

			layout.setTypeSettingsProperties(typeSettingsProperties);

			try {
				_layoutLocalService.updateLayout(
					layout.getGroupId(), layout.isPrivateLayout(),
					layout.getLayoutId(), layout.getTypeSettings());
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
		}
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutTemplateLocalService(
		LayoutTemplateLocalService layoutTemplateLocalService) {

		_layoutTemplateLocalService = layoutTemplateLocalService;
	}

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.nested.portlets.web)(&(release.schema.version>=1.0.0)(!(release.schema.version>=2.0.0))))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NestedPortletsPortlet.class);

	private static final Pattern _columnIdPattern = Pattern.compile(
		"([<].*?id=[\"'])([^ ]*?)([\"'].*?[>])", Pattern.DOTALL);
	private static final Pattern _processColumnPattern = Pattern.compile(
		"(processColumn[(]\")(.*?)(\"(?:, *\"(?:.*?)\")?[)])", Pattern.DOTALL);

	private LayoutLocalService _layoutLocalService;
	private LayoutTemplateLocalService _layoutTemplateLocalService;

	@Reference
	private Portal _portal;

}