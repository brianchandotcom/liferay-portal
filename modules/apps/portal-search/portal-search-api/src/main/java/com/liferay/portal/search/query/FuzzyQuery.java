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

package com.liferay.portal.search.query;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface FuzzyQuery extends Query {

	public String getField();

	public Float getFuzziness();

	public Integer getMaxEdits();

	public Integer getMaxExpansions();

	public Integer getPrefixLength();

	public String getRewrite();

	public Boolean getTranspositions();

	public String getValue();

	public void setFuzziness(Float fuzziness);

	public void setMaxEdits(Integer maxEdits);

	public void setMaxExpansions(Integer maxExpansions);

	public void setPrefixLength(Integer prefixLength);

	public void setRewrite(String rewrite);

	public void setTranspositions(Boolean transpositions);

}