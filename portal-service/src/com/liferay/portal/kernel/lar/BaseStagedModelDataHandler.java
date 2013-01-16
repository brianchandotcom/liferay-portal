/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

	public void exportModelData(
			T stagedModel, PortletDataContext portletDataContext,
			Element... elements)
		throws PortletDataException {

		try {
			doExportModelData(stagedModel, portletDataContext, elements);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public abstract String getClassName();

	public void importModelData(
			Element stagedModelElement, PortletDataContext portletDataContext)
		throws PortletDataException {

		String path = stagedModelElement.attributeValue("path");

		T stagedModel = (T)portletDataContext.getZipEntryAsObject(
			stagedModelElement, path);

		importModelData(stagedModel, path, portletDataContext);
	}

	public void importModelData(
			T stagedModel, String path, PortletDataContext portletDataContext)
		throws PortletDataException {

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		try {
			doImportModelData(stagedModel, path, portletDataContext);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	protected abstract void doExportModelData(
			T stagedModel, PortletDataContext portletDataContext,
			Element... elements)
		throws Exception;

	protected abstract void doImportModelData(
			T stagedModel, String path, PortletDataContext portletDataContext)
		throws Exception;

}