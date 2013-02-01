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

package com.liferay.moduleadmin.internal;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.StringBundler;

import freemarker.ext.beans.BeansWrapper;

import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.jsp.PageContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;
import org.osgi.framework.startlevel.BundleStartLevel;
import org.osgi.framework.wiring.BundleRequirement;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;

/**
 * @author Raymond Augé
 */
public class ModuleUtil {

	public BundleStartLevel getBundleStartLevel(Bundle bundle) {
		return bundle.adapt(BundleStartLevel.class);
	}

	public BundleWiring getBundleWiring(Bundle bundle) {
		return bundle.adapt(BundleWiring.class);
	}

	public TemplateModel getHeaders(Bundle bundle, String languageId)
		throws TemplateModelException {

		Map<String, String> headerMap = new HashMap<String, String>();

		Dictionary<String, String> headers = bundle.getHeaders(languageId);

		Enumeration<String> keys = headers.keys();

		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String value = headers.get(key);

			headerMap.put(key, value);
		}

		BeansWrapper beansWrapper = new BeansWrapper();

		beansWrapper.setSimpleMapWrapper(true);

		return beansWrapper.wrap(headerMap);
	}

	@SuppressWarnings("rawtypes")
	public List<ServiceReference> getRegisteredServices(Bundle bundle) {
		ServiceReference[] serviceReferences = bundle.getRegisteredServices();

		if (serviceReferences == null) {
			serviceReferences = new ServiceReference[0];
		}

		return Arrays.asList(serviceReferences);
	}

	@SuppressWarnings("rawtypes")
	public List<ServiceReference> getServicesInUse(Bundle bundle) {
		ServiceReference[] serviceReferences = bundle.getServicesInUse();

		if (serviceReferences == null) {
			serviceReferences = new ServiceReference[0];
		}

		return Arrays.asList(serviceReferences);
	}

	public String processImports(PageContext pageContext, Bundle bundle)
		throws InvalidSyntaxException {

		StringBundler sb = new StringBundler();

		BundleWiring bundleWiring = getBundleWiring(bundle);

		List<BundleRequirement> requirements = bundleWiring.getRequirements(
			BundleRevision.PACKAGE_NAMESPACE);

		List<BundleWire> requiredWires = bundleWiring.getRequiredWires(
			BundleRevision.PACKAGE_NAMESPACE);

		for (BundleRequirement bundleRequirement : requirements) {
			Map<String, String> directives = bundleRequirement.getDirectives();

			String filterSpec = directives.get("filter");
			String resolution = directives.get("resolution");

			if (resolution.equals("dynamic")) {
				continue;
			}

			Matcher matcher = _pattern.matcher(filterSpec);

			if (matcher.matches()) {
				String packageName = matcher.group(1);

				sb.append(packageName);
			}
			else {
				System.out.println(
					"Somekind of error happened parsing: " + filterSpec);

				continue;
			}

			boolean satisfied = false;

			for (BundleWire bundleWire : requiredWires) {
				if (bundleRequirement.matches(bundleWire.getCapability())) {
					satisfied = true;

					BundleRevision revision =
						bundleWire.getProviderWiring().getRevision();
					Map<String, Object> attributes =
						bundleWire.getCapability().getAttributes();

					Version version = (Version)attributes.get("version");

					sb.append(" {");

					if (!version.equals(Version.emptyVersion)) {
						sb.append(version);
						sb.append(", ");
					}

					sb.append(revision.getSymbolicName());
					sb.append("[");
					sb.append(revision.getBundle().getBundleId());
					sb.append("]}");
				}
			}

			if (!satisfied) {
				sb.append(" <strong class='resolved'>");
				sb.append(LanguageUtil.get(pageContext, "un-resolved"));
				sb.append("</strong>");
			}

			if (resolution.equals("optional")) {
				sb.append(" <strong class='resolution'>");
				sb.append(LanguageUtil.get(pageContext, resolution));
				sb.append("</strong>");
			}

			sb.append("<br/> ");
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private Pattern _pattern = Pattern.compile(
		"\\(&\\(osgi\\.wiring\\.package=([\\w\\.\\*]+)\\)(\\(version>=?([\\w" +
		"\\.-]+)\\)|\\(&\\(version>=?([\\w\\.-]+)\\)\\(\\!\\(version>=?([\\w" +
		"\\.-]+)\\)\\)\\))?(.*)\\)");

}