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

package com.liferay.object.web.internal.info.item.action;

import com.liferay.info.exception.InfoItemActionExecutionException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.action.executor.InfoItemActionExecutor;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManagerProvider;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

/**
 * @author Rubén Pulido
 */
public class ObjectEntryInfoItemActionExecutor
	implements InfoItemActionExecutor<ObjectEntry> {

	public ObjectEntryInfoItemActionExecutor(
		ObjectDefinition objectDefinition,
		ObjectEntryManagerRegistry objectEntryManagerRegistry) {

		_objectDefinition = objectDefinition;
		_objectEntryManagerRegistry = objectEntryManagerRegistry;
	}

	@Override
	public void executeInfoItemAction(
			InfoItemIdentifier infoItemIdentifier, String fieldId)
		throws InfoItemActionExecutionException {

		try {
			if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier)) {
				throw new InfoItemActionExecutionException();
			}

			DefaultObjectEntryManager defaultObjectEntryManager =
				DefaultObjectEntryManagerProvider.provide(
					_objectEntryManagerRegistry.getObjectEntryManager(
						_objectDefinition.getStorageType()));

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

			DTOConverterContext dtoConverterContext =
				new DefaultDTOConverterContext(
					null, _objectDefinition.getObjectDefinitionId(),
					themeDisplay.getLocale(), null, themeDisplay.getUser());

			String objectActionName = fieldId;

			String objectActionPrefix =
				ObjectAction.class.getSimpleName() + StringPool.UNDERLINE;

			if (objectActionName.startsWith(objectActionPrefix)) {
				objectActionName = objectActionName.substring(
					objectActionPrefix.length());
			}

			ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
				(ClassPKInfoItemIdentifier)infoItemIdentifier;

			defaultObjectEntryManager.executeObjectAction(
				dtoConverterContext, objectActionName, _objectDefinition,
				classPKInfoItemIdentifier.getClassPK());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			throw new InfoItemActionExecutionException();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryInfoItemActionExecutor.class);

	private final ObjectDefinition _objectDefinition;
	private final ObjectEntryManagerRegistry _objectEntryManagerRegistry;

}