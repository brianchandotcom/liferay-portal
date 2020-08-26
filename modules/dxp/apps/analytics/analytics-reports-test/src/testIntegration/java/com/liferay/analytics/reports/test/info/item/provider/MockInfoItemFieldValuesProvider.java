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

package com.liferay.analytics.reports.test.info.item.provider;

import com.liferay.analytics.reports.test.MockObject;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.type.WebImage;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

/**
 * @author Cristina González
 */
public class MockInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<MockObject> {

	public static Builder builder() {
		return new Builder();
	}

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(MockObject mockObject) {
		WebImage webImage = new WebImage(_authorProfileImage);

		webImage.setAlt(_authorName);

		return InfoItemFieldValues.builder(
		).infoFieldValue(
			new InfoFieldValue<>(
				InfoField.builder(
				).infoFieldType(
					TextInfoFieldType.INSTANCE
				).name(
					"authorProfileImage"
				).build(),
				webImage)
		).build();
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

		public MockInfoItemFieldValuesProvider build() {
			return new MockInfoItemFieldValuesProvider(
				_authorName, _authorProfileImage);
		}

		private String _authorName;
		private String _authorProfileImage;

	}

	private MockInfoItemFieldValuesProvider(
		String authorName, String authorProfileImage) {

		if (authorName == null) {
			_authorName = RandomTestUtil.randomString();
		}
		else {
			_authorName = authorName;
		}

		if (authorProfileImage == null) {
			_authorProfileImage = RandomTestUtil.randomString();
		}
		else {
			_authorProfileImage = authorProfileImage;
		}
	}

	private final String _authorName;
	private final String _authorProfileImage;

}