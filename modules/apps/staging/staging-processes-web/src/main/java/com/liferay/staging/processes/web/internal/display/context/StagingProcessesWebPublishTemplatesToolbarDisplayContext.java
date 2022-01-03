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

package com.liferay.staging.processes.web.internal.display.context;

import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.exportimport.util.comparator.ExportImportConfigurationNameComparator;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.BaseManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.staging.processes.web.internal.search.PublishConfigurationDisplayTerms;
import com.liferay.staging.processes.web.internal.search.PublishConfigurationSearchTerms;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Péter Alius
 * @author Péter Borkuti
 */
public class StagingProcessesWebPublishTemplatesToolbarDisplayContext
	extends BaseManagementToolbarDisplayContext {

	public StagingProcessesWebPublishTemplatesToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, PageContext pageContext,
		PortletURL iteratorURL) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse);

		long companyId = PortalUtil.getCompanyId(liferayPortletRequest);
		long groupId = (long)pageContext.getAttribute("groupId");

		_stagingGroupId = (long)pageContext.getAttribute("stagingGroupId");

		Group stagingGroup = GroupLocalServiceUtil.fetchGroup(_stagingGroupId);

		_searchContainer = _createSearchContainer(
			companyId, groupId, iteratorURL, stagingGroup.isStagedRemotely());
	}

	@Override
	public String getClearResultsURL() {
		return PortletURLBuilder.create(
			_getRenderURL()
		).setMVCPath(
			"/publish_templates/view_publish_configurations.jsp"
		).buildString();
	}

	@Override
	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					_getRenderURL(), "mvcRenderCommandName",
					"/staging_processes/edit_publish_configuration", "groupId",
					String.valueOf(_stagingGroupId), "layoutSetBranchId",
					ParamUtil.getString(
						httpServletRequest, "layoutSetBranchId"),
					"layoutSetBranchName",
					ParamUtil.getString(
						httpServletRequest, "layoutSetBranchName"),
					"privateLayout", Boolean.FALSE.toString());

				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "new"));
			}
		).build();
	}

	@Override
	public int getItemsTotal() {
		return _searchContainer.getTotal();
	}

	@Override
	public String getSearchActionURL() {
		return PortletURLBuilder.create(
			_getRenderURL()
		).setMVCRenderCommandName(
			"/staging_processes/view_publish_configurations"
		).buildString();
	}

	public SearchContainer<ExportImportConfiguration> getSearchContainer() {
		return _searchContainer;
	}

	private SearchContainer<ExportImportConfiguration> _createSearchContainer(
		long companyId, long groupId, PortletURL iteratorURL,
		boolean stagedRemotely) {

		SearchContainer<ExportImportConfiguration> searchContainer =
			new SearchContainer(
				liferayPortletRequest,
				new PublishConfigurationDisplayTerms(liferayPortletRequest),
				new PublishConfigurationSearchTerms(liferayPortletRequest),
				SearchContainer.DEFAULT_CUR_PARAM,
				SearchContainer.DEFAULT_DELTA, iteratorURL, null,
				"there-are-no-saved-publish-templates");

		searchContainer.setOrderByCol("name");
		searchContainer.setOrderByComparator(
			new ExportImportConfigurationNameComparator(
				Objects.equals(getOrderByType(), "asc")));
		searchContainer.setOrderByType(getOrderByType());

		int exportImportConfigurationType =
			ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_LOCAL;

		if (stagedRemotely) {
			exportImportConfigurationType =
				ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_REMOTE;
		}

		PublishConfigurationSearchTerms searchTerms =
			(PublishConfigurationSearchTerms)searchContainer.getSearchTerms();

		List<ExportImportConfiguration> results =
			ExportImportConfigurationLocalServiceUtil.
				getExportImportConfigurations(
					companyId, groupId, searchTerms.getKeywords(),
					exportImportConfigurationType, searchContainer.getStart(),
					searchContainer.getEnd(),
					searchContainer.getOrderByComparator());
		int total =
			ExportImportConfigurationLocalServiceUtil.
				getExportImportConfigurationsCount(
					companyId, groupId, searchTerms.getKeywords(),
					exportImportConfigurationType);

		searchContainer.setResults(results);
		searchContainer.setTotal(total);

		return searchContainer;
	}

	private PortletURL _getRenderURL() {
		return liferayPortletResponse.createRenderURL();
	}

	private final SearchContainer<ExportImportConfiguration> _searchContainer;
	private final long _stagingGroupId;

}