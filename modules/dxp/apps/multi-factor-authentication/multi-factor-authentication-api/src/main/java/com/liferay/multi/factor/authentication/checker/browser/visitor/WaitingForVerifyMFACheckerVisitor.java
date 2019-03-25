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

package com.liferay.multi.factor.authentication.checker.browser.visitor;

import aQute.bnd.annotation.ProviderType;

import com.liferay.multi.factor.authentication.checker.MFAChecker;
import com.liferay.multi.factor.authentication.checker.browser.BrowserMFAChecker;
import com.liferay.multi.factor.authentication.checker.composite.MandatoryCompositeMFAChecker;
import com.liferay.multi.factor.authentication.checker.composite.OptionalCompositeMFAChecker;
import com.liferay.multi.factor.authentication.checker.setup.visitor.IsUserSetupCompleteMFACheckerVisitor;
import com.liferay.multi.factor.authentication.checker.setup.visitor.SupportsSetupMFACheckerVisitor;
import com.liferay.multi.factor.authentication.checker.visitor.MFACheckerVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Sierra Andrés
 */
@ProviderType
public class WaitingForVerifyMFACheckerVisitor
	implements MFACheckerVisitor<List<BrowserMFAChecker>> {

	public WaitingForVerifyMFACheckerVisitor(
		HttpServletRequest httpServletRequest, long userId) {

		_isBrowserVerifiedVisitor = new IsBrowserVerifiedMFACheckerVisitor(
			httpServletRequest, userId);
		_isUserSetupCompleteVisitor = new IsUserSetupCompleteMFACheckerVisitor(
			userId);
	}

	@Override
	public List<BrowserMFAChecker> visit(
		MandatoryCompositeMFAChecker mandatoryCompositeMFAChecker) {

		List<BrowserMFAChecker> mfaCheckers = new ArrayList<>();

		for (MFAChecker mfaChecker :
				mandatoryCompositeMFAChecker.getMFACheckers()) {

			if (!mfaCheckers.isEmpty()) {
				break;
			}

			if (mfaChecker.accept(_supportsSetupVisitor) &&
				!mfaChecker.accept(_isUserSetupCompleteVisitor)) {

				throw new IllegalStateException(
					"Mandatory step setup must be complete");
			}

			mfaCheckers.addAll(mfaChecker.accept(this));
		}

		return mfaCheckers;
	}

	@Override
	public List<BrowserMFAChecker> visit(MFAChecker mfaChecker) {
		if (!_supportsBrowserVisitor.visit(mfaChecker)) {
			throw new RuntimeException(
				mfaChecker.getClass() + " must implement BrowserMFAChecker");
		}

		if (_isBrowserVerifiedVisitor.visit(mfaChecker)) {
			return Collections.emptyList();
		}

		return Collections.singletonList((BrowserMFAChecker)mfaChecker);
	}

	@Override
	public List<BrowserMFAChecker> visit(
		OptionalCompositeMFAChecker optionalCompositeMFAChecker) {

		List<MFAChecker> mfaCheckers =
			optionalCompositeMFAChecker.getMFACheckers();

		ArrayList<BrowserMFAChecker> availableMFACheckers = new ArrayList<>();

		for (MFAChecker mfaChecker : mfaCheckers) {
			if (mfaChecker.accept(_supportsSetupVisitor) &&
				!mfaChecker.accept(_isUserSetupCompleteVisitor)) {

				continue;
			}

			availableMFACheckers.addAll(mfaChecker.accept(this));
		}

		return availableMFACheckers;
	}

	private static final SupportsBrowserMFACheckerVisitor
		_supportsBrowserVisitor = new SupportsBrowserMFACheckerVisitor();
	private static final SupportsSetupMFACheckerVisitor _supportsSetupVisitor =
		new SupportsSetupMFACheckerVisitor();

	private final IsBrowserVerifiedMFACheckerVisitor _isBrowserVerifiedVisitor;
	private final IsUserSetupCompleteMFACheckerVisitor
		_isUserSetupCompleteVisitor;

}