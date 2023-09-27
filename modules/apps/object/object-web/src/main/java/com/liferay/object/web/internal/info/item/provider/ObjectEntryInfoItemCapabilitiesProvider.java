/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.info.item.provider;

import com.liferay.info.item.capability.InfoItemCapability;
import com.liferay.info.item.provider.InfoItemCapabilitiesProvider;
import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

/**
 * @author Guilherme Camacho
 */
public class ObjectEntryInfoItemCapabilitiesProvider
	implements InfoItemCapabilitiesProvider<ObjectEntry> {

	public ObjectEntryInfoItemCapabilitiesProvider(
		InfoItemCapability displayPageInfoItemCapability,
		InfoItemCapability editPageInfoItemCapability,
		InfoItemCapability templateInfoItemCapability) {

		_displayPageInfoItemCapability = displayPageInfoItemCapability;
		_editPageInfoItemCapability = editPageInfoItemCapability;
		_templateInfoItemCapability = templateInfoItemCapability;
	}

	@Override
	public List<InfoItemCapability> getInfoItemCapabilities() {
		return ListUtil.fromArray(
			_displayPageInfoItemCapability, _editPageInfoItemCapability,
			_templateInfoItemCapability);
	}

	private final InfoItemCapability _displayPageInfoItemCapability;
	private final InfoItemCapability _editPageInfoItemCapability;
	private final InfoItemCapability _templateInfoItemCapability;

}