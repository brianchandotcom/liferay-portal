/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.search.spi.model.index.contributor;

import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationTemplateLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author Gustavo Lima
 */
public class NotificationTemplateModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<NotificationTemplate> {

	public NotificationTemplateModelIndexerWriterContributor(
		NotificationTemplateLocalService notificationTemplateLocalService) {

		_notificationTemplateLocalService = notificationTemplateLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _notificationTemplateLocalService.
			getIndexableActionableDynamicQuery();
	}

	private final NotificationTemplateLocalService
		_notificationTemplateLocalService;

}