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
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Locale;
import java.util.Map;

import javax.ws.rs.core.UriInfo;

/**
 * @author Feliphe Marinho
 */
public class RequestBodyBuilder {

	public RequestBodyBuilder aggregation(Aggregation aggregation) {
		_requestBody.setAggregation(aggregation);

		return this;
	}

	public JSONObject buildJSONObject() throws JSONException {
		JSONObject requestBodyJSONObject = JSONFactoryUtil.createJSONObject(
			JSONFactoryUtil.looseSerialize(_requestBody));

		ObjectEntry objectEntry = _requestBody.getObjectEntry();

		if (objectEntry == null) {
			return requestBodyJSONObject;
		}

		JSONObject objectEntryJSONObject = JSONFactoryUtil.createJSONObject(
			JSONFactoryUtil.looseSerialize(objectEntry));

		objectEntryJSONObject = JSONFactoryUtil.createJSONObject(
			HashMapBuilder.put(
				"creator", objectEntryJSONObject.get("creator")
			).put(
				"dateCreated", objectEntryJSONObject.get("dateCreated")
			).put(
				"dateModified", objectEntryJSONObject.get("dateModified")
			).put(
				"externalReferenceCode",
				objectEntryJSONObject.get("externalReferenceCode")
			).put(
				"status", objectEntryJSONObject.get("status")
			).build());

		Map<String, Object> properties = objectEntry.getProperties();

		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			objectEntryJSONObject.put(entry.getKey(), entry.getValue());
		}

		requestBodyJSONObject.put("objectEntry", objectEntryJSONObject);

		return requestBodyJSONObject;
	}

	public RequestBodyBuilder companyId(long companyId) {
		_requestBody.setCompanyId(companyId);

		return this;
	}

	public RequestBodyBuilder externalReferenceCode(
		String externalReferenceCode) {

		_requestBody.setExternalReferenceCode(externalReferenceCode);

		return this;
	}

	public RequestBodyBuilder filterString(String filterString) {
		_requestBody.setFilterString(filterString);

		return this;
	}

	public RequestBodyBuilder locale(Locale locale) {
		_requestBody.setLocale(locale);

		return this;
	}

	public RequestBodyBuilder objectDefinitionExternalReferenceCode(
		String objectDefinitionExternalReferenceCode) {

		_requestBody.setObjectDefinitionExternalReferenceCode(
			objectDefinitionExternalReferenceCode);

		return this;
	}

	public RequestBodyBuilder objectEntry(ObjectEntry objectEntry) {
		_requestBody.setObjectEntry(objectEntry);

		return this;
	}

	public RequestBodyBuilder pagination(Pagination pagination) {
		_requestBody.setPagination(pagination);

		return this;
	}

	public RequestBodyBuilder scopeKey(String scopeKey) {
		_requestBody.setScopeKey(scopeKey);

		return this;
	}

	public RequestBodyBuilder search(String search) {
		_requestBody.setSearch(search);

		return this;
	}

	public RequestBodyBuilder sorts(Sort[] sorts) {
		_requestBody.setSorts(sorts);

		return this;
	}

	public RequestBodyBuilder uriInfo(UriInfo uriInfo) {
		_requestBody.setUriInfo(uriInfo);

		return this;
	}

	public RequestBodyBuilder userId(long userId) {
		_requestBody.setUserId(userId);

		return this;
	}

	private final RequestBody _requestBody = new RequestBody();

}