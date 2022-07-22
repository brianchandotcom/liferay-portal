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
import java.util.Map;

/**
 * @author Marco Leo
 */
public class ObjectEntryStatusCheckBoxFDSFilter extends BaseCheckBoxFDSFilter {

	public ObjectEntryStatusCheckBoxFDSFilter(
		Map<String, Object> preloadedData) {

		_preloadedData = preloadedData;
	}

	@Override
	public List<CheckBoxFDSFilterItem> getCheckBoxFDSFilterItems(
		Locale locale) {

		return ListUtil.fromArray(
			new CheckBoxFDSFilterItem(
				WorkflowConstants.LABEL_APPROVED,
				WorkflowConstants.STATUS_APPROVED),
			new CheckBoxFDSFilterItem(
				WorkflowConstants.LABEL_DENIED,
				WorkflowConstants.STATUS_DENIED),
			new CheckBoxFDSFilterItem(
				WorkflowConstants.LABEL_DRAFT, WorkflowConstants.STATUS_DRAFT),
			new CheckBoxFDSFilterItem(
				WorkflowConstants.LABEL_EXPIRED,
				WorkflowConstants.STATUS_EXPIRED),
			new CheckBoxFDSFilterItem(
				WorkflowConstants.LABEL_IN_TRASH,
				WorkflowConstants.STATUS_IN_TRASH),
			new CheckBoxFDSFilterItem(
				WorkflowConstants.LABEL_INACTIVE,
				WorkflowConstants.STATUS_INACTIVE),
			new CheckBoxFDSFilterItem(
				WorkflowConstants.LABEL_INCOMPLETE,
				WorkflowConstants.STATUS_INCOMPLETE),
			new CheckBoxFDSFilterItem(
				WorkflowConstants.LABEL_PENDING,
				WorkflowConstants.STATUS_PENDING),
			new CheckBoxFDSFilterItem(
				WorkflowConstants.LABEL_SCHEDULED,
				WorkflowConstants.STATUS_SCHEDULED));
	}

	@Override
	public String getEntityFieldType() {
		return "collection";
	}

	@Override
	public String getId() {
		return "status";
	}

	@Override
	public String getLabel() {
		return "status";
	}

	@Override
	public Map<String, Object> getPreloadedData() {
		return _preloadedData;
	}

	private final Map<String, Object> _preloadedData;

}