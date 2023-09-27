/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.model;

/**
 * @author Fabio Diego Mastrorilli
 */
public class StepModel {

	public String getId() {
		return _id;
	}

	public String getLabel() {
		return _label;
	}

	public String getState() {
		return _state;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setState(String state) {
		_state = state;
	}

	private String _id;
	private String _label;
	private String _state;

}