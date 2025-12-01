/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.k8s.agent.internal.osgi.commands;

import com.liferay.osgi.util.osgi.commands.OSGiCommands;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.persistence.InMemoryOnlyConfigurationThreadLocal;
import com.liferay.portal.k8s.agent.internal.util.ConfigurationUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsValues;

import java.io.IOException;

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
		"osgi.command.function=show", "osgi.command.scope=cxconfig"
	},
	service = OSGiCommands.class
)
public class CXConfigOSGiCommands implements OSGiCommands {

	public void list(String... filters)
		throws InvalidSyntaxException, IOException, PortalException {

		Configuration[] cxConfigurations = _getConfigurations(filters);

		if ((cxConfigurations != null) && (cxConfigurations.length > 0)) {
			_printConfigurations(cxConfigurations);
		}
		else {
			System.out.println("No configurations found.");
		}
	}

	public void reload(String... args)
		throws InvalidSyntaxException, IOException {

		if (ArrayUtil.isEmpty(args)) {
			System.out.println("No PID provided.");
		}
		else if (args.length > 1) {
			System.out.println("Too many arguments.");
		}
		else {
			String pid = args[0];

			Configuration cxConfiguration = _getConfiguration(pid);

			if (cxConfiguration != null) {
				Configuration reloadedCxConfiguration = _reloadConfiguration(
					cxConfiguration);

				System.out.println(
					"Reloaded configuration for " +
						reloadedCxConfiguration.getPid());
			}
			else {
				System.out.println("No configuration found.");
			}
		}
	}

	public void show(String... args)
		throws InvalidSyntaxException, IOException {

		if (ArrayUtil.isEmpty(args)) {
			System.out.println("No PID provided.");
		}
		else if (args.length > 1) {
			System.out.println("Too many arguments.");
		}
		else {
			String pid = args[0];

			Configuration configuration = _getConfiguration(pid);

			if (configuration != null) {
				System.out.println(_printConfiguration(configuration));
			}
			else {
				System.out.println("No configuration found.");
			}
		}
	}

	private String _formatProperties(Dictionary<String, Object> properties) {
		if (!properties.isEmpty()) {
			List<String> sortedKeys = Collections.list(properties.keys());

			Collections.sort(sortedKeys);

			StringBundler sb = new StringBundler();

			for (String key : sortedKeys) {
				sb.append(key);

				sb.append(StringPool.COLON);
				sb.append(StringPool.SPACE);

				Object value = properties.get(key);

				if (value instanceof String[]) {
					sb.append(StringPool.NEW_LINE);

					for (String element : (String[])value) {
						sb.append(StringPool.TAB);
						sb.append(element);
						sb.append(StringPool.NEW_LINE);
					}
				}
				else {
					String valueString = value.toString();

					if (key.equals("baseURL")) {
						valueString = valueString.replaceAll(
							Pattern.quote("${portalURL}"),
							_portal.getPathContext());
					}

					sb.append(valueString);
					sb.append(StringPool.NEW_LINE);
				}
			}

			return sb.toString();
		}

		return "";
	}

	private Configuration _getConfiguration(String pid)
		throws InvalidSyntaxException, IOException {

		Configuration[] cxConfigurations = _getConfigurations();

		if (cxConfigurations != null) {
			for (Configuration cxConfiguration : cxConfigurations) {
				if (Objects.equals(cxConfiguration.getPid(), pid)) {
					return cxConfiguration;
				}
			}
		}

		return null;
	}

	private Configuration[] _getConfigurations(String... filters)
		throws InvalidSyntaxException, IOException {

		String deploymentFilter = "(|(.cx.config.key=*)(.k8s.config.key=*))";

		if (filters.length > 0) {
			StringBundler otherFiltersSB = new StringBundler();
			boolean deploymentFilterIsSet = false;

			for (String filter : filters) {
				String[] splitFilter = filter.split("=", 2);

				if (splitFilter.length == 2) {
					String key = splitFilter[0];
					String value = splitFilter[1];

					if (key.equals("deploymentType")) {
						if (!deploymentFilterIsSet) {
							if (value.equals("agent")) {
								deploymentFilter = "(.k8s.config.key=*)";
								deploymentFilterIsSet = true;
							}
							else if (value.equals("bundle")) {
								deploymentFilter = "(.cx.config.key=*)";
								deploymentFilterIsSet = true;
							}
						}
					}
					else if (key.equals("webId")) {
						String defaultCompanyWebId =
							PropsValues.COMPANY_DEFAULT_WEB_ID;

						otherFiltersSB.append(
							"(dxp.lxc.liferay.com.virtualInstanceId=");

						if (value.equals(defaultCompanyWebId)) {
							value = "default";
						}

						otherFiltersSB.append(
							value
						).append(
							")"
						);
					}
					else if (key.equals("type")) {
						otherFiltersSB.append(
							"(type="
						).append(
							value
						).append(
							")"
						);
					}
					else if (key.equals("name")) {
						otherFiltersSB.append(
							"(name="
						).append(
							value
						).append(
							")"
						);
					}
					else {
						return new Configuration[0];
					}
				}
				else {
					return new Configuration[0];
				}
			}

			String finalFilter = String.format(
				"(&%s%s)", deploymentFilter, otherFiltersSB);

			return _configurationAdmin.listConfigurations(finalFilter);
		}

		return _configurationAdmin.listConfigurations(deploymentFilter);
	}

	private String _getConfigurationTableRow(
		Configuration configuration, String format) {

		return String.format(
			format, configuration.getPid(),
			configuration.getProperties(
			).get(
				"name"
			),
			configuration.getProperties(
			).get(
				"type"
			),
			configuration.getProperties(
			).get(
				"dxp.lxc.liferay.com.virtualInstanceId"
			));
	}

	private String _printConfiguration(Configuration cxConfiguration) {
		StringBundler sb = new StringBundler(1);

		sb.append(
			StringPool.NEW_LINE
		).append(
			"PID: "
		).append(
			cxConfiguration.getPid()
		).append(
			StringPool.NEW_LINE
		).append(
			"Factory PID: "
		).append(
			cxConfiguration.getFactoryPid()
		).append(
			StringPool.NEW_LINE
		).append(
			"Bundle location: "
		).append(
			cxConfiguration.getBundleLocation()
		).append(
			StringPool.NEW_LINE
		).append(
			_formatProperties(cxConfiguration.getProperties())
		);

		return sb.toString();
	}

	private void _printConfigurations(Configuration[] configurations) {
		int idWidth = 150;
		int nameWidth = 40;
		int typeWidth = 20;
		int webIdWidth = 15;

		String format = StringBundler.concat(
			"| %-", idWidth, "s | %-", nameWidth, "s | %-", typeWidth, "s | %-",
			webIdWidth, "s |%n");

		System.out.printf(format, "pid", "name", "type", "webId");

		int totalWidth =
			idWidth + nameWidth + typeWidth + webIdWidth + (4 * 3) + 2;

		System.out.println("-".repeat(totalWidth));

		for (Configuration configuration : configurations) {
			System.out.println(
				_getConfigurationTableRow(configuration, format));
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

			Configuration reloadedCxConfiguration =
				ConfigurationUtil.getConfiguration(
					_configurationAdmin, originalPid);

			reloadedCxConfiguration.update(originalProperties);

			return reloadedCxConfiguration;
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