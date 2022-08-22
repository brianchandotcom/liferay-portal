/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.workflow;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;
import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public class DefaultWorkflowTransition implements WorkflowTransition {

	@Override
	public String getLabel(Locale locale) {
		String label = _labelMap.get(locale);

		if (label != null) {
			return HtmlUtil.escape(label);
		}

		label = _labelMap.get(LocaleUtil.getSiteDefault());

		if (label != null) {
			return HtmlUtil.escape(label);
		}

		Language language = LanguageUtil.getLanguage();

		return HtmlUtil.escape(
			language.get(locale, (_name != null) ? _name : _PROCEED_KEY));
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
	public String getSourceNodeName() {
		return _sourceNodeName;
	}

	@Override
	public String getTargetNodeName() {
		return _targetNodeName;
	}

	public void setLabelMap(Map<Locale, String> labelMap) {
		_labelMap = labelMap;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setSourceNodeName(String sourceNodeName) {
		_sourceNodeName = sourceNodeName;
	}

	public void setTargetNodeName(String targetNodeName) {
		_targetNodeName = targetNodeName;
	}

	private static final String _PROCEED_KEY = "proceed";

	private Map<Locale, String> _labelMap;
	private String _name;
	private String _sourceNodeName;
	private String _targetNodeName;

}