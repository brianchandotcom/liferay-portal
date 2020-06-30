/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.test.util;

import com.liferay.portal.kernel.test.rule.MethodTestRule;
import com.liferay.portal.search.index.IndexStatusManager;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.callback.InjectTestBag;

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