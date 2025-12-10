/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.content.processor;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.StagedModel;

import java.io.Serializable;

/**
 * @author Gergely Mathe
 * @author Máté Thurzó
 */
public interface ExportImportContentProcessor<T extends Serializable> {

	public T replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			T content, boolean exportReferencedContent, boolean escapeContent)
		throws Exception;

	public default String replaceExportContentReferences(
			String content, PortletDataContext portletDataContext)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	public T replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			T content)
		throws Exception;

	public default String replaceImportContentReferences(
			String content, PortletDataContext portletDataContext)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	public void validateContentReferences(long groupId, T content)
		throws PortalException;

}