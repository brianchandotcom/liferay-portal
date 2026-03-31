/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.admin.web.internal.frontend.data.set.provider;

import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.frontend.data.set.SystemFDSEntryRegistry;
import com.liferay.frontend.data.set.admin.web.internal.constants.FDSAdminPortletFDSNames;
import com.liferay.frontend.data.set.admin.web.internal.constants.FDSAdminPortletKeys;
import com.liferay.frontend.data.set.admin.web.internal.frontend.data.set.model.FDSSelectionFilterItem;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManagerProvider;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Miguel Arroyo
 */
@Component(
	property = "fds.data.provider.key=" + FDSAdminPortletFDSNames.FDS_ENTRY_FDS_SELECTION_FILTER_ITEMS,
	service = FDSDataProvider.class
)
public class FDSEntryFDSSelectionFilterItemFDSDataProvider
	implements FDSDataProvider<FDSSelectionFilterItem> {

	@Override
	public List<FDSSelectionFilterItem> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_checkPermissions(themeDisplay);

		List<Map.Entry<String, String>> fdsEntries = _getFDSEntries(
			themeDisplay.getCompanyId(), fdsKeywords.getKeywords());

		int start = Math.max(fdsPagination.getStartPosition(), 0);
		int end = Math.min(fdsPagination.getEndPosition(), fdsEntries.size());

		if ((start >= fdsEntries.size()) || (start >= end)) {
			return Collections.emptyList();
		}

		List<FDSSelectionFilterItem> fdsSelectionFilterItems =
			new ArrayList<>();

		for (Map.Entry<String, String> entry : fdsEntries.subList(start, end)) {
			fdsSelectionFilterItems.add(
				new FDSSelectionFilterItem(entry.getKey(), entry.getValue()));
		}

		return fdsSelectionFilterItems;
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_checkPermissions(themeDisplay);

		return _getFDSEntries(
			themeDisplay.getCompanyId(), fdsKeywords.getKeywords()
		).size();
	}

	private void _addCustomFDSEntries(
			Map<String, String> fdsEntries, long companyId, String keywords)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_DATA_SET", companyId);

		if (objectDefinition == null) {
			return;
		}

		ObjectEntryManager objectEntryManager =
			DefaultObjectEntryManagerProvider.provide(
				_objectEntryManagerRegistry.getObjectEntryManager(
					objectDefinition.getCompanyId(),
					objectDefinition.getStorageType()));

		try {
			Page<ObjectEntry> objectEntriesPage =
				objectEntryManager.getObjectEntries(
					companyId, objectDefinition, null, null,
					new DefaultDTOConverterContext(
						false, null, null, null, null,
						LocaleUtil.getMostRelevantLocale(), null, null),
					null, null, keywords, null);

			for (ObjectEntry objectEntry : objectEntriesPage.getItems()) {
				String externalReferenceCode =
					objectEntry.getExternalReferenceCode();

				if (Validator.isNull(externalReferenceCode) ||
					fdsEntries.containsKey(externalReferenceCode)) {

					continue;
				}

				Map<String, Object> properties = objectEntry.getProperties();

				String label = GetterUtil.getString(properties.get("label"));

				if (Validator.isNull(label)) {
					label = externalReferenceCode;
				}

				fdsEntries.put(externalReferenceCode, label);
			}
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}
	}

	private void _addSystemFDSEntries(
		Map<String, String> fdsEntries, String keywords) {

		List<SystemFDSEntry> systemFDSEntries =
			FDSDataProviderUtil.getSystemFDSEntries(
				keywords, _systemFDSEntryRegistry);

		if (ListUtil.isEmpty(systemFDSEntries)) {
			return;
		}

		for (SystemFDSEntry systemFDSEntry : systemFDSEntries) {
			String label = systemFDSEntry.getTitle();

			if (Validator.isNull(label)) {
				label = systemFDSEntry.getName();
			}

			fdsEntries.put(systemFDSEntry.getName(), label);
		}
	}

	private void _checkPermissions(ThemeDisplay themeDisplay)
		throws PortalException {

		if (!PortletPermissionUtil.hasControlPanelAccessPermission(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), _portlet)) {

			throw new PrincipalException.MustHavePermission(
				themeDisplay.getPermissionChecker(), _portlet.getPortletClass(),
				_portlet.getPortletId(), ActionKeys.ACCESS_IN_CONTROL_PANEL);
		}
	}

	private List<Map.Entry<String, String>> _getFDSEntries(
			long companyId, String keywords)
		throws PortalException {

		Map<String, String> fdsEntries = new LinkedHashMap<>();

		_addSystemFDSEntries(fdsEntries, keywords);
		_addCustomFDSEntries(fdsEntries, companyId, keywords);

		List<Map.Entry<String, String>> sortedFDSEntries = new ArrayList<>(
			fdsEntries.entrySet());

		Collections.sort(
			sortedFDSEntries,
			Comparator.comparing(
				Map.Entry::getValue, String::compareToIgnoreCase));

		return sortedFDSEntries;
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

	@Reference(
		target = "(jakarta.portlet.name=" + FDSAdminPortletKeys.FDS_ADMIN + ")"
	)
	private Portlet _portlet;

	@Reference
	private SystemFDSEntryRegistry _systemFDSEntryRegistry;

}