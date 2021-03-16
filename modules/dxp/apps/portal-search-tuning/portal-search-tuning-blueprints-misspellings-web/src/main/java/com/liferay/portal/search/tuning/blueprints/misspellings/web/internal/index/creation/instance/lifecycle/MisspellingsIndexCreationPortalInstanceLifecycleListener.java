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

package com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.index.creation.instance.lifecycle;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.search.tuning.blueprints.misspellings.index.name.MisspellingsIndexName;
import com.liferay.portal.search.tuning.blueprints.misspellings.index.name.MisspellingsIndexNameBuilder;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.index.MisspellingsIndexCreator;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.index.MisspellingsIndexReader;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class MisspellingsIndexCreationPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		if (Objects.equals(
				_searchEngineInformation.getVendorString(), "Solr")) {

			return;
		}

		MisspellingsIndexName misspellingsIndexName =
			_misspellingsIndexNameBuilder.getMisspellingsIndexName(
				company.getCompanyId());

		if (!_misspellingsIndexReader.isIndexExists(misspellingsIndexName)) {
			_misspellingsIndexCreator.create(misspellingsIndexName);
		}
	}

	@Reference
	private MisspellingsIndexCreator _misspellingsIndexCreator;

	@Reference
	private MisspellingsIndexNameBuilder _misspellingsIndexNameBuilder;

	@Reference
	private MisspellingsIndexReader _misspellingsIndexReader;

	@Reference
	private SearchEngineInformation _searchEngineInformation;

}