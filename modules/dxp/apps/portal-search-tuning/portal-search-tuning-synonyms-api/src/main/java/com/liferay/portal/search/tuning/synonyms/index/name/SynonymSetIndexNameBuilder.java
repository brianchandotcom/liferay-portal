/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.index.name;

/**
 * @author Adam Brandizzi
 */
public interface SynonymSetIndexNameBuilder {

	public SynonymSetIndexName getSynonymSetIndexName(long companyId);

}