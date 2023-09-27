/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.exportimport.content.processor;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.xml.Element;

/**
 * @author Adolfo Pérez
 */
public class AMReferenceExporter {

	public AMReferenceExporter(
		PortletDataContext portletDataContext, StagedModel stagedModel,
		boolean exportReferencedContent) {

		_portletDataContext = portletDataContext;
		_stagedModel = stagedModel;
		_exportReferencedContent = exportReferencedContent;
	}

	public void exportReference(FileEntry fileEntry)
		throws PortletDataException {

		if (_exportReferencedContent) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				_portletDataContext, _stagedModel, fileEntry,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}
		else {
			Element element = _portletDataContext.getExportDataElement(
				_stagedModel);

			_portletDataContext.addReferenceElement(
				_stagedModel, element, fileEntry,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
		}
	}

	private final boolean _exportReferencedContent;
	private final PortletDataContext _portletDataContext;
	private final StagedModel _stagedModel;

}