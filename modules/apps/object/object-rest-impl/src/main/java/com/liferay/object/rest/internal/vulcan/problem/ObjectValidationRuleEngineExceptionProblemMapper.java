/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.vulcan.problem;

import com.liferay.object.exception.ObjectValidationRuleEngineException;
import com.liferay.object.validation.rule.ObjectValidationRuleResult;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.problem.Problem;
import com.liferay.portal.vulcan.problem.ProblemMapper;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Miguel Barcos
 */
@Component(service = ProblemMapper.class)
public class ObjectValidationRuleEngineExceptionProblemMapper
	implements ProblemMapper<ObjectValidationRuleEngineException> {

	@Override
	public Problem getProblem(
		ObjectValidationRuleEngineException
			objectValidationRuleEngineException) {

		List<ObjectValidationRuleResult> objectValidationRuleResults =
			objectValidationRuleEngineException.
				getObjectValidationRuleResults();

		if (ListUtil.isEmpty(objectValidationRuleResults)) {
			return new Problem() {

				@Override
				public String getDetail(Locale locale) {
					return null;
				}

				@Override
				public Status getStatus() {
					return Status.BAD_REQUEST;
				}

				@Override
				public String getTitle(Locale locale) {
					return _language.get(
						locale,
						objectValidationRuleEngineException.getMessageKey(),
						objectValidationRuleEngineException.getMessage());
				}

				@Override
				public String getType() {
					return null;
				}

			};
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

		return new Problem() {

			@Override
			public String getDetail(Locale locale) {
				return jsonArray.toString();
			}

			@Override
			public Status getStatus() {
				return Status.BAD_REQUEST;
			}

			@Override
			public String getTitle(Locale locale) {
				return null;
			}

			@Override
			public String getType() {
				return ObjectValidationRuleEngineException.class.getName();
			}

		};
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}