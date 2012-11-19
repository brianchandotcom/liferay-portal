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
public class StagedModelDataHandlerImpl<T extends StagedModel>
	implements StagedModelDataHandler<T> {

	public void export(
			T entity, PortletDataContext portletDataContext,
			Element... elements)
		throws Exception {

		return;
	}

	public void importData(
			Element entityElement, PortletDataContext portletDataContext)
		throws Exception {

		String path = entityElement.attributeValue("path");

		T entity = (T)portletDataContext.getZipEntryAsObject(
			entityElement, path);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		importData(entity, path, portletDataContext);
	}

	public void importData(
			T entity, String path, PortletDataContext portletDataContext)
		throws Exception {

		return;
	}

}