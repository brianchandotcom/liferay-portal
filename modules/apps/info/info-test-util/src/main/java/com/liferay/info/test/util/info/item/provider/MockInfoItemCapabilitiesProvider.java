/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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