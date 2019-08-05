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

package com.liferay.portal.split.packages.test;

import aQute.bnd.header.OSGiHeader;
import aQute.bnd.header.Parameters;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.SetUtil;
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
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class SplitPackagesTest {

	@Test
	public void testSplitPackages() throws IOException {
		Map<ExportPackage, Set<String>> allowedSplitPackageNames =
			_getAllowedSplitPackageNames();
		Map<Bundle, Set<ExportPackage>> exportPackagesMap = new HashMap<>();

		Bundle systemBundle = (Bundle)ModuleFrameworkUtilAdapter.getFramework();

		BundleContext bundleContext = systemBundle.getBundleContext();

		for (Bundle bundle : bundleContext.getBundles()) {
			Set<ExportPackage> exportPackages = _getExportPackages(bundle);

			if (exportPackages == null) {
				continue;
			}

			for (Map.Entry<Bundle, Set<ExportPackage>> entry :
					exportPackagesMap.entrySet()) {

				Set<ExportPackage> duplicateExportPackages = new HashSet<>(
					entry.getValue());

				duplicateExportPackages.retainAll(exportPackages);

				_processSplitPackages(
					bundle, entry.getKey(), duplicateExportPackages,
					allowedSplitPackageNames);
			}

			exportPackagesMap.put(bundle, exportPackages);
		}
	}

	private Map<ExportPackage, Set<String>> _getAllowedSplitPackageNames()
		throws IOException {

		Map<ExportPackage, Set<String>> allowedSplitPackageNames =
			new HashMap<>();

		String[] lines = StringUtil.splitLines(
			StringUtil.read(
				SplitPackagesTest.class.getResourceAsStream(
					"dependencies/allowed_split_packages.txt")));

		for (String line : lines) {
			String[] lineParts = StringUtil.split(line, StringPool.SEMICOLON);

			allowedSplitPackageNames.put(
				new ExportPackage(lineParts[0], lineParts[1]),
				SetUtil.fromArray(StringUtil.split(lineParts[2])));
		}

		return allowedSplitPackageNames;
	}

	private Set<ExportPackage> _getExportPackages(Bundle bundle) {
		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		String exportPackageName = headers.get(Constants.EXPORT_PACKAGE);

		if (exportPackageName == null) {
			return null;
		}

		Set<ExportPackage> exportPackages = new HashSet<>();

		Parameters parameters = OSGiHeader.parseHeader(exportPackageName);

		Map<String, ? extends Map<String, String>> exportPackagesMap =
			parameters.asMapMap();

		for (Map.Entry<String, ? extends Map<String, String>> entry :
				exportPackagesMap.entrySet()) {

			Map<String, String> attributes = entry.getValue();

			exportPackages.add(
				new ExportPackage(
					entry.getKey(),
					attributes.get(Constants.VERSION_ATTRIBUTE)));
		}

		return exportPackages;
	}

	private void _processSplitPackages(
		Bundle currentBundle, Bundle previousBundle,
		Collection<ExportPackage> duplicateExportPackages,
		Map<ExportPackage, Set<String>> allowedSplitPackageNames) {

		for (ExportPackage duplicateExportPackage : duplicateExportPackages) {
			boolean hasSplitPackages = false;

			Set<String> symbolicNames = allowedSplitPackageNames.get(
				duplicateExportPackage);

			if ((symbolicNames == null) ||
				!symbolicNames.contains(currentBundle.getSymbolicName()) ||
				!symbolicNames.contains(previousBundle.getSymbolicName())) {

				hasSplitPackages = true;
			}

			Assert.assertFalse(
				StringBundler.concat(
					"Detected split packages ", duplicateExportPackage, " in ",
					previousBundle, " and ", currentBundle),
				hasSplitPackages);
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