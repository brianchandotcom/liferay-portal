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

package com.liferay.portal.search.internal.facet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.FacetBuilder;
import com.liferay.portal.kernel.search.facet.FacetManager;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.search.facet.FacetFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = FacetManager.class)
public class FacetManagerImpl implements FacetManager {

	@Override
	public FacetBuilder createFacetBuilder(
		String id, SearchContext searchContext) {

		return new FacetBuilderImpl(id, searchContext);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addFacetFactory(FacetFactory facetFactory) {
		_facetFactories.put(facetFactory.getFacetClassName(), facetFactory);
	}

	protected FacetFactory getFacetFactory(String id) {
		FacetFactory facetFactory = _facetFactories.get(id);

		if (facetFactory == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to find facet factory for id " + id);
			}

			return null;
		}

		return facetFactory;
	}

	protected void removeFacetFactory(FacetFactory facetFactory) {
		_facetFactories.remove(facetFactory.getFacetClassName());
	}

	protected class FacetBuilderImpl implements FacetBuilder {

		public FacetBuilderImpl(String id, SearchContext searchContext) {
			_id = id;
			_searchContext = searchContext;
		}

		@Override
		public Facet build() {
			FacetFactory facetFactory = getFacetFactory(_id);

			if (facetFactory == null) {
				return null;
			}

			Facet facet = facetFactory.newInstance(_searchContext);

			applyFacetConfiguration(facet);
			applyStatic(facet);
			applyValues(facet);

			return facet;
		}

		@Override
		public void setFacetConfiguration(
			FacetConfiguration facetConfiguration) {

			_facetConfiguration = facetConfiguration;
		}

		@Override
		public void setStatic(boolean isStatic) {
			_static = isStatic;
		}

		@Override
		public void setValues(long[] values) {
			_valuesLong = values;
		}

		@Override
		public void setValues(String[] values) {
			_valuesString = values;
		}

		protected void applyFacetConfiguration(Facet facet) {
			if (_facetConfiguration != null) {
				facet.setFacetConfiguration(_facetConfiguration);
			}
		}

		protected void applyStatic(Facet facet) {
			if (_static != null) {
				facet.setStatic(_static);
			}
		}

		protected void applyValues(Facet facet) {
			if (!(facet instanceof MultiValueFacet)) {
				return;
			}

			MultiValueFacet multiValueFacet = (MultiValueFacet)facet;

			if (_valuesLong != null) {
				multiValueFacet.setValues(_valuesLong);
			}

			if (_valuesString != null) {
				multiValueFacet.setValues(_valuesString);
			}
		}

		private FacetConfiguration _facetConfiguration;
		private final String _id;
		private final SearchContext _searchContext;
		private Boolean _static;
		private long[] _valuesLong;
		private String[] _valuesString;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		FacetManagerImpl.class);

	private final Map<String, FacetFactory> _facetFactories =
		new ConcurrentHashMap<>();

}