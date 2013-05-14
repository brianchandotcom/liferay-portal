/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.bi.rules;

import com.liferay.portal.kernel.bi.rules.Fact;
import com.liferay.portal.kernel.bi.rules.Query;
import com.liferay.portal.kernel.bi.rules.RulesEngine;
import com.liferay.portal.kernel.bi.rules.RulesResourceRetriever;
import com.liferay.portal.kernel.messaging.proxy.BaseProxyBean;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;

import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 */
@DoPrivileged
public class RulesEngineProxyBean extends BaseProxyBean implements RulesEngine {

	public void add(
		String domainName, RulesResourceRetriever RulesResourceRetriever,
		ClassLoader... clientClassLoaders) {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsRuleDomain(String domainName) {
		throw new UnsupportedOperationException();
	}

	public void execute(
		RulesResourceRetriever RulesResourceRetriever, List<Fact<?>> facts,
		ClassLoader... clientClassLoaders) {

		throw new UnsupportedOperationException();
	}

	public Map<String, ?> execute(
		RulesResourceRetriever RulesResourceRetriever, List<Fact<?>> facts,
		Query query, ClassLoader... clientClassLoaders) {

		throw new UnsupportedOperationException();
	}

	public void execute(
		String domainName, List<Fact<?>> facts,
		ClassLoader... clientClassLoaders) {

		throw new UnsupportedOperationException();
	}

	public Map<String, ?> execute(
		String domainName, List<Fact<?>> facts, Query query,
		ClassLoader... clientClassLoaders) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void remove(String domainName) {
		throw new UnsupportedOperationException();
	}

	public void update(
		String domainName, RulesResourceRetriever RulesResourceRetriever,
		ClassLoader... clientClassLoaders) {

		throw new UnsupportedOperationException();
	}

}