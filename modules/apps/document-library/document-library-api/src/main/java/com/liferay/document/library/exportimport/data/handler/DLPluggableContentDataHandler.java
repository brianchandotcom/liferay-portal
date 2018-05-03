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

package com.liferay.document.library.exportimport.data.handler;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.xml.Element;

/**
 * @author Adolfo Pérez
 */
public interface DLPluggableContentDataHandler<T extends StagedModel> {

	public void exportContent(
			PortletDataContext portletDataContext, Element element,
			T stagedModel)
		throws Exception;

	public void importContent(
			PortletDataContext portletDataContext, Element element,
			T stagedModel, T importedModel)
		throws Exception;

}