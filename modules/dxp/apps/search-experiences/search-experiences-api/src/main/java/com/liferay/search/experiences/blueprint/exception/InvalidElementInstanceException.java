/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.blueprint.exception;

/**
 * @author André de Oliveira
 */
public class InvalidElementInstanceException extends RuntimeException {

	public static InvalidElementInstanceException at(int index) {
		InvalidElementInstanceException invalidElementInstanceException =
			new InvalidElementInstanceException(
				"Invalid element instance at: " + index);

		invalidElementInstanceException._index = index;

		return invalidElementInstanceException;
	}

	public int getIndex() {
		return _index;
	}

	private InvalidElementInstanceException(String message) {
		super(message);
	}

	private int _index;

}