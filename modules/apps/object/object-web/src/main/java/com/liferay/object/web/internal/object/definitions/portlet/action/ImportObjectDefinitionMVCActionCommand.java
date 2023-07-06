/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.definitions.portlet.action;

import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.constants.ObjectPortletKeys;
import com.liferay.object.exception.ObjectDefinitionNameException;
import com.liferay.object.exception.ObjectViewColumnFieldNameException;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Gabriel Albuquerque
 */
@Component(
	property = {
		"javax.portlet.name=" + ObjectPortletKeys.OBJECT_DEFINITIONS,
		"mvc.command.name=/object_definitions/import_object_definition"
	},
	service = MVCActionCommand.class
)
public class ImportObjectDefinitionMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			_importObjectDefinition(actionRequest);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(actionResponse);

			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			JSONObject jsonObject = null;

			if (exception instanceof ObjectDefinitionNameException) {
				Class<?> clazz = exception.getClass();

				jsonObject = JSONUtil.put(
					"type",
					"ObjectDefinitionNameException." + clazz.getSimpleName());
			}
			else if (exception instanceof ObjectViewColumnFieldNameException) {
				jsonObject = JSONUtil.put(
					"title",
					_language.get(
						_portal.getHttpServletRequest(actionRequest),
						"the-structure-was-imported-without-a-custom-view"));
			}
			else {
				jsonObject = JSONUtil.put(
					"title",
					_language.get(
						_portal.getHttpServletRequest(actionRequest),
						"the-structure-failed-to-import"));
			}

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}

		hideDefaultSuccessMessage(actionRequest);
	}

	private void _importObjectDefinition(ActionRequest actionRequest)
		throws Exception {

		ObjectDefinitionResource.Builder builder =
			_objectDefinitionResourceFactory.create();

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ObjectDefinitionResource objectDefinitionResource = builder.user(
			themeDisplay.getUser()
		).build();

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		String objectDefinitionJSON = FileUtil.read(
			uploadPortletRequest.getFile("objectDefinitionJSON"));

		JSONObject objectDefinitionJSONObject = _jsonFactory.createJSONObject(
			objectDefinitionJSON);

		objectDefinitionJSONObject.remove(
			"accountEntryRestrictedObjectFieldId");

		ObjectDefinition objectDefinition = ObjectDefinition.toDTO(
			objectDefinitionJSONObject.toString());

		objectDefinition.setActive(false);

		String externalReferenceCode = ParamUtil.getString(
			actionRequest, "externalReferenceCode");

		if (Validator.isNotNull(externalReferenceCode)) {
			objectDefinition.setExternalReferenceCode(externalReferenceCode);
		}

		objectDefinition.setName(ParamUtil.getString(actionRequest, "name"));

		if (FeatureFlagManagerUtil.isEnabled("LPS-148856")) {
			objectDefinition.setObjectFolderExternalReferenceCode(
				ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_UNCATEGORIZED);
		}

		ObjectDefinition putObjectDefinition =
			objectDefinitionResource.putObjectDefinitionByExternalReferenceCode(
				objectDefinition.getExternalReferenceCode(), objectDefinition);

		putObjectDefinition.setPortlet(objectDefinition.getPortlet());

		if (!FeatureFlagManagerUtil.isEnabled("LPS-167253")) {
			objectDefinitionJSONObject.remove("modifiable");
		}

		if (!FeatureFlagManagerUtil.isEnabled("LPS-135430")) {
			putObjectDefinition.setStorageType(StringPool.BLANK);
		}

		objectDefinitionResource.putObjectDefinition(
			putObjectDefinition.getId(), putObjectDefinition);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImportObjectDefinitionMVCActionCommand.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private ObjectDefinitionResource.Factory _objectDefinitionResourceFactory;

	@Reference
	private Portal _portal;

}