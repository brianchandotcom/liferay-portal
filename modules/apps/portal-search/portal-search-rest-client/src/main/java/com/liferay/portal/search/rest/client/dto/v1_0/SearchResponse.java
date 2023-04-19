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

package com.liferay.portal.search.rest.client.dto.v1_0;

import com.liferay.portal.search.rest.client.function.UnsafeSupplier;
import com.liferay.portal.search.rest.client.serdes.v1_0.SearchResponseSerDes;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
public class SearchResponse implements Cloneable, Serializable {

	public static SearchResponse toDTO(String json) {
		return SearchResponseSerDes.toDTO(json);
	}

	public Map<String, Object> getAggregationResults() {
		return aggregationResults;
	}

	public void setAggregationResults(Map<String, Object> aggregationResults) {
		this.aggregationResults = aggregationResults;
	}

	public void setAggregationResults(
		UnsafeSupplier<Map<String, Object>, Exception>
			aggregationResultsUnsafeSupplier) {

		try {
			aggregationResults = aggregationResultsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Object> aggregationResults;

	public Object[] getDocuments() {
		return documents;
	}

	public void setDocuments(Object[] documents) {
		this.documents = documents;
	}

	public void setDocuments(
		UnsafeSupplier<Object[], Exception> documentsUnsafeSupplier) {

		try {
			documents = documentsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object[] documents;

	public Map<String, Object> getFacets() {
		return facets;
	}

	public void setFacets(Map<String, Object> facets) {
		this.facets = facets;
	}

	public void setFacets(
		UnsafeSupplier<Map<String, Object>, Exception> facetsUnsafeSupplier) {

		try {
			facets = facetsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Object> facets;

	public Float getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Float maxScore) {
		this.maxScore = maxScore;
	}

	public void setMaxScore(
		UnsafeSupplier<Float, Exception> maxScoreUnsafeSupplier) {

		try {
			maxScore = maxScoreUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Float maxScore;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public void setPage(UnsafeSupplier<Integer, Exception> pageUnsafeSupplier) {
		try {
			page = pageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer page;

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageSize(
		UnsafeSupplier<Integer, Exception> pageSizeUnsafeSupplier) {

		try {
			pageSize = pageSizeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer pageSize;

	public Object getRequest() {
		return request;
	}

	public void setRequest(Object request) {
		this.request = request;
	}

	public void setRequest(
		UnsafeSupplier<Object, Exception> requestUnsafeSupplier) {

		try {
			request = requestUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object request;

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public void setResponse(
		UnsafeSupplier<Object, Exception> responseUnsafeSupplier) {

		try {
			response = responseUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object response;

	public Long getTotalHits() {
		return totalHits;
	}

	public void setTotalHits(Long totalHits) {
		this.totalHits = totalHits;
	}

	public void setTotalHits(
		UnsafeSupplier<Long, Exception> totalHitsUnsafeSupplier) {

		try {
			totalHits = totalHitsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long totalHits;

	@Override
	public SearchResponse clone() throws CloneNotSupportedException {
		return (SearchResponse)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SearchResponse)) {
			return false;
		}

		SearchResponse searchResponse = (SearchResponse)object;

		return Objects.equals(toString(), searchResponse.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SearchResponseSerDes.toJSON(this);
	}

}