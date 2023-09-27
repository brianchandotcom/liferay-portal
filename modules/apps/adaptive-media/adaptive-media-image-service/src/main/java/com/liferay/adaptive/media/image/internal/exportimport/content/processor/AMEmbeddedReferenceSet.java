/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.exportimport.content.processor;

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