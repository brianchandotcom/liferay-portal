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

package com.liferay.portal.search.web.internal.facet.display.context;

import java.util.List;

/**
 * @author Joshua Cords
 */
public interface FacetDisplayContext {

	public List<BucketDisplayContext> getBucketDisplayContexts();

	public long getDisplayStyleGroupId();

	public String getPaginationStartParameterName();

	public String getParameterName();

	public String getParameterValue();

	public List<String> getParameterValues();

	public boolean isNothingSelected();

	public boolean isRenderNothing();

	public void setBucketDisplayContexts(
		List<BucketDisplayContext> bucketDisplayContexts);

	public void setNothingSelected(boolean nothingSelected);

	public void setPaginationStartParameterName(
		String paginationStartParameterName);

	public void setParameterName(String parameterName);

	public void setParameterValue(String paramValue);

	public void setParameterValues(List<String> parameterValues);

	public void setRenderNothing(boolean renderNothing);

}