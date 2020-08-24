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
import com.liferay.portal.kernel.json.JSONUtil;

/**
 * @author Cristina González
 */
public class MockInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<MockObject> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(MockObject mockObject) {
		return InfoItemFieldValues.builder(
		).infoFieldValue(
			new InfoFieldValue<>(
				InfoField.builder(
				).infoFieldType(
					TextInfoFieldType.INSTANCE
				).name(
					"authorProfileImage"
				).build(),
				JSONUtil.put(
					"alt", "Author Name"
				).put(
					"authorId", 12345L
				))
		).build();
	}

}