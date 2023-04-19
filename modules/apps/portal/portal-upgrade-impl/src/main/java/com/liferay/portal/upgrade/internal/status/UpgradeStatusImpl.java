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

package com.liferay.portal.upgrade.internal.status;

import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.ReleaseManager;
import com.liferay.portal.kernel.upgrade.UpgradeStatus;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.apache.logging.log4j.ThreadContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Luis Ortiz
 */
@Component(service = {UpgradeStatus.class, UpgradeStatusImpl.class})
public class UpgradeStatusImpl implements UpgradeStatus {

	public void addErrorMessage(String loggerName, String message) {
		Map<String, Integer> messages = _errorMessages.computeIfAbsent(
			loggerName, key -> new ConcurrentHashMap<>());

		int occurrences = messages.computeIfAbsent(message, key -> 0);

		occurrences++;

		messages.put(message, occurrences);
	}

	public void addUpgradeProcessMessage(String loggerName, String message) {
		List<String> messages = _upgradeProcessMessages.computeIfAbsent(
			loggerName, key -> new ArrayList<>());

		messages.add(message);
	}

	public void addWarningMessage(String loggerName, String message) {
		Map<String, Integer> messages = _warningMessages.computeIfAbsent(
			loggerName, key -> new ConcurrentHashMap<>());

		int occurrences = messages.computeIfAbsent(message, key -> 0);

		occurrences++;

		messages.put(message, occurrences);
	}

	public void finish() {
		_state = _calculateState();
		_type = _calculateType();

		if (PropsValues.UPGRADE_LOG_CONTEXT_ENABLED) {
			ThreadContext.put("upgrade.type", _type);
			ThreadContext.put("upgrade.result", _state);
		}

		if (_log.isInfoEnabled()) {
			if (_type.equals("no upgrade")) {
				if (_state.equals("success")) {
					_log.info("No pending upgrades to run");
				}
				else {
					_log.info(
						"Upgrade process failed or upgrade dependencies are " +
							"not resolved");
				}
			}
			else {
				_log.info(
					StringBundler.concat(
						StringUtil.toUpperCase(_type.substring(0, 1)),
						_type.substring(1), " upgrade finished with state ",
						_state));
			}
		}

		if (PropsValues.UPGRADE_LOG_CONTEXT_ENABLED) {
			ThreadContext.clearMap();
		}
	}

	public Map<String, Map<String, Integer>> getErrorMessages() {
		return _filter(_errorMessages);
	}

	public String getFinalSchemaVersion(String servletContextName) {
		SchemaVersions schemaVersions = _schemaVersionsMap.get(
			servletContextName);

		if (schemaVersions == null) {
			return null;
		}

		return schemaVersions._getFinal();
	}

	public String getInitialSchemaVersion(String servletContextName) {
		SchemaVersions schemaVersions = _schemaVersionsMap.get(
			servletContextName);

		if (schemaVersions == null) {
			return null;
		}

		return schemaVersions._getInitial();
	}

	@Override
	public String getState() {
		return _state;
	}

	@Override
	public String getType() {
		return _type;
	}

	public Map<String, ArrayList<String>> getUpgradeProcessMessages() {
		return _upgradeProcessMessages;
	}

	public Map<String, Map<String, Integer>> getWarningMessages() {
		return _filter(_warningMessages);
	}

	public void start() {
		_state = "running";

		_processRelease(
			(moduleSchemaVersions, schemaVersion) ->
				moduleSchemaVersions._setInitial(schemaVersion));
	}

	private String _calculateState() {
		boolean check;

		try {
			check = _releaseManager.getStatus();
		}
		catch (Exception exception) {
			_log.error(
				StringBundler.concat(
					"Unable to check the upgrade state due to ",
					exception.getMessage(), ". Please check manually."));

			return "failure";
		}

		if (!_errorMessages.isEmpty() || !check) {
			return "failure";
		}

		if (!_warningMessages.isEmpty()) {
			return "warning";
		}

		return "success";
	}

	private String _calculateType() {
		_processRelease(
			(moduleSchemaVersions, schemaVersion) ->
				moduleSchemaVersions._setFinal(schemaVersion));

		String type = "no upgrade";

		for (Map.Entry<String, SchemaVersions> schemaVersionsEntry :
				_schemaVersionsMap.entrySet()) {

			SchemaVersions schemaVersions = schemaVersionsEntry.getValue();

			if (schemaVersions._getInitial() == null) {
				continue;
			}

			Version initialVersion = Version.parseVersion(
				schemaVersions._getInitial());
			Version finalVersion = Version.parseVersion(
				schemaVersions._getFinal());

			if (initialVersion.equals(finalVersion)) {
				continue;
			}

			if (initialVersion.getMajor() < finalVersion.getMajor()) {
				return "major";
			}

			if (type.equals("minor")) {
				continue;
			}

			if (initialVersion.getMinor() < finalVersion.getMinor()) {
				type = "minor";

				continue;
			}

			type = "micro";
		}

		return type;
	}

	private Map<String, Map<String, Integer>> _filter(
		Map<String, Map<String, Integer>> messages) {

		for (String filteredClassName : _FILTERED_CLASS_NAMES) {
			messages.remove(filteredClassName);
		}

		return messages;
	}

	private void _processRelease(
		UnsafeBiConsumer<SchemaVersions, String, Exception> unsafeBiConsumer) {

		DataSource dataSource = InfrastructureUtil.getDataSource();

		if (dataSource == null) {
			return;
		}

		try (Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select servletContextName, schemaVersion from Release_")) {

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String servletContextName = resultSet.getString(
					"servletContextName");
				String schemaVersion = resultSet.getString("schemaVersion");

				SchemaVersions moduleSchemaVersions = _schemaVersionsMap.get(
					servletContextName);

				if (moduleSchemaVersions == null) {
					moduleSchemaVersions = new SchemaVersions(null);

					_schemaVersionsMap.put(
						servletContextName, moduleSchemaVersions);
				}

				unsafeBiConsumer.accept(moduleSchemaVersions, schemaVersion);
			}
		}
		catch (SQLException sqlException) {
			_log.error("Unable to get schema version", sqlException);
		}
		catch (Exception exception) {
			_log.error("Unable to process Release_ table", exception);
		}
	}

	private static final String[] _FILTERED_CLASS_NAMES = {
		"com.liferay.portal.search.elasticsearch7.internal.sidecar." +
			"SidecarManager"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeStatusImpl.class);

	private final Map<String, Map<String, Integer>> _errorMessages =
		new ConcurrentHashMap<>();

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile ReleaseManager _releaseManager;

	private final Map<String, SchemaVersions> _schemaVersionsMap =
		new ConcurrentHashMap<>();
	private String _state =
		PropsValues.UPGRADE_DATABASE_AUTO_RUN || DBUpgrader.isUpgradeClient() ?
			"pending" : "not enabled";
	private String _type =
		PropsValues.UPGRADE_DATABASE_AUTO_RUN || DBUpgrader.isUpgradeClient() ?
			"pending" : "not enabled";
	private final Map<String, ArrayList<String>> _upgradeProcessMessages =
		new ConcurrentHashMap<>();
	private final Map<String, Map<String, Integer>> _warningMessages =
		new ConcurrentHashMap<>();

	private class SchemaVersions {

		public SchemaVersions(String initial) {
			_initial = initial;
		}

		private String _getFinal() {
			return _final;
		}

		private String _getInitial() {
			return _initial;
		}

		private void _setFinal(String aFinal) {
			_final = aFinal;
		}

		private void _setInitial(String initial) {
			if (initial == null) {
				_initial = "0.0.0";

				return;
			}

			_initial = initial;
		}

		private String _final;
		private String _initial;

	}

}