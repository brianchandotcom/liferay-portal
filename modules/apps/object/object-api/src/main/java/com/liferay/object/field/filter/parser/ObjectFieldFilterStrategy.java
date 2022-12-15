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

package com.liferay.object.field.filter.parser;

import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public interface ObjectFieldFilterStrategy {

	public FDSFilter getFDSFilter() throws PortalException;

	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems()
		throws PortalException;

	public Map<String, Object> parse() throws PortalException;

	public String toValueSummary() throws PortalException;

	public void validate() throws PortalException;

}