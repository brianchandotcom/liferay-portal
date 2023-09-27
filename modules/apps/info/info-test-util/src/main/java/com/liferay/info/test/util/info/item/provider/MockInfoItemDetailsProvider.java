/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.test.util.info.item.provider;

import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.test.util.model.MockObject;

/**
 * @author Lourdes Fernández Besada
 */
public class MockInfoItemDetailsProvider
	implements InfoItemDetailsProvider<MockObject> {

	@Override
	public InfoItemClassDetails getInfoItemClassDetails() {
		return new InfoItemClassDetails(MockObject.class.getName());
	}

	@Override
	public InfoItemDetails getInfoItemDetails(MockObject mockObject) {
		return new InfoItemDetails(
			getInfoItemClassDetails(),
			new InfoItemReference(
				MockObject.class.getName(), mockObject.getClassPK()));
	}

}