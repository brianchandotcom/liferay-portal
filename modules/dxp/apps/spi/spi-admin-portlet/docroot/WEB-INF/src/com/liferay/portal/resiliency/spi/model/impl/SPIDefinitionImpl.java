/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.resiliency.spi.model.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.resiliency.mpi.MPIHelperUtil;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.resiliency.spi.util.SPIAdminConstants;
import com.liferay.portal.resiliency.spi.util.SPIConfigurationTemplate;

import java.io.File;

import java.rmi.RemoteException;

/**
 * @author Michael C. Han
 */
public class SPIDefinitionImpl extends SPIDefinitionBaseImpl {

	@Override
	public void deleteBaseDir() {
		File baseDir = new File(getBaseDirName());

		FileUtil.deltree(baseDir);
	}

	@Override
	public String getAgentClassName() {
		return GetterUtil.getString(
			getTypeSettingsProperty("agent-class-name"));
	}

	@Override
	public String getBaseDir() {
		String baseDirName = getBaseDirName();

		File baseDir = new File(baseDirName);

		FileUtil.deltree(baseDir);

		if (!baseDir.mkdir()) {
			throw new SystemException(
				"Unable to create base directory " + baseDirName);
		}

		return baseDirName;
	}

	@Override
	public String getJavaExecutable() {
		return GetterUtil.getString(getTypeSettingsProperty("java-executable"));
	}

	@Override
	public int getMaxRestartAttempts() {
		return GetterUtil.getInteger(
			getTypeSettingsProperty("max-restart-attempts"), -1);
	}

	@Override
	public int getMaxThreads() {
		return GetterUtil.getInteger(getTypeSettingsProperty("max-threads"));
	}

	@Override
	public int getMinThreads() {
		return GetterUtil.getInteger(getTypeSettingsProperty("min-threads"));
	}

	@Override
	public String getNotificationRecipients() {
		return GetterUtil.getString(
			getTypeSettingsProperty("notification-recipients"));
	}

	@Override
	public long getPingInterval() {
		return GetterUtil.getLong(getTypeSettingsProperty("ping-interval"));
	}

	@Override
	public String getPortalProperties() {
		return GetterUtil.getString(
			getTypeSettingsProperty("portal-properties"));
	}

	@Override
	public long getRegisterTimeout() {
		return GetterUtil.getLong(getTypeSettingsProperty("register-timeout"));
	}

	@Override
	public int getRestartAttempts() {
		return GetterUtil.getInteger(
			getTypeSettingsProperty("restart-attempts"));
	}

	@Override
	public long getShutdownTimeout() {
		return GetterUtil.getLong(getTypeSettingsProperty("shutdown-timeout"));
	}

	@Override
	public SPI getSPI() {
		if (_spi == null) {
			_spi = MPIHelperUtil.getSPI(
				SPIConfigurationTemplate.getSPIProviderName(),
				String.valueOf(getSpiDefinitionId()));
		}

		return _spi;
	}

	@Override
	public String getStatusLabel() {
		return SPIAdminConstants.getStatusLabel(getStatus());
	}

	@Override
	public String getTypeSettings() {
		if (_typeSettingsUnicodeProperties == null) {
			return super.getTypeSettings();
		}

		return _typeSettingsUnicodeProperties.toString();
	}

	@Override
	public UnicodeProperties getTypeSettingsProperties() {
		if (_typeSettingsUnicodeProperties == null) {
			_typeSettingsUnicodeProperties = new UnicodeProperties(true);

			_typeSettingsUnicodeProperties.fastLoad(super.getTypeSettings());
		}

		return _typeSettingsUnicodeProperties;
	}

	@Override
	public String getTypeSettingsProperty(String key) {
		UnicodeProperties typeSettingsUnicodeProperties =
			getTypeSettingsProperties();

		return typeSettingsUnicodeProperties.getProperty(key);
	}

	@Override
	public String getTypeSettingsProperty(String key, String defaultValue) {
		UnicodeProperties typeSettingsUnicodeProperties =
			getTypeSettingsProperties();

		return typeSettingsUnicodeProperties.getProperty(key, defaultValue);
	}

	@Override
	public boolean isAlive() {
		SPI spi = getSPI();

		if (spi == null) {
			return false;
		}

		try {
			return spi.isAlive();
		}
		catch (RemoteException remoteException) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Unable to determine whether " + getName() +
						" is alive or not",
					remoteException);
			}

			return false;
		}
	}

	@Override
	public void setMaxRestartAttempts(int maxRestartAttempts) {
		UnicodeProperties typeSettingsUnicodeProperties =
			getTypeSettingsProperties();

		if (maxRestartAttempts >= 0) {
			typeSettingsUnicodeProperties.setProperty(
				"max-restart-attempts", String.valueOf(maxRestartAttempts));
		}
		else {
			typeSettingsUnicodeProperties.remove("max-restart-attempts");
		}

		setTypeSettingsProperties(typeSettingsUnicodeProperties);
	}

	@Override
	public void setNotificationRecipients(String notificationRecipients) {
		UnicodeProperties typeSettingsUnicodeProperties =
			getTypeSettingsProperties();

		if (Validator.isNull(notificationRecipients)) {
			typeSettingsUnicodeProperties.remove("notification-recipients");
		}
		else {
			typeSettingsUnicodeProperties.setProperty(
				"notification-recipients", notificationRecipients);
		}

		setTypeSettingsProperties(typeSettingsUnicodeProperties);
	}

	@Override
	public void setPortalProperties(String portalProperties) {
		UnicodeProperties typeSettingsUnicodeProperties =
			getTypeSettingsProperties();

		if (Validator.isNull(portalProperties)) {
			typeSettingsUnicodeProperties.remove("portal-properties");
		}
		else {
			typeSettingsUnicodeProperties.setProperty(
				"portal-properties", portalProperties);
		}

		setTypeSettingsProperties(typeSettingsUnicodeProperties);
	}

	@Override
	public void setRestartAttempts(int restartAttempts) {
		UnicodeProperties typeSettingsUnicodeProperties =
			getTypeSettingsProperties();

		typeSettingsUnicodeProperties.setProperty(
			"restart-attempts", String.valueOf(restartAttempts));

		setTypeSettingsProperties(typeSettingsUnicodeProperties);
	}

	@Override
	public void setTypeSettings(String typeSettings) {
		_typeSettingsUnicodeProperties = null;

		super.setTypeSettings(typeSettings);
	}

	@Override
	public void setTypeSettingsProperties(
		UnicodeProperties typeSettingsUnicodeProperties) {

		_typeSettingsUnicodeProperties = typeSettingsUnicodeProperties;

		super.setTypeSettings(_typeSettingsUnicodeProperties.toString());
	}

	protected String getBaseDirName() {
		return System.getProperty("java.io.tmpdir") + File.separator +
			getSpiDefinitionId();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SPIDefinitionImpl.class);

	private SPI _spi;
	private UnicodeProperties _typeSettingsUnicodeProperties;

}