/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.jaxrs.exception.mapper;

import com.liferay.object.exception.ObjectValidationRuleEngineException;
import com.liferay.object.validation.rule.ObjectValidationRuleResult;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * @author Luis Miguel Barcos
 */
public class ObjectValidationRuleEngineExceptionMapper
	extends BaseExceptionMapper<ObjectValidationRuleEngineException> {

	public ObjectValidationRuleEngineExceptionMapper(
		JSONFactory jsonFactory, Language language) {

		_jsonFactory = jsonFactory;
		_language = language;
	}

	@Override
	protected Problem getProblem(
		ObjectValidationRuleEngineException
			objectValidationRuleEngineException) {

		List<ObjectValidationRuleResult> objectValidationRuleResults =
			objectValidationRuleEngineException.
				getObjectValidationRuleResults();

		if (ListUtil.isEmpty(objectValidationRuleResults)) {
			return new Problem(
				Response.Status.BAD_REQUEST,
				_language.get(
					_acceptLanguage.getPreferredLocale(),
					objectValidationRuleEngineException.getMessageKey(),
					objectValidationRuleEngineException.getMessage()));
		}

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (ObjectValidationRuleResult objectValidationRuleResult :
				objectValidationRuleResults) {

			jsonArray.put(
				JSONUtil.put(
					"errorMessage", objectValidationRuleResult.getErrorMessage()
				).put(
					"objectFieldName",
					objectValidationRuleResult.getObjectFieldName()
				));
		}

		return new Problem(
			jsonArray.toString(), Response.Status.BAD_REQUEST, null,
			ObjectValidationRuleEngineException.class.getName());
	}

	@Context
	private AcceptLanguage _acceptLanguage;

	private final JSONFactory _jsonFactory;
	private final Language _language;

}