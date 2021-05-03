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

package com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.index.creation.instance.lifecycle;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.name.KeywordIndexName;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.name.KeywordIndexNameBuilder;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.index.KeywordIndexCreator;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.index.KeywordIndexReader;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class KeywordIndexCreationPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		if (Objects.equals(
				_searchEngineInformation.getVendorString(), "Solr")) {

			return;
		}

		KeywordIndexName keywordIndexName =
			_keywordIndexNameBuilder.getKeywordIndexName(
				company.getCompanyId());

		if (!_keywordIndexReader.isIndexExists(keywordIndexName)) {
			_keywordIndexCreator.create(keywordIndexName);
		}
	}

	@Reference
	private KeywordIndexCreator _keywordIndexCreator;

	@Reference
	private KeywordIndexNameBuilder _keywordIndexNameBuilder;

	@Reference
	private KeywordIndexReader _keywordIndexReader;

	@Reference
	private SearchEngineInformation _searchEngineInformation;

}