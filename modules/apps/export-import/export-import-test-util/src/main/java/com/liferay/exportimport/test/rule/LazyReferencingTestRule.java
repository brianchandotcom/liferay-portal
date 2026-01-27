/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.test.rule;

import com.liferay.exportimport.test.util.LazyReferencingTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.test.rule.AbstractTestRule;

import org.junit.runner.Description;

/**
 * @author Carlos Correa
 */
public class LazyReferencingTestRule extends AbstractTestRule<Void, Void> {

	public static final LazyReferencingTestRule INSTANCE =
		new LazyReferencingTestRule();

	@Override
	protected void afterClass(Description description, Void previousValue) {
		_restoreLazyReferencingThreadLocal();
	}

	@Override
	protected void afterMethod(
		Description description, Void previousValue, Object target) {

		_restoreLazyReferencingThreadLocal();
	}

	@Override
	protected Void beforeClass(Description description) {
		_setUpLazyReferencingThreadLocal(description);

		return null;
	}

	@Override
	protected Void beforeMethod(Description description, Object target)
		throws Throwable {

		_setUpLazyReferencingThreadLocal(description);

		return null;
	}

	private void _restoreLazyReferencingThreadLocal() {
		if (_safeCloseable != null) {
			_safeCloseable.close();
		}
	}

	private void _setUpLazyReferencingThreadLocal(Description description) {
		LazyReferencing lazyReferencing = description.getAnnotation(
			LazyReferencing.class);

		if (lazyReferencing != null) {
			_safeCloseable =
				LazyReferencingTestUtil.setLazyReferencingWithSafeCloseable(
					lazyReferencing.enabled());
		}
	}

	private SafeCloseable _safeCloseable;

}