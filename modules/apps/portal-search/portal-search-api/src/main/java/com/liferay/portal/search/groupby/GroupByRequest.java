/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.groupby;

import com.liferay.portal.kernel.search.Sort;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Bryan Engler
 * @author Michael C. Han
 */
@ProviderType
public interface GroupByRequest {

	public int getDocsSize();

	public Sort[] getDocsSorts();

	public int getDocsStart();

	public String getField();

	public int getTermsSize();

	public Sort[] getTermsSorts();

	public int getTermsStart();

	public void setDocsSize(int docsSize);

	public void setDocsSorts(Sort... docsSorts);

	public void setDocsStart(int docsStart);

	public void setField(String field);

	public void setTermsSize(int termsSize);

	public void setTermsSorts(Sort... termsSorts);

	public void setTermsStart(int termsStart);

}