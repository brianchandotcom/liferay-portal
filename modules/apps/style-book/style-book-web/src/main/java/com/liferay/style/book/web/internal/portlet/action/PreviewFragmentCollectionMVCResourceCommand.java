/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.web.internal.portlet.action;

import com.liferay.client.extension.type.ThemeCSSCET;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.fragment.contributor.FragmentCollectionContributorRegistry;
import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.frontend.token.definition.constants.FrontendTokenDefinitionConstants;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.theme.ThemeUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.style.book.constants.StyleBookPortletKeys;
import com.liferay.style.book.web.internal.constants.StyleBookWebKeys;

import java.util.Objects;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	property = {
		"javax.portlet.name=" + StyleBookPortletKeys.STYLE_BOOK,
		"mvc.command.name=/style_book/preview_fragment_collection"
	},
	service = MVCResourceCommand.class
)
public class PreviewFragmentCollectionMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);

		httpServletRequest.setAttribute(
			StyleBookWebKeys.FRAGMENT_COLLECTION_CONTRIBUTOR_TRACKER,
			_fragmentCollectionContributorRegistry);

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(resourceResponse);

		String renderedPreviewFragmentCollection =
			_renderPreviewFragmentCollection(
				httpServletRequest, httpServletResponse);

		String renderedPortalNormal = _renderPortalNormal(
			httpServletRequest, httpServletResponse);

		Document document = Jsoup.parse(renderedPortalNormal);

		Element element = document.body();

		element.html(renderedPreviewFragmentCollection);

		ServletResponseUtil.write(httpServletResponse, document.html());
	}

	private String _renderPortalNormal(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			themeDisplay.getScopeGroupId(), false);

		Theme theme = layoutSet.getTheme();

		if (!FeatureFlagManagerUtil.isEnabled(
				themeDisplay.getCompanyId(), "LPD-30204")) {

			themeDisplay.setLayoutSet(layoutSet);

			themeDisplay.setLookAndFeel(theme, layoutSet.getColorScheme());
		}
		else {
			String themeId = GetterUtil.getString(
				httpServletRequest.getParameter("styleBookEntryThemeId"));

			FrontendTokenDefinition frontendTokenDefinition =
				_frontendTokenDefinitionRegistry.getFrontendTokenDefinition(
					themeDisplay.getCompanyId(), themeId);

			if ((frontendTokenDefinition != null) &&
				Objects.equals(
					frontendTokenDefinition.getThemeType(),
					FrontendTokenDefinitionConstants.
						THEME_TYPE_THEME_CSS_CET)) {

				themeId = theme.getThemeId();

				ThemeCSSCET themeCSSCET = (ThemeCSSCET)_cetManager.getCET(
					themeDisplay.getCompanyId(), themeId);

				if (themeCSSCET != null) {
					if (_portal.isRightToLeft(httpServletRequest)) {
						themeDisplay.setClayCSSURL(themeCSSCET.getClayRTLURL());
						themeDisplay.setMainCSSURL(themeCSSCET.getMainRTLURL());
					}
					else {
						themeDisplay.setClayCSSURL(themeCSSCET.getClayURL());
						themeDisplay.setMainCSSURL(themeCSSCET.getMainURL());
					}
				}
			}

			theme = _themeLocalService.fetchTheme(
				themeDisplay.getCompanyId(), themeId);

			themeDisplay.setLookAndFeel(
				theme,
				_themeLocalService.getColorScheme(
					themeDisplay.getCompanyId(), theme.getThemeId(),
					StringPool.BLANK));
		}

		return ThemeUtil.include(
			ServletContextPool.get(StringPool.BLANK), httpServletRequest,
			httpServletResponse, "portal_normal.ftl", theme, false);
	}

	private String _renderPreviewFragmentCollection(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/preview_fragment_collection.jsp");

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			httpServletResponse, unsyncStringWriter);

		requestDispatcher.include(httpServletRequest, pipingServletResponse);

		return unsyncStringWriter.toString();
	}

	@Reference
	private CETManager _cetManager;

	@Reference
	private FragmentCollectionContributorRegistry
		_fragmentCollectionContributorRegistry;

	@Reference
	private FrontendTokenDefinitionRegistry _frontendTokenDefinitionRegistry;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private Portal _portal;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.style.book.web)")
	private ServletContext _servletContext;

	@Reference
	private ThemeLocalService _themeLocalService;

}