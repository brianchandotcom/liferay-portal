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

import com.liferay.info.item.capability.InfoItemCapability;
import com.liferay.info.item.provider.InfoItemCapabilitiesProvider;
import com.liferay.info.test.util.model.MockObject;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

/**
 * @author Lourdes Fernández Besada
 */
public class MockInfoItemCapabilitiesProvider
	implements InfoItemCapabilitiesProvider<MockObject> {

	public MockInfoItemCapabilitiesProvider(
		InfoItemCapability... infoItemCapabilities) {

		_infoItemCapabilities = infoItemCapabilities;
	}

	@Override
	public List<InfoItemCapability> getInfoItemCapabilities() {
		return ListUtil.fromArray(_infoItemCapabilities);
	}

	private final InfoItemCapability[] _infoItemCapabilities;

}