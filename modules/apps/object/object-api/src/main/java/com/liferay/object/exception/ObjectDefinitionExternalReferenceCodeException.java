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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Selton Guedes
 */
public class ObjectDefinitionExternalReferenceCodeException
	extends PortalException {

	public static class
		ForbiddenUnmodifiableSystemObjectDefinitionExternalReferenceCode
			extends ObjectDefinitionExternalReferenceCodeException {

		public ForbiddenUnmodifiableSystemObjectDefinitionExternalReferenceCode(
			String externalReferenceCode) {

			super(
				StringBundler.concat(
					"Forbidden unmodifiable system object definition external ",
					"reference code ", externalReferenceCode));
		}

	}

	public static class MustNotStartWithPrefix
		extends ObjectDefinitionExternalReferenceCodeException {

		public MustNotStartWithPrefix() {
			super("The prefix L_ is reserved for Liferay");
		}

	}

	private ObjectDefinitionExternalReferenceCodeException(String msg) {
		super(msg);
	}

}