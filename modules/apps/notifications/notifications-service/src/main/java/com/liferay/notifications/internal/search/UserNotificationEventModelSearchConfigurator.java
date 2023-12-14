/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notifications.internal.search;

import com.liferay.notifications.internal.search.spi.model.index.contributor.UserNotificationEventModelIndexerWriterContributor;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(service = ModelSearchConfigurator.class)
public class UserNotificationEventModelSearchConfigurator
	implements ModelSearchConfigurator<UserNotificationEvent> {

	@Override
	public String getClassName() {
		return UserNotificationEvent.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK, Field.GROUP_ID,
			Field.UID
		};
	}

	@Override
	public ModelIndexerWriterContributor<UserNotificationEvent>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor =
			new UserNotificationEventModelIndexerWriterContributor(
				_dynamicQueryBatchIndexingActionableFactory,
				_userNotificationEventLocalService);
	}

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	private ModelIndexerWriterContributor<UserNotificationEvent>
		_modelIndexWriterContributor;

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}