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

package com.liferay.multi.factor.authentication.checker;

import com.liferay.multi.factor.authentication.checker.visitor.MFACheckerVisitor;

import java.util.Locale;

/**
 * @author Tomas Polesovsky
 */
public interface MFAChecker {

	public default <T> T accept(MFACheckerVisitor<T> mfaCheckerVisitor) {
		return mfaCheckerVisitor.visit(this);
	}

	public default String getLabel(Locale locale) {
		return getName();
	}

	public String getName();

	public boolean isEnabled();

}