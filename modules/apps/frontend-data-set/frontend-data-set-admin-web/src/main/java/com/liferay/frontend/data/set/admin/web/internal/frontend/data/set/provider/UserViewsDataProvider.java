/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.admin.web.internal.frontend.data.set.provider;

import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.frontend.data.set.SystemFDSEntryRegistry;
import com.liferay.frontend.data.set.admin.web.internal.constants.FDSAdminPortletKeys;
import com.liferay.frontend.data.set.admin.web.internal.frontend.data.set.model.UserViewDataSet;
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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Miguel Arroyo
 */
@Component(
	property = "fds.data.provider.key=userViews",
	service = FDSDataProvider.class
)
public class UserViewsDataProvider implements FDSDataProvider<UserViewDataSet> {

	@Override
	public List<UserViewDataSet> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_checkPermissions(themeDisplay);

		List<Map.Entry<String, String>> sortedDataSetEntries =
			_getSortedDataSetEntries(
				themeDisplay.getCompanyId(), fdsKeywords.getKeywords());

		int start = Math.max(fdsPagination.getStartPosition(), 0);
		int end = Math.min(
			fdsPagination.getEndPosition(), sortedDataSetEntries.size());

		if (start >= sortedDataSetEntries.size()) {
			return Collections.emptyList();
		}

		List<UserViewDataSet> userViewDataSets = new ArrayList<>(end - start);

		for (Map.Entry<String, String> entry :
				sortedDataSetEntries.subList(start, end)) {

			userViewDataSets.add(
				new UserViewDataSet(entry.getValue(), entry.getKey()));
		}

		return userViewDataSets;
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_checkPermissions(themeDisplay);

		return _getSortedDataSetEntries(
			themeDisplay.getCompanyId(), fdsKeywords.getKeywords()).size();
	}

	private void _addDataSetObjectEntries(
			Map<String, String> dataSetEntries, long companyId, String keywords)
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
			Page<ObjectEntry> countPage = objectEntryManager.getObjectEntries(
				companyId, objectDefinition, null, null,
				new DefaultDTOConverterContext(
					false, null, null, null, null,
					LocaleUtil.getMostRelevantLocale(), null, null),
				null, Pagination.of(1, 1), keywords, null);

			if (countPage.getTotalCount() <= 0) {
				return;
			}

			Page<ObjectEntry> objectEntriesPage =
				objectEntryManager.getObjectEntries(
					companyId, objectDefinition, null, null,
					new DefaultDTOConverterContext(
						false, null, null, null, null,
						LocaleUtil.getMostRelevantLocale(), null, null),
					null,
					Pagination.of(
						1, Math.toIntExact(countPage.getTotalCount())),
					keywords, null);

			for (ObjectEntry objectEntry : objectEntriesPage.getItems()) {
				String externalReferenceCode =
					objectEntry.getExternalReferenceCode();

				if (Validator.isNull(externalReferenceCode) ||
					dataSetEntries.containsKey(externalReferenceCode)) {

					continue;
				}

				Map<String, Object> properties = objectEntry.getProperties();

				String label = GetterUtil.getString(properties.get("label"));

				if (Validator.isNull(label)) {
					label = externalReferenceCode;
				}

				dataSetEntries.put(externalReferenceCode, label);
			}
		}
		catch (PortalException portalException) {
			throw portalException;
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}
	}

	private void _addSystemFDSEntries(
		Map<String, String> dataSetEntries, String keywords) {

		Set<String> systemFDSNames = _systemFDSEntryRegistry.getSystemFDSNames();

		if (systemFDSNames == null) {
			return;
		}

		for (String systemFDSName : systemFDSNames) {
			SystemFDSEntry systemFDSEntry =
				_systemFDSEntryRegistry.getSystemFDSEntry(systemFDSName);

			if (systemFDSEntry == null) {
				continue;
			}

			String label = systemFDSEntry.getTitle();

			if (Validator.isNull(label)) {
				label = systemFDSEntry.getName();
			}

			if (Validator.isNotNull(keywords) &&
				!StringUtil.matchesIgnoreCase(label, keywords) &&
				!StringUtil.matchesIgnoreCase(
					systemFDSEntry.getName(), keywords)) {

				continue;
			}

			dataSetEntries.put(systemFDSEntry.getName(), label);
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

	private List<Map.Entry<String, String>> _getSortedDataSetEntries(
			long companyId, String keywords)
		throws PortalException {

		Map<String, String> dataSetEntries = new LinkedHashMap<>();

		_addSystemFDSEntries(dataSetEntries, keywords);
		_addDataSetObjectEntries(dataSetEntries, companyId, keywords);

		List<Map.Entry<String, String>> sortedDataSetEntries = new ArrayList<>(
			dataSetEntries.entrySet());

		Collections.sort(
			sortedDataSetEntries,
			Comparator.comparing(
				Map.Entry::getValue, String::compareToIgnoreCase));

		return sortedDataSetEntries;
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
