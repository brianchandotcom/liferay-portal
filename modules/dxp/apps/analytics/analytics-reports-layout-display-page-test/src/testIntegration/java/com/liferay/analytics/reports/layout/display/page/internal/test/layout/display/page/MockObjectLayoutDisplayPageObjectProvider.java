/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.layout.display.page.internal.test.layout.display.page;

import com.liferay.analytics.reports.layout.display.page.internal.test.MockObject;
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