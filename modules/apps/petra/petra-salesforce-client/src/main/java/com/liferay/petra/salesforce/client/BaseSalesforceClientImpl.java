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

package com.liferay.petra.salesforce.client;

import com.sforce.async.AsyncApiException;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.GetUserInfoResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import com.sforce.ws.SessionRenewer;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Objects;

import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Brian Wing Shun Chan
 * @author Peter Shin
 * @author Rachael Koestartyo
 */
public abstract class BaseSalesforceClientImpl implements SalesforceClient {

	@Override
	public String getAuthEndpoint() {
		return _authEndpoint;
	}

	@Override
	public int getConnectionTimeout() {
		return _connectionTimeout;
	}

	@Override
	public ConnectorConfig getConnectorConfig() {
		if (_connectorConfig == null) {
			afterPropertiesSet();
		}

		return _connectorConfig;
	}

	@Override
	public String getPassword() {
		return _password;
	}

	@Override
	public int getReadTimeout() {
		return _readTimeout;
	}

	@Override
	public String getServiceEndpoint() {
		return _serviceEndpoint;
	}

	@Override
	public String getSessionId() {
		return _sessionId;
	}

	@Override
	public String getUserName() {
		return _userName;
	}

	@Override
	public boolean isDebugEnabled() {
		return _debugEnabled;
	}

	@Override
	public void setAuthEndpoint(String authEndpoint) {
		_authEndpoint = authEndpoint;
	}

	@Override
	public void setConnectionTimeout(int connectionTimeout) {
		_connectionTimeout = connectionTimeout;
	}

	@Override
	public void setDebugEnabled(boolean debugEnabled) {
		_debugEnabled = debugEnabled;
	}

	@Override
	public void setPassword(String password) {
		_password = password;
	}

	@Override
	public void setReadTimeout(int readTimeout) {
		_readTimeout = readTimeout;
	}

	@Override
	public void setServiceEndpoint(String serviceEndpoint) {
		_serviceEndpoint = serviceEndpoint;
	}

	@Override
	public void setSessionId(String sessionId) {
		_sessionId = sessionId;
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	protected void afterPropertiesSet() {
		_connectorConfig = new ConnectorConfig();

		_connectorConfig.setAuthEndpoint(_authEndpoint);
		_connectorConfig.setConnectionTimeout(_connectionTimeout * 60000);
		_connectorConfig.setReadTimeout(_readTimeout * 60000);
		_connectorConfig.setSessionRenewer(new SalesforceSessionRenewer());

		if (!Objects.isNull(_sessionId)) {
			_connectorConfig.setServiceEndpoint(_serviceEndpoint);
			_connectorConfig.setSessionId(_sessionId);
		}
		else {
			_connectorConfig.setPassword(_password);
			_connectorConfig.setUsername(_userName);
		}

		if (!_debugEnabled) {
			return;
		}

		_connectorConfig.setPrettyPrintXml(true);
		_connectorConfig.setTraceMessage(true);

		String baseDirName = System.getProperty("default.liferay.home");

		if (baseDirName == null) {
			baseDirName = System.getProperty("user.dir");
		}

		String filePathName = baseDirName + "/logs/salesforce.log";

		if (_logger.isInfoEnabled()) {
			_logger.info("Salesforce log file: {}", filePathName);
		}

		Path filePath = Paths.get(filePathName);

		if (!Files.exists(filePath)) {
			try {
				Path parentFilePath = filePath.getParent();

				if (!Files.exists(parentFilePath)) {
					Files.createDirectories(parentFilePath);
				}

				Files.createFile(filePath);
			}
			catch (IOException ioException) {
				_logger.error("Unable to create log file", ioException);
			}
		}

		try {
			_connectorConfig.setTraceFile(filePathName);
		}
		catch (FileNotFoundException fileNotFoundException) {
			_logger.error("File not found", fileNotFoundException);
		}
	}

	protected PartnerConnection getPartnerConnection()
		throws ConnectionException {

		return _getPartnerConnection(false);
	}

	protected int getRetryCount(
			int retryCount, AsyncApiException asyncApiException)
		throws AsyncApiException {

		if (retryCount <= 0) {
			throw asyncApiException;
		}

		return _getRetryCount(retryCount);
	}

	protected int getRetryCount(
			int retryCount, ConnectionException connectionException)
		throws ConnectionException {

		if (retryCount <= 0) {
			throw connectionException;
		}

		return _getRetryCount(retryCount);
	}

	private PartnerConnection _getPartnerConnection(boolean newConnection)
		throws ConnectionException {

		if (!newConnection && (_partnerConnection != null)) {
			try {
				GetUserInfoResult userInfoResult =
					_partnerConnection.getUserInfo();

				int sessionSecondsValid =
					userInfoResult.getSessionSecondsValid();

				if (sessionSecondsValid > 120) {
					return _partnerConnection;
				}

				if (_logger.isInfoEnabled()) {
					_logger.info(
						"Renewing session expiring in {} seconds",
						sessionSecondsValid);
				}
			}
			catch (ConnectionException connectionException) {
				if (_logger.isInfoEnabled()) {
					_logger.info("Session has expired and will be renewed now");
				}
			}

			_connectorConfig = null;
		}

		ConnectorConfig connectorConfig = getConnectorConfig();

		try {
			_partnerConnection = Connector.newConnection(connectorConfig);

			return _partnerConnection;
		}
		catch (ConnectionException connectionException1) {
			for (int i = 0; i < _SALESFORCE_CONNECTION_RETRY_COUNT; i++) {
				if (_logger.isInfoEnabled()) {
					_logger.info("Retrying new connection: {}", i + 1);
				}

				try {
					_partnerConnection = Connector.newConnection(
						connectorConfig);

					return _partnerConnection;
				}
				catch (ConnectionException connectionException2) {
					if ((i + 1) >= _SALESFORCE_CONNECTION_RETRY_COUNT) {
						throw connectionException2;
					}
				}
			}

			throw connectionException1;
		}
	}

	private int _getRetryCount(int retryCount) {
		retryCount--;

		if (_logger.isInfoEnabled()) {
			Thread thread = Thread.currentThread();

			StackTraceElement stackTraceElement = thread.getStackTrace()[3];

			_logger.info(
				"Retrying: {} ({})", stackTraceElement.getMethodName(),
				retryCount);
		}

		return retryCount;
	}

	private static final int _SALESFORCE_CONNECTION_RETRY_COUNT = 3;

	private static final Logger _logger = LoggerFactory.getLogger(
		BaseSalesforceClientImpl.class);

	private String _authEndpoint;
	private int _connectionTimeout = 1;
	private ConnectorConfig _connectorConfig;
	private boolean _debugEnabled;
	private PartnerConnection _partnerConnection;
	private String _password;
	private int _readTimeout = 1;
	private String _serviceEndpoint;
	private String _sessionId;
	private String _userName;

	private class SalesforceSessionRenewer implements SessionRenewer {

		@Override
		public SessionRenewalHeader renewSession(
				ConnectorConfig connectorConfig)
			throws ConnectionException {

			_partnerConnection = _getPartnerConnection(true);

			SessionRenewalHeader sessionRenewalHeader =
				new SessionRenewalHeader();

			sessionRenewalHeader.headerElement =
				_partnerConnection.getSessionHeader();
			sessionRenewalHeader.name = new QName(
				"urn:partner.soap.sforce.com", "SessionHeader");

			return sessionRenewalHeader;
		}

	}

}