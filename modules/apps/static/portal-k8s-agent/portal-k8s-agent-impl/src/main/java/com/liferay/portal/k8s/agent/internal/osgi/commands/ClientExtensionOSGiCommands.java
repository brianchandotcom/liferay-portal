/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsValues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

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
		"osgi.command.function=show", "osgi.command.scope=clientextension"
	},
	service = OSGiCommands.class
)
public class ClientExtensionOSGiCommands implements OSGiCommands {

	public void list(String... filterStrings) throws Exception {
		Configuration[] configurations = _getConfigurations(filterStrings);

		if (ArrayUtil.isEmpty(configurations)) {
			System.out.println(
				"Unable to find configuration for filters " +
					Arrays.toString(filterStrings));

			return;
		}

		_printConfigurations(configurations);
	}

	public void reload(String pid) throws Exception {
		Configuration configuration = _getConfiguration(pid);

		if (configuration == null) {
			System.out.println("Unable to find configuration for PID " + pid);

			return;
		}

		configuration = _reload(configuration);

		System.out.println(
			"Reloaded configuration for PID " + configuration.getPid());
	}

	public void show(String pid) throws Exception {
		Configuration configuration = _getConfiguration(pid);

		if (configuration == null) {
			System.out.println("Unable to find configuration for PID " + pid);

			return;
		}

		_printConfiguration(configuration);
	}

	private String _format(Dictionary<String, Object> properties) {
		if (properties.isEmpty()) {
			return StringPool.BLANK;
		}

		List<String> lines = new ArrayList<>();

		List<String> keys = Collections.list(properties.keys());

		Collections.sort(keys);

		for (String key : keys) {
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

	private Configuration _getConfiguration(String pid) throws Exception {
		Configuration[] configurations = _getConfigurations(
			"service.pid=" + pid);

		if (ArrayUtil.isEmpty(configurations)) {
			return null;
		}

		return configurations[0];
	}

	private Configuration[] _getConfigurations(String... filterStrings)
		throws Exception {

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

			sb.append("(");
			sb.append(key);
			sb.append("=");
			sb.append(value);
			sb.append(")");
		}

		return _configurationAdmin.listConfigurations(
			StringBundler.concat("(&", deploymentFilterString, sb, ")"));
	}

	private void _printConfiguration(Configuration configuration) {
		System.out.println(
			StringBundler.concat(
				"\nPID: ", configuration.getPid(), "\nFactoryPID: ",
				configuration.getFactoryPid(), "\nBundle location: ",
				configuration.getBundleLocation(), "\n",
				_format(configuration.getProperties())));
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

		int totalWidth = idWidth + nameWidth + typeWidth + webIdWidth + 14;

		System.out.println("-".repeat(totalWidth));

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			System.out.printf(
				formatString, configuration.getPid(), properties.get("name"),
				properties.get("type"),
				properties.get("dxp.lxc.liferay.com.virtualInstanceId"));
		}
	}

	private Configuration _reload(Configuration configuration)
		throws Exception {

		String pid = configuration.getPid();
		Dictionary<String, Object> properties = configuration.getProperties();

		configuration.delete();

		try (SafeCloseable safeCloseable =
				InMemoryOnlyConfigurationThreadLocal.
					setInMemoryOnlyWithSafeCloseable(true)) {

			configuration = ConfigurationUtil.getConfiguration(
				_configurationAdmin, pid);

			configuration.update(properties);

			return configuration;
		}
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private Portal _portal;

}