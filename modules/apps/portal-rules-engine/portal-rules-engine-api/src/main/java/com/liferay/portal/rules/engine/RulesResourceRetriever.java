/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.rules.engine;

import com.liferay.portal.kernel.resource.ResourceRetriever;

import java.io.Serializable;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class RulesResourceRetriever implements Serializable {

	public RulesResourceRetriever(ResourceRetriever resourceRetriever) {
		this(resourceRetriever, null);
	}

	public RulesResourceRetriever(
		ResourceRetriever resourceRetriever, String rulesLanguage) {

		if (resourceRetriever != null) {
			_resourceRetrievers.add(resourceRetriever);
		}

		_rulesLanguage = rulesLanguage;
	}

	public RulesResourceRetriever(String rulesLanguage) {
		this(null, rulesLanguage);
	}

	public void addResourceRetriever(ResourceRetriever resourceRetriever) {
		_resourceRetrievers.add(resourceRetriever);
	}

	public Set<ResourceRetriever> getResourceRetrievers() {
		return _resourceRetrievers;
	}

	public String getRulesLanguage() {
		return _rulesLanguage;
	}

	private final Set<ResourceRetriever> _resourceRetrievers = new HashSet<>();
	private final String _rulesLanguage;

}