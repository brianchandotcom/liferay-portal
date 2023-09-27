/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.util;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.xml.Document;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Marcellus Tavares
 * @author Bruno Basto
 */
@ProviderType
public interface JournalConverter {

	public String getContent(
			DDMStructure ddmStructure, Fields ddmFields, long groupId)
		throws Exception;

	public Fields getDDMFields(DDMStructure ddmStructure, String content)
		throws PortalException;

	public Document getDocument(
			DDMStructure ddmStructure, Fields ddmFields, long groupId)
		throws Exception;

}