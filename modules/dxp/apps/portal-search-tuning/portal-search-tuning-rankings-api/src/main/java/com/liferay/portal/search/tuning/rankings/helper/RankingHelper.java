/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.helper;

import java.util.Collection;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Almir Ferreira
 */
@ProviderType
public interface RankingHelper {

	public String getDocumentId(String documentId);

	public Collection<String> getQueryStrings(
		String queryString, List<String> aliases);

	public List<String> translateDocumentIds(List<String> documentIds);

}