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

package com.liferay.multi.factor.authentication.checker.composite;

import com.liferay.multi.factor.authentication.checker.MFAChecker;
import com.liferay.multi.factor.authentication.checker.visitor.MFACheckerVisitor;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Carlos Sierra Andrés
 */
@ProviderType
public class MandatoryCompositeMFAChecker extends BaseCompositeMFAChecker {

	public MandatoryCompositeMFAChecker(List<MFAChecker> mfaCheckers) {
		super(mfaCheckers);
	}

	@Override
	public <T> T accept(MFACheckerVisitor<T> mfaCheckerVisitor) {
		return mfaCheckerVisitor.visit(this);
	}

}