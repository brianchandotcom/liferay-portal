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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author Cristina González
 */
public class MockAnalyticsReportsInfoItem
	implements AnalyticsReportsInfoItem<MockObject> {

	public static Builder builder() {
		return new Builder();
	}

	@Override
	public String getAuthorName(MockObject mockObject) {
		return null;
	}

	@Override
	public List<Locale> getAvailableLocales(MockObject mockObject) {
		return _locales;
	}

	@Override
	public Locale getDefaultLocale(MockObject mockObject) {
		return _locales.get(0);
	}

	@Override
	public Date getPublishDate(MockObject mockObjec) {
		return _publishDate;
	}

	@Override
	public String getTitle(MockObject mockObject, Locale locale) {
		return _title;
	}

	public static class Builder {

		public MockAnalyticsReportsInfoItem build() {
			return new MockAnalyticsReportsInfoItem(
				_locales, _publishDate, _title);
		}

		public Builder locales(List<Locale> locales) {
			_locales = locales;

			return this;
		}

		public Builder publishDate(Date publishDate) {
			_publishDate = publishDate;

			return this;
		}

		public Builder title(String title) {
			_title = title;

			return this;
		}

		private List<Locale> _locales;
		private Date _publishDate;
		private String _title;

	}

	private MockAnalyticsReportsInfoItem(
		List<Locale> locales, Date publishDate, String title) {

		if (ListUtil.isEmpty(locales)) {
			_locales = Collections.singletonList(LocaleUtil.getDefault());
		}
		else {
			_locales = Collections.unmodifiableList(locales);
		}

		if (publishDate == null) {
			_publishDate = new Date();
		}
		else {
			_publishDate = publishDate;
		}

		_title = title;
	}

	private final List<Locale> _locales;
	private final Date _publishDate;
	private final String _title;

}