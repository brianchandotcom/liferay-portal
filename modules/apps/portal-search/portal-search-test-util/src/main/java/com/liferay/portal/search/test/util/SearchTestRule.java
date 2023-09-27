/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util;

import com.liferay.portal.kernel.test.rule.MethodTestRule;
import com.liferay.portal.search.index.IndexStatusManager;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.InjectTestBag;

import org.junit.runner.Description;

/**
 * @author André de Oliveira
 */
public class SearchTestRule extends MethodTestRule<Void> {

	public SearchTestRule() {
		_injectTestBag = _createInjectTestBag();
	}

	@Override
	public void afterMethod(Description description, Void c, Object target)
		throws Throwable {

		SearchFixture.tearDown(indexStatusManager);

		_injectTestBag.resetFields();
	}

	@Override
	public Void beforeMethod(Description description, Object target)
		throws Throwable {

		_injectTestBag.injectFields();

		SearchFixture.setUp(indexStatusManager);

		return null;
	}

	@Inject
	protected IndexStatusManager indexStatusManager;

	private InjectTestBag _createInjectTestBag() {
		try {
			return new InjectTestBag(SearchTestRule.class, this);
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private final InjectTestBag _injectTestBag;

}