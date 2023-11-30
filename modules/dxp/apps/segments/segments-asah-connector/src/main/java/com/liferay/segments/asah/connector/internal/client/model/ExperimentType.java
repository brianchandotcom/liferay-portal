/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.client.model;

import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Marcellus Tavares
 * @author Sarai Díaz
 * @author David Arques
 */
public enum ExperimentType {

	AB("ab"), MAB("mab");

	public static ExperimentType parse(String type) {
		for (ExperimentType experimentType : values()) {
			if (StringUtil.equalsIgnoreCase(type, experimentType.name())) {
				return experimentType;
			}
		}

		return null;
	}

	public String getLabel() {
		return _label;
	}

	private ExperimentType(String label) {
		_label = label;
	}

	private final String _label;

}