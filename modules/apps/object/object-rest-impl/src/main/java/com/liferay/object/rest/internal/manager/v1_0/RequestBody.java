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

package com.liferay.object.rest.internal.manager.v1_0;

import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Locale;

import javax.ws.rs.core.UriInfo;

/**
 * @author Feliphe Marinho
 */
public class RequestBody {

	public Aggregation getAggregation() {
		return _aggregation;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public String getFilterString() {
		return _filterString;
	}

	public Locale getLocale() {
		return _locale;
	}

	public String getObjectDefinitionExternalReferenceCode() {
		return _objectDefinitionExternalReferenceCode;
	}

	public ObjectEntry getObjectEntry() {
		return _objectEntry;
	}

	public Pagination getPagination() {
		return _pagination;
	}

	public String getScopeKey() {
		return _scopeKey;
	}

	public String getSearch() {
		return _search;
	}

	public Sort[] getSorts() {
		return _sorts;
	}

	public UriInfo getUriInfo() {
		return _uriInfo;
	}

	public long getUserId() {
		return _userId;
	}

	public void setAggregation(Aggregation aggregation) {
		_aggregation = aggregation;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public void setFilterString(String filterString) {
		_filterString = filterString;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public void setObjectDefinitionExternalReferenceCode(
		String objectDefinitionExternalReferenceCode) {

		_objectDefinitionExternalReferenceCode =
			objectDefinitionExternalReferenceCode;
	}

	public void setObjectEntry(ObjectEntry objectEntry) {
		_objectEntry = objectEntry;
	}

	public void setPagination(Pagination pagination) {
		_pagination = pagination;
	}

	public void setScopeKey(String scopeKey) {
		_scopeKey = scopeKey;
	}

	public void setSearch(String search) {
		_search = search;
	}

	public void setSorts(Sort[] sorts) {
		_sorts = sorts;
	}

	public void setUriInfo(UriInfo uriInfo) {
		_uriInfo = uriInfo;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	private Aggregation _aggregation;
	private long _companyId;
	private String _externalReferenceCode;
	private String _filterString;
	private Locale _locale;
	private String _objectDefinitionExternalReferenceCode;
	private ObjectEntry _objectEntry;
	private Pagination _pagination;
	private String _scopeKey;
	private String _search;
	private Sort[] _sorts;
	private UriInfo _uriInfo;
	private long _userId;

}