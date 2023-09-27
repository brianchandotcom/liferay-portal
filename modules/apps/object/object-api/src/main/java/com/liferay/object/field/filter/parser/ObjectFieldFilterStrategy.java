/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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