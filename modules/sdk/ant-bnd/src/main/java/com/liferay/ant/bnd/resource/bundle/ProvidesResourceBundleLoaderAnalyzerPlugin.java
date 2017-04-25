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

import aQute.bnd.header.Attrs;
import aQute.bnd.header.Parameters;
import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Constants;
import aQute.bnd.osgi.Jar;
import aQute.bnd.service.AnalyzerPlugin;

/**
 * @author Carlos Sierra Andrés
 * @author Gregory Amerson
 */
public class ProvidesResourceBundleLoaderAnalyzerPlugin
	implements AnalyzerPlugin {

	@Override
	public boolean analyzeJar(Analyzer analyzer) throws Exception {
		Jar jar = analyzer.getJar();

		if (!jar.exists("content/Language.properties")) {
			return false;
		}

		Parameters provideCapabilityHeaders = new SortedParameters(
			analyzer.getProperty(Constants.PROVIDE_CAPABILITY));

		Parameters parameters = new SortedParameters();

		Attrs attrs = new Attrs();

		attrs.put("bundle.symbolic.name", analyzer.getBsn());
		attrs.put("resource.bundle.base.name", "content.Language");

		parameters.add("liferay.resource.bundle", attrs);

		provideCapabilityHeaders.mergeWith(parameters, false);

		analyzer.setProperty(
			Constants.PROVIDE_CAPABILITY, provideCapabilityHeaders.toString());

		return true;
	}

}