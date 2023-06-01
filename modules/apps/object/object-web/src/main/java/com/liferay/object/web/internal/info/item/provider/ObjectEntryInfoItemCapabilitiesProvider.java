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