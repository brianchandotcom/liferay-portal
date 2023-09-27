/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luan Maoski
 */
@Component(
	property = "indexer.class.name=com.liferay.message.boards.model.MBThread",
	service = ModelPreFilterContributor.class
)
public class MBThreadModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		addWorkflowStatusFilter(
			booleanFilter, modelSearchSettings, searchContext);

		boolean discussion = GetterUtil.getBoolean(
			searchContext.getAttribute("discussion"));

		booleanFilter.addRequiredTerm("discussion", discussion);

		long endDate = GetterUtil.getLong(
			searchContext.getAttribute("endDate"));
		long startDate = GetterUtil.getLong(
			searchContext.getAttribute("startDate"));

		if ((endDate > 0) && (startDate > 0)) {
			booleanFilter.addRangeTerm("lastPostDate", startDate, endDate);
		}

		long participantUserId = GetterUtil.getLong(
			searchContext.getAttribute("participantUserId"));

		if (participantUserId > 0) {
			booleanFilter.addRequiredTerm(
				"participantUserIds", participantUserId);
		}
	}

	protected void addWorkflowStatusFilter(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		workflowStatusModelPreFilterContributor.contribute(
			booleanFilter, modelSearchSettings, searchContext);
	}

	@Reference(target = "(model.pre.filter.contributor.id=WorkflowStatus)")
	protected ModelPreFilterContributor workflowStatusModelPreFilterContributor;

}