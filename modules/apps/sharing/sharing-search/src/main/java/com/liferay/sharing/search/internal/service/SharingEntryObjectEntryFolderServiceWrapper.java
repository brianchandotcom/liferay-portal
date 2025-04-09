/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.search.internal.service;

import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectEntryFolderLocalServiceWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.service.SharingEntryLocalService;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mikel Lorza
 */
@Component(service = ServiceWrapper.class)
public class SharingEntryObjectEntryFolderServiceWrapper
	extends ObjectEntryFolderLocalServiceWrapper {

	@Override
	public ObjectEntryFolder updateObjectEntryFolder(
			long userId, long objectEntryFolderId,
			long parentObjectEntryFolderId, Map<Locale, String> labelMap,
			String name)
		throws PortalException {

		ObjectEntryFolder objectEntryFolder = super.updateObjectEntryFolder(
			userId, objectEntryFolderId, parentObjectEntryFolderId, labelMap,
			name);

		return _reindexSharingEntries(objectEntryFolder);
	}

	@Override
	public ObjectEntryFolder updateObjectEntryFolder(
		ObjectEntryFolder objectEntryFolder) {

		return _reindexSharingEntries(
			super.updateObjectEntryFolder(objectEntryFolder));
	}

	private ObjectEntryFolder _reindexSharingEntries(
		ObjectEntryFolder objectEntryFolder) {

		ClassName className = _classNameLocalService.fetchClassName(
			ObjectEntryFolder.class.getName());

		if (className == null) {
			return objectEntryFolder;
		}

		Indexer<Object> indexer = _indexerRegistry.getIndexer(
			className.getValue());

		if (indexer == null) {
			return objectEntryFolder;
		}

		List<SharingEntry> sharingEntries =
			_sharingEntryLocalService.getSharingEntries(
				className.getClassNameId(),
				objectEntryFolder.getObjectEntryFolderId());

		sharingEntries.forEach(
			sharingEntry -> {
				try {
					indexer.reindex(sharingEntry);
				}
				catch (Exception exception) {
					if (_log.isDebugEnabled()) {
						_log.debug(exception);
					}
				}
			});

		return objectEntryFolder;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SharingEntryObjectEntryFolderServiceWrapper.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

}