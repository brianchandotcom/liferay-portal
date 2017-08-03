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

package com.liferay.lcs.notification;

/**
 * @author Matija Petanjek
 */
public enum LCSMessageSeverity {

	DANGER("lcs-message-severity-danger", 3),
	INFO("lcs-message-severity-info", 1),
	WARNING("lcs-message-severity-warning", 2);

	public static LCSMessageSeverity valueOf(int level) {
		for (LCSMessageSeverity lcsMessageSeverity : values()) {
			if (level == lcsMessageSeverity.getLevel()) {
				return lcsMessageSeverity;
			}
		}

		return null;
	}

	public String getLabel() {
		return _label;
	}

	public int getLevel() {
		return _level;
	}

	private LCSMessageSeverity(String label, int level) {
		_label = label;
		_level = level;
	}

	private final String _label;
	private final int _level;

}