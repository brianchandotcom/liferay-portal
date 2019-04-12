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

package com.liferay.frontend.taglib.clay.servlet.taglib.soy;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.taglib.clay.internal.ClayTableTagSchemaContributorsProvider;
import com.liferay.frontend.taglib.clay.internal.ClayTagDataSourceProvider;
import com.liferay.frontend.taglib.clay.internal.js.loader.modules.extender.npm.NPMResolverProvider;
import com.liferay.frontend.taglib.clay.internal.servlet.taglib.display.context.TableDefaults;
import com.liferay.frontend.taglib.clay.servlet.taglib.contributor.ClayTableTagSchemaContributor;
import com.liferay.frontend.taglib.clay.servlet.taglib.data.ClayTagDataSource;
import com.liferay.frontend.taglib.clay.servlet.taglib.data.Pagination;
import com.liferay.frontend.taglib.clay.servlet.taglib.model.pagination.PaginationEntry;
import com.liferay.frontend.taglib.clay.servlet.taglib.model.table.Schema;
import com.liferay.frontend.taglib.clay.servlet.taglib.model.table.Size;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.base.BaseClayTag;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Iván Zaera Avellón
 */
public class TableTag<T> extends BaseClayTag {

	@Override
	public int doStartTag() {
		setComponentBaseName("com.liferay.frontend.taglib.clay.Table");
		setHydrate(true);
		setModuleBaseName("table");

		int returnValue = super.doStartTag();

		_populateContextDefaultValues();

		ClayTagDataSource<T> clayTagDataSource = getClayTagDataSource();

		if (clayTagDataSource != null) {
			_populateContextItems(clayTagDataSource);
			_populateContextPagination();
		}

		List<ClayTableTagSchemaContributor> clayTableTagSchemaContributors =
			getTableTagSchemaContributors();

		if (clayTableTagSchemaContributors != null) {
			_populateSchema(clayTableTagSchemaContributors);
		}

		putValue("schema", _schema.toMap());

		boolean selectable = GetterUtil.getBoolean(getValue("selectable"));

		boolean showCheckbox = GetterUtil.getBoolean(
			getValue("showCheckbox"), TableDefaults.isShowCheckbox(selectable));

		setShowCheckbox(showCheckbox);

		return returnValue;
	}

	@Override
	public String getModule() {
		NPMResolver npmResolver = NPMResolverProvider.getNPMResolver();

		if (npmResolver == null) {
			return StringPool.BLANK;
		}

		return npmResolver.resolveModuleName(
			"frontend-taglib-clay/table/Table.es");
	}

	public void setActionsMenuVariant(String actionsMenuVariant) {
		putValue("actionsMenuVariant", actionsMenuVariant);
	}

	public void setDataSourceKey(String dataSourceKey) {
		putValue("dataSourceKey", dataSourceKey);
	}

	public void setDataSourceURL(String dataSourceURL) {
		putValue("dataSourceURL", dataSourceURL);
	}

	public void setItems(Collection<?> items) {
		putValue("items", items);
	}

	public void setPage(int page) {
		putValue("page", page);
	}

	public void setPageParamName(String pageParamName) {
		putValue("pageParamName", pageParamName);
	}

	public void setPageSize(int pageSize) {
		putValue("pageSize", pageSize);
	}

	public void setPageSizeParamName(String pageSizeParamName) {
		putValue("pageSizeParamName", pageSizeParamName);
	}

	public void setSchema(Schema schema) {
		_schema = schema;
	}

	public void setSelectable(Boolean selectable) {
		putValue("selectable", selectable);
	}

	public void setShowActionsMenu(Boolean showActionsMenu) {
		putValue("showActionsMenu", showActionsMenu);
	}

	public void setShowCheckbox(Boolean showCheckbox) {
		putValue("showCheckbox", showCheckbox);
	}

	public void setSize(Size size) {
		String sizeValue = null;

		if (size != null) {
			sizeValue = size.getValue();
		}

		putValue("size", sizeValue);
	}

	public void setTableClasses(String tableClasses) {
		putValue("tableClasses", tableClasses);
	}

	public void setTableSchemaContributorKey(String tableSchemaContributorKey) {
		putValue("tableSchemaContributorKey", tableSchemaContributorKey);
	}

	public void setUseDefaultClasses(Boolean useDefaultClasses) {
		putValue("useDefaultClasses", useDefaultClasses);
	}

	public void setWrapTable(Boolean wrapTable) {
		putValue("wrapTable", wrapTable);
	}

	protected ClayTagDataSource<T> getClayTagDataSource() {
		String dataSourceKey = getValue("dataSourceKey");

		if (Validator.isNull(dataSourceKey)) {
			return null;
		}

		return (ClayTagDataSource<T>)
			ClayTagDataSourceProvider.getClayTagDataSource(dataSourceKey);
	}

	protected List<ClayTableTagSchemaContributor>
		getTableTagSchemaContributors() {

		String tableSchemaContributorKey = GetterUtil.getString(
			getValue("tableSchemaContributorKey"));

		if (Validator.isNull(tableSchemaContributorKey)) {
			return null;
		}

		return ClayTableTagSchemaContributorsProvider.
			getClayTableTagSchemaContributors(tableSchemaContributorKey);
	}

	private List<PaginationEntry> _getPaginationEntries() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String portletId = String.valueOf(themeDisplay.getPlid());

		PortletURL portletURL = PortletURLFactoryUtil.create(
			request, portletId, PortletRequest.RENDER_PHASE);

		String deltaParam = SearchContainer.DEFAULT_DELTA_PARAM;

		String portletURLString = HttpUtil.removeParameter(
			portletURL.toString(), getNamespace() + deltaParam);

		List<PaginationEntry> clayPaginationEntries = new ArrayList<>();

		for (int delta : PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) {
			if (delta > SearchContainer.MAX_DELTA) {
				continue;
			}

			String url = HttpUtil.addParameter(
				portletURLString, getNamespace() + deltaParam, delta);

			clayPaginationEntries.add(new PaginationEntry(url, delta));
		}

		return clayPaginationEntries;
	}

	private void _populateContextDefaultValues() {
		Map<String, Object> context = getContext();

		putValue("page", context.getOrDefault("page", 1));
		putValue(
			"pageParamName", context.getOrDefault("pageParamName", "page"));
		putValue("pageSize", context.getOrDefault("pageSize", 5));
		putValue(
			"pageSizeParamName",
			context.getOrDefault("pageSizeParamName", "pageSize"));
	}

	private void _populateContextItems(ClayTagDataSource<T> clayTagDataSource) {
		setItems(
			clayTagDataSource.getItems(
				request,
				new Pagination(getValue("pageSize"), getValue("page"))));

		putValue("totalItems", clayTagDataSource.getTotalItemsCount());
	}

	private void _populateContextPagination() {
		List<PaginationEntry> paginationEntries = _getPaginationEntries();

		putValue("paginationEntries", paginationEntries);

		putValue("paginationSelectedEntry", 0);

		int page = getValue("page");

		for (int i = 0; i < paginationEntries.size(); i++) {
			PaginationEntry paginationEntry = paginationEntries.get(i);

			if (paginationEntry.getLabel() == page) {
				putValue("paginationSelectedEntry", i);

				break;
			}
		}
	}

	private void _populateSchema(
		List<ClayTableTagSchemaContributor> clayTableTagSchemaContributors) {

		for (ClayTableTagSchemaContributor clayTableTagSchemaContributor :
				clayTableTagSchemaContributors) {

			clayTableTagSchemaContributor.populate(_schema);
		}
	}

	private Schema _schema = new Schema();

}