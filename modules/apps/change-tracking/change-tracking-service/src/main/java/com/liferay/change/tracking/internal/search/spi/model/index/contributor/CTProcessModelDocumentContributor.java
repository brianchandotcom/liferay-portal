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

package com.liferay.change.tracking.internal.search.spi.model.index.contributor;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "indexer.class.name=com.liferay.change.tracking.model.CTProcess",
	service = ModelDocumentContributor.class
)
public class CTProcessModelDocumentContributor
	implements ModelDocumentContributor<CTProcess> {

	@Override
	public void contribute(Document document, CTProcess ctProcess) {
		document.addDate(Field.CREATE_DATE, ctProcess.getCreateDate());
		document.addKeyword(Field.TYPE, ctProcess.getType());

		User user = _userLocalService.fetchUser(ctProcess.getUserId());

		if (user != null) {
			document.addKeyword(Field.USER_ID, user.getUserId());
			document.addText(Field.USER_NAME, user.getFullName());
		}

		document.addKeyword(Field.USER_ID, ctProcess.getUserId());

		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.fetchBackgroundTask(
				ctProcess.getBackgroundTaskId());

		if (backgroundTask != null) {
			document.addKeyword(Field.STATUS, backgroundTask.getStatus());
		}

		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctProcess.getCtCollectionId());

		if (ctCollection != null) {
			document.addText(Field.DESCRIPTION, ctCollection.getDescription());
			document.addText(Field.NAME, ctCollection.getName());
		}
	}

	@Reference
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private UserLocalService _userLocalService;

}