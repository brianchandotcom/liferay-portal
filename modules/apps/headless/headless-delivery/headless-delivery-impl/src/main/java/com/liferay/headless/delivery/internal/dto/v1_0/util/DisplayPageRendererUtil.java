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

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.headless.delivery.dto.v1_0.RenderedContent;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryException;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.ServicePreAction;
import com.liferay.portal.events.ThemeServicePreAction;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.servlet.DummyHttpServletResponse;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.theme.ThemeUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.JaxRsLinkUtil;

import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @author Jürgen Kappler
 */
public class DisplayPageRendererUtil {

	public static RenderedContent[] getRenderedContent(
		Class<?> baseClass, String itemClassName, long itemClassPK,
		long itemClassTypeId, DTOConverterContext dtoConverterContext,
		long groupId, Object item,
		InfoItemServiceTracker infoItemServiceTracker,
		LayoutDisplayPageProviderTracker layoutDisplayPageProviderTracker,
		LayoutLocalService layoutLocalService,
		LayoutPageTemplateEntryService layoutPageTemplateEntryService,
		String methodName) {

		Optional<UriInfo> uriInfoOptional =
			dtoConverterContext.getUriInfoOptional();

		if (!uriInfoOptional.isPresent()) {
			return null;
		}

		UriInfo uriInfo = uriInfoOptional.get();

		return TransformUtil.transformToArray(
			layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
				groupId, PortalUtil.getClassNameId(itemClassName),
				itemClassTypeId,
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE),
			layoutPageTemplateEntry -> new RenderedContent() {
				{
					contentTemplateId =
						layoutPageTemplateEntry.getLayoutPageTemplateEntryKey();
					contentTemplateName = layoutPageTemplateEntry.getName();
					markedAsDefault =
						layoutPageTemplateEntry.isDefaultTemplate();
					renderedContentURL = JaxRsLinkUtil.getJaxRsLink(
						"headless-delivery", baseClass, methodName, uriInfo,
						itemClassPK,
						layoutPageTemplateEntry.
							getLayoutPageTemplateEntryKey());

					setRenderedContentValue(
						() -> {
							if (!uriInfoOptional.map(
									UriInfo::getQueryParameters
								).map(
									parameters -> parameters.getFirst(
										"nestedFields")
								).map(
									fields -> fields.contains(
										"renderedContentValue")
								).orElse(
									false
								)) {

								return null;
							}

							return toHTML(
								itemClassName, itemClassTypeId,
								layoutPageTemplateEntry.
									getLayoutPageTemplateEntryKey(),
								groupId,
								dtoConverterContext.getHttpServletRequest(),
								new DummyHttpServletResponse(), item,
								infoItemServiceTracker,
								layoutDisplayPageProviderTracker,
								layoutLocalService,
								layoutPageTemplateEntryService);
						});
				}
			},
			RenderedContent.class);
	}

	public static String toHTML(
			String itemClassName, long itemClassTypeId, String displayPageKey,
			long groupId, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Object item,
			InfoItemServiceTracker infoItemServiceTracker,
			LayoutDisplayPageProviderTracker layoutDisplayPageProviderTracker,
			LayoutLocalService layoutLocalService,
			LayoutPageTemplateEntryService layoutPageTemplateEntryService)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryService.getLayoutPageTemplateEntry(
				groupId, displayPageKey);

		if ((layoutPageTemplateEntry.getType() !=
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE) &&
			(layoutPageTemplateEntry.getClassNameId() !=
				PortalUtil.getClassNameId(itemClassName)) &&
			(layoutPageTemplateEntry.getClassTypeId() != itemClassTypeId)) {

			throw new NoSuchPageTemplateEntryException();
		}

		Layout layout = layoutLocalService.getLayout(
			layoutPageTemplateEntry.getPlid());

		httpServletRequest = DynamicServletRequest.addQueryString(
			httpServletRequest, "p_l_id=" + layout.getPlid(), false);

		httpServletRequest.setAttribute(InfoDisplayWebKeys.INFO_ITEM, item);

		InfoItemDetailsProvider infoItemDetailsProvider =
			infoItemServiceTracker.getFirstInfoItemService(
				InfoItemDetailsProvider.class, itemClassName);

		InfoItemDetails infoItemDetails =
			infoItemDetailsProvider.getInfoItemDetails(item);

		httpServletRequest.setAttribute(
			InfoDisplayWebKeys.INFO_ITEM_DETAILS, infoItemDetails);

		httpServletRequest.setAttribute(
			InfoDisplayWebKeys.INFO_ITEM_FIELD_VALUES_PROVIDER,
			infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class, itemClassName));
		httpServletRequest.setAttribute(
			LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER,
			_getLayoutDisplayPageObjectProvider(
				infoItemDetails.getInfoItemReference(),
				layoutDisplayPageProviderTracker));
		httpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY,
			_getThemeDisplay(httpServletRequest, layout));

		layout.includeLayoutContent(httpServletRequest, httpServletResponse);

		StringBundler sb = (StringBundler)httpServletRequest.getAttribute(
			WebKeys.LAYOUT_CONTENT);

		LayoutSet layoutSet = layout.getLayoutSet();

		ServletContext servletContext = ServletContextPool.get(
			StringPool.BLANK);

		if (httpServletRequest.getAttribute(WebKeys.CTX) == null) {
			httpServletRequest.setAttribute(WebKeys.CTX, servletContext);
		}

		Document document = Jsoup.parse(
			ThemeUtil.include(
				servletContext, httpServletRequest, httpServletResponse,
				"portal_normal.ftl", layoutSet.getTheme(), false));

		Element bodyElement = document.body();

		bodyElement.html(sb.toString());

		return document.html();
	}

	private static LayoutDisplayPageObjectProvider<?>
		_getLayoutDisplayPageObjectProvider(
			InfoItemReference infoItemReference,
			LayoutDisplayPageProviderTracker layoutDisplayPageProviderTracker) {

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			layoutDisplayPageProviderTracker.
				getLayoutDisplayPageProviderByClassName(
					infoItemReference.getClassName());

		return layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
			infoItemReference);
	}

	private static ThemeDisplay _getThemeDisplay(
			HttpServletRequest httpServletRequest, Layout layout)
		throws Exception {

		ServicePreAction servicePreAction = new ServicePreAction();

		HttpServletResponse httpServletResponse =
			new DummyHttpServletResponse();

		servicePreAction.servicePre(
			httpServletRequest, httpServletResponse, false);

		ThemeServicePreAction themeServicePreAction =
			new ThemeServicePreAction();

		themeServicePreAction.run(httpServletRequest, httpServletResponse);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		themeDisplay.setLayout(layout);
		themeDisplay.setScopeGroupId(layout.getGroupId());
		themeDisplay.setSiteGroupId(layout.getGroupId());

		return themeDisplay;
	}

}