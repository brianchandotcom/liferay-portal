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
import com.liferay.blogs.uad.test.BlogsEntryUADEntityTestHelper;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import com.liferay.user.associated.data.aggregator.UADAggregator;
import com.liferay.user.associated.data.display.UADEntityDisplay;
import com.liferay.user.associated.data.test.util.BaseUADEntityDisplayTestCase;

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
public class BlogsEntryUADEntityDisplayTest extends BaseUADEntityDisplayTestCase {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new LiferayIntegrationTestRule();

	@Override
	protected BaseModel<?> addBaseModel(long userId) throws Exception {
		BlogsEntry blogsEntry = _blogsEntryUADEntityTestHelper.addBlogsEntry(userId);

		_blogsEntries.add(blogsEntry);

		return blogsEntry;
	}

	@Override
	protected String getApplicationName() {
		return BlogsUADConstants.APPLICATION_NAME;
	}

	@Override
	protected UADAggregator getUADAggregator() {
		return _uadAggregator;
	}

	@Override
	protected UADEntityDisplay getUADEntityDisplay() {
		return _uadEntityDisplay;
	}

	@Override
	protected String getTypeDescription() {
		return "A blog post";
	}

	@After
	public void tearDown() throws Exception {
		_blogsEntryUADEntityTestHelper.cleanUpDependencies(_blogsEntries);
	}

	@DeleteAfterTestRun
	private final List<BlogsEntry> _blogsEntries = new ArrayList<BlogsEntry>();
	@Inject
	private BlogsEntryUADEntityTestHelper _blogsEntryUADEntityTestHelper;
	@Inject(filter = "model.class.name=" +
	BlogsUADConstants.CLASS_NAME_BLOGS_ENTRY)
	private UADAggregator _uadAggregator;
	@Inject(filter = "model.class.name=" +
	BlogsUADConstants.CLASS_NAME_BLOGS_ENTRY)
	private UADEntityDisplay _uadEntityDisplay;
}