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

import com.liferay.portal.kernel.messaging.proxy.MessagingProxy;
import com.liferay.portal.kernel.messaging.proxy.ProxyMode;

import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 * @author Vihang Pathak
 */
public interface RulesEngine {

	@MessagingProxy(mode = ProxyMode.SYNC)
	public void add(
			String domainName, RulesResourceRetriever rulesResourceRetriever)
		throws RulesEngineException;

	@MessagingProxy(mode = ProxyMode.SYNC)
	public boolean containsRuleDomain(String domainName)
		throws RulesEngineException;

	@MessagingProxy(mode = ProxyMode.ASYNC)
	public void execute(
			RulesResourceRetriever rulesResourceRetriever, List<Fact<?>> facts)
		throws RulesEngineException;

	@MessagingProxy(mode = ProxyMode.SYNC)
	public Map<String, ?> execute(
			RulesResourceRetriever rulesResourceRetriever, List<Fact<?>> facts,
			Query query)
		throws RulesEngineException;

	@MessagingProxy(mode = ProxyMode.ASYNC)
	public void execute(String domainName, List<Fact<?>> facts)
		throws RulesEngineException;

	@MessagingProxy(mode = ProxyMode.SYNC)
	public Map<String, ?> execute(
			String domainName, List<Fact<?>> facts, Query query)
		throws RulesEngineException;

	@MessagingProxy(mode = ProxyMode.SYNC)
	public void remove(String domainName) throws RulesEngineException;

	@MessagingProxy(mode = ProxyMode.SYNC)
	public void update(
			String domainName, RulesResourceRetriever rulesResourceRetriever)
		throws RulesEngineException;

}