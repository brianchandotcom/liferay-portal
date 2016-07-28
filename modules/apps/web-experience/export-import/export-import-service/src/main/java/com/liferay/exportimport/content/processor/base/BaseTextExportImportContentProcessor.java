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

package com.liferay.exportimport.content.processor.base;

import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Gergely Mathe
 * @author Mate Thurzo
 */
public abstract class BaseTextExportImportContentProcessor
	<S extends StagedModel> implements ExportImportContentProcessor<S, String> {

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext, S stagedModel,
			String content, boolean exportReferencedContent,
			boolean escapeContent)
		throws Exception {

		if (escapeContent) {
			content = StringUtil.replace(
				content, StringPool.AMPERSAND_ENCODED, StringPool.AMPERSAND);
		}

		return content;
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, S stagedModel,
			String content)
		throws Exception {

		return content;
	}

	@Override
	public boolean validateContentReferences(long groupId, String content) {
		return true;
	}

}