/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.product.navigation.control.menu;

import com.liferay.launch.set.details.LaunchSetDetails;
import com.liferay.launch.set.details.provider.LaunchSetDetailsProvider;
import com.liferay.launch.set.permission.LaunchSetPermission;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
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
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
public class DraftLayoutSelectorProductNavigationControlMenuEntry
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
			_reactRenderer.renderReact(
				new ComponentDescriptor(
					"{DraftLayoutSelector} from " +
						"layout-content-page-editor-web"),
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
		return _isEditMode(httpServletRequest);
	}

	@Override
	public boolean isShow(HttpServletRequest httpServletRequest)
		throws PortalException {

		if (!_isEditMode(httpServletRequest)) {
			return false;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!FeatureFlagManagerUtil.isEnabled(
				themeDisplay.getCompanyId(), "LPD-72278")) {

			return false;
		}

		Layout layout = themeDisplay.getLayout();

		if (!layout.isDraftLayout() || layout.isTypeUtility() ||
			layout.isTypeAssetDisplay()) {

			return false;
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByPlid(layout.getClassPK());

		if ((layoutPageTemplateEntry != null) ||
			!_hasLaunchDrafts(layout.getClassPK())) {

			return false;
		}

		return super.isShow(httpServletRequest);
	}

	private Map<String, Object> _getDraftLayoutMap(
		boolean disabled, HttpServletRequest httpServletRequest, String label,
		Layout layout, ThemeDisplay themeDisplay) {

		boolean active = layout.getPlid() == themeDisplay.getPlid();

		return HashMapBuilder.<String, Object>put(
			"active", active
		).put(
			"disabled", disabled
		).put(
			"href",
			() -> {
				if (active || disabled) {
					return null;
				}

				return _getEditURL(layout, httpServletRequest, themeDisplay);
			}
		).put(
			"label", label
		).put(
			"launchSetId", layout.getLaunchSetId()
		).build();
	}

	private List<Map<String, Object>> _getDraftLayouts(
		HttpServletRequest httpServletRequest) {

		List<Map<String, Object>> draftLayouts = new ArrayList<>();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		Layout mainDraftLayout = layout;

		if (layout.getLaunchSetId() != 0) {
			mainDraftLayout = _layoutLocalService.fetchDraftLayout(
				layout.getClassPK());
		}

		draftLayouts.add(
			_getDraftLayoutMap(
				false, httpServletRequest,
				_language.get(httpServletRequest, "main"), mainDraftLayout,
				themeDisplay));

		draftLayouts.addAll(
			_getLaunchDraftLayoutMaps(
				layout.getClassPK(), httpServletRequest, themeDisplay));

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

	private List<Map<String, Object>> _getLaunchDraftLayoutMaps(
		long classPK, HttpServletRequest httpServletRequest,
		ThemeDisplay themeDisplay) {

		String restrictedLaunchLabel = _language.get(
			httpServletRequest, "restricted-launch");

		List<Map<String, Object>> launchDraftLayoutMaps =
			TransformUtil.transform(
				_layoutLocalService.fetchDraftLayouts(classPK),
				draftLayout -> {
					long launchSetId = draftLayout.getLaunchSetId();

					if (launchSetId == 0) {
						return null;
					}

					LaunchSetDetails launchSetDetails =
						_launchSetDetailsProvider.fetchLaunchSetDetails(
							launchSetId);

					if (launchSetDetails == null) {
						return null;
					}

					boolean restricted = !_launchSetPermission.contains(
						themeDisplay.getPermissionChecker(), launchSetId,
						ActionKeys.VIEW);

					return _getDraftLayoutMap(
						restricted, httpServletRequest,
						restricted ? restrictedLaunchLabel :
							launchSetDetails.getName(),
						draftLayout, themeDisplay);
				});

		launchDraftLayoutMaps.sort(
			Comparator.comparing(
				map -> (String)map.get("label"),
				String.CASE_INSENSITIVE_ORDER));

		return launchDraftLayoutMaps;
	}

	private boolean _hasLaunchDrafts(long classPK) {
		if (_layoutLocalService.countDraftLayouts(classPK) > 1) {
			return true;
		}

		return false;
	}

	private boolean _isEditMode(HttpServletRequest httpServletRequest) {
		String mode = ParamUtil.getString(
			httpServletRequest, "p_l_mode", Constants.VIEW);

		return mode.equals(Constants.EDIT);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DraftLayoutSelectorProductNavigationControlMenuEntry.class);

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
	private Portal _portal;

	@Reference
	private ReactRenderer _reactRenderer;

}