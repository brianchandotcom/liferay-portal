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

package com.liferay.frontend.data.set.sample.web.internal.filter;

import com.liferay.frontend.data.set.filter.BaseCheckBoxFrontendDataSetFilter;
import com.liferay.frontend.data.set.filter.CheckBoxFrontendDataSetFilterItem;
import com.liferay.frontend.data.set.filter.FrontendDataSetFilter;
import com.liferay.frontend.data.set.sample.web.internal.constants.FrontendDataSetSampleFrontendDataSetNames;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Javier de Arcos
 */
@Component(
	property = "frontend.data.set.name=" + FrontendDataSetSampleFrontendDataSetNames.HEADLESS_SAMPLE,
	service = FrontendDataSetFilter.class
)
public class StatusCheckBoxFrontendDataSetFilter
	extends BaseCheckBoxFrontendDataSetFilter {

	@Override
	public List<CheckBoxFrontendDataSetFilterItem>
		getCheckBoxFrontendDataSetFilterItems(Locale locale) {

		return ListUtil.fromArray(
			new CheckBoxFrontendDataSetFilterItem(
				WorkflowConstants.getStatusLabel(
					WorkflowConstants.STATUS_APPROVED),
				WorkflowConstants.STATUS_APPROVED),
			new CheckBoxFrontendDataSetFilterItem(
				WorkflowConstants.getStatusLabel(
					WorkflowConstants.STATUS_DRAFT),
				WorkflowConstants.STATUS_DRAFT),
			new CheckBoxFrontendDataSetFilterItem(
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