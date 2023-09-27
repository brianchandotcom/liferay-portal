/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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