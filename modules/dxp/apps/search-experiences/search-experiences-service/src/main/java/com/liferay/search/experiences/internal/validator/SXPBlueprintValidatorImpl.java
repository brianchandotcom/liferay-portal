/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.validator;

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.search.experiences.exception.SXPBlueprintTitleException;
import com.liferay.search.experiences.validator.SXPBlueprintValidator;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(enabled = false, service = SXPBlueprintValidator.class)
public class SXPBlueprintValidatorImpl implements SXPBlueprintValidator {

	@Override
	public void validate(Map<Locale, String> titleMap)
		throws SXPBlueprintTitleException {

		if (MapUtil.isEmpty(titleMap)) {
			throw new SXPBlueprintTitleException("Title is empty");
		}
	}

}