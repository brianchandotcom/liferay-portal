/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.admin.web.internal.portlet.action;

import com.liferay.frontend.data.set.admin.web.internal.constants.FDSAdminPortletKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManagerProvider;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.io.Serializable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kevin Tan
 */
@Component(
	property = {
		"javax.portlet.name=" + FDSAdminPortletKeys.FDS_ADMIN,
		"mvc.command.name=/frontend_data_set_admin/save_fds_sort"
	},
	service = MVCResourceCommand.class
)
public class SaveFDSSortMVCResourceCommand
	extends BaseTransactionalMVCResourceCommand {

	@Override
	protected void doTransactionalCommand(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String dataSetId = ParamUtil.getString(resourceRequest, "dataSetId");
		String externalReferenceCode = ParamUtil.getString(
			resourceRequest, "externalReferenceCode");
		String fieldName = ParamUtil.getString(resourceRequest, "fieldName");
		String labelI18n = ParamUtil.getString(resourceRequest, "labelI18n");
		String orderType = ParamUtil.getString(resourceRequest, "orderType");
		boolean useAsDefaultSorting = GetterUtil.getBoolean(
			ParamUtil.getString(resourceRequest, "useAsDefaultSorting"));

		ObjectDefinition fdsViewObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				themeDisplay.getCompanyId(), "FDSView");

		if (useAsDefaultSorting) {
			Collection<ObjectEntry> fdsSortObjectEntries =
				_getFDSSortObjectEntries(
					fdsViewObjectDefinition, Long.valueOf(dataSetId));

			for (ObjectEntry fdsSortObjectEntry : fdsSortObjectEntries) {
				Map<String, Object> properties =
					fdsSortObjectEntry.getProperties();

				boolean defaultProperty = GetterUtil.getBoolean(
					properties.get("default"));

				if (defaultProperty) {
					Map<String, Serializable> values = new HashMap<>();

					for (Map.Entry<String, Object> entry :
							properties.entrySet()) {

						values.put(
							entry.getKey(), (Serializable)entry.getValue());
					}

					values.put("default", false);

					_objectEntryService.updateObjectEntry(
						fdsSortObjectEntry.getId(), values,
						new ServiceContext());
				}
			}
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				themeDisplay.getCompanyId(), "FDSSort");

		Map<String, Serializable> labelI18nMap =
			(Map<String, Serializable>)_jsonFactory.looseDeserialize(labelI18n);

		_objectEntryService.addOrUpdateObjectEntry(
			externalReferenceCode, 0, objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"default", useAsDefaultSorting
			).put(
				"fieldName", fieldName
			).put(
				"label_i18n", (Serializable)labelI18nMap
			).put(
				"orderType", orderType
			).put(
				"r_fdsViewFDSSortRelationship_c_fdsViewId", dataSetId
			).build(),
			new ServiceContext());

		Collection<ObjectEntry> updatedFDSSortObjectEntries =
			_getFDSSortObjectEntries(
				fdsViewObjectDefinition, Long.valueOf(dataSetId));

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, updatedFDSSortObjectEntries);
	}

	private Collection<ObjectEntry> _getFDSSortObjectEntries(
			ObjectDefinition fdsViewObjectDefinition, Long objectEntryId)
		throws Exception {

		DTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(
				false, null, null, null, null,
				LocaleUtil.getMostRelevantLocale(), null, null);

		DefaultObjectEntryManager defaultObjectEntryManager =
			DefaultObjectEntryManagerProvider.provide(
				_objectEntryManagerRegistry.getObjectEntryManager(
					fdsViewObjectDefinition.getStorageType()));

		Page<ObjectEntry> relatedObjectEntriesPage =
			defaultObjectEntryManager.getObjectEntryRelatedObjectEntries(
				dtoConverterContext, fdsViewObjectDefinition, objectEntryId,
				"fdsViewFDSSortRelationship",
				Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS));

		return relatedObjectEntriesPage.getItems();
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

	@Reference
	private ObjectEntryService _objectEntryService;

}