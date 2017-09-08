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

package com.liferay.adaptive.media.blogs.internal.exportimport.content.processor;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class AMEmbeddedReferenceSet {

	public AMEmbeddedReferenceSet(
		PortletDataContext portletDataContext, StagedModel stagedModel,
		Map<String, Long> embeddedReferences) {

		_portletDataContext = portletDataContext;
		_stagedModel = stagedModel;
		_embeddedReferences = embeddedReferences;
	}

	public boolean containsReference(String path) {
		return _embeddedReferences.containsKey(path);
	}

	public long importReference(String path) throws PortletDataException {
		long classPK = _embeddedReferences.get(path);

		StagedModelDataHandlerUtil.importReferenceStagedModel(
			_portletDataContext, _stagedModel, DLFileEntry.class, classPK);

		Map<Long, Long> dlFileEntryIds =
			(Map<Long, Long>)_portletDataContext.getNewPrimaryKeysMap(
				DLFileEntry.class);

		return MapUtil.getLong(dlFileEntryIds, classPK, classPK);
	}

	private final Map<String, Long> _embeddedReferences;
	private final PortletDataContext _portletDataContext;
	private final StagedModel _stagedModel;

}