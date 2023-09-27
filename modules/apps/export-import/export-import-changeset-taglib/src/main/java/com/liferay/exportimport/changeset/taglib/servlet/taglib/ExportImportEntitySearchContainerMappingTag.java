/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.changeset.taglib.servlet.taglib;

import com.liferay.exportimport.changeset.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.taglib.ui.SearchContainerTag;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Akos Thurzo
 */
public class ExportImportEntitySearchContainerMappingTag<R> extends IncludeTag {

	public static final String DEFAULT_SEARCH_CONTAINER_MAPPING_ID =
		"exportImportEntitySearchContainerMapping";

	@Override
	public int doStartTag() throws JspException {
		SearchContainerTag<R> searchContainerTag =
			(SearchContainerTag<R>)findAncestorWithClass(
				this, SearchContainerTag.class);

		if (searchContainerTag == null) {
			throw new JspException("Requires liferay-ui:search-container");
		}

		_searchContainer = searchContainerTag.getSearchContainer();

		return super.doStartTag();
	}

	public String getSearchContainerMappingId() {
		return _searchContainerMappingId;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setSearchContainerMappingId(String searchContainerMappingId) {
		_searchContainerMappingId = searchContainerMappingId;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_searchContainer = null;
		_searchContainerMappingId = DEFAULT_SEARCH_CONTAINER_MAPPING_ID;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-export-import-changeset:" +
				"export-import-entity-search-container-mapping:searchContainer",
			_searchContainer);
		httpServletRequest.setAttribute(
			"liferay-export-import-changeset:" +
				"export-import-entity-search-container-mapping:" +
					"searchContainerMappingId",
			_searchContainerMappingId);
	}

	private static final String _PAGE =
		"/export_import_entity_search_container_mapping/page.jsp";

	private SearchContainer<R> _searchContainer;
	private String _searchContainerMappingId =
		DEFAULT_SEARCH_CONTAINER_MAPPING_ID;

}