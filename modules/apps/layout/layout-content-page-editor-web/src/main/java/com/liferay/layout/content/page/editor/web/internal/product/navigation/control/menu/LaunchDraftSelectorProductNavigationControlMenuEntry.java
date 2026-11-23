/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.product.navigation.control.menu;

import com.liferay.launch.set.details.LaunchSetDetails;
import com.liferay.launch.set.details.provider.LaunchSetDetailsProvider;
import com.liferay.launch.set.permission.LaunchSetPermission;
import com.liferay.launch.set.util.comparator.LaunchSetDetailsNameComparator;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.react.renderer.ComponentDescriptor;
import com.liferay.portal.template.react.renderer.ReactRenderer;
import com.liferay.product.navigation.control.menu.BaseProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Writer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = {
		"product.navigation.control.menu.category.key=" + ProductNavigationControlMenuCategoryKeys.TOOLS,
		"product.navigation.control.menu.entry.order:Integer=110"
	},
	service = ProductNavigationControlMenuEntry.class
)
public class LaunchDraftSelectorProductNavigationControlMenuEntry
	extends BaseProductNavigationControlMenuEntry {

	@Override
	public String getLabel(Locale locale) {
		return null;
	}

	@Override
	public String getURL(HttpServletRequest httpServletRequest) {
		return null;
	}

	@Override
	public boolean includeIcon(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		Writer writer = httpServletResponse.getWriter();

		writer.write("<li class=\"control-menu-nav-item\">");

		try {
			String portletNamespace = _portal.getPortletNamespace(
				ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET);

			_reactRenderer.renderReact(
				new ComponentDescriptor(
					"{LaunchDraftSelector} from layout-content-page-editor-web",
					portletNamespace + "LaunchDraftSelector", null, true),
				HashMapBuilder.<String, Object>put(
					"draftLayouts", _getDraftLayouts(httpServletRequest)
				).build(),
				httpServletRequest, writer);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		writer.write("</li>");

		return true;
	}

	@Override
	public boolean isRelevant(HttpServletRequest httpServletRequest) {
		String mode = ParamUtil.getString(
			httpServletRequest, "p_l_mode", Constants.VIEW);

		return mode.equals(Constants.EDIT);
	}

	@Override
	public boolean isShow(HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!FeatureFlagManagerUtil.isEnabled(
				themeDisplay.getCompanyId(), "LPD-72278")) {

			return false;
		}

		Layout layout = themeDisplay.getLayout();

		if (!layout.isDraftLayout()) {
			return false;
		}

		String mode = ParamUtil.getString(
			httpServletRequest, "p_l_mode", Constants.VIEW);

		if (!mode.equals(Constants.EDIT)) {
			return false;
		}

		long classPK = layout.getClassPK();

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByPlid(classPK);

		if (layoutPageTemplateEntry != null) {
			return false;
		}

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.
				fetchLayoutUtilityPageEntryByPlid(classPK);

		if (layoutUtilityPageEntry != null) {
			return false;
		}

		return super.isShow(httpServletRequest);
	}

	private Map<String, Object> _getDraftLayoutMap(
		Layout draftLayout, HttpServletRequest httpServletRequest,
		LaunchSetDetails launchSetDetails, Set<Long> restrictedLaunchSetIds,
		ThemeDisplay themeDisplay) {

		long launchSetId = draftLayout.getLaunchSetId();

		boolean restricted = restrictedLaunchSetIds.contains(launchSetId);

		return HashMapBuilder.<String, Object>put(
			"active", draftLayout.getPlid() == themeDisplay.getPlid()
		).put(
			"disabled", restricted
		).put(
			"href",
			() -> {
				if (restricted ||
					(draftLayout.getPlid() == themeDisplay.getPlid())) {

					return null;
				}

				return _getEditURL(
					draftLayout, httpServletRequest, themeDisplay);
			}
		).put(
			"label",
			() -> {
				if (launchSetId > 0) {
					return launchSetDetails.getName();
				}

				return _language.get(httpServletRequest, "main");
			}
		).put(
			"launchSetId", launchSetId
		).build();
	}

	private List<Map<String, Object>> _getDraftLayouts(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		Map<Long, Layout> launchDraftLayoutsMap = new HashMap<>();

		Set<Long> restrictedLaunchSetIds = new HashSet<>();

		List<LaunchSetDetails> launchSetDetailsList = TransformUtil.transform(
			_layoutLocalService.fetchDraftLayouts(layout.getClassPK()),
			draftLayout -> {
				long launchSetId = draftLayout.getLaunchSetId();

				if (launchSetId == 0) {
					return null;
				}

				launchDraftLayoutsMap.put(launchSetId, draftLayout);

				if (!_launchSetPermission.contains(
						themeDisplay.getPermissionChecker(), launchSetId,
						ActionKeys.VIEW)) {

					restrictedLaunchSetIds.add(launchSetId);

					return new LaunchSetDetails(
						launchSetId,
						_language.get(httpServletRequest, "restricted-launch"));
				}

				return _launchSetDetailsProvider.fetchLaunchSetDetails(
					launchSetId);
			});

		launchSetDetailsList.sort(LaunchSetDetailsNameComparator.INSTANCE);

		List<Map<String, Object>> draftLayouts = new ArrayList<>();

		draftLayouts.add(
			_getDraftLayoutMap(
				_layoutLocalService.fetchDraftLayout(layout.getClassPK()),
				httpServletRequest, null, restrictedLaunchSetIds,
				themeDisplay));

		draftLayouts.addAll(
			TransformUtil.transform(
				launchSetDetailsList,
				launchSetDetails -> _getDraftLayoutMap(
					launchDraftLayoutsMap.get(
						launchSetDetails.getLaunchSetId()),
					httpServletRequest, launchSetDetails,
					restrictedLaunchSetIds, themeDisplay)));

		return draftLayouts;
	}

	private String _getEditURL(
			Layout draftLayout, HttpServletRequest httpServletRequest,
			ThemeDisplay themeDisplay)
		throws PortalException {

		String editURL = _portal.getLayoutFullURL(draftLayout, themeDisplay);

		String backURL = ParamUtil.getString(
			httpServletRequest, "p_l_back_url");

		if (Validator.isNotNull(backURL)) {
			editURL = HttpComponentsUtil.addParameters(
				editURL, "p_l_back_url", backURL);
		}

		String redirect = ParamUtil.getString(httpServletRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			editURL = HttpComponentsUtil.addParameters(
				editURL, "redirect", redirect);
		}

		long launchSetId = draftLayout.getLaunchSetId();

		if (launchSetId > 0) {
			return HttpComponentsUtil.addParameters(
				editURL, "editLaunchSetId", launchSetId, "p_l_mode",
				Constants.EDIT);
		}

		return HttpComponentsUtil.addParameters(
			editURL, "p_l_mode", Constants.EDIT);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LaunchDraftSelectorProductNavigationControlMenuEntry.class);

	@Reference
	private Language _language;

	@Reference
	private LaunchSetDetailsProvider _launchSetDetailsProvider;

	@Reference
	private LaunchSetPermission _launchSetPermission;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private ReactRenderer _reactRenderer;

}