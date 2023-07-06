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
import com.liferay.info.field.InfoField;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.action.executor.InfoItemActionExecutor;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManagerProvider;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectActionLocalService;
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
		InfoItemFormProvider<ObjectEntry> infoItemFormProvider,
		ObjectActionLocalService objectActionLocalService,
		ObjectDefinition objectDefinition,
		ObjectEntryManagerRegistry objectEntryManagerRegistry) {

		_infoItemFormProvider = infoItemFormProvider;
		_objectActionLocalService = objectActionLocalService;
		_objectDefinition = objectDefinition;
		_objectEntryManagerRegistry = objectEntryManagerRegistry;
	}

	@Override
	public void executeInfoItemAction(
			InfoItemIdentifier infoItemIdentifier, String fieldId)
		throws InfoItemActionExecutionException {

		String errorMessage = null;

		try {
			if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier)) {
				throw new InfoItemActionExecutionException();
			}

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			if ((serviceContext == null) ||
				(serviceContext.getThemeDisplay() == null)) {

				throw new InfoItemActionExecutionException();
			}

			InfoForm infoForm = _infoItemFormProvider.getInfoForm();

			if (infoForm == null) {
				throw new InfoItemActionExecutionException();
			}

			InfoField<?> infoField = infoForm.getInfoField(fieldId);

			if (infoField == null) {
				throw new InfoItemActionExecutionException();
			}

			DefaultObjectEntryManager defaultObjectEntryManager =
				DefaultObjectEntryManagerProvider.provide(
					_objectEntryManagerRegistry.getObjectEntryManager(
						_objectDefinition.getStorageType()));

			ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

			DTOConverterContext dtoConverterContext =
				new DefaultDTOConverterContext(
					null, _objectDefinition.getObjectDefinitionId(),
					themeDisplay.getLocale(), null, themeDisplay.getUser());

			ObjectAction objectAction =
				_objectActionLocalService.getObjectAction(
					_objectDefinition.getObjectDefinitionId(),
					infoField.getName(),
					ObjectActionTriggerConstants.KEY_STANDALONE);

			errorMessage = objectAction.getErrorMessage(
				themeDisplay.getLocale());

			ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
				(ClassPKInfoItemIdentifier)infoItemIdentifier;

			defaultObjectEntryManager.executeObjectAction(
				dtoConverterContext, infoField.getName(), _objectDefinition,
				classPKInfoItemIdentifier.getClassPK());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			if (errorMessage != null) {
				throw new InfoItemActionExecutionException(errorMessage);
			}

			throw new InfoItemActionExecutionException();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryInfoItemActionExecutor.class);

	private final InfoItemFormProvider<ObjectEntry> _infoItemFormProvider;
	private final ObjectActionLocalService _objectActionLocalService;
	private final ObjectDefinition _objectDefinition;
	private final ObjectEntryManagerRegistry _objectEntryManagerRegistry;

}