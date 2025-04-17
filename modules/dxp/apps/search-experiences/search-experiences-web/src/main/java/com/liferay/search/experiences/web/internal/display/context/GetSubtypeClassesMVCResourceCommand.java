/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.web.internal.display.context;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;

import java.util.List;
import java.util.Locale;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joshua Cords
 */
@Component(
	enabled = false,
	property = {
		"javax.portlet.name=com_liferay_search_experiences_web_internal_blueprint_admin_portlet_SXPBlueprintAdminPortlet",
		"mvc.command.name=/search_experiences/get_subtype_classes"
	},
	service = MVCResourceCommand.class
)
public class GetSubtypeClassesMVCResourceCommand implements MVCResourceCommand {

	@Override
	public boolean serveResource(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse) {

		try {
			String cmd = ParamUtil.getString(resourceRequest, Constants.CMD);
			JSONObject jsonObject = null;

			if (cmd.equals("getSubtypeClasses")) {
				jsonObject = _getSubtypeClassesJSONObject(resourceRequest);
			}
			else if (cmd.equals("getSubtypeClassesInfo")) {
				jsonObject = _getSubtypeClassesInfoJSONObject(resourceRequest);
			}
			else {
				return false;
			}

			writeJSONPortletResponse(
				resourceRequest, resourceResponse, jsonObject);

			return false;
		}
		catch (RuntimeException runtimeException) {
			_log.error(runtimeException);

			throw runtimeException;
		}
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

	private void _addDDMStructureInfo(
		JSONArray subtypeClassInfoJSONArray, String[] identifierArray,
		Locale locale, ResourceRequest resourceRequest) {

		try {
			Group group = _groupLocalService.getGroupByExternalReferenceCode(
				identifierArray[1], _portal.getCompanyId(resourceRequest));

			DDMStructure ddmStructure =
				_ddmStructureLocalService.getStructureByExternalReferenceCode(
					identifierArray[2], group.getGroupId(),
					_portal.getClassNameId(identifierArray[0]));

			_addSubtypeClassInfo(
				subtypeClassInfoJSONArray, identifierArray[0],
				group.getExternalReferenceCode(), group.getName(locale),
				ddmStructure.getExternalReferenceCode(),
				ddmStructure.getName(locale));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}
	}

	private void _addDLFileEntryTypesInfo(
		JSONArray subtypeClassInfoJSONArray, String[] identifierArray,
		Locale locale, ResourceRequest resourceRequest) {

		try {
			if (identifierArray[1].equals(StringPool.BLANK)) {
				DLFileEntryType basicDocumentDLFileEntryType =
					_dlFileEntryTypeLocalService.
						getBasicDocumentDLFileEntryType();

				_addSubtypeClassInfo(
					subtypeClassInfoJSONArray, identifierArray[0],
					StringPool.BLANK, StringPool.BLANK,
					basicDocumentDLFileEntryType.getExternalReferenceCode(),
					basicDocumentDLFileEntryType.getName(locale));

				return;
			}

			Group group = _groupLocalService.getGroupByExternalReferenceCode(
				identifierArray[1], _portal.getCompanyId(resourceRequest));

			DLFileEntryType dlFileEntryType =
				_dlFileEntryTypeLocalService.
					getDLFileEntryTypeByExternalReferenceCode(
						identifierArray[2], group.getGroupId());

			_addSubtypeClassInfo(
				subtypeClassInfoJSONArray, identifierArray[0],
				group.getExternalReferenceCode(), group.getName(locale),
				dlFileEntryType.getExternalReferenceCode(),
				dlFileEntryType.getName(locale));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}
	}

	private void _addSubtypeClassInfo(
		JSONArray subtypeClassInfoJSONArray, String className,
		String groupExternalReferenceCode, String groupLocalizedName,
		String subtypeClassExternalReferenceCode,
		String subtypeClassLocalizedName) {

		subtypeClassInfoJSONArray.put(
			JSONUtil.put(
				"className", className
			).put(
				"groupExternalReferenceCode", groupExternalReferenceCode
			).put(
				"groupLocalizedName", groupLocalizedName
			).put(
				"subtypeClassExternalReferenceCode",
				subtypeClassExternalReferenceCode
			).put(
				"subtypeClassLocalizedName", subtypeClassLocalizedName
			));
	}

	private JSONObject _getDDMStructuresJSONObject(
		String className, ResourceRequest resourceRequest) {

		JSONArray subtypeClassInfoJSONArray = _jsonFactory.createJSONArray();

		int page = ParamUtil.getInteger(resourceRequest, "page", 1);
		int pageSize = ParamUtil.getInteger(resourceRequest, "pageSize", 10);

		int start = (page - 1) * pageSize;
		int end = page * pageSize;

		List<DDMStructure> ddmStructures = _ddmStructureLocalService.search(
			_portal.getCompanyId(resourceRequest), new long[0],
			_portal.getClassNameId(className), null,
			WorkflowConstants.STATUS_ANY, start, end, null);

		Locale locale = LocaleUtil.fromLanguageId(
			ParamUtil.getString(resourceRequest, "languageId"));

		for (DDMStructure ddmStructure : ddmStructures) {
			try {
				Group group = _groupLocalService.getGroup(
					ddmStructure.getGroupId());

				_addSubtypeClassInfo(
					subtypeClassInfoJSONArray, className,
					group.getExternalReferenceCode(), group.getName(locale),
					ddmStructure.getExternalReferenceCode(),
					ddmStructure.getName(locale));
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception);
				}
			}
		}

		return JSONUtil.put(
			"subtypeClasses", subtypeClassInfoJSONArray
		).put(
			"totalCount",
			_ddmStructureLocalService.searchCount(
				_portal.getCompanyId(resourceRequest), new long[0],
				_portal.getClassNameId(className), null,
				WorkflowConstants.STATUS_ANY)
		);
	}

	private JSONObject _getDLFileEntryTypesJSONObject(
		String className, ResourceRequest resourceRequest) {

		JSONArray subtypeClassInfoJSONArray = _jsonFactory.createJSONArray();

		int page = ParamUtil.getInteger(resourceRequest, "page", 1);
		int pageSize = ParamUtil.getInteger(resourceRequest, "pageSize", 10);

		int start = (page - 1) * pageSize;
		int end = page * pageSize;

		List<DLFileEntryType> dlFileEntryTypes =
			_dlFileEntryTypeLocalService.search(
				_portal.getCompanyId(resourceRequest), new long[0], null, true,
				start, end, null);

		Locale locale = LocaleUtil.fromLanguageId(
			ParamUtil.getString(resourceRequest, "languageId"));

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			try {
				if (dlFileEntryType.getGroupId() == 0) {
					_addSubtypeClassInfo(
						subtypeClassInfoJSONArray, className, StringPool.BLANK,
						StringPool.BLANK,
						dlFileEntryType.getExternalReferenceCode(),
						dlFileEntryType.getName(locale));

					continue;
				}

				Group group = _groupLocalService.getGroup(
					dlFileEntryType.getGroupId());

				_addSubtypeClassInfo(
					subtypeClassInfoJSONArray, className,
					group.getExternalReferenceCode(), group.getName(locale),
					dlFileEntryType.getExternalReferenceCode(),
					dlFileEntryType.getName(locale));
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception);
				}
			}
		}

		return JSONUtil.put(
			"subtypeClasses", subtypeClassInfoJSONArray
		).put(
			"totalCount",
			_dlFileEntryTypeLocalService.searchCount(
				_portal.getCompanyId(resourceRequest), new long[0], null, true)
		);
	}

	private JSONObject _getSubtypeClassesInfoJSONObject(
		ResourceRequest resourceRequest) {

		String[] subtypeClassIdentifiers = ParamUtil.getStringValues(
			resourceRequest, "subTypeIdentifiers");

		if (subtypeClassIdentifiers == null) {
			return null;
		}

		JSONArray subtypeClassesJSONArray = _jsonFactory.createJSONArray();

		Locale locale = LocaleUtil.fromLanguageId(
			ParamUtil.getString(resourceRequest, "languageId"));

		for (String subtypeClassIdentifier : subtypeClassIdentifiers) {
			String[] subtypeClassIdentifierArray = StringUtil.split(
				subtypeClassIdentifier, "&&");

			String className = subtypeClassIdentifierArray[0];

			if (className.equals(DLFileEntry.class.getName())) {
				_addDLFileEntryTypesInfo(
					subtypeClassesJSONArray, subtypeClassIdentifierArray,
					locale, resourceRequest);
			}
			else if (className.equals(JournalArticle.class.getName())) {
				_addDDMStructureInfo(
					subtypeClassesJSONArray, subtypeClassIdentifierArray,
					locale, resourceRequest);
			}
		}

		return JSONUtil.put("subtypeClasses", subtypeClassesJSONArray);
	}

	private JSONObject _getSubtypeClassesJSONObject(
		ResourceRequest resourceRequest) {

		String className = ParamUtil.getString(resourceRequest, "className");

		if (Validator.isNull(className)) {
			return null;
		}

		if (className.equals(DLFileEntry.class.getName())) {
			return _getDLFileEntryTypesJSONObject(className, resourceRequest);
		}
		else if (className.equals(JournalArticle.class.getName())) {
			return _getDDMStructuresJSONObject(className, resourceRequest);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetSubtypeClassesMVCResourceCommand.class);

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}