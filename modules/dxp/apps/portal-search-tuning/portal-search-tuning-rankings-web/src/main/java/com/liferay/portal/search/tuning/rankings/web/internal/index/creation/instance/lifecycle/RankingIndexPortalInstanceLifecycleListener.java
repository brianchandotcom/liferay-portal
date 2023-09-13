/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index.creation.instance.lifecycle;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.search.capabilities.SearchCapabilities;
import com.liferay.portal.search.spi.index.lifecycle.IndexLifecycleManager;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class RankingIndexPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		if (!_searchCapabilities.isResultRankingsSupported()) {
			return;
		}

		_rankingIndexLifecycleManager.createIndex(company.getCompanyId());
	}

	@Override
	public void portalInstanceUnregistered(Company company) throws Exception {
		if (!_searchCapabilities.isResultRankingsSupported()) {
			return;
		}

		_rankingIndexLifecycleManager.deleteIndex(company.getCompanyId());
	}

	@Reference(
		target = "(component.name=com.liferay.portal.search.tuning.rankings.web.internal.index.lifecycle.RankingIndexLifecycleManager)"
	)
	private IndexLifecycleManager _rankingIndexLifecycleManager;

	@Reference
	private SearchCapabilities _searchCapabilities;

}