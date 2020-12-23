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

import com.liferay.headless.delivery.dto.v1_0.ContentPage;
import com.liferay.headless.delivery.internal.dto.v1_0.converter.ContentPageDTOConverter;
import com.liferay.headless.delivery.resource.v1_0.ContentPageResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.ServicePreAction;
import com.liferay.portal.events.ThemeServicePreAction;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.servlet.DummyHttpServletResponse;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.taglib.util.ThemeUtil;

import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import javax.validation.constraints.NotNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier de Arcos
 * @author Jürgen Kappler
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/content-page.properties",
	scope = ServiceScope.PROTOTYPE, service = ContentPageResource.class
)
public class ContentPageResourceImpl extends BaseContentPageResourceImpl {

	@Override
	public ContentPage getSiteContentPagePrivateFriendlyUrlPath(
			@NotNull Long siteId, @NotNull String friendlyUrlPath)
		throws Exception {

		return _toContentPage(
			_getLayout(siteId, true, friendlyUrlPath),
			"getSiteContentPagePrivateFriendlyUrlPath");
	}

	@Override
	public String getSiteContentPagePrivateFriendlyUrlPathRenderedPage(
			@NotNull Long siteId, @NotNull String friendlyUrlPath)
		throws Exception {

		return _getRenderedLayout(friendlyUrlPath, true, siteId);
	}

	@Override
	public ContentPage getSiteContentPagePublicFriendlyUrlPath(
			@NotNull Long siteId, @NotNull String friendlyUrlPath)
		throws Exception {

		return _toContentPage(
			_getLayout(siteId, false, friendlyUrlPath),
			"getSiteContentPagePublicFriendlyUrlPath");
	}

	@Override
	public String getSiteContentPagePublicFriendlyUrlPathRenderedPage(
			@NotNull Long siteId, @NotNull String friendlyUrlPath)
		throws Exception {

		return _getRenderedLayout(friendlyUrlPath, false, siteId);
	}

	@Override
	public Page<ContentPage> getSiteContentPagesPage(
		@NotNull Long siteId, String search, Aggregation aggregation,
		Filter filter, Pagination pagination, Sort[] sorts) {

		List<Layout> layouts = _layoutService.getLayouts(
			siteId, LayoutConstants.TYPE_CONTENT);

		return Page.of(
			TransformUtil.transform(
				layouts,
				layout -> _toContentPage(layout, "getSiteContentPagesPage")));
	}

	private Layout _getLayout(
			Long groupId, boolean privateLayout, String friendlyUrlPath)
		throws Exception {

		String languageId = LocaleUtil.toLanguageId(
			contextAcceptLanguage.getPreferredLocale());

		LayoutFriendlyURL layoutFriendlyURL =
			_layoutFriendlyURLLocalService.fetchLayoutFriendlyURL(
				groupId, privateLayout,
				StringPool.FORWARD_SLASH + friendlyUrlPath, languageId);

		return Optional.ofNullable(
			layoutFriendlyURL
		).map(
			existingLayoutFriendlyURL -> _layoutLocalService.fetchLayout(
				existingLayoutFriendlyURL.getPlid())
		).orElseThrow(
			() -> {
				StringBuilder sb = new StringBuilder(6);

				sb.append("No ");

				if (privateLayout) {
					sb.append("private ");
				}
				else {
					sb.append("public ");
				}

				sb.append("page exists with friendly URL path ");
				sb.append(friendlyUrlPath);
				sb.append(" and language ID ");
				sb.append(languageId);

				return new NoSuchModelException(sb.toString());
			}
		);
	}

	private String _getRenderedLayout(
			String friendlyUrlPath, boolean privateLayout, Long groupId)
		throws Exception {

		Layout layout = _getLayout(groupId, privateLayout, friendlyUrlPath);

		contextHttpServletRequest = DynamicServletRequest.addQueryString(
			contextHttpServletRequest, "p_l_id=" + layout.getPlid(), false);

		contextHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(layout));

		layout.includeLayoutContent(
			contextHttpServletRequest, contextHttpServletResponse);

		StringBundler sb =
			(StringBundler)contextHttpServletRequest.getAttribute(
				WebKeys.LAYOUT_CONTENT);

		LayoutSet layoutSet = layout.getLayoutSet();

		ServletContext servletContext = ServletContextPool.get(
			StringPool.BLANK);

		if (contextHttpServletRequest.getAttribute(WebKeys.CTX) == null) {
			contextHttpServletRequest.setAttribute(WebKeys.CTX, servletContext);
		}

		Document document = Jsoup.parse(
			ThemeUtil.include(
				servletContext, contextHttpServletRequest,
				contextHttpServletResponse, "portal_normal.ftl",
				layoutSet.getTheme(), false));

		Element bodyElement = document.body();

		bodyElement.html(sb.toString());

		return document.html();
	}

	private ThemeDisplay _getThemeDisplay(Layout layout) throws Exception {
		ServicePreAction servicePreAction = new ServicePreAction();

		HttpServletResponse httpServletResponse =
			new DummyHttpServletResponse();

		servicePreAction.servicePre(
			contextHttpServletRequest, httpServletResponse, false);

		ThemeServicePreAction themeServicePreAction =
			new ThemeServicePreAction();

		themeServicePreAction.run(
			contextHttpServletRequest, httpServletResponse);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)contextHttpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		themeDisplay.setLayout(layout);
		themeDisplay.setScopeGroupId(layout.getGroupId());
		themeDisplay.setSiteGroupId(layout.getGroupId());

		return themeDisplay;
	}

	private ContentPage _toContentPage(Layout layout, String methodName)
		throws Exception {

		return _contentPageDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				HashMapBuilder.put(
					"get",
					addAction(
						"VIEW", methodName,
						"com.liferay.portal.kernel.model.Group",
						layout.getGroupId())
				).build(),
				_dtoConverterRegistry, contextHttpServletRequest,
				layout.getPlid(), contextAcceptLanguage.getPreferredLocale(),
				contextUriInfo, contextUser),
			layout);
	}

	@Reference
	private ContentPageDTOConverter _contentPageDTOConverter;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private LayoutFriendlyURLLocalService _layoutFriendlyURLLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutService _layoutService;

}