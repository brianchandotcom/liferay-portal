/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.helper;

import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;

import javax.servlet.http.HttpServletRequest;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Pablo Molina
 */
@ProviderType
public interface LayoutWarningMessageHelper {

	public String getCollectionWarningMessage(
			CollectionStyledLayoutStructureItem collectionItem,
			HttpServletRequest httpServletRequest)
		throws Exception;

}