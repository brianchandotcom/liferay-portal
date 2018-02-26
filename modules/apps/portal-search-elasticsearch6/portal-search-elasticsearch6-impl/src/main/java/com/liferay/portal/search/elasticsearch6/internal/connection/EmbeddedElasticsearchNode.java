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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.node.InternalSettingsPreparer;
import org.elasticsearch.node.Node;
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
			Netty4Plugin.class);

		return PluginJarConflictCheckSuppression.execute(
			() -> new EmbeddedElasticsearchNode(environment, classpathPlugins));
	}

	public EmbeddedElasticsearchNode(
		Environment environment,
		Collection<Class<? extends Plugin>> classpathPlugins) {

		super(environment, classpathPlugins);
	}

}