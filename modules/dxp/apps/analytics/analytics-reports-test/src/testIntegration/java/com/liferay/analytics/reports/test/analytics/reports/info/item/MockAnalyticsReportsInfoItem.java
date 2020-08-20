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

package com.liferay.analytics.reports.test.analytics.reports.info.item;

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.analytics.reports.test.MockObject;

import java.util.Date;
import java.util.Locale;

/**
 * @author Cristina González
 */
public class MockAnalyticsReportsInfoItem
	implements AnalyticsReportsInfoItem<MockObject> {

	@Override
	public String getAuthorName(MockObject mockObject) {
		return null;
	}

	@Override
	public Date getPublishDate(MockObject mockObjec) {
		return null;
	}

	@Override
	public String getTitle(MockObject mockObject, Locale locale) {
		return null;
	}

}