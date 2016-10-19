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

package com.liferay.exportimport.kernel.lar;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Raymond Augé
 */
@ProviderType
public class PortletDataHandlerChoice extends PortletDataHandlerControl {

	public PortletDataHandlerChoice(String namespace, String controlName) {
		this(namespace, controlName, 0, _DEFAULT_CHOICES);
	}

	public PortletDataHandlerChoice(
		String namespace, String controlName, int defaultChoice) {

		this(namespace, controlName, defaultChoice, _DEFAULT_CHOICES);
	}

	public PortletDataHandlerChoice(
		String namespace, String controlName, int defaultChoice,
		String[] choices) {

		super(namespace, controlName);

		_defaultChoice = defaultChoice;
		_choices = choices;
	}

	public String[] getChoices() {
		if ((_choices == null) || (_choices.length < 1)) {
			return _DEFAULT_CHOICES;
		}
		else {
			return _choices;
		}
	}

	public String getDefaultChoice() {
		return getChoices()[getDefaultChoiceIndex()];
	}

	public int getDefaultChoiceIndex() {
		if ((_defaultChoice < 0) || (_defaultChoice >= _choices.length)) {
			return 0;
		}
		else {
			return _defaultChoice;
		}
	}

	private static final String[] _DEFAULT_CHOICES =
		new String[] {"false", "true"};

	private String[] _choices;
	private final int _defaultChoice;

}