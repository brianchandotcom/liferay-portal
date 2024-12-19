/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.web.internal.display.context;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * @author Joshua Cords
 */
@Component(
	property = {
		"javax.portlet.name=com_liferay_search_experiences_web_internal_blueprint_admin_portlet_SXPBlueprintAdminPortlet",
		"mvc.command.name=/search_experiences/get_class_subtypes"
	},
	service = MVCResourceCommand.class
)
public class GetClassSubtypesMVCResourceCommand implements MVCResourceCommand {

	@Override
	public boolean serveResource(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse) {

		try {
			writeJSONPortletResponse(
				resourceRequest, resourceResponse,
				getClassSubtypesJSONObject(resourceRequest));

			return false;
		}
		catch (RuntimeException runtimeException) {
			_log.error(runtimeException);

			throw runtimeException;
		}
	}

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	protected JSONObject getClassSubtypesJSONObject(
		ResourceRequest resourceRequest) {

		String cmd = ParamUtil.getString(resourceRequest, Constants.CMD);

		if (!cmd.equals("getClassSubtypes")) {
			return null;
		}

		String classType = ParamUtil.getString(resourceRequest, "classType");
		String lookupClassType = classType;


		if (Validator.isNull(classType)) {
			return null;
		}

		if (lookupClassType.equals(DLFileEntry.class.getName())) {
			lookupClassType = DLFileEntryMetadata.class.getName();
		}

		JSONArray classSubtypeJSONArray = JSONFactoryUtil.createJSONArray();

		List<DDMStructure> classStructures =
			_ddmStructureLocalService.getClassStructures(
				ParamUtil.getLong(resourceRequest, "companyId"),
				_portal.getClassNameId(lookupClassType)
			);

		Locale locale = LocaleUtil.fromLanguageId(
			ParamUtil.getString(
				resourceRequest, "languageId"));

		for (DDMStructure ddmStructure : classStructures) {
			try {
				Group group = _groupLocalService.getGroup(ddmStructure.getGroupId());

				JSONObject subtypeJSONObject = JSONUtil.put(
					"classSubtypeExternalReferenceCode",
					ddmStructure.getExternalReferenceCode()
				).put(
					"groupExternalReferenceCode",
					group.getExternalReferenceCode()
				).put(
					"groupLocalizedName", group.getName(locale)
				).put(
					"classSubtypeLocalizedName", ddmStructure.getName(locale)
				).put(
					"classType", classType
				);

				classSubtypeJSONArray.put(subtypeJSONObject);
			} catch (Exception exception) {
				_log.error(exception);
			}
		}

		return JSONUtil.put("classSubtypes", classSubtypeJSONArray);
	}

	protected void writeJSONPortletResponse(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse,
		JSONObject jsonObject) {

		if (jsonObject == null) {
			return;
		}

		try {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse, jsonObject);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetClassSubtypesMVCResourceCommand.class);

}