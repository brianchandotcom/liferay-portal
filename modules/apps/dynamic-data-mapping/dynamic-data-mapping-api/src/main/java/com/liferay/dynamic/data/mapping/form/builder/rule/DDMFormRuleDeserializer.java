/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.builder.rule;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;

import java.util.List;

/**
 * @author Gabriel Albuquerque
 */
public interface DDMFormRuleDeserializer {

	public List<DDMFormRule> deserialize(DDMForm ddmForm, JSONArray jsonArray)
		throws PortalException;

}