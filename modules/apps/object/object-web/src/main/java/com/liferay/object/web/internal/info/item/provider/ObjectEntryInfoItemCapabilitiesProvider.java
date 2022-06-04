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
import com.liferay.layout.page.template.info.item.capability.DisplayPageInfoItemCapability;
import com.liferay.layout.page.template.info.item.capability.EditPageInfoItemCapability;
import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.template.info.item.capability.TemplateInfoItemCapability;

import java.util.List;

/**
 * @author Guilherme Camacho
 */
public class ObjectEntryInfoItemCapabilitiesProvider
	implements InfoItemCapabilitiesProvider<ObjectEntry> {

	public ObjectEntryInfoItemCapabilitiesProvider(
		DisplayPageInfoItemCapability displayPageInfoItemCapability,
		EditPageInfoItemCapability editPageInfoItemCapability,
		TemplateInfoItemCapability templatePageInfoItemCapability) {

		_displayPageInfoItemCapability = displayPageInfoItemCapability;
		_editPageInfoItemCapability = editPageInfoItemCapability;
		_templatePageInfoItemCapability = templatePageInfoItemCapability;
	}

	@Override
	public List<InfoItemCapability> getInfoItemCapabilities() {
		return ListUtil.fromArray(
			_displayPageInfoItemCapability, _editPageInfoItemCapability,
			_templatePageInfoItemCapability);
	}

	private final DisplayPageInfoItemCapability _displayPageInfoItemCapability;
	private final EditPageInfoItemCapability _editPageInfoItemCapability;
	private final TemplateInfoItemCapability _templatePageInfoItemCapability;

}