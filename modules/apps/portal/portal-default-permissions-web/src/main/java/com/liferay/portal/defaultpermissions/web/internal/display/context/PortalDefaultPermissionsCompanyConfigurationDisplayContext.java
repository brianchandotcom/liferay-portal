/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.defaultpermissions.web.internal.display.context;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.defaultpermissions.asset.PortalDefaultPermissionsAssetRegistry;
import com.liferay.portal.defaultpermissions.web.internal.search.PortalDefaultPermissionsSearch;
import com.liferay.portal.defaultpermissions.web.internal.search.PortalDefaultPermissionsSearchEntry;
import com.liferay.portal.defaultpermissions.web.internal.search.PortalDefaultPermissionsSearchEntryClassNamePredicate;
import com.liferay.portal.defaultpermissions.web.internal.search.PortalDefaultPermissionsSearchEntryLabelPredicate;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.function.Predicate;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Stefano Motta
 */
public class PortalDefaultPermissionsCompanyConfigurationDisplayContext {

	public PortalDefaultPermissionsCompanyConfigurationDisplayContext(
		HttpServletRequest httpServletRequest, Language language,
		PortalDefaultPermissionsAssetRegistry
			portalDefaultPermissionsAssetRegistry) {

		_httpServletRequest = httpServletRequest;
		_language = language;

		PortletRequest portletRequest =
			(PortletRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		PortletResponse portletResponse =
			(PortletResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		_liferayPortletRequest = PortalUtil.getLiferayPortletRequest(
			portletRequest);

		_liferayPortletResponse = PortalUtil.getLiferayPortletResponse(
			portletResponse);

		_portalDefaultPermissionsAssetRegistry =
			portalDefaultPermissionsAssetRegistry;
	}

	public String getEditURL(String className) {
		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_httpServletRequest,
				"com_liferay_portlet_configuration_web_portlet_" +
					"PortletConfigurationPortlet",
				ActionRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/configuration/edit_portal_configuration_permissions"
		).setParameter(
			"modelResource", className
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public PortletURL getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setKeywords(
			() -> {
				String keywords = ParamUtil.getString(
					_httpServletRequest, "keywords");

				if (Validator.isNotNull(keywords)) {
					return keywords;
				}

				return null;
			}
		).setParameter(
			"delta",
			() -> {
				String delta = ParamUtil.getString(
					_httpServletRequest, "delta");

				if (Validator.isNotNull(delta)) {
					return delta;
				}

				return null;
			}
		).buildPortletURL();
	}

	public PortalDefaultPermissionsSearch getSearchContainer() {
		PortalDefaultPermissionsSearch searchContainer =
			new PortalDefaultPermissionsSearch(
				_liferayPortletRequest, getPortletURL());

		DisplayTerms searchTerms = searchContainer.getSearchTerms();

		List<PortalDefaultPermissionsSearchEntry>
			portalDefaultPermissionSearchEntries =
				_createPortalDefaultPermissionSearchEntryList();

		portalDefaultPermissionSearchEntries = filter(
			portalDefaultPermissionSearchEntries, searchTerms.getKeywords(),
			searchTerms.getKeywords());

		searchContainer.setResultsAndTotal(
			ListUtil.sort(
				portalDefaultPermissionSearchEntries,
				searchContainer.getOrderByComparator()));

		return searchContainer;
	}

	protected Predicate<PortalDefaultPermissionsSearchEntry> createPredicate(
		String className, String label) {

		Predicate<PortalDefaultPermissionsSearchEntry> predicate =
			new PortalDefaultPermissionsSearchEntryClassNamePredicate(
				className);

		return predicate.or(
			new PortalDefaultPermissionsSearchEntryLabelPredicate(label));
	}

	protected List<PortalDefaultPermissionsSearchEntry> filter(
		List<PortalDefaultPermissionsSearchEntry>
			portalDefaultPermissionSearchEntries,
		String className, String label) {

		if (Validator.isNull(className) && Validator.isNull(label)) {
			return portalDefaultPermissionSearchEntries;
		}

		Predicate<PortalDefaultPermissionsSearchEntry> predicate =
			createPredicate(className, label);

		return ListUtil.filter(
			portalDefaultPermissionSearchEntries, predicate::test);
	}

	private List<PortalDefaultPermissionsSearchEntry>
		_createPortalDefaultPermissionSearchEntryList() {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return TransformUtil.transform(
			_portalDefaultPermissionsAssetRegistry.
				getPortalDefaultPermissionsAssets(),
			portalDefaultPermissionsAsset ->
				new PortalDefaultPermissionsSearchEntry(
					portalDefaultPermissionsAsset.getClassName(),
					_language.get(
						themeDisplay.getLocale(),
						portalDefaultPermissionsAsset.getLabel())));
	}

	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final PortalDefaultPermissionsAssetRegistry
		_portalDefaultPermissionsAssetRegistry;

}