/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.k8s.agent.internal.osgi.commands;

import com.liferay.osgi.util.osgi.commands.OSGiCommands;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.configuration.persistence.InMemoryOnlyConfigurationThreadLocal;
import com.liferay.portal.k8s.agent.internal.util.ConfigurationUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsValues;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Anna Zombori-Suszter
 */
@Component(
	property = {
		"osgi.command.function=list", "osgi.command.function=reload",
		"osgi.command.function=show", "osgi.command.scope=clientextensions"
	},
	service = OSGiCommands.class
)
public class ClientExtensionsOSGiCommands implements OSGiCommands {

	public void list(String... filterStrings)
		throws InvalidSyntaxException, IOException, PortalException {

		Configuration[] configurations = _getConfigurations(filterStrings);

		if (ArrayUtil.isEmpty(configurations)) {
			System.out.println("No configurations found.");

			return;
		}

		_printConfigurations(configurations);
	}

	public void reload(String pid) throws InvalidSyntaxException, IOException {
		Configuration configuration = _getConfiguration(pid);

		if (configuration == null) {
			System.out.println("No configuration found.");

			return;
		}

		Configuration reloadedConfiguration = _reloadConfiguration(
			configuration);

		System.out.println(
			"Reloaded configuration for " + reloadedConfiguration.getPid());
	}

	public void show(String pid) throws InvalidSyntaxException, IOException {
		Configuration configuration = _getConfiguration(pid);

		if (configuration == null) {
			System.out.println("No configuration found.");

			return;
		}

		System.out.println(_printConfiguration(configuration));
	}

	private String _formatProperties(Dictionary<String, Object> properties) {
		if (properties.isEmpty()) {
			return StringPool.BLANK;
		}

		List<String> lines = new ArrayList<>();

		List<String> sortedKeys = Collections.list(properties.keys());

		Collections.sort(sortedKeys);

		for (String key : sortedKeys) {
			Object value = properties.get(key);

			if (value instanceof String[]) {
				lines.add(key + ": ");

				for (String element : (String[])value) {
					lines.add("\t" + element);
				}

				continue;
			}

			String valueString = value.toString();

			if (key.equals("baseURL")) {
				valueString = valueString.replaceAll(
					Pattern.quote("${portalURL}"), _portal.getPathContext());
			}

			lines.add(StringBundler.concat(key, ": ", valueString));
		}

		lines.add(StringPool.BLANK);

		return StringUtil.merge(lines, StringPool.NEW_LINE);
	}

	private Configuration _getConfiguration(String pid)
		throws InvalidSyntaxException, IOException {

		Configuration[] configuration = _getConfigurations(
			"service.pid=" + pid);

		if (ArrayUtil.isEmpty(configuration)) {
			return null;
		}

		return configuration[0];
	}

	private Configuration[] _getConfigurations(String... filterStrings)
		throws InvalidSyntaxException, IOException {

		String deploymentFilterString =
			"(|(.cx.config.key=*)(.k8s.config.key=*))";

		if (filterStrings.length == 0) {
			return _configurationAdmin.listConfigurations(
				deploymentFilterString);
		}

		StringBundler sb = new StringBundler();

		for (String filterString : filterStrings) {
			String[] parts = filterString.split("=", 2);

			if (parts.length != 2) {
				System.out.println("Invalid filter: " + filterString);

				return null;
			}

			String key = parts[0];
			String value = parts[1];

			if (key.equals("deploymentType")) {
				if (value.equals("agent")) {
					deploymentFilterString = "(.k8s.config.key=*)";
				}

				if (value.equals("bundle")) {
					deploymentFilterString = "(.cx.config.key=*)";
				}

				continue;
			}

			if (key.equals("webId")) {
				key = "dxp.lxc.liferay.com.virtualInstanceId";

				if (Objects.equals(PropsValues.COMPANY_DEFAULT_WEB_ID, value)) {
					value = "default";
				}
			}

			sb.append(String.format("(%s=%s)", key, value));
		}

		return _configurationAdmin.listConfigurations(
			String.format("(&%s%s)", deploymentFilterString, sb));
	}

	private String _getConfigurationTableRow(
		Configuration configuration, String formatString) {

		Dictionary<String, Object> properties = configuration.getProperties();

		return String.format(
			formatString, configuration.getPid(), properties.get("name"),
			properties.get("type"),
			properties.get("dxp.lxc.liferay.com.virtualInstanceId"));
	}

	private String _printConfiguration(Configuration configuration) {
		return String.format(
			"\nPID: %s\nFactoryPID: %s\n Bundle location: %s\n%s",
			configuration.getPid(), configuration.getFactoryPid(),
			configuration.getBundleLocation(),
			_formatProperties(configuration.getProperties()));
	}

	private void _printConfigurations(Configuration[] configurations) {
		int idWidth = 150;
		int nameWidth = 40;
		int typeWidth = 20;
		int webIdWidth = 15;

		String formatString = StringBundler.concat(
			"| %-", idWidth, "s | %-", nameWidth, "s | %-", typeWidth, "s | %-",
			webIdWidth, "s |%n");

		System.out.printf(formatString, "pid", "name", "type", "webId");

		int totalWidth =
			idWidth + nameWidth + typeWidth + webIdWidth + (4 * 3) + 2;

		System.out.println("-".repeat(totalWidth));

		for (Configuration configuration : configurations) {
			System.out.println(
				_getConfigurationTableRow(configuration, formatString));
		}
	}

	private Configuration _reloadConfiguration(Configuration configuration)
		throws IOException {

		Dictionary<String, Object> originalProperties =
			configuration.getProperties();

		String originalPid = configuration.getPid();

		configuration.delete();

		try (SafeCloseable safeCloseable =
				InMemoryOnlyConfigurationThreadLocal.
					setInMemoryOnlyWithSafeCloseable(true)) {

			Configuration reloadedConfiguration =
				ConfigurationUtil.getConfiguration(
					_configurationAdmin, originalPid);

			reloadedConfiguration.update(originalProperties);

			return reloadedConfiguration;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private Portal _portal;

}