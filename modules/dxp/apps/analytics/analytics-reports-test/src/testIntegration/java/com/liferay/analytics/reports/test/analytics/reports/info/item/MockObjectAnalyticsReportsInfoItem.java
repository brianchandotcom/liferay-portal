/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.test.analytics.reports.info.item;

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.analytics.reports.test.MockObject;
import com.liferay.info.type.WebImage;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author Cristina González
 */
public class MockObjectAnalyticsReportsInfoItem
	implements AnalyticsReportsInfoItem<MockObject> {

	public static Builder builder() {
		return new Builder();
	}

	@Override
	public String getAuthorName(MockObject mockObject) {
		return _authorName;
	}

	@Override
	public WebImage getAuthorWebImage(MockObject mockObject, Locale locale) {
		return new WebImage(_authorProfileImage);
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

	@Override
	public boolean isShow(MockObject mockObject) {
		return _show;
	}

	public static class Builder {

		public Builder authorName(String authorName) {
			_authorName = authorName;

			return this;
		}

		public Builder authorProfileImage(String authorProfileImage) {
			_authorProfileImage = authorProfileImage;

			return this;
		}

		public MockObjectAnalyticsReportsInfoItem build() {
			return new MockObjectAnalyticsReportsInfoItem(
				_authorName, _authorProfileImage, _locales, _publishDate, _show,
				_title);
		}

		public Builder locales(List<Locale> locales) {
			_locales = locales;

			return this;
		}

		public Builder publishDate(Date publishDate) {
			_publishDate = publishDate;

			return this;
		}

		public Builder show(boolean show) {
			_show = show;

			return this;
		}

		public Builder title(String title) {
			_title = title;

			return this;
		}

		private String _authorName;
		private String _authorProfileImage;
		private List<Locale> _locales;
		private Date _publishDate;
		private boolean _show;
		private String _title;

	}

	private MockObjectAnalyticsReportsInfoItem(
		String authorName, String authorProfileImage, List<Locale> locales,
		Date publishDate, boolean show, String title) {

		if (authorName == null) {
			_authorName = StringPool.BLANK;
		}
		else {
			_authorName = authorName;
		}

		if (authorProfileImage == null) {
			_authorProfileImage = StringPool.BLANK;
		}
		else {
			_authorProfileImage = authorProfileImage;
		}

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

		_show = show;
		_title = title;
	}

	private final String _authorName;
	private final String _authorProfileImage;
	private final List<Locale> _locales;
	private final Date _publishDate;
	private final boolean _show;
	private final String _title;

}