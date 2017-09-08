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