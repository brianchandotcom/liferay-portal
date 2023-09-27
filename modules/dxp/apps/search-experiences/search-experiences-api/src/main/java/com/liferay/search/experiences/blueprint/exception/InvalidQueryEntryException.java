/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.blueprint.exception;

/**
 * @author André de Oliveira
 */
public class InvalidQueryEntryException extends RuntimeException {

	public static InvalidQueryEntryException at(int index) {
		InvalidQueryEntryException invalidQueryEntryException =
			new InvalidQueryEntryException("Invalid query entry at: " + index);

		invalidQueryEntryException._index = index;

		return invalidQueryEntryException;
	}

	public int getIndex() {
		return _index;
	}

	private InvalidQueryEntryException(String message) {
		super(message);
	}

	private int _index;

}