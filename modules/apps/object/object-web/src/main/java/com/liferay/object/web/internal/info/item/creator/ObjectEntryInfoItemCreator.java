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

package com.liferay.object.web.internal.info.item.creator;

import com.liferay.info.exception.InfoFormException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.creator.InfoItemCreator;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.Serializable;

import java.util.Collection;

/**
 * @author Eudaldo Alonso
 */
public class ObjectEntryInfoItemCreator
	implements InfoItemCreator<ObjectEntry> {

	public ObjectEntryInfoItemCreator(
		ObjectDefinition objectDefinition,
		ObjectEntryService objectEntryService) {

		_objectDefinition = objectDefinition;
		_objectEntryService = objectEntryService;
	}

	@Override
	public ObjectEntry createFromInfoItemFieldValues(
			InfoItemFieldValues infoItemFieldValues)
		throws InfoFormException {

		try {
			Collection<InfoFieldValue<Object>> infoFieldValues =
				infoItemFieldValues.getInfoFieldValues();

			HashMapBuilder.HashMapWrapper<String, Serializable> hashMapWrapper =
				HashMapBuilder.create(infoFieldValues.size());

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

			for (InfoFieldValue<Object> infoFieldValue : infoFieldValues) {
				InfoField infoField = infoFieldValue.getInfoField();

				hashMapWrapper.put(
					infoField.getName(),
					(Serializable)infoFieldValue.getValue(
						themeDisplay.getLocale()));
			}

			return _objectEntryService.addObjectEntry(
				themeDisplay.getScopeGroupId(),
				_objectDefinition.getObjectDefinitionId(),
				hashMapWrapper.build(), serviceContext);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			throw new InfoFormException();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryInfoItemCreator.class);

	private final ObjectDefinition _objectDefinition;
	private final ObjectEntryService _objectEntryService;

}