/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.StagedModel;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 */
public abstract class BaseStagedModelDataHandler<T extends StagedModel>
	implements StagedModelDataHandler<T> {

	public void exportStagedModel(
			PortletDataContext portletDataContext, Element element,
			T stagedModel)
		throws PortletDataException {

		exportStagedModel(
			portletDataContext, new Element[] {element}, stagedModel);
	}

	public void exportStagedModel(
			PortletDataContext portletDataContext, Element[] elements,
			T stagedModel)
		throws PortletDataException {

		String path = StagedModelPathUtil.getPath(stagedModel);

		if (portletDataContext.isPathProcessed(path)) {
			return;
		}

		try {
			doExportStagedModel(
				portletDataContext, elements, (T)stagedModel.clone());
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public abstract String getClassName();

	public void importStagedModel(
			PortletDataContext portletDataContext, Element element,
			T stagedModel)
		throws PortletDataException {

		String path = StagedModelPathUtil.getPath(stagedModel);

		if (portletDataContext.isPathProcessed(path)) {
			return;
		}

		try {
			doImportStagedModel(portletDataContext, element, stagedModel);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	protected abstract void doExportStagedModel(
			PortletDataContext portletDataContext, Element[] elements,
			T stagedModel)
		throws Exception;

	protected abstract void doImportStagedModel(
			PortletDataContext portletDataContext, Element element,
			T stagedModel)
		throws Exception;

}