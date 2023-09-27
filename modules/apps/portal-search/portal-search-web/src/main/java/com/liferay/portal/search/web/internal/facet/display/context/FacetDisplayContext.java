/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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