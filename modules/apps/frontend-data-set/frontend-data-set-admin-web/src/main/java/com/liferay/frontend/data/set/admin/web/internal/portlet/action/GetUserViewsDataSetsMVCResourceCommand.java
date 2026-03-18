/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.admin.web.internal.portlet.action;

import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.frontend.data.set.SystemFDSEntryRegistry;
import com.liferay.frontend.data.set.admin.web.internal.constants.FDSAdminPortletKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManagerProvider;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import jakarta.portlet.ResourceRequest;
import jakarta.portlet.ResourceResponse;

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
	property = {
		"jakarta.portlet.name=" + FDSAdminPortletKeys.FDS_ADMIN,
		"mvc.command.name=/frontend_data_set_admin/get_user_views_data_sets"
	},
	service = MVCResourceCommand.class
)
public class GetUserViewsDataSetsMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		HttpServletRequest httpServletRequest =
			_portal.getOriginalServletRequest(
				_portal.getHttpServletRequest(resourceRequest));

		int page = ParamUtil.getInteger(httpServletRequest, "page", 1);
		int pageSize = ParamUtil.getInteger(httpServletRequest, "pageSize", 10);
		String search = ParamUtil.getString(httpServletRequest, "search");

		Map<String, String> dataSetEntries = new LinkedHashMap<>();

		_addSystemDataSets(dataSetEntries, search);
		_addCustomDataSets(dataSetEntries, themeDisplay.getCompanyId(), search);

		List<Map.Entry<String, String>> sortedDataSetEntries = new ArrayList<>(
			dataSetEntries.entrySet());

		Collections.sort(
			sortedDataSetEntries,
			Comparator.comparing(
				Map.Entry::getValue, String::compareToIgnoreCase));

		if (page < 1) {
			page = 1;
		}

		if (pageSize < 1) {
			pageSize = 10;
		}

		int totalCount = sortedDataSetEntries.size();
		int start = Math.max((page - 1) * pageSize, 0);

		int end = Math.min(start + pageSize, totalCount);

		JSONArray itemsJSONArray = _jsonFactory.createJSONArray();

		if (start < totalCount) {
			for (Map.Entry<String, String> entry :
					sortedDataSetEntries.subList(start, end)) {

				itemsJSONArray.put(
					JSONUtil.put(
						"label", entry.getValue()
					).put(
						"value", entry.getKey()
					));
			}
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			JSONUtil.put(
				"items", itemsJSONArray
			).put(
				"totalCount", totalCount
			));
	}

	private void _addCustomDataSets(
			Map<String, String> dataSetEntries, long companyId, String search)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					_LIFERAY_DATA_SET_ERC, companyId);

		if (objectDefinition == null) {
			return;
		}

		ObjectEntryManager objectEntryManager =
			DefaultObjectEntryManagerProvider.provide(
				_objectEntryManagerRegistry.getObjectEntryManager(
					objectDefinition.getCompanyId(),
					objectDefinition.getStorageType()));

		Page<ObjectEntry> objectEntriesPage =
			objectEntryManager.getObjectEntries(
				companyId, objectDefinition, search, null,
				new DefaultDTOConverterContext(
					false, null, null, null, null,
					LocaleUtil.getMostRelevantLocale(), null, null),
				null, Pagination.of(1, _MAX_CUSTOM_DATA_SET_ENTRIES), null,
				null);

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

	private void _addSystemDataSets(
		Map<String, String> dataSetEntries, String search) {

		Set<String> systemFDSNames =
			_systemFDSEntryRegistry.getSystemFDSNames();

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

			if (Validator.isNotNull(search) &&
				!StringUtil.matchesIgnoreCase(label, search) &&
				!StringUtil.matchesIgnoreCase(
					systemFDSEntry.getName(), search)) {

				continue;
			}

			dataSetEntries.put(systemFDSEntry.getName(), label);
		}
	}

	private static final String _LIFERAY_DATA_SET_ERC = "L_DATA_SET";

	private static final int _MAX_CUSTOM_DATA_SET_ENTRIES = 200;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private SystemFDSEntryRegistry _systemFDSEntryRegistry;

}