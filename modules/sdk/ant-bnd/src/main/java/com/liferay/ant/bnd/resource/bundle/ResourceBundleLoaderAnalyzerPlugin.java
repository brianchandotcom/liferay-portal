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

package com.liferay.ant.bnd.resource.bundle;

import aQute.bnd.osgi.Analyzer;
import aQute.bnd.service.AnalyzerPlugin;

/**
 * @author Carlos Sierra Andrés
 * @author Gregory Amerson
 */
public class ResourceBundleLoaderAnalyzerPlugin implements AnalyzerPlugin {

	@Override
	public boolean analyzeJar(Analyzer analyzer) throws Exception {
		boolean modified = false;

		for (AnalyzerPlugin analyzerPlugin : _analyzerPlugins) {
			if (analyzerPlugin.analyzeJar(analyzer)) {
				modified = true;
			}
		}

		return modified;
	}

	private final AnalyzerPlugin[] _analyzerPlugins = new AnalyzerPlugin[] {
		new ProvidesResourceBundleLoaderAnalyzerPlugin(),
		new AggregateResourceBundleLoaderAnalyzerPlugin()
	};

}