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

package com.liferay.multi.factor.authentication.checker.email.otp.service.persistence.impl.constants;

import com.liferay.petra.string.StringBundler;

import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Arthur Chan
 * @generated
 */
public class MFAEmailOTPPersistenceConstants {

	public static final String BUNDLE_SYMBOLIC_NAME =
		"com.liferay.multi.factor.authentication.checker.email.otp.service";

	public static final String ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER =
		"(origin.bundle.symbolic.name=" + BUNDLE_SYMBOLIC_NAME + ")";

	public static final String SERVICE_CONFIGURATION_FILTER =
		"(&" + ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER + "(name=service))";

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			MFAEmailOTPPersistenceConstants.class);

		if (!BUNDLE_SYMBOLIC_NAME.equals(bundle.getSymbolicName())) {
			throw new IllegalStateException(
				StringBundler.concat(
					"Incorrect ", Constants.BUNDLE_SYMBOLICNAME, " for bundle ",
					bundle.getSymbolicName()));
		}
	}

}