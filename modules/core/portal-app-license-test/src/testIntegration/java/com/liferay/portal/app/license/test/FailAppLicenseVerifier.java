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

package com.liferay.portal.app.license.test;

import com.liferay.portal.app.license.AppLicenseVerifier;

import org.osgi.framework.Bundle;

/**
 * @author Amos Fong
 */
public class FailAppLicenseVerifier implements AppLicenseVerifier {

	public static final Exception EXCEPTION = new Exception();

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #verify(String,
	 *             String, String, String[])}
	 */
	@Deprecated
	@Override
	public void verify(
			Bundle bundle, String productId, String productType,
			String productVersion)
		throws Exception {

		throw EXCEPTION;
	}

	@Override
	public void verify(
			String productId, String productType, String productVersion,
			String... bundleSymbolicNames)
		throws Exception {

		throw EXCEPTION;
	}

}