/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.listener;

import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.model.ModelListener;

/**
 * @author Sergio Jiménez del Coso
 */
public interface RelevantObjectEntryModelListener
	extends ModelListener<ObjectEntry> {

	public String getObjectDefinitionExternalReferenceCode();

}