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

package com.liferay.blogs.uad.display.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.uad.constants.BlogsUADConstants;
import com.liferay.blogs.uad.test.BlogsEntryUADTestHelper;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.test.util.BaseUADDisplayTestCase;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;

import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@RunWith(Arquillian.class)
public class BlogsEntryUADDisplayTest extends BaseUADDisplayTestCase<BlogsEntry> {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new LiferayIntegrationTestRule();

	@Override
	protected BlogsEntry addBaseModel(long userId) throws Exception {
		BlogsEntry blogsEntry = _blogsEntryUADTestHelper.addBlogsEntry(userId);

		_blogsEntries.add(blogsEntry);

		return blogsEntry;
	}

	@Override
	protected String getApplicationName() {
		return BlogsUADConstants.APPLICATION_NAME;
	}

	@Override
	protected UADDisplay getUADDisplay() {
		return _uadDisplay;
	}

	@After
	public void tearDown() throws Exception {
		_blogsEntryUADTestHelper.cleanUpDependencies(_blogsEntries);
	}

	@DeleteAfterTestRun
	private final List<BlogsEntry> _blogsEntries = new ArrayList<BlogsEntry>();
	@Inject
	private BlogsEntryUADTestHelper _blogsEntryUADTestHelper;
	@Inject(filter = "component.name=*.BlogsEntryUADDisplay")
	private UADDisplay _uadDisplay;
}