/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.debug.internal.osgi.commands;

import com.liferay.osgi.util.osgi.commands.OSGiCommands;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleRequirement;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = {"osgi.command.function=listCap", "osgi.command.scope=system"},
	service = OSGiCommands.class
)
public class CapabilityOSGiCommands implements OSGiCommands {

	public void listCap(long bundlId, String... namespaces) {
		Bundle bundle = _bundleContext.getBundle(bundlId);

		if (bundle == null) {
			System.out.println("No such bundle with id : " + bundlId);

			return;
		}

		if (namespaces.length == 0) {
			namespaces = new String[] {null};
		}

		Map<String, Map.Entry<Set<BundleCapability>, Set<BundleRequirement>>>
			capabilities = new TreeMap<>();

		for (String namespace : namespaces) {
			_collectCapabilities(capabilities, bundle, namespace);
		}

		_printCapabilities(capabilities);
	}

	public void listCap(String... namespaces) {
		if (namespaces.length == 0) {
			namespaces = new String[] {null};
		}

		Map<String, Map.Entry<Set<BundleCapability>, Set<BundleRequirement>>>
			capabilities = new TreeMap<>();

		Bundle[] bundles = _bundleContext.getBundles();

		for (String namespace : namespaces) {
			for (Bundle bundle : bundles) {
				_collectCapabilities(capabilities, bundle, namespace);
			}
		}

		_printCapabilities(capabilities);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private void _collectCapabilities(
		Map<String, Map.Entry<Set<BundleCapability>, Set<BundleRequirement>>>
			capabilities,
		Bundle bundle, String namespace) {

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		for (BundleCapability bundleCapability :
				bundleWiring.getCapabilities(namespace)) {

			Map.Entry<Set<BundleCapability>, Set<BundleRequirement>> entry =
				capabilities.computeIfAbsent(
					bundleCapability.getNamespace(),
					key -> new AbstractMap.SimpleImmutableEntry<>(
						new HashSet<>(), new HashSet<>()));

			Set<BundleCapability> bundleCapabilities = entry.getKey();

			bundleCapabilities.add(bundleCapability);
		}

		for (BundleRequirement bundleRequirement :
				bundleWiring.getRequirements(namespace)) {

			Map.Entry<Set<BundleCapability>, Set<BundleRequirement>> entry =
				capabilities.computeIfAbsent(
					bundleRequirement.getNamespace(),
					key -> new AbstractMap.SimpleImmutableEntry<>(
						new HashSet<>(), new HashSet<>()));

			Set<BundleRequirement> bundleRequirements = entry.getValue();

			bundleRequirements.add(bundleRequirement);
		}
	}

	private void _printCapabilities(
		Map<String, Map.Entry<Set<BundleCapability>, Set<BundleRequirement>>>
			capabilities) {

		for (Map.Entry
				<String,
				 Map.Entry<Set<BundleCapability>, Set<BundleRequirement>>>
					entry1 : capabilities.entrySet()) {

			System.out.println(
				"\n==== namespace : " + entry1.getKey() + " ====\n");

			Map.Entry<Set<BundleCapability>, Set<BundleRequirement>> entry2 =
				entry1.getValue();

			Map<Bundle, List<BundleCapability>> bundleCapabilities =
				new TreeMap<>();

			for (BundleCapability bundleCapability : entry2.getKey()) {
				BundleRevision bundleRevision = bundleCapability.getRevision();

				List<BundleCapability> currentBundleCapabilities =
					bundleCapabilities.computeIfAbsent(
						bundleRevision.getBundle(), key -> new ArrayList<>());

				currentBundleCapabilities.add(bundleCapability);
			}

			System.out.println("Providers : ");

			for (Map.Entry<Bundle, List<BundleCapability>> entry3 :
					bundleCapabilities.entrySet()) {

				System.out.println("\t" + entry3.getKey());

				for (BundleCapability bundleCapability : entry3.getValue()) {
					System.out.println("\t\t" + bundleCapability);
				}
			}

			Map<Bundle, List<BundleRequirement>> bundleRequirements =
				new TreeMap<>();

			for (BundleRequirement bundleRequirement : entry2.getValue()) {
				BundleRevision bundleRevision = bundleRequirement.getRevision();

				List<BundleRequirement> currentBundleRequirements =
					bundleRequirements.computeIfAbsent(
						bundleRevision.getBundle(), key -> new ArrayList<>());

				currentBundleRequirements.add(bundleRequirement);
			}

			System.out.println("Consumers : ");

			for (Map.Entry<Bundle, List<BundleRequirement>> entry4 :
					bundleRequirements.entrySet()) {

				System.out.println("\t" + entry4.getKey());

				for (BundleRequirement bundleRequirement : entry4.getValue()) {
					System.out.println("\t\t" + bundleRequirement);
				}
			}
		}
	}

	private BundleContext _bundleContext;

}