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

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 */
public class ObjectFieldRelationshipTypeException extends PortalException {

	public static class MustNotDeleteObjectFieldRelationshipType
		extends ObjectFieldRelationshipTypeException {

		public MustNotDeleteObjectFieldRelationshipType() {
			super(
				"Object field cannot be deleted because it is of " +
					"relationship type.");
		}

	}

	public static class
		MustNotEditDescriptionAndTitleUsingObjectFieldRelationshipType
			extends ObjectFieldRelationshipTypeException {

		public MustNotEditDescriptionAndTitleUsingObjectFieldRelationshipType() {
			super(
				"Description and title object fields cannot have a " +
					"relationship type");
		}

	}

	public static class MustNotEditNameOrDBTypeOfObjectFieldRelationshipType
		extends ObjectFieldRelationshipTypeException {

		public MustNotEditNameOrDBTypeOfObjectFieldRelationshipType() {
			super(
				"Object field relationship name and DB type cannot be changed");
		}

	}

	private ObjectFieldRelationshipTypeException(String msg) {
		super(msg);
	}

}