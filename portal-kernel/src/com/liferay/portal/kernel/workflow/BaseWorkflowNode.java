/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.workflow;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Locale;
import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public abstract class BaseWorkflowNode implements WorkflowNode {

	@Override
	public String getLabel(Locale locale) {
		if (MapUtil.isNotEmpty(_labelMap) && (_labelMap.get(locale) != null)) {
			return HtmlUtil.escape(_labelMap.get(locale));
		}

		String name = LanguageUtil.get(locale, _name, null);

		if (name != null) {
			return HtmlUtil.escape(name);
		}

		String label = _labelMap.get(LocaleUtil.getSiteDefault());

		if (label != null) {
			return HtmlUtil.escape(label);
		}

		return _name;
	}

	@Override
	public Map<Locale, String> getLabelMap() {
		return _labelMap;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Type getType() {
		return _type;
	}

	public void setLabelMap(Map<Locale, String> labelMap) {
		_labelMap = labelMap;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setType(Type type) {
		_type = type;
	}

	private Map<Locale, String> _labelMap;
	private String _name;
	private Type _type;

}