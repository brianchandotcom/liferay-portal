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
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Paulo Albuquerque
 * @author Selton Guedes
 */
public class ObjectLayoutBoxException extends PortalException {

	public static class MustBeDefaultStorageType
		extends ObjectLayoutBoxException {

		public MustBeDefaultStorageType(String objectLayoutBoxType) {
			super(
				StringBundler.concat(
					"The ", objectLayoutBoxType,
					" object layout box only can be used in object ",
					"definitions with default storage type"));
		}

	}

	public static class MustBeEnabled extends ObjectLayoutBoxException {

		public MustBeEnabled(String objectLayoutBoxType) {
			super(
				StringBundler.concat(
					"The ", objectLayoutBoxType,
					" object layout box must be enabled on the object ",
					"definition to be used"));
		}

	}

	public static class MustNotHaveObjectLayoutRows
		extends ObjectLayoutBoxException {

		public MustNotHaveObjectLayoutRows(String objectLayoutBoxType) {
			super(
				StringBundler.concat(
					"The ", objectLayoutBoxType,
					" object layout box must not have layout rows"));
		}

	}

	public static class MustOnlyContainOnePerObjectLayout
		extends ObjectLayoutBoxException {

		public MustOnlyContainOnePerObjectLayout(String objectLayoutBoxType) {
			super(
				StringBundler.concat(
					"There can only be one ", objectLayoutBoxType,
					" object layout box per object layout"));
		}

	}

	public static class RequiredType extends ObjectLayoutBoxException {

		public RequiredType() {
			super("No value defined for type");
		}

	}

	private ObjectLayoutBoxException(String msg) {
		super(msg);
	}

}