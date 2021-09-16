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

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.experiences.exception.SXPBlueprintConfigurationsJSONException;
import com.liferay.search.experiences.exception.SXPBlueprintTitleException;
import com.liferay.search.experiences.internal.validator.util.JSONSchemaValidatorUtil;
import com.liferay.search.experiences.problem.Problem;
import com.liferay.search.experiences.problem.Severity;
import com.liferay.search.experiences.validator.SXPBlueprintValidator;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.everit.json.schema.ValidationException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = SXPBlueprintValidator.class)
public class SXPBlueprintValidatorImpl
	extends BaseJSONValidator implements SXPBlueprintValidator {

	@Override
	public void validateConfigurationJSON(String configurationJSON)
		throws SXPBlueprintConfigurationsJSONException {

		_validateConfigurationJSON(configurationJSON);
	}

	@Override
	public void validateSXPBlueprint(
			Map<Locale, String> titleMap, String configurationJSON)
		throws SXPBlueprintConfigurationsJSONException,
			   SXPBlueprintTitleException {

		_validateTitle(titleMap);

		_validateConfigurationJSON(configurationJSON);
	}

	private void _validateConfigurationJSON(String configurationJSON)
		throws SXPBlueprintConfigurationsJSONException {

		if (Validator.isNull(configurationJSON)) {
			return;
		}

		try {
			InputStream configurationJSONSchemaInputStream =
				SXPBlueprintValidatorImpl.class.getResourceAsStream(
					"dependencies/sxpblueprint.schema.json");

			JSONSchemaValidatorUtil.validate(
				configurationJSON, configurationJSONSchemaInputStream);
		}
		catch (ValidationException validationException) {
			List<Problem> problems = new ArrayList<>();

			addJSONValidationProblems(problems, validationException);

			throw new SXPBlueprintConfigurationsJSONException(
				"There were (" + problems.size() + ") validation errors",
				problems);
		}
	}

	private void _validateTitle(Map<Locale, String> titleMap)
		throws SXPBlueprintTitleException {

		if (MapUtil.isEmpty(titleMap)) {
			List<Problem> problems = new ArrayList<>();

			problems.add(
				new Problem.Builder().message(
					"Title empty"
				).languageKey(
					"core.errors.title-empty"
				).severity(
					Severity.ERROR
				).build());

			throw new SXPBlueprintTitleException(
				"Title cannot be empty", problems);
		}
	}

}