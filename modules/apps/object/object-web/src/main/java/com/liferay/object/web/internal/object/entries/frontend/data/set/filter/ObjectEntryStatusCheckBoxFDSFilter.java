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

package com.liferay.object.web.internal.object.entries.frontend.data.set.filter;

import com.liferay.frontend.data.set.filter.BaseCheckBoxFDSFilter;
import com.liferay.frontend.data.set.filter.CheckBoxFDSFilterItem;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Locale;

/**
 * @author Marco Leo
 */
public class ObjectEntryStatusCheckBoxFDSFilter extends BaseCheckBoxFDSFilter {

	@Override
	public List<CheckBoxFDSFilterItem> getCheckBoxFDSFilterItems(
		Locale locale) {

		return ListUtil.fromArray(
			new CheckBoxFDSFilterItem(
				WorkflowConstants.getStatusLabel(
					WorkflowConstants.STATUS_APPROVED),
				WorkflowConstants.STATUS_APPROVED),
			new CheckBoxFDSFilterItem(
				WorkflowConstants.getStatusLabel(
					WorkflowConstants.STATUS_DRAFT),
				WorkflowConstants.STATUS_DRAFT),
			new CheckBoxFDSFilterItem(
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