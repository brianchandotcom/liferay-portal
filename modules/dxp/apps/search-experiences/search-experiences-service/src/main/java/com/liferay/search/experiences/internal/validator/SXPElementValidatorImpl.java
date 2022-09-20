/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.validator;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.experiences.exception.SXPElementElementDefinitionJSONException;
import com.liferay.search.experiences.exception.SXPElementTitleException;
import com.liferay.search.experiences.internal.validator.util.JSONSchemaValidatorUtil;
import com.liferay.search.experiences.problem.Problem;
import com.liferay.search.experiences.problem.Severity;
import com.liferay.search.experiences.validator.SXPElementValidator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(
	enabled = false, immediate = true, service = SXPElementValidator.class
)
public class SXPElementValidatorImpl implements SXPElementValidator {

	@Override
	public void validate(String elementDefinitionJSON, int type)
		throws SXPElementElementDefinitionJSONException {

		if (Validator.isNull(elementDefinitionJSON)) {
			return;
		}

		// TODO What should the standard be for JSON schema files?

		List<Problem> problems = JSONSchemaValidatorUtil.validate(
			SXPElementValidatorImpl.class, _wrap(elementDefinitionJSON, type),
			"dependencies/sxpelement.schema.json");

		if (ListUtil.isNotEmpty(problems)) {
			throw new SXPElementElementDefinitionJSONException(problems);
		}
	}

	@Override
	public void validate(
			String elementDefinitionJSON, Map<Locale, String> titleMap,
			int type)
		throws SXPElementElementDefinitionJSONException,
			   SXPElementTitleException {

		validate(elementDefinitionJSON, type);

		if (MapUtil.isEmpty(titleMap)) {
			throw new SXPElementTitleException(
				ListUtil.fromArray(
					new Problem.Builder(
					).message(
						"Title is empty"
					).severity(
						Severity.ERROR
					).build()));
		}
	}

	private String _wrap(String elementDefinitionJSON, int type) {
		StringBuilder sb = new StringBuilder();

		// TODO Use a constants class for type

		if (type == 1) {
			sb.append("{\"aggregation_element\": ");
		}
		else if (type == 10) {
			sb.append("{\"query_element\": ");
		}
		else if (type == 15) {
			sb.append("{\"suggester_element\": ");
		}

		sb.append(elementDefinitionJSON);
		sb.append("}");

		return sb.toString();
	}

}