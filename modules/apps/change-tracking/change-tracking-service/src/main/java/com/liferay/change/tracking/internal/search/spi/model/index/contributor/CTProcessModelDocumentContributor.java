/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		document.addKeyword(Field.COMPANY_ID, ctProcess.getCompanyId());
		document.addDate(Field.CREATE_DATE, ctProcess.getCreateDate());

		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctProcess.getCtCollectionId());

		if (ctCollection != null) {
			document.addText(Field.DESCRIPTION, ctCollection.getDescription());
			document.addText(Field.NAME, ctCollection.getName());
		}

		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.fetchBackgroundTask(
				ctProcess.getBackgroundTaskId());

		if (backgroundTask != null) {
			document.addKeyword(Field.STATUS, backgroundTask.getStatus());
		}

		document.addKeyword(Field.TYPE, ctProcess.getType());

		User user = _userLocalService.fetchUser(ctProcess.getUserId());

		if (user != null) {
			document.addKeyword(Field.USER_ID, user.getUserId());
			document.addText(Field.USER_NAME, user.getFullName());
		}
		else {
			document.addKeyword(Field.USER_ID, ctProcess.getUserId());
		}
	}

	@Reference
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private UserLocalService _userLocalService;

}