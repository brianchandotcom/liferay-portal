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

package com.liferay.portal.test.rule;

import com.liferay.portal.kernel.test.rule.AbstractTestRule;

import org.junit.runner.Description;

/**
 * @author Preston Crary
 */
public class InjectTestRule
	extends AbstractTestRule<InjectTestBag, InjectTestBag> {

	public static final InjectTestRule INSTANCE = new InjectTestRule();

	@Override
	public void afterClass(Description description, InjectTestBag injectTestBag)
		throws Exception {

		injectTestBag.resetFields();
	}

	@Override
	public void afterMethod(
			Description description, InjectTestBag injectTestBag, Object target)
		throws Exception {

		injectTestBag.resetFields();
	}

	@Override
	public InjectTestBag beforeClass(Description description) throws Exception {
		InjectTestBag injectTestBag = new InjectTestBag(
			description.getTestClass());

		injectTestBag.injectFields();

		return injectTestBag;
	}

	@Override
	public InjectTestBag beforeMethod(Description description, Object target)
		throws Exception {

		InjectTestBag injectTestBag = new InjectTestBag(
			description.getTestClass(), target);

		injectTestBag.injectFields();

		return injectTestBag;
	}

}