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

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Carlos Sierra Andrés
 */
@ProviderType
public abstract class BaseCollectorMFACheckerVisitor
	implements MFACheckerVisitor<List<MFAChecker>> {

	public BaseCollectorMFACheckerVisitor(Predicate<MFAChecker> predicate) {
		_predicate = predicate;
	}

	@Override
	public List<MFAChecker> visit(
		MandatoryCompositeMFAChecker mandatoryCompositeMFAChecker) {

		return _flatMapMFACheckers(
			mandatoryCompositeMFAChecker.getMFACheckers());
	}

	@Override
	public List<MFAChecker> visit(MFAChecker mfaChecker) {
		if (_predicate.test(mfaChecker)) {
			return Collections.singletonList(mfaChecker);
		}

		return Collections.emptyList();
	}

	@Override
	public List<MFAChecker> visit(
		OptionalCompositeMFAChecker optionalMFACheckerMFACheckers) {

		return _flatMapMFACheckers(
			optionalMFACheckerMFACheckers.getMFACheckers());
	}

	private List<MFAChecker> _flatMapMFACheckers(List<MFAChecker> mfaCheckers) {
		Stream<MFAChecker> stream = mfaCheckers.stream();

		return stream.flatMap(
			mfaChecker -> mfaChecker.accept(
				this
			).stream()
		).collect(
			Collectors.toList()
		);
	}

	private final Predicate<MFAChecker> _predicate;

}