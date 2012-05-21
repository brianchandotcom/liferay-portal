/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.bi.rules;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 * @author Raymond Augé
 */
public class RulesEngineUtil {

	public static void add(
			String domainName, RulesResourceRetriever rulesResourceRetriever)
		throws RulesEngineException {

		getRulesEngine().add(domainName, rulesResourceRetriever);
	}

	public static void add(
			String domainName, RulesResourceRetriever rulesResourceRetriever,
			ClassLoader... classloaders)
		throws RulesEngineException {

		getRulesEngine().add(domainName, rulesResourceRetriever, classloaders);
	}

	public static boolean containsRuleDomain(String domainName)
		throws RulesEngineException {

		return getRulesEngine().containsRuleDomain(domainName);
	}

	public static void execute(
			RulesResourceRetriever rulesResourceRetriever, List<Fact<?>> facts)
		throws RulesEngineException {

		getRulesEngine().execute(rulesResourceRetriever, facts);
	}

	public static void execute(
			RulesResourceRetriever rulesResourceRetriever, List<Fact<?>> facts,
			ClassLoader... classloaders)
		throws RulesEngineException {

		getRulesEngine().execute(rulesResourceRetriever, facts, classloaders);
	}

	public static Map<String, ?> execute(
			RulesResourceRetriever rulesResourceRetriever, List<Fact<?>> facts,
			Query query)
		throws RulesEngineException {

		return getRulesEngine().execute(rulesResourceRetriever, facts, query);
	}

	public static Map<String, ?> execute(
			RulesResourceRetriever rulesResourceRetriever, List<Fact<?>> facts,
			Query query, ClassLoader... classloaders)
		throws RulesEngineException {

		return getRulesEngine().execute(
			rulesResourceRetriever, facts, query, classloaders);
	}

	public static void execute(String domainName, List<Fact<?>> facts)
		throws RulesEngineException {

		getRulesEngine().execute(domainName, facts);
	}

	public static void execute(
			String domainName, List<Fact<?>> facts, ClassLoader... classloaders)
		throws RulesEngineException {

		getRulesEngine().execute(domainName, facts, classloaders);
	}

	public static Map<String, ?> execute(
			String domainName, List<Fact<?>> facts, Query query)
		throws RulesEngineException {

		return getRulesEngine().execute(domainName, facts, query);
	}

	public static Map<String, ?> execute(
			String domainName, List<Fact<?>> facts, Query query,
			ClassLoader... classloaders)
		throws RulesEngineException {

		return getRulesEngine().execute(domainName, facts, query, classloaders);
	}

	public static RulesEngine getRulesEngine() {
		PortalRuntimePermission.checkGetBeanProperty(RulesEngineUtil.class);

		return _rulesEngine;
	}

	public static void remove(String domainName) throws RulesEngineException {
		getRulesEngine().remove(domainName);
	}

	public static void update(
			String domainName, RulesResourceRetriever rulesResourceRetriever)
		throws RulesEngineException {

		getRulesEngine().update(domainName, rulesResourceRetriever);
	}

	public static void update(
			String domainName, RulesResourceRetriever rulesResourceRetriever,
			ClassLoader... classloaders)
		throws RulesEngineException {

		getRulesEngine().update(
			domainName, rulesResourceRetriever, classloaders);
	}

	public void setRulesEngine(RulesEngine rulesEngine) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_rulesEngine = rulesEngine;
	}

	private static RulesEngine _rulesEngine;

}