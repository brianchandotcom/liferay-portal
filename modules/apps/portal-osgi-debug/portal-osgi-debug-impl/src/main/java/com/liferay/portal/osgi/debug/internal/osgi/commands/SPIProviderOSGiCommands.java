/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.debug.internal.osgi.commands;

import com.liferay.osgi.util.osgi.commands.OSGiCommands;
import com.liferay.portal.kernel.util.URLUtil;

import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = {
		"osgi.command.function=listSPIProviders", "osgi.command.scope=system"
	},
	service = OSGiCommands.class
)
public class SPIProviderOSGiCommands implements OSGiCommands {

	public void listSPIProviders(long bundleId, String... spiTypes)
		throws IOException {

		Bundle bundle = _bundleContext.getBundle(bundleId);

		if (bundle == null) {
			System.out.println("Invalid bundle ID: " + bundleId);

			return;
		}

		_print(
			bundle, bundle.findEntries("/META-INF/services/", null, true),
			new HashSet<>(Arrays.asList(spiTypes)));
	}

	public void listSPIProviders(String... spiTypes) throws IOException {
		Set<String> spiTypeSet = new HashSet<>(Arrays.asList(spiTypes));

		for (Bundle bundle : _bundleContext.getBundles()) {
			Enumeration<URL> enumeration = bundle.findEntries(
				"/META-INF/services/", null, true);

			if (enumeration != null) {
				_print(bundle, enumeration, spiTypeSet);
			}
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private boolean _matches(URL url, Set<String> spiTypeSet) {
		if (spiTypeSet.isEmpty()) {
			return true;
		}

		String path = url.getPath();

		int index = path.lastIndexOf('/');

		return spiTypeSet.contains(path.substring(index + 1));
	}

	private void _print(
			Bundle bundle, Enumeration<URL> enumeration, Set<String> spiTypeSet)
		throws IOException {

		List<URL> urls = new ArrayList<>();

		if (enumeration != null) {
			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				if (_matches(url, spiTypeSet)) {
					urls.add(url);
				}
			}
		}

		if (!urls.isEmpty()) {
			System.out.println(bundle + ":");

			for (URL url : urls) {
				System.out.println("\t" + url);
				System.out.println("\t\t" + URLUtil.toString(url));
			}
		}
	}

	private BundleContext _bundleContext;

}