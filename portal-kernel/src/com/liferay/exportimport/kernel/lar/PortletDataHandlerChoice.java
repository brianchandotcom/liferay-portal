/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.lar;

/**
 * @author Raymond Augé
 */
public class PortletDataHandlerChoice extends PortletDataHandlerControl {

	public PortletDataHandlerChoice(String namespace, String name) {
		this(namespace, name, 0, _DEFAULT_CHOICES);
	}

	public PortletDataHandlerChoice(
		String namespace, String name, int defaultChoice) {

		this(namespace, name, defaultChoice, _DEFAULT_CHOICES);
	}

	public PortletDataHandlerChoice(
		String namespace, String name, int defaultChoice, String[] choices) {

		super(namespace, name);

		_defaultChoice = defaultChoice;
		_choices = choices;
	}

	public String[] getChoices() {
		if ((_choices == null) || (_choices.length < 1)) {
			return _DEFAULT_CHOICES;
		}

		return _choices;
	}

	public String getDefaultChoice() {
		return getChoices()[getDefaultChoiceIndex()];
	}

	public int getDefaultChoiceIndex() {
		if ((_defaultChoice < 0) || (_defaultChoice >= _choices.length)) {
			return 0;
		}

		return _defaultChoice;
	}

	private static final String[] _DEFAULT_CHOICES = {"false", "true"};

	private String[] _choices;
	private final int _defaultChoice;

}