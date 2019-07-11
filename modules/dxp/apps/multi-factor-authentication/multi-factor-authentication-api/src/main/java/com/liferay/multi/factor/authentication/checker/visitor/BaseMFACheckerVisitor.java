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

package com.liferay.multi.factor.authentication.checker.visitor;

import com.liferay.multi.factor.authentication.checker.MFAChecker;
import com.liferay.multi.factor.authentication.checker.composite.MandatoryCompositeMFAChecker;
import com.liferay.multi.factor.authentication.checker.composite.OptionalCompositeMFAChecker;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Carlos Sierra Andrés
 */
@ProviderType
public abstract class BaseMFACheckerVisitor
	implements MFACheckerVisitor<Boolean> {

	public BaseMFACheckerVisitor(Predicate<MFAChecker> predicate) {
		_predicate = predicate;
	}

	public <T> BaseMFACheckerVisitor(Class<T> clazz, Predicate<T> predicate) {
		this(
			mfaChecker -> {
				if (clazz.isInstance(mfaChecker)) {
					return predicate.test((T)mfaChecker);
				}

				return false;
			});
	}

	@Override
	public Boolean visit(MandatoryCompositeMFAChecker mandatoryMFAChecker) {
		List<MFAChecker> mfaCheckers = mandatoryMFAChecker.getMFACheckers();

		Stream<MFAChecker> stream = mfaCheckers.stream();

		return stream.allMatch(mfaChecker -> mfaChecker.accept(this));
	}

	@Override
	public Boolean visit(MFAChecker mfaChecker) {
		return _predicate.test(mfaChecker);
	}

	@Override
	public Boolean visit(OptionalCompositeMFAChecker optionalMFAChecker) {
		List<MFAChecker> mfaCheckers = optionalMFAChecker.getMFACheckers();

		Stream<MFAChecker> stream = mfaCheckers.stream();

		return stream.anyMatch(mfaChecker -> mfaChecker.accept(this));
	}

	private final Predicate<MFAChecker> _predicate;

}