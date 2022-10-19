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