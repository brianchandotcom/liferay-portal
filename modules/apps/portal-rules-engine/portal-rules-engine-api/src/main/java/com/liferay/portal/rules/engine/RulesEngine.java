/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.rules.engine;

import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 * @author Vihang Pathak
 */
public interface RulesEngine {

	public void add(
			String domainName, RulesResourceRetriever rulesResourceRetriever)
		throws RulesEngineException;

	public boolean containsRuleDomain(String domainName)
		throws RulesEngineException;

	public void execute(
			RulesResourceRetriever rulesResourceRetriever, List<Fact<?>> facts)
		throws RulesEngineException;

	public Map<String, ?> execute(
			RulesResourceRetriever rulesResourceRetriever, List<Fact<?>> facts,
			Query query)
		throws RulesEngineException;

	public void execute(String domainName, List<Fact<?>> facts)
		throws RulesEngineException;

	public Map<String, ?> execute(
			String domainName, List<Fact<?>> facts, Query query)
		throws RulesEngineException;

	public void remove(String domainName) throws RulesEngineException;

	public void update(
			String domainName, RulesResourceRetriever rulesResourceRetriever)
		throws RulesEngineException;

}