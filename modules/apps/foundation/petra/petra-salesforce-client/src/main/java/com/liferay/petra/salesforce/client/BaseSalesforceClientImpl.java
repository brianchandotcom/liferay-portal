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
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Brian Wing Shun Chan
 * @author Peter Shin
 */
public class BaseSalesforceClientImpl implements BaseSalesforceClient {

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
	public void setUserName(String userName) {
		_userName = userName;
	}

	protected void afterPropertiesSet() {
		_connectorConfig = new ConnectorConfig();

		_connectorConfig.setAuthEndpoint(_authEndpoint);
		_connectorConfig.setConnectionTimeout(_connectionTimeout * 60000);
		_connectorConfig.setPassword(_password);
		_connectorConfig.setReadTimeout(_readTimeout * 60000);
		_connectorConfig.setUsername(_userName);

		if (_debugEnabled) {
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

			File file = new File(filePathName);

			if (!file.exists()) {
				File parentFile = file.getParentFile();

				parentFile.mkdirs();

				try {
					file.createNewFile();
				}
				catch (IOException ioe) {
					_logger.error("Unable to create log file", ioe);
				}
			}

			try {
				_connectorConfig.setTraceFile(filePathName);
			}
			catch (FileNotFoundException fnfe) {
				_logger.error("File not found", fnfe);
			}
		}
	}

	protected PartnerConnection getPartnerConnection()
		throws ConnectionException {

		if (_partnerConnection != null) {
			return _partnerConnection;
		}

		ConnectorConfig connectorConfig = getConnectorConfig();

		try {
			_partnerConnection = Connector.newConnection(connectorConfig);

			return _partnerConnection;
		}
		catch (ConnectionException ce1) {
			for (int i = 0; i < _SALESFORCE_CONNECTION_RETRY_COUNT; i++) {
				if (_logger.isInfoEnabled()) {
					_logger.info("Retrying new connection: {}", i + 1);
				}

				try {
					return Connector.newConnection(connectorConfig);
				}
				catch (ConnectionException ce2) {
					if ((i + 1) >= _SALESFORCE_CONNECTION_RETRY_COUNT) {
						throw ce2;
					}
				}
			}

			throw ce1;
		}
	}

	protected int getRetryCount(int retryCount) {
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

	protected int getRetryCount(
			int retryCount, AsyncApiException asyncApiException)
		throws AsyncApiException {

		if (retryCount <= 0) {
			throw asyncApiException;
		}

		return getRetryCount(retryCount);
	}

	protected int getRetryCount(
			int retryCount, ConnectionException connectionException)
		throws ConnectionException {

		if (retryCount <= 0) {
			throw connectionException;
		}

		return getRetryCount(retryCount);
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
	private String _userName;

}