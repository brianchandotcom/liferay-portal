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

package com.liferay.split.packages.test;

import aQute.bnd.header.OSGiHeader;
import aQute.bnd.header.Parameters;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.module.framework.ModuleFrameworkUtilAdapter;

import java.io.IOException;

import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

/**
 * @author Tom Wang
 */
@RunWith(Arquillian.class)
public class SplitPackagesTest {

	@Test
	public void testSplitPackage() throws IOException {
		Bundle frameworkBundle =
			(Bundle)ModuleFrameworkUtilAdapter.getFramework();

		BundleContext frameworkBundleContext =
			frameworkBundle.getBundleContext();

		Bundle[] frameworkBundles = frameworkBundleContext.getBundles();

		Map<Bundle, Set<ExportPackage>> bundlesMap = new HashMap<>();

		Map<ExportPackage, Set<String>> allowedSplitPackages =
			_getAllowedSplitPackages();

		for (Bundle bundle : frameworkBundles) {
			Set<ExportPackage> bundleExportPackages = _getBundleExportPackages(
				bundle);

			if (bundleExportPackages == null) {
				continue;
			}

			for (Map.Entry<Bundle, Set<ExportPackage>> entry :
					bundlesMap.entrySet()) {

				Set<ExportPackage> mapBundlePackages = new HashSet<>(
					entry.getValue());

				mapBundlePackages.retainAll(bundleExportPackages);

				if (!mapBundlePackages.isEmpty()) {
					_processDuplicatedPackages(
						entry.getKey(), mapBundlePackages,
						allowedSplitPackages, bundle);
				}
			}

			bundlesMap.put(bundle, bundleExportPackages);
		}
	}

	private Map<ExportPackage, Set<String>> _getAllowedSplitPackages()
		throws IOException {

		Map<ExportPackage, Set<String>> allowedSplitPackages =
			new HashMap<>();

		for (String splitPackagesLine :
				StringUtil.splitLines(
					StringUtil.read(
						SplitPackagesTest.class.getResourceAsStream(
							"AllowedSplitPackages.txt")))) {

			String[] splitPackagesParts = StringUtil.split(
				splitPackagesLine, ';');

			ExportPackage exportPackage = new ExportPackage(
				splitPackagesParts[0], splitPackagesParts[1]);

			allowedSplitPackages.put(
				exportPackage,
				SetUtil.fromArray(StringUtil.split(splitPackagesParts[2])));
		}

		return allowedSplitPackages;
	}

	private Set<ExportPackage> _getBundleExportPackages(Bundle bundle) {
		Dictionary<String, String> headers = bundle.getHeaders();

		String exportPackage = headers.get(Constants.EXPORT_PACKAGE);

		if (exportPackage == null) {
			return null;
		}

		Parameters parameters = OSGiHeader.parseHeader(exportPackage);

		Map<String, ? extends Map<String, String>> exportPackages =
			parameters.asMapMap();

		Set<ExportPackage> bundleExportPackages = new HashSet<>();

		for (Map.Entry<String, ? extends Map<String, String>> entry :
				exportPackages.entrySet()) {

			String packageName = entry.getKey();

			Map<String, String> attributes = entry.getValue();

			String version = attributes.get(Constants.VERSION_ATTRIBUTE);

			if (version == null) {
				version = "0.0";
			}

			bundleExportPackages.add(new ExportPackage(packageName, version));
		}

		return bundleExportPackages;
	}

	private void _processDuplicatedPackages(
		Bundle mapBundle, Collection<ExportPackage> duplicatedExportPackages,
		Map<ExportPackage, Set<String>> allowedSplitPackages,
		Bundle currentBundle) {

		for (ExportPackage duplicatedExportPackage : duplicatedExportPackages) {
			Set<String> symbolicNames = allowedSplitPackages.get(
				duplicatedExportPackage);

			if ((symbolicNames == null) ||
				!symbolicNames.contains(currentBundle.getSymbolicName())) {

				Assert.fail(
					"Detected split packages " + duplicatedExportPackage +
						" in " + mapBundle + " and " + currentBundle);
			}
		}
	}

	private class ExportPackage {

		@Override
		public boolean equals(Object obj) {
			ExportPackage exportPackage = (ExportPackage)obj;

			if (Objects.equals(_name, exportPackage._name) &&
				Objects.equals(_version, exportPackage._version)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hashCode = HashUtil.hash(0, _name);

			return HashUtil.hash(hashCode, _version);
		}

		@Override
		public String toString() {
			StringBundler sb = new StringBundler(5);

			sb.append("{name=");
			sb.append(_name);
			sb.append(", version=");
			sb.append(_version);
			sb.append("}");

			return sb.toString();
		}

		private ExportPackage(String name, String version) {
			_name = name;
			_version = version;
		}

		private final String _name;
		private final String _version;

	}

}