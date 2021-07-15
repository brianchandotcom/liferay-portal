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

package com.liferay.object.web.internal.layout.display.page;

import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Guilherme Camacho
 */
@Component(immediate = true, service = LayoutDisplayPageProvider.class)
public class ObjectEntryLayoutDisplayPageProvider
	implements LayoutDisplayPageProvider<ObjectEntry> {

	@Override
	public String getClassName() {
		return ObjectEntry.class.getName();
	}

	@Override
	public LayoutDisplayPageObjectProvider<ObjectEntry>
		getLayoutDisplayPageObjectProvider(
			InfoItemReference infoItemReference) {

		try {
			ObjectEntry objectEntry = _objectEntryLocalService.getObjectEntry(
				infoItemReference.getClassPK());

			if ((objectEntry == null) || objectEntry.isDraft()) {
				return null;
			}

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					objectEntry.getObjectDefinitionId());

			return new ObjectEntryLayoutDisplayPageObjectProvider(
				objectEntry, objectDefinition);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public LayoutDisplayPageObjectProvider<ObjectEntry>
		getLayoutDisplayPageObjectProvider(long groupId, String urlTitle) {

		return getLayoutDisplayPageObjectProvider(
			new InfoItemReference(
				ObjectEntry.class.getName(), Long.valueOf(urlTitle)));
	}

	@Override
	public String getURLSeparator() {
		return "/o/";
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}