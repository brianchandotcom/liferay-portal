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

package com.liferay.exportimport.content.processor;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.model.StagedModel;

import java.io.Serializable;

/**
 * @author Gergely Mathe
 * @author Mate Thurzo
 */
public interface ExportImportContentProcessor
	<S extends StagedModel, T extends Serializable> {

	public T replaceExportContentReferences(
			PortletDataContext portletDataContext, S stagedModel, T content,
			boolean exportReferencedContent, boolean escapeContent)
		throws Exception;

	public T replaceImportContentReferences(
			PortletDataContext portletDataContext, S stagedModel, T content)
		throws Exception;

	public boolean validateContentReferences(long groupId, T content);

}