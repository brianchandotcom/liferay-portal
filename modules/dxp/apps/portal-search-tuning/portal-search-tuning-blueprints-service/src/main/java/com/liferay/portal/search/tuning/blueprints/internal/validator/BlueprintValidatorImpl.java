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

package com.liferay.portal.search.tuning.blueprints.internal.validator;

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.tuning.blueprints.exception.BlueprintValidationException;
import com.liferay.portal.search.tuning.blueprints.validation.BlueprintValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = BlueprintValidator.class)
public class BlueprintValidatorImpl implements BlueprintValidator {

	@Override
	public void validate(Map<Locale, String> titleMap, String configuration)
		throws BlueprintValidationException {

		List<String> errors = new ArrayList<>();

		if (!_isBlueprintValid(titleMap, configuration, errors)) {
			throw new BlueprintValidationException(errors);
		}
	}

	private boolean _isBlueprintValid(
			Map<Locale, String> titleMap, String configuration,
			List<String> errors)
		throws BlueprintValidationException {

		boolean result = true;

		result &= _isConfigurationValid(configuration, errors);
		result &= _isTitleValid(titleMap, errors);

		return result;
	}

	private boolean _isConfigurationValid(
		String configuration, List<String> errors) {

		configuration = StringUtil.trim(configuration);

		if (Validator.isBlank(configuration)) {
			errors.add("configurationEmpty");

			return false;
		}

		return true;
	}

	private boolean _isTitleValid(
		final Map<Locale, String> titleMap, final List<String> errors) {

		boolean result = true;

		if (MapUtil.isEmpty(titleMap)) {
			errors.add("titleEmpty");
			result = false;
		}

		return result;
	}

}