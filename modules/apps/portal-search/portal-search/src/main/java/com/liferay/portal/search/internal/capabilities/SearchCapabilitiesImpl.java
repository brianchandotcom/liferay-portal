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

package com.liferay.portal.search.internal.capabilities;

import com.liferay.portal.search.capabilities.SearchCapabilities;
import com.liferay.portal.search.engine.SearchEngineInformation;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = SearchCapabilities.class)
public class SearchCapabilitiesImpl implements SearchCapabilities {

	@Override
	public boolean isCommerceSupported() {
		if (_isSearchEngineSolr()) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isConcurrentModeSupported() {
		if (_isSearchEngineSolr()) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isResultRankingsSupported() {
		if (_isSearchEngineSolr()) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isSearchExperiencesSupported() {
		if (_isSearchEngineSolr()) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isSynonymsSupported() {
		if (_isSearchEngineSolr()) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isWorkflowMetricsSupported() {
		if (_isSearchEngineSolr()) {
			return false;
		}

		return true;
	}

	private boolean _isSearchEngineSolr() {
		if (Objects.equals(
				_searchEngineInformation.getVendorString(), "Solr")) {

			return true;
		}

		return false;
	}

	@Reference
	private SearchEngineInformation _searchEngineInformation;

}