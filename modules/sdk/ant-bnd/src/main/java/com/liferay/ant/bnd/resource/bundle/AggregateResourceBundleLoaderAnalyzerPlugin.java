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
import aQute.bnd.service.AnalyzerPlugin;

import aQute.libg.filters.AndFilter;
import aQute.libg.filters.LiteralFilter;
import aQute.libg.filters.NotFilter;
import aQute.libg.filters.SimpleFilter;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Gregory Amerson
 */
public class AggregateResourceBundleLoaderAnalyzerPlugin
	implements AnalyzerPlugin {

	@Override
	public boolean analyzeJar(Analyzer analyzer) throws Exception {
		Parameters aggregateResourceBundlesParameters = new SortedParameters(
			analyzer.getProperty("-liferay-aggregate-resource-bundles"));

		if (aggregateResourceBundlesParameters.isEmpty()) {
			return false;
		}

		Parameters provideCapabilityHeaders = new SortedParameters(
			analyzer.getProperty(Constants.PROVIDE_CAPABILITY));

		Parameters requireCapabilityHeaders = new SortedParameters(
			analyzer.getProperty(Constants.REQUIRE_CAPABILITY));

		List<String> aggregateKeys = new ArrayList<>(
			aggregateResourceBundlesParameters.keySet());

		Collections.sort(aggregateKeys);

		for (String aggregateResourceBundlesParameter : aggregateKeys) {
			Attrs attrs = new Attrs();

			attrs.put(
				"filter:",
				new SimpleFilter(
					"bundle.symbolic.name",
					aggregateResourceBundlesParameter).toString());

			requireCapabilityHeaders.add("liferay.resource.bundle", attrs);
		}

		analyzer.setProperty(
			Constants.REQUIRE_CAPABILITY, requireCapabilityHeaders.toString());

		StringBuilder aggregateValue = new StringBuilder();

		AndFilter andFilter = new AndFilter();

		andFilter.addChild(
			new SimpleFilter("bundle.symbolic.name", analyzer.getBsn()));
		andFilter.addChild(
			new NotFilter(new LiteralFilter("(aggregate=true)")));
		andFilter.append(aggregateValue);

		for (String aggregateResourceBundlesParameter : aggregateKeys) {
			aggregateValue.append(",");

			new SimpleFilter(
				"bundle.symbolic.name",
				aggregateResourceBundlesParameter).append(aggregateValue);
		}

		Attrs provideAttrs = new Attrs();

		provideAttrs.put(
			"resource.bundle.aggregate:String", aggregateValue.toString());
		provideAttrs.put("bundle.symbolic.name", analyzer.getBsn());
		provideAttrs.put("resource.bundle.base.name", "content.Language");
		provideAttrs.put("service.ranking:Long", "1");
		provideAttrs.put("aggregate", "true");

		String servletContextName = analyzer.getProperty("Web-ContextPath");

		if (servletContextName != null) {
			try {
				Path servletContextPath = Paths.get(servletContextName);

				servletContextName = servletContextPath.subpath(
					0, 1).toString();
			}
			catch (Exception e) {

				// ignore

			}
		}

		if (servletContextName == null) {
			servletContextName = analyzer.getBsn();
		}

		provideAttrs.put("servlet.context.name", servletContextName);

		provideCapabilityHeaders.add("liferay.resource.bundle", provideAttrs);

		analyzer.setProperty(
			Constants.PROVIDE_CAPABILITY, provideCapabilityHeaders.toString());

		return true;
	}

}