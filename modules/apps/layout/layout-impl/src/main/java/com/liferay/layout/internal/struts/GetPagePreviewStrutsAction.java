/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.struts;

import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderRegistry;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.theme.ThemeUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsWebKeys;
import com.liferay.segments.service.SegmentsExperienceLocalService;

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
	property = "path=/portal/get_page_preview", service = StrutsAction.class
)
public class GetPagePreviewStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ThemeDisplay currentThemeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ThemeDisplay themeDisplay = (ThemeDisplay)currentThemeDisplay.clone();

		long selPlid = ParamUtil.getLong(httpServletRequest, "selPlid");

		if (selPlid > 0) {
			Layout layout = _layoutLocalService.fetchLayout(selPlid);

			themeDisplay.setLayout(layout);

			LayoutSet layoutSet = layout.getLayoutSet();

			themeDisplay.setLayoutSet(layoutSet);
			themeDisplay.setLookAndFeel(
				layoutSet.getTheme(), layoutSet.getColorScheme());

			themeDisplay.setPlid(layout.getPlid());
			themeDisplay.setScopeGroupId(layout.getGroupId());
		}

		if (!LayoutPermissionUtil.containsLayoutUpdatePermission(
				PermissionCheckerFactoryUtil.create(themeDisplay.getRealUser()),
				themeDisplay.getLayout())) {

			httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);

			return null;
		}

		long[] currentSegmentsExperienceIds = GetterUtil.getLongValues(
			httpServletRequest.getAttribute(
				SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS));
		Layout currentLayout = (Layout)httpServletRequest.getAttribute(
			WebKeys.LAYOUT);
		boolean currentPortletDecorate = GetterUtil.getBoolean(
			httpServletRequest.getAttribute(WebKeys.PORTLET_DECORATE));

		try {
			long segmentsExperienceId = ParamUtil.getLong(
				httpServletRequest, "segmentsExperienceId",
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(selPlid));

			httpServletRequest.setAttribute(
				SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS,
				new long[] {segmentsExperienceId});

			httpServletRequest.setAttribute(
				WebKeys.PORTLET_DECORATE, Boolean.FALSE);

			String languageId = ParamUtil.getString(
				httpServletRequest, "languageId",
				LocaleUtil.toLanguageId(themeDisplay.getLocale()));

			themeDisplay.setLocale(LocaleUtil.fromLanguageId(languageId));

			themeDisplay.setSignedIn(false);

			User guestUser = _userLocalService.getGuestUser(
				themeDisplay.getCompanyId());

			themeDisplay.setUser(guestUser);

			Layout layout = themeDisplay.getLayout();

			layout.setClassNameId(0);

			String className = ParamUtil.getString(
				httpServletRequest, "className");
			long classPK = ParamUtil.getLong(httpServletRequest, "classPK");

			if (layout.isTypeAssetDisplay() &&
				(Validator.isNull(className) || (classPK <= 0))) {

				layout.setType(LayoutConstants.TYPE_CONTENT);
			}

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			ServiceContext clonedServiceContext =
				(ServiceContext)serviceContext.clone();

			clonedServiceContext.setPlid(layout.getPlid());
			clonedServiceContext.setScopeGroupId(layout.getGroupId());

			ServiceContextThreadLocal.pushServiceContext(clonedServiceContext);

			httpServletRequest.setAttribute(WebKeys.LAYOUT, layout);
			httpServletRequest.setAttribute(
				WebKeys.THEME_DISPLAY, themeDisplay);

			if (Validator.isNotNull(className) && (classPK > 0)) {
				_includeInfoItemObjects(className, classPK, httpServletRequest);
			}

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
			httpServletRequest.setAttribute(
				SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS,
				currentSegmentsExperienceIds);
			httpServletRequest.setAttribute(WebKeys.LAYOUT, currentLayout);
			httpServletRequest.setAttribute(
				WebKeys.PORTLET_DECORATE, currentPortletDecorate);
			httpServletRequest.setAttribute(
				WebKeys.THEME_DISPLAY, currentThemeDisplay);

			ServiceContextThreadLocal.popServiceContext();
		}

		return null;
	}

	private void _includeInfoItemObjects(
			String className, long classPK,
			HttpServletRequest httpServletRequest)
		throws Exception {

		InfoItemObjectProvider<?> infoItemObjectProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemObjectProvider.class, className,
				ClassPKInfoItemIdentifier.INFO_ITEM_SERVICE_FILTER);

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			new ClassPKInfoItemIdentifier(classPK);

		String version = ParamUtil.getString(httpServletRequest, "version");

		if (Validator.isNotNull(version)) {
			classPKInfoItemIdentifier.setVersion(version);
		}

		Object infoItem = infoItemObjectProvider.getInfoItem(
			classPKInfoItemIdentifier);

		httpServletRequest.setAttribute(InfoDisplayWebKeys.INFO_ITEM, infoItem);

		InfoItemDetailsProvider infoItemDetailsProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemDetailsProvider.class, className);

		httpServletRequest.setAttribute(
			InfoDisplayWebKeys.INFO_ITEM_DETAILS,
			infoItemDetailsProvider.getInfoItemDetails(infoItem));

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			_layoutDisplayPageProviderRegistry.
				getLayoutDisplayPageProviderByClassName(className);

		httpServletRequest.setAttribute(
			LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_PROVIDER,
			layoutDisplayPageProvider);

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
				new InfoItemReference(className, classPK));

		if (layoutDisplayPageObjectProvider != null) {
			httpServletRequest.setAttribute(
				LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER,
				layoutDisplayPageObjectProvider);
		}
	}

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private LayoutDisplayPageProviderRegistry
		_layoutDisplayPageProviderRegistry;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}