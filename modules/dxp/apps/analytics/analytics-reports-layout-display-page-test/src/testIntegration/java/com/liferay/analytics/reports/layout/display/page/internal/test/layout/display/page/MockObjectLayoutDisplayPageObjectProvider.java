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