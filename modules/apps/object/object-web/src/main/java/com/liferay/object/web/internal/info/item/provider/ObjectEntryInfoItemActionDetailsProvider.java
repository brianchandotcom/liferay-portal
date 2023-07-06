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

import com.liferay.info.field.InfoField;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.provider.InfoItemActionDetailsProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Locale;
import java.util.Map;

/**
 * @author Rubén Pulido
 */
public class ObjectEntryInfoItemActionDetailsProvider
	implements InfoItemActionDetailsProvider<ObjectEntry> {

	public ObjectEntryInfoItemActionDetailsProvider(
		InfoItemFormProvider<ObjectEntry> infoItemFormProvider,
		ObjectActionLocalService objectActionLocalService,
		ObjectDefinition objectDefinition) {

		_infoItemFormProvider = infoItemFormProvider;
		_objectActionLocalService = objectActionLocalService;
		_objectDefinition = objectDefinition;
	}

	@Override
	public Map<Locale, String> getInfoItemActionErrorMessageMap(String fieldId)
		throws PortalException {

		try {
			InfoForm infoForm = _infoItemFormProvider.getInfoForm();

			if (infoForm == null) {
				throw new PortalException();
			}

			InfoField<?> infoField = infoForm.getInfoField(fieldId);

			if (infoField == null) {
				throw new PortalException();
			}

			ObjectAction objectAction =
				_objectActionLocalService.getObjectAction(
					_objectDefinition.getObjectDefinitionId(),
					infoField.getName(),
					ObjectActionTriggerConstants.KEY_STANDALONE);

			return objectAction.getErrorMessageMap();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			throw new PortalException(exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryInfoItemActionDetailsProvider.class);

	private final InfoItemFormProvider<ObjectEntry> _infoItemFormProvider;
	private final ObjectActionLocalService _objectActionLocalService;
	private final ObjectDefinition _objectDefinition;

}