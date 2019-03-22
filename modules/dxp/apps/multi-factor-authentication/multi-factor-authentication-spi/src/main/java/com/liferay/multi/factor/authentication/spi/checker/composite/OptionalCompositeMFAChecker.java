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

package com.liferay.multi.factor.authentication.spi.checker.composite;

import aQute.bnd.annotation.ProviderType;

import com.liferay.multi.factor.authentication.spi.checker.MFAChecker;
import com.liferay.multi.factor.authentication.spi.checker.visitor.MFACheckerVisitor;

import java.util.List;

/**
 * @author Carlos Sierra Andrés
 */
@ProviderType
public class OptionalCompositeMFAChecker extends BaseCompositeMFAChecker {

	public OptionalCompositeMFAChecker(List<MFAChecker> mfaCheckers) {
		super(mfaCheckers);
	}

	@Override
	public <T> T accept(MFACheckerVisitor<T> visitor) {
		return visitor.visit(this);
	}

}