/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.rest.client.dto.v1_0;

import com.liferay.search.experiences.rest.client.function.UnsafeSupplier;
import com.liferay.search.experiences.rest.client.serdes.v1_0.ConfigurationSerDes;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class Configuration implements Cloneable, Serializable {

	public static Configuration toDTO(String json) {
		return ConfigurationSerDes.toDTO(json);
	}

	public Advanced getAdvanced() {
		return advanced;
	}

	public void setAdvanced(Advanced advanced) {
		this.advanced = advanced;
	}

	public void setAdvanced(
		UnsafeSupplier<Advanced, Exception> advancedUnsafeSupplier) {

		try {
			advanced = advancedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Advanced advanced;

	public Map<String, ?> getAggregations() {
		return aggregations;
	}

	public void setAggregations(Map<String, ?> aggregations) {
		this.aggregations = aggregations;
	}

	public void setAggregations(
		UnsafeSupplier<Map<String, ?>, Exception> aggregationsUnsafeSupplier) {

		try {
			aggregations = aggregationsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, ?> aggregations;

	public Facet getFacet() {
		return facet;
	}

	public void setFacet(Facet facet) {
		this.facet = facet;
	}

	public void setFacet(UnsafeSupplier<Facet, Exception> facetUnsafeSupplier) {
		try {
			facet = facetUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Facet facet;

	public General getGeneral() {
		return general;
	}

	public void setGeneral(General general) {
		this.general = general;
	}

	public void setGeneral(
		UnsafeSupplier<General, Exception> generalUnsafeSupplier) {

		try {
			general = generalUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected General general;

	public Highlight getHighlight() {
		return highlight;
	}

	public void setHighlight(Highlight highlight) {
		this.highlight = highlight;
	}

	public void setHighlight(
		UnsafeSupplier<Highlight, Exception> highlightUnsafeSupplier) {

		try {
			highlight = highlightUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Highlight highlight;

	public Query[] getQueries() {
		return queries;
	}

	public void setQueries(Query[] queries) {
		this.queries = queries;
	}

	public void setQueries(
		UnsafeSupplier<Query[], Exception> queriesUnsafeSupplier) {

		try {
			queries = queriesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Query[] queries;

	@Override
	public Configuration clone() throws CloneNotSupportedException {
		return (Configuration)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Configuration)) {
			return false;
		}

		Configuration configuration = (Configuration)object;

		return Objects.equals(toString(), configuration.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ConfigurationSerDes.toJSON(this);
	}

}