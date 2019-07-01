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

package com.liferay.multi.factor.authentication.checker.headless.visitor;

import com.liferay.multi.factor.authentication.checker.MFAChecker;
import com.liferay.multi.factor.authentication.checker.composite.MandatoryCompositeMFAChecker;
import com.liferay.multi.factor.authentication.checker.composite.OptionalCompositeMFAChecker;
import com.liferay.multi.factor.authentication.checker.headless.HeadlessMFAChecker;
import com.liferay.multi.factor.authentication.checker.setup.visitor.IsUserSetupCompleteMFACheckerVisitor;
import com.liferay.multi.factor.authentication.checker.setup.visitor.SupportsSetupMFACheckerVisitor;
import com.liferay.multi.factor.authentication.checker.visitor.MFACheckerVisitor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Carlos Sierra Andrés
 */
@ProviderType
public class VerifyHeadlessMFACheckerVisitor
	implements MFACheckerVisitor<Boolean> {

	public VerifyHeadlessMFACheckerVisitor(
		HttpServletRequest httpServletRequest, long userId) {

		_httpServletRequest = httpServletRequest;
		_userId = userId;

		_isHeadlessVerifiedMFACheckerVisitor =
			new IsHeadlessVerifiedMFACheckerVisitor(
				_httpServletRequest, _userId);
		_isUserSetupCompleteMFACheckerVisitor =
			new IsUserSetupCompleteMFACheckerVisitor(_userId);
	}

	@Override
	public Boolean visit(
		MandatoryCompositeMFAChecker mandatoryCompositeMFAChecker) {

		List<MFAChecker> mfaCheckers =
			mandatoryCompositeMFAChecker.getMFACheckers();

		if (mfaCheckers.isEmpty()) {
			return false;
		}

		for (MFAChecker mfaChecker : mfaCheckers) {
			if (!mfaChecker.accept(_supportsHeadlessMFACheckerVisitor)) {
				return false;
			}

			if (mfaChecker.accept(_supportsSetupMFACheckerVisitor) &&
				!mfaChecker.accept(_isUserSetupCompleteMFACheckerVisitor)) {

				return false;
			}

			if (mfaChecker.accept(_isHeadlessVerifiedMFACheckerVisitor)) {
				continue;
			}

			if (!mfaChecker.accept(this)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public Boolean visit(MFAChecker mfaChecker) {
		if (mfaChecker instanceof HeadlessMFAChecker) {
			HeadlessMFAChecker headlessMFAChecker =
				(HeadlessMFAChecker)mfaChecker;

			if (headlessMFAChecker.isHeadlessVerified(
					_httpServletRequest, _userId)) {

				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean visit(
		OptionalCompositeMFAChecker optionalCompositeMFAChecker) {

		List<MFAChecker> mfaCheckers =
			optionalCompositeMFAChecker.getMFACheckers();

		for (MFAChecker mfaChecker : mfaCheckers) {
			if (!mfaChecker.accept(_supportsHeadlessMFACheckerVisitor)) {
				continue;
			}

			if (mfaChecker.accept(_supportsSetupMFACheckerVisitor) &&
				!mfaChecker.accept(_isUserSetupCompleteMFACheckerVisitor)) {

				continue;
			}

			if (mfaChecker.accept(_isHeadlessVerifiedMFACheckerVisitor)) {
				return true;
			}

			if (mfaChecker.accept(this)) {
				return true;
			}
		}

		return false;
	}

	private static final SupportsHeadlessMFACheckerVisitor
		_supportsHeadlessMFACheckerVisitor =
			new SupportsHeadlessMFACheckerVisitor();
	private static final SupportsSetupMFACheckerVisitor
		_supportsSetupMFACheckerVisitor = new SupportsSetupMFACheckerVisitor();

	private final HttpServletRequest _httpServletRequest;
	private final IsHeadlessVerifiedMFACheckerVisitor
		_isHeadlessVerifiedMFACheckerVisitor;
	private final IsUserSetupCompleteMFACheckerVisitor
		_isUserSetupCompleteMFACheckerVisitor;
	private final long _userId;

}