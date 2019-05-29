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