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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsWebKeys;
import com.liferay.taglib.util.ThemeUtil;

import java.util.Locale;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/get_page_preview"
	},
	service = MVCResourceCommand.class
)
public class GetPagePreviewMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale currentLocale = themeDisplay.getLocale();

		long[] currentSegmentsExperienceIds = GetterUtil.getLongValues(
			resourceRequest.getAttribute(
				SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS),
			new long[] {SegmentsExperienceConstants.ID_DEFAULT});
		boolean currentPortletDecorate = GetterUtil.getBoolean(
			resourceRequest.getAttribute(WebKeys.PORTLET_DECORATE));
		User currentUser = (User)resourceRequest.getAttribute(WebKeys.USER);

		try {
			long segmentsExperienceId = ParamUtil.getLong(
				resourceRequest, "segmentsExperienceId");

			resourceRequest.setAttribute(
				SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS,
				new long[] {segmentsExperienceId});

			resourceRequest.setAttribute(
				WebKeys.PORTLET_DECORATE, Boolean.FALSE);

			String languageId = ParamUtil.getString(
				resourceRequest, "languageId",
				LocaleUtil.toLanguageId(themeDisplay.getLocale()));

			themeDisplay.setLocale(LocaleUtil.fromLanguageId(languageId));

			themeDisplay.setSignedIn(false);

			User defaultUser = _userLocalService.getDefaultUser(
				themeDisplay.getCompanyId());

			themeDisplay.setUser(defaultUser);

			Layout layout = themeDisplay.getLayout();

			layout.setClassNameId(0);

			String className = ParamUtil.getString(
				resourceRequest, "className");
			long classPK = ParamUtil.getLong(resourceRequest, "classPK");

			if (layout.isTypeAssetDisplay() &&
				(Validator.isNull(className) || (classPK <= 0))) {

				layout.setType(LayoutConstants.TYPE_CONTENT);
			}

			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(resourceRequest);

			if (Validator.isNotNull(className) && (classPK > 0)) {
				_includeInfoItemObjects(className, classPK, httpServletRequest);
			}

			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(resourceResponse);

			layout.includeLayoutContent(
				httpServletRequest, httpServletResponse);

			ServletContext servletContext = ServletContextPool.get(
				StringPool.BLANK);
			LayoutSet layoutSet = themeDisplay.getLayoutSet();

			Document document = Jsoup.parse(
				ThemeUtil.include(
					servletContext, httpServletRequest, httpServletResponse,
					"portal_normal.ftl", layoutSet.getTheme(), false));

			Element contentElement = document.getElementById("content");

			StringBundler sb = (StringBundler)httpServletRequest.getAttribute(
				WebKeys.LAYOUT_CONTENT);

			contentElement.html(sb.toString());

			ServletResponseUtil.write(httpServletResponse, document.toString());
		}
		finally {
			resourceRequest.setAttribute(
				SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS,
				currentSegmentsExperienceIds);
			resourceRequest.setAttribute(
				WebKeys.PORTLET_DECORATE, currentPortletDecorate);

			themeDisplay.setLocale(currentLocale);
			themeDisplay.setSignedIn(true);
			themeDisplay.setUser(currentUser);
		}
	}

	private void _includeInfoItemObjects(
			String className, long classPK,
			HttpServletRequest httpServletRequest)
		throws Exception {

		InfoItemObjectProvider<?> infoItemObjectProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemObjectProvider.class, className);

		Object infoItem = infoItemObjectProvider.getInfoItem(
			new ClassPKInfoItemIdentifier(classPK));

		httpServletRequest.setAttribute(InfoDisplayWebKeys.INFO_ITEM, infoItem);

		InfoItemDetailsProvider infoItemDetailsProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemDetailsProvider.class, className);

		httpServletRequest.setAttribute(
			InfoDisplayWebKeys.INFO_ITEM_DETAILS,
			infoItemDetailsProvider.getInfoItemDetails(infoItem));

		httpServletRequest.setAttribute(
			InfoDisplayWebKeys.INFO_ITEM_FIELD_VALUES_PROVIDER,
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class, className));
	}

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private LayoutDisplayPageProviderTracker _layoutDisplayPageProviderTracker;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}