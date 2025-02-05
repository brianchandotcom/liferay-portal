/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.action;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.batch.engine.action.ImportTaskPreAction;
import com.liferay.batch.engine.constants.BatchEngineImportTaskConstants;
import com.liferay.batch.engine.context.ImportTaskContext;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.jackson.databind.ser.VulcanPropertyFilter;

import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Vendel Toreki
 * @author Petteri Karttunen
 */
@Component(service = ImportTaskPreAction.class)
public class ItemImportTaskPreAction implements ImportTaskPreAction {

	@Override
	public void run(
			BatchEngineImportTask batchEngineImportTask,
			ImportTaskContext importTaskContext, Object item)
		throws Exception {

		if (!StringUtil.equals(
				batchEngineImportTask.getParameterValue(
					"importCreatorStrategy"),
				BatchEngineImportTaskConstants.
					IMPORT_CREATOR_STRATEGY_KEEP_CREATOR)) {

			return;
		}

		JSONObject jsonObject = _toJSONObject(item);

		if (jsonObject == null) {
			return;
		}

		User user = _getCreatorUser(jsonObject);

		if (user == null) {
			return;
		}

		String name = PrincipalThreadLocal.getName();

		if (GetterUtil.getLong(name) == user.getUserId()) {
			return;
		}

		PrincipalThreadLocal.setName(user.getUserId());

		importTaskContext.setOriginalUserId(name);
	}

	private User _getCreatorUser(JSONObject jsonObject) {
		JSONObject creatorJSONObject = jsonObject.getJSONObject("creator");

		if (creatorJSONObject == null) {
			return null;
		}

		User user = null;

		String externalReferenceCode = creatorJSONObject.getString(
			"externalReferenceCode");
		long userId = creatorJSONObject.getLong("id");

		if (!Validator.isBlank(externalReferenceCode)) {
			user = _userLocalService.fetchUserByExternalReferenceCode(
				externalReferenceCode, CompanyThreadLocal.getCompanyId());
		}

		if ((user == null) && (userId > 0)) {
			user = _userLocalService.fetchUser(userId);
		}

		return user;
	}

	private JSONObject _toJSONObject(Object item) {
		try {
			String json = _toJSON(item);

			if (Validator.isNull(json)) {
				return null;
			}

			return _jsonFactory.createJSONObject(json);
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return null;
	}

	private String _toJSON(Object item)
		throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();

		ObjectWriter objectWriter = objectMapper.writer(
			new SimpleFilterProvider(
			).addFilter(
				"Liferay.Vulcan",
				VulcanPropertyFilter.of(
					Set.of(
						"creator", "creator.externalReferenceCode",
						"creator.id"),
					null)
			));

		return objectWriter.writeValueAsString(item);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ItemImportTaskPreAction.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private UserLocalService _userLocalService;

}