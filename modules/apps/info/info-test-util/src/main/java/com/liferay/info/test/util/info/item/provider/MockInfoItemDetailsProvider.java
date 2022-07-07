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