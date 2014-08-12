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

package com.liferay.portlet.documentlibrary.documenttype;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerMap;
import com.liferay.registry.collections.ServiceTrackerMapFactory;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Iván Zaera
 */
public class DocumentTypeHandlerUtil {

	public static DocumentTypeHandler getDocumentTypeHandler(
		DLFileEntry dlFileEntry) {

		DLFileEntryType dlFileEntryType =
				DLFileEntryTypeLocalServiceUtil.fetchFileEntryType(
					dlFileEntry.getFileEntryTypeId());

		if (dlFileEntryType != null) {
			return getDocumentTypeHandler(dlFileEntryType);
		}

		return null;
	}

	public static DocumentTypeHandler getDocumentTypeHandler(
		DLFileEntryType dlFileEntryType) {

		Collection<DocumentTypeHandler> documentTypeHandlers =
			getDocumentTypeHandlers();

		for (DocumentTypeHandler documentTypeHandler : documentTypeHandlers) {
			if (documentTypeHandler.handles(dlFileEntryType)) {
				return documentTypeHandler;
			}
		}

		return null;
	}

	public static DocumentTypeHandler getDocumentTypeHandler(UUID id) {
		return _documentTypeHandlers.getService(id);
	}

	public static Collection<DocumentTypeHandler> getDocumentTypeHandlers() {
		try {
			return RegistryUtil.getRegistry().getServices(
				DocumentTypeHandler.class, null);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	private static final ServiceTrackerMap<UUID, DocumentTypeHandler>
		_documentTypeHandlers =
			ServiceTrackerMapFactory.createObjectServiceTrackerMap(
				DocumentTypeHandler.class, null,
				new ServiceReferenceMapper<UUID>() {
					@Override
					public void map(
						ServiceReference<?> serviceReference,
						ServiceReferenceMapper.Emitter<UUID> emitter) {

						DocumentTypeHandler documentTypeHandler =
							(DocumentTypeHandler)
								RegistryUtil.getRegistry().getService(
									serviceReference);

						emitter.emit(documentTypeHandler.getId());
					}
				});

}