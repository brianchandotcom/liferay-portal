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

package com.liferay.multi.factor.authentication.checker.setup.visitor;

import com.liferay.multi.factor.authentication.checker.MFAChecker;
import com.liferay.multi.factor.authentication.checker.composite.MandatoryCompositeMFAChecker;
import com.liferay.multi.factor.authentication.checker.setup.MFACheckerSetup;
import com.liferay.multi.factor.authentication.checker.visitor.BaseMFACheckerVisitor;

import java.util.List;
import java.util.stream.Stream;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Carlos Sierra Andrés
 */
@ProviderType
public class ForceUserSetupMFACheckerVisitor extends BaseMFACheckerVisitor {

	public ForceUserSetupMFACheckerVisitor(long userId) {
		super(
			MFACheckerSetup.class,
			mfaCheckerSetup -> mfaCheckerSetup.isForceUserSetup(userId));
	}

	@Override
	public Boolean visit(
		MandatoryCompositeMFAChecker mandatoryCompositeMFAChecker) {

		List<MFAChecker> mfaCheckers =
			mandatoryCompositeMFAChecker.getMFACheckers();

		Stream<MFAChecker> stream = mfaCheckers.stream();

		return stream.anyMatch(mfaChecker -> mfaChecker.accept(this));
	}

}