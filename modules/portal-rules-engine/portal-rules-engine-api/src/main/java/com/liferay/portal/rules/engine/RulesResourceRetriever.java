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