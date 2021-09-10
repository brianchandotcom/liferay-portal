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

package com.liferay.object.web.internal.object.entries.frontend.taglib.clay.data.set.filter;

import com.liferay.frontend.taglib.clay.data.set.filter.BaseCheckBoxClayDataSetFilter;
import com.liferay.frontend.taglib.clay.data.set.filter.CheckBoxClayDataSetFilterItem;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Locale;

/**
 * @author Marco Leo
 */
public class ObjectEntryStatusClayTableDataSetFilter
	extends BaseCheckBoxClayDataSetFilter {

	@Override
	public List<CheckBoxClayDataSetFilterItem>
		getCheckBoxClayDataSetFilterItems(Locale locale) {

		return ListUtil.fromArray(
			new CheckBoxClayDataSetFilterItem(
				WorkflowConstants.getStatusLabel(
					WorkflowConstants.STATUS_APPROVED),
				WorkflowConstants.STATUS_APPROVED),
			new CheckBoxClayDataSetFilterItem(
				WorkflowConstants.getStatusLabel(
					WorkflowConstants.STATUS_DRAFT),
				WorkflowConstants.STATUS_DRAFT),
			new CheckBoxClayDataSetFilterItem(
				WorkflowConstants.getStatusLabel(
					WorkflowConstants.STATUS_PENDING),
				WorkflowConstants.STATUS_PENDING));
	}

	@Override
	public String getId() {
		return "status";
	}

	@Override
	public String getLabel() {
		return "status";
	}

}