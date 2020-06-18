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

package com.liferay.frontend.css.variables.web.internal.servlet.taglib;

import com.liferay.frontend.css.variables.ScopedCSSVariables;
import com.liferay.frontend.css.variables.ScopedCSSVariablesProvider;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = DynamicInclude.class)
public class ScopedCSSVariablesTopHeadDynamicInclude
	extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String dynamicIncludeKey)
		throws IOException {

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.print("<style data-senna-track=\"temporary\" ");
		printWriter.print("type=\"text/css\">\n");

		for (ScopedCSSVariablesProvider scopedCSSVariablesProvider :
				_scopedCssVariablesProviders) {

			_writeCSSVariables(
				printWriter,
				scopedCSSVariablesProvider.getScopedCSSVariablesCollection(
					httpServletRequest));
		}

		printWriter.print("</style>\n");
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_head.jsp#post");
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_scopedCssVariablesProviders = ServiceTrackerListFactory.open(
			bundleContext, ScopedCSSVariablesProvider.class,
			new PropertyServiceReferenceComparator<>("service.ranking"));
	}

	@Deactivate
	protected void deactivate() {
		_scopedCssVariablesProviders.close();

		_scopedCssVariablesProviders = null;
	}

	private void _writeCSSVariables(
		PrintWriter printWriter,
		Collection<ScopedCSSVariables> scopedCSSVariablesCollection) {

		for (ScopedCSSVariables scopedCSSVariables :
				scopedCSSVariablesCollection) {

			printWriter.print(scopedCSSVariables.getScope());
			printWriter.print(" {\n");

			Map<String, String> cssVariables =
				scopedCSSVariables.getCSSVariables();

			for (Map.Entry<String, String> entry : cssVariables.entrySet()) {
				printWriter.print("--");
				printWriter.print(entry.getKey());
				printWriter.print(": ");
				printWriter.print(entry.getValue());
				printWriter.print(";\n");
			}

			printWriter.print("}\n");
		}
	}

	private ServiceTrackerList
		<ScopedCSSVariablesProvider, ScopedCSSVariablesProvider>
			_scopedCssVariablesProviders;

}