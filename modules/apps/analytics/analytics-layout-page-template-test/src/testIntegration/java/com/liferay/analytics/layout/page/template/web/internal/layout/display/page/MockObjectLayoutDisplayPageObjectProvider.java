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

package com.liferay.analytics.layout.page.template.web.internal.layout.display.page;

import com.liferay.analytics.layout.page.template.web.internal.MockObject;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Locale;

/**
 * @author Cristina González
 */
public class MockObjectLayoutDisplayPageObjectProvider
	implements LayoutDisplayPageObjectProvider<MockObject> {

	public MockObjectLayoutDisplayPageObjectProvider(long classNameId) {
		this(classNameId, 0L);
	}

	public MockObjectLayoutDisplayPageObjectProvider(
		long classNameId, long groupId) {

		_classNameId = classNameId;
		_groupId = groupId;

		_title = RandomTestUtil.randomString();
	}

	@Override
	public long getClassNameId() {
		return _classNameId;
	}

	@Override
	public long getClassPK() {
		return 0;
	}

	@Override
	public long getClassTypeId() {
		return 0;
	}

	@Override
	public String getDescription(Locale locale) {
		return null;
	}

	@Override
	public MockObject getDisplayObject() {
		return new MockObject();
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public String getKeywords(Locale locale) {
		return null;
	}

	@Override
	public String getTitle(Locale locale) {
		return _title;
	}

	@Override
	public String getURLTitle(Locale locale) {
		return null;
	}

	private final long _classNameId;
	private final long _groupId;
	private final String _title;

}