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

package com.liferay.portal.search.elasticsearch6.internal.connection;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.lucene.util.SetOnce;

import org.elasticsearch.analysis.common.CommonAnalysisPlugin;
import org.elasticsearch.common.logging.LogConfigurator;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.reindex.ReindexPlugin;
import org.elasticsearch.node.InternalSettingsPreparer;
import org.elasticsearch.node.Node;
import org.elasticsearch.painless.PainlessPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.transport.Netty4Plugin;

/**
 * @author André de Oliveira
 */
public class EmbeddedElasticsearchNode extends Node {

	public static Node newInstance(Settings settings) {
		Environment environment = InternalSettingsPreparer.prepareEnvironment(
			settings, null);

		List<Class<? extends Plugin>> classpathPlugins = Arrays.asList(
			CommonAnalysisPlugin.class, Netty4Plugin.class,
			PainlessPlugin.class, ReindexPlugin.class);

		try {
			LogConfigurator.registerErrorListener();

			LogConfigurator.configure(environment);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to find log4j2.properties", e);
			}
		}

		return PluginJarConflictCheckSuppression.execute(
			() -> new EmbeddedElasticsearchNode(environment, classpathPlugins));
	}

	public EmbeddedElasticsearchNode(
		Environment environment,
		Collection<Class<? extends Plugin>> classpathPlugins) {

		super(environment, classpathPlugins, false);
	}

	@Override
	protected void registerDerivedNodeNameWithLogger(String nodeName) {
		try {
			LogConfigurator.setNodeName(nodeName);
		}
		catch (SetOnce.AlreadySetException soase) {
			if (_log.isDebugEnabled()) {
				_log.debug("Node name has already been set");
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EmbeddedElasticsearchNode.class);

}