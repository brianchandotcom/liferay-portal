/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.admin.web.internal.portlet.action;

import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.frontend.data.set.SystemFDSEntryRegistry;
import com.liferay.frontend.data.set.admin.web.internal.constants.FDSAdminPortletKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.Serializable;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marko Cikos
 */
@Component(
	property = {
		"javax.portlet.name=" + FDSAdminPortletKeys.FDS_ADMIN,
		"mvc.command.name=/frontend_data_set_admin/import_system_data_set"
	},
	service = MVCResourceCommand.class
)
public class ImportSystemDataSetMVCResourceCommand
	extends BaseTransactionalMVCResourceCommand {

	@Override
	protected void doTransactionalCommand(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String name = ParamUtil.getString(resourceRequest, "name");

		SystemFDSEntry systemFDSEntry =
			_systemFDSEntryRegistry.getSystemFDSEntry(name);

		ObjectDefinition dataSetObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				themeDisplay.getCompanyId(), "DataSet");

		ObjectEntry objectEntry = _objectEntryService.addOrUpdateObjectEntry(
			name, 0, dataSetObjectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"additionalAPIURLParameters",
				systemFDSEntry.getAdditionalAPIURLParameters()
			).put(
				"defaultItemsPerPage", systemFDSEntry.getDefaultItemsPerPage()
			).put(
				"description", systemFDSEntry.getDescription()
			).put(
				"label", systemFDSEntry.getTitle()
			).put(
				"listOfItemsPerPage",
				StringUtil.merge(
					systemFDSEntry.getListOfItemsPerPage(), StringPool.COMMA)
			).put(
				"restApplication", systemFDSEntry.getRESTApplication()
			).put(
				"restEndpoint", systemFDSEntry.getRESTEndpoint()
			).put(
				"restSchema", systemFDSEntry.getRESTSchema()
			).build(),
			new ServiceContext());

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, objectEntry);
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

	@Reference
	private ObjectEntryService _objectEntryService;

	@Reference
	private SystemFDSEntryRegistry _systemFDSEntryRegistry;

}