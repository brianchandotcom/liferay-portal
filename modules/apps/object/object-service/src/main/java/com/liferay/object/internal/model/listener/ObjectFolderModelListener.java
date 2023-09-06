/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.model.listener;

import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.object.service.persistence.ObjectFolderPersistence;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = ModelListener.class)
public class ObjectFolderModelListener extends BaseModelListener<ObjectFolder> {

	@Override
	public void onAfterRemove(ObjectFolder objectFolder)
		throws ModelListenerException {

		try {
			ObjectFolder uncategorizedObjectFolder =
				_objectFolderPersistence.findByC_N(
					objectFolder.getCompanyId(),
					ObjectFolderConstants.NAME_UNCATEGORIZED);

			for (ObjectDefinition objectDefinition :
					_objectDefinitionPersistence.findByObjectFolderId(
						objectFolder.getObjectFolderId())) {

				_objectDefinitionLocalService.updateObjectFolderId(
					objectDefinition.getObjectDefinitionId(),
					uncategorizedObjectFolder.getObjectFolderId());
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Override
	public void onAfterUpdate(
			ObjectFolder originalObjectFolder, ObjectFolder objectFolder)
		throws ModelListenerException {

		try {
			for (ObjectDefinition objectDefinition :
					_objectDefinitionPersistence.findByObjectFolderId(
						objectFolder.getObjectFolderId())) {

				Indexer<ObjectDefinition> indexer =
					IndexerRegistryUtil.nullSafeGetIndexer(
						ObjectDefinition.class);

				indexer.reindex(objectDefinition);
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

	@Reference
	private ObjectFolderPersistence _objectFolderPersistence;

}