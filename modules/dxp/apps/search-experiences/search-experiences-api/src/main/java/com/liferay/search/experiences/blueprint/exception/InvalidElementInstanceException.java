/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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