/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.struts;

import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(
	property = "path=/cms/update-structure", service = StrutsAction.class
)
public class UpdateStructureStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		try {
			String[] deletedObjectRelationshipERCs = ParamUtil.getStringValues(
				httpServletRequest, "deletedObjectRelationshipERCs");
			String[] deletedRepeatableGroupsERCs = ParamUtil.getStringValues(
				httpServletRequest, "deletedRepeatableGroupsERCs");
			String objectDefinition = ParamUtil.getString(
				httpServletRequest, "objectDefinition");
			JSONArray repeatableGroupObjectDefinitionsJSONArray =
				_jsonFactory.createJSONArray(
					ParamUtil.getString(
						httpServletRequest,
						"repeatableGroupObjectDefinitions"));

			_updateStructure(
				deletedRepeatableGroupsERCs, httpServletRequest,
				objectDefinition, deletedObjectRelationshipERCs,
				repeatableGroupObjectDefinitionsJSONArray);
		}
		catch (Exception exception) {
			jsonObject = _jsonFactory.createJSONObject();

			jsonObject.put(
				"error",
				_language.get(
					httpServletRequest.getLocale(),
					"an-unexpected-error-occurred-while-saving-or-publishing-",
						"the-content-structure"));

			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);
		httpServletResponse.setStatus(HttpServletResponse.SC_OK);

		ServletResponseUtil.write(httpServletResponse, jsonObject.toString());

		return null;
	}

	private ObjectDefinitionResource _getObjectDefinitionResource(User user) {
		ObjectDefinitionResource.Builder builder =
			_objectDefinitionResourceFactory.create();

		builder.user(user);

		return builder.build();
	}

	private List<ObjectDefinition> _getObjectDefinitions(
		JSONArray objectDefinitionsJSONArray) {

		List<ObjectDefinition> objectDefinitions = new ArrayList<>();

		for (int i = 0; i < objectDefinitionsJSONArray.length(); i++) {
			ObjectDefinition objectDefinition = ObjectDefinition.toDTO(
				String.valueOf(objectDefinitionsJSONArray.getJSONObject(i)));

			objectDefinitions.add(objectDefinition);
		}

		return objectDefinitions;
	}

	private void _updateStructure(
			String[] deletedRepeatableGroupsERCs,
			HttpServletRequest httpServletRequest,
			String objectDefinitionString, String[] objectRelationshipERCs,
			JSONArray repeatableGroupObjectDefinitionsJSONArray)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<ObjectDefinition> repeatableGroupObjectDefinitions =
			_getObjectDefinitions(repeatableGroupObjectDefinitionsJSONArray);

		Callable<Void> callable = new UpdateStructureCallable(
			themeDisplay.getCompanyId(), deletedRepeatableGroupsERCs,
			ObjectDefinition.toDTO(objectDefinitionString),
			objectRelationshipERCs, repeatableGroupObjectDefinitions,
			themeDisplay.getUser());

		try {
			TransactionInvokerUtil.invoke(_transactionConfig, callable);
		}
		catch (Throwable throwable) {
			if (throwable instanceof Exception) {
				throw (Exception)throwable;
			}

			throw new Exception(throwable);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpdateStructureStrutsAction.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectDefinitionResource.Factory _objectDefinitionResourceFactory;

	@Reference
	private ObjectDefinitionService _objectDefinitionService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	private class UpdateStructureCallable implements Callable<Void> {

		@Override
		public Void call() throws Exception {
			ObjectDefinitionResource objectDefinitionResource =
				_getObjectDefinitionResource(_user);

			if (ArrayUtil.isNotEmpty(_objectRelationshipERCs)) {
				com.liferay.object.model.ObjectDefinition objectDefinition =
					_objectDefinitionService.
						getObjectDefinitionByExternalReferenceCode(
							_objectDefinition.getExternalReferenceCode(),
							_companyId);

				for (String objectRelationshipERC : _objectRelationshipERCs) {
					ObjectRelationship objectRelationship =
						_objectRelationshipLocalService.
							getObjectRelationshipByExternalReferenceCode(
								objectRelationshipERC, _companyId,
								objectDefinition.getObjectDefinitionId());

					if (!objectRelationship.isEdge()) {
						continue;
					}

					_objectRelationshipLocalService.updateObjectRelationship(
						objectRelationship.getExternalReferenceCode(),
						objectRelationship.getObjectRelationshipId(),
						objectRelationship.getParameterObjectFieldId(),
						objectRelationship.getDeletionType(), false,
						objectRelationship.getLabelMap(), null);
				}
			}

			if (ArrayUtil.isNotEmpty(_deletedRepeatableGroupsERCs)) {
				for (String repeatableGroupERC : _deletedRepeatableGroupsERCs) {
					com.liferay.object.model.ObjectDefinition objectDefinition =
						_objectDefinitionLocalService.
							getObjectDefinitionByExternalReferenceCode(
								repeatableGroupERC, _companyId);

					_objectDefinitionService.deleteObjectDefinition(
						objectDefinition.getObjectDefinitionId());
				}
			}

			if (ListUtil.isNotEmpty(_repeatableGroupObjectDefinitions)) {
				for (ObjectDefinition objectDefinition :
						_repeatableGroupObjectDefinitions) {

					objectDefinitionResource.
						putObjectDefinitionByExternalReferenceCode(
							objectDefinition.getExternalReferenceCode(),
							objectDefinition);
				}
			}

			objectDefinitionResource.putObjectDefinitionByExternalReferenceCode(
				_objectDefinition.getExternalReferenceCode(),
				_objectDefinition);

			return null;
		}

		private UpdateStructureCallable(
			long companyId, String[] deletedRepeatableGroupsERCs,
			ObjectDefinition objectDefinition, String[] objectRelationshipERCs,
			List<ObjectDefinition> repeatableGroupObjectDefinitions,
			User user) {

			_companyId = companyId;
			_deletedRepeatableGroupsERCs = deletedRepeatableGroupsERCs;
			_objectDefinition = objectDefinition;
			_objectRelationshipERCs = objectRelationshipERCs;
			_repeatableGroupObjectDefinitions =
				repeatableGroupObjectDefinitions;
			_user = user;
		}

		private final long _companyId;
		private final String[] _deletedRepeatableGroupsERCs;
		private final ObjectDefinition _objectDefinition;
		private final String[] _objectRelationshipERCs;
		private final List<ObjectDefinition> _repeatableGroupObjectDefinitions;
		private final User _user;

	}

}