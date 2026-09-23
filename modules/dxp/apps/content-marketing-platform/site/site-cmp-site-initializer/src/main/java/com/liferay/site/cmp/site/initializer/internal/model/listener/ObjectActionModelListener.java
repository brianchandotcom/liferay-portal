/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.model.listener;

import com.liferay.object.constants.ObjectActionConstants;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Leite
 */
@Component(service = ModelListener.class)
public class ObjectActionModelListener extends BaseModelListener<ObjectAction> {

	@Override
	public void onBeforeCreate(ObjectAction objectAction)
		throws ModelListenerException {

		try {
			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinition(
					objectAction.getObjectDefinitionId());

			if (!StringUtil.equals(
					objectDefinition.getExternalReferenceCode(),
					"L_CMP_PROJECT") &&
				!StringUtil.equals(
					objectDefinition.getExternalReferenceCode(),
					"L_CMP_TASK")) {

				return;
			}

			Map<String, Map<String, String>> subscriptionObjectActions =
				ObjectActionConstants.getSubscriptionObjectActions();

			if (!subscriptionObjectActions.containsKey(
					objectAction.getName())) {

				return;
			}

			objectAction.setActive(false);
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}