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

package com.liferay.portal.resiliency.spi.service.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.nio.intraband.rpc.IntrabandRPCUtil;
import com.liferay.portal.kernel.resiliency.PortalResiliencyException;
import com.liferay.portal.kernel.resiliency.mpi.MPIHelperUtil;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.provider.SPIProvider;
import com.liferay.portal.kernel.resiliency.spi.remote.SystemPropertiesProcessCallable;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.resiliency.spi.backgroundtask.StartSPIBackgroundTaskExecutor;
import com.liferay.portal.resiliency.spi.backgroundtask.StopSPIBackgroundTaskExecutor;
import com.liferay.portal.resiliency.spi.exception.DuplicateSPIDefinitionConnectorException;
import com.liferay.portal.resiliency.spi.exception.DuplicateSPIDefinitionException;
import com.liferay.portal.resiliency.spi.exception.InvalidDatabaseConfigurationException;
import com.liferay.portal.resiliency.spi.exception.InvalidSPIDefinitionConnectorException;
import com.liferay.portal.resiliency.spi.exception.SPIDefinitionActiveException;
import com.liferay.portal.resiliency.spi.model.SPIDefinition;
import com.liferay.portal.resiliency.spi.monitor.SPIDefinitionMonitorUtil;
import com.liferay.portal.resiliency.spi.service.ServletContextUtil;
import com.liferay.portal.resiliency.spi.service.base.SPIDefinitionLocalServiceBaseImpl;
import com.liferay.portal.resiliency.spi.util.SPIAdminConstants;
import com.liferay.portal.resiliency.spi.util.SPIConfigurationTemplate;

import java.io.Serializable;

import java.rmi.RemoteException;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.ServletContext;

/**
 * @author Michael C. Han
 */
public class SPIDefinitionLocalServiceImpl
	extends SPIDefinitionLocalServiceBaseImpl {

	@Override
	public SPIDefinition addSPIDefinition(
			long userId, String name, String connectorAddress,
			int connectorPort, String description, String jvmArguments,
			String portletIds, String servletContextNames, String typeSettings,
			ServiceContext serviceContext)
		throws PortalException {

		// SPI definition

		User user = userLocalService.getUser(userId);
		Date now = new Date();

		validatePortalConfigurations();
		validateName(user.getCompanyId(), name);
		validateConnector(connectorAddress, connectorPort);

		long spiDefinitionId = counterLocalService.increment();

		SPIDefinition spiDefinition = spiDefinitionPersistence.create(
			spiDefinitionId);

		spiDefinition.setCompanyId(user.getCompanyId());
		spiDefinition.setUserId(user.getUserId());
		spiDefinition.setUserName(user.getFullName());
		spiDefinition.setCreateDate(serviceContext.getCreateDate(now));
		spiDefinition.setModifiedDate(serviceContext.getModifiedDate(now));
		spiDefinition.setName(name);
		spiDefinition.setConnectorAddress(connectorAddress);
		spiDefinition.setConnectorPort(connectorPort);
		spiDefinition.setDescription(description);

		setJVMArguments(spiDefinition, jvmArguments);

		setPortletIdsAndServletContextNames(
			spiDefinition, spiDefinitionId, portletIds, servletContextNames);

		spiDefinition.setTypeSettings(normalizeTypeSettings(typeSettings));
		spiDefinition.setStatus(SPIAdminConstants.STATUS_STOPPED);
		spiDefinition.setExpandoBridgeAttributes(serviceContext);

		spiDefinition = spiDefinitionPersistence.update(spiDefinition);

		// Resources

		resourceLocalService.addModelResources(spiDefinition, serviceContext);

		return spiDefinition;
	}

	@Override
	public SPIDefinition deleteSPIDefinition(long spiDefinitionId)
		throws PortalException {

		SPIDefinition spiDefinition = spiDefinitionPersistence.findByPrimaryKey(
			spiDefinitionId);

		return deleteSPIDefinition(spiDefinition);
	}

	@Override
	public SPIDefinition deleteSPIDefinition(SPIDefinition spiDefinition)
		throws PortalException {

		// SPI definition

		spiDefinitionLocalService.stopSPI(spiDefinition.getSpiDefinitionId());

		spiDefinitionPersistence.remove(spiDefinition);

		// Resources

		resourceLocalService.deleteResource(
			spiDefinition, ResourceConstants.SCOPE_INDIVIDUAL);

		// Expando

		expandoRowLocalService.deleteRows(spiDefinition.getSpiDefinitionId());

		return spiDefinition;
	}

	@Override
	public Tuple getPortletIdsAndServletContextNames() {
		return getPortletIdsAndServletContextNames(0);
	}

	@Override
	public SPIDefinition getSPIDefinition(long spiDefinitionId)
		throws PortalException {

		return spiDefinitionPersistence.findByPrimaryKey(spiDefinitionId);
	}

	@Override
	public SPIDefinition getSPIDefinition(long companyId, String name)
		throws PortalException {

		return spiDefinitionPersistence.findByC_N(companyId, name);
	}

	@Override
	public List<SPIDefinition> getSPIDefinitions() {
		return spiDefinitionPersistence.findAll();
	}

	@Override
	public List<SPIDefinition> getSPIDefinitions(long companyId, int status) {
		return spiDefinitionPersistence.findByC_S(companyId, status);
	}

	@Override
	public List<SPIDefinition> getSPIDefinitions(
		long companyId, int[] statuses) {

		return spiDefinitionPersistence.findByC_S(companyId, statuses);
	}

	@Clusterable
	@Override
	public void startSPI(long spiDefinitionId) throws PortalException {
		SPIDefinition spiDefinition = spiDefinitionPersistence.findByPrimaryKey(
			spiDefinitionId);

		if (spiDefinition.isAlive()) {
			return;
		}

		String name = spiDefinition.getName();

		SPI spi = spiDefinition.getSPI();

		if (spi == null) {
			spi = createSPI(spiDefinition);
		}

		try {
			Properties properties = PropertiesUtil.load(
				spiDefinition.getPortalProperties());

			Map<String, String> propertiesMap = new HashMap<>();

			for (String propertyName : properties.stringPropertyNames()) {
				propertiesMap.put(
					"portal:".concat(propertyName),
					properties.getProperty(propertyName));
			}

			IntrabandRPCUtil.execute(
				spi.getRegistrationReference(),
				new SystemPropertiesProcessCallable(propertiesMap));

			spi.init();

			if (_log.isInfoEnabled()) {
				_log.info("Initialized SPI " + name);
			}

			String portalContextPath = PortalUtil.getPathContext();

			ServletContext portalServletContext = ServletContextPool.get(
				portalContextPath);

			String portalDirName = portalServletContext.getRealPath(
				portalContextPath);

			spi.addWebapp(portalContextPath, portalDirName);

			if (_log.isInfoEnabled()) {
				_log.info("Add portal " + portalDirName + " to SPI " + spi);
			}

			spi.start();

			if (_log.isInfoEnabled()) {
				_log.info("Started SPI " + spi);
			}

			SPIConfiguration spiConfiguration = spi.getSPIConfiguration();

			for (String servletContextName :
					spiConfiguration.getServletContextNames()) {

				ServletContext servletContext = ServletContextPool.get(
					servletContextName);

				String contextPath = servletContext.getContextPath();

				String pluginDirName = servletContext.getRealPath(
					StringPool.BLANK);

				spi.addWebapp(contextPath, pluginDirName);

				if (_log.isInfoEnabled()) {
					_log.info("Add plugin " + contextPath + " to SPI " + spi);
				}
			}

			spiDefinition.setStatus(SPIAdminConstants.STATUS_STARTED);
			spiDefinition.setStatusMessage(null);

			spiDefinition = spiDefinitionPersistence.update(spiDefinition);

			SPIDefinitionMonitorUtil.register(spiDefinition);
		}
		catch (Exception exception) {
			throw new PortalException(
				"Unable to initialize SPI " + spiDefinitionId, exception);
		}
	}

	@Override
	public long startSPIinBackground(long userId, long spiDefinitionId)
		throws PortalException {

		return startSPIinBackground(userId, spiDefinitionId, false);
	}

	@Override
	public long startSPIinBackground(
			long userId, long spiDefinitionId, boolean automatedRestart)
		throws PortalException {

		SPIDefinition spiDefinition = spiDefinitionPersistence.findByPrimaryKey(
			spiDefinitionId);

		UnicodeProperties typeSettingsProperties =
			spiDefinition.getTypeSettingsProperties();

		Map<String, Serializable> taskContextMap =
			HashMapBuilder.<String, Serializable>put(
				"spiDefinitionId", spiDefinitionId
			).build();

		StringBundler sb = new StringBundler(4);

		sb.append("start_");
		sb.append(spiDefinition.getName());
		sb.append("_");
		sb.append(System.currentTimeMillis());

		BackgroundTask backgroundTask =
			BackgroundTaskManagerUtil.addBackgroundTask(
				userId, 0, sb.toString(),
				new String[] {ServletContextUtil.getServletContextName()},
				StartSPIBackgroundTaskExecutor.class, taskContextMap,
				new ServiceContext());

		typeSettingsProperties.setProperty(
			"backgroundTaskId",
			String.valueOf(backgroundTask.getBackgroundTaskId()));

		spiDefinition.setTypeSettingsProperties(typeSettingsProperties);

		if (!automatedRestart && (spiDefinition.getRestartAttempts() > 0)) {
			spiDefinition.setRestartAttempts(0);
		}
		else {
			spiDefinition.deleteBaseDir();
		}

		spiDefinition.setStatus(SPIAdminConstants.STATUS_STARTING);
		spiDefinition.setStatusMessage(null);

		spiDefinitionPersistence.update(spiDefinition);

		return backgroundTask.getBackgroundTaskId();
	}

	@Clusterable
	@Override
	public void stopSPI(long spiDefinitionId) throws PortalException {
		SPIDefinitionMonitorUtil.unregister(spiDefinitionId);

		SPIDefinition spiDefinition = spiDefinitionPersistence.findByPrimaryKey(
			spiDefinitionId);

		try {
			SPI spi = spiDefinition.getSPI();

			if (spi == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No SPI with name " + spiDefinition.getName());
				}

				return;
			}

			if (spiDefinition.isAlive()) {
				spi.stop();

				spi.destroy();
			}

			spiDefinition.deleteBaseDir();

			spiDefinition.setStatusMessage(null);
		}
		catch (RemoteException remoteException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to stop SPI " + spiDefinitionId, remoteException);
			}

			spiDefinition.setStatusMessage(
				StackTraceUtil.getStackTrace(remoteException));
		}
		finally {
			spiDefinition.setStatus(SPIAdminConstants.STATUS_STOPPED);
		}

		spiDefinitionPersistence.update(spiDefinition);
	}

	@Override
	public long stopSPIinBackground(long userId, long spiDefinitionId)
		throws PortalException {

		SPIDefinition spiDefinition = spiDefinitionPersistence.findByPrimaryKey(
			spiDefinitionId);

		Map<String, Serializable> taskContextMap =
			HashMapBuilder.<String, Serializable>put(
				"spiDefinitionId", spiDefinitionId
			).build();

		StringBundler sb = new StringBundler(4);

		sb.append("stop_");
		sb.append(spiDefinition.getName());
		sb.append("_");
		sb.append(System.currentTimeMillis());

		BackgroundTask backgroundTask =
			BackgroundTaskManagerUtil.addBackgroundTask(
				userId, 0, sb.toString(),
				new String[] {ServletContextUtil.getServletContextName()},
				StopSPIBackgroundTaskExecutor.class, taskContextMap,
				new ServiceContext());

		long backgroundTaskId = backgroundTask.getBackgroundTaskId();

		spiDefinition.setStatus(SPIAdminConstants.STATUS_STOPPING);
		spiDefinition.setStatusMessage(null);

		UnicodeProperties typeSettingsProperties =
			spiDefinition.getTypeSettingsProperties();

		typeSettingsProperties.setProperty(
			"backgroundTaskId", String.valueOf(backgroundTaskId));

		spiDefinition.setTypeSettingsProperties(typeSettingsProperties);

		spiDefinitionPersistence.update(spiDefinition);

		return backgroundTaskId;
	}

	@Override
	public SPIDefinition updateSPIDefinition(
			long spiDefinitionId, int status, String statusMessage)
		throws PortalException {

		SPIDefinition spiDefinition = spiDefinitionPersistence.findByPrimaryKey(
			spiDefinitionId);

		spiDefinition.setStatus(status);
		spiDefinition.setStatusMessage(statusMessage);

		return spiDefinitionPersistence.update(spiDefinition);
	}

	@Override
	public SPIDefinition updateSPIDefinition(
			long userId, long spiDefinitionId, String connectorAddress,
			int connectorPort, String description, String jvmArguments,
			String portletIds, String servletContextNames, String typeSettings,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		SPIDefinition spiDefinition = spiDefinitionPersistence.findByPrimaryKey(
			spiDefinitionId);

		String spiDefinitionConnectorAddress =
			spiDefinition.getConnectorAddress();

		if (!spiDefinitionConnectorAddress.equals(connectorAddress) &&
			(spiDefinition.getConnectorPort() != connectorPort)) {

			validateConnector(connectorAddress, connectorPort);
		}

		if (spiDefinition.isAlive()) {
			throw new SPIDefinitionActiveException();
		}

		spiDefinition.setCompanyId(user.getCompanyId());
		spiDefinition.setUserId(user.getUserId());
		spiDefinition.setUserName(user.getFullName());
		spiDefinition.setModifiedDate(serviceContext.getModifiedDate(null));
		spiDefinition.setConnectorAddress(connectorAddress);
		spiDefinition.setConnectorPort(connectorPort);
		spiDefinition.setDescription(description);

		setJVMArguments(spiDefinition, jvmArguments);

		setPortletIdsAndServletContextNames(
			spiDefinition, spiDefinitionId, portletIds, servletContextNames);

		spiDefinition.setTypeSettings(normalizeTypeSettings(typeSettings));
		spiDefinition.setExpandoBridgeAttributes(serviceContext);

		return spiDefinitionPersistence.update(spiDefinition);
	}

	@Override
	public SPIDefinition updateTypeSettings(
			long userId, long spiDefinitionId, String typeSettings,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		SPIDefinition spiDefinition = spiDefinitionPersistence.findByPrimaryKey(
			spiDefinitionId);

		spiDefinition.setModifiedDate(serviceContext.getModifiedDate(null));
		spiDefinition.setUserId(user.getUserId());
		spiDefinition.setUserName(user.getFullName());
		spiDefinition.setTypeSettings(typeSettings);

		return spiDefinitionPersistence.update(spiDefinition);
	}

	protected SPI createSPI(SPIDefinition spiDefinition)
		throws PortalException {

		SPIProvider spiProvider = MPIHelperUtil.getSPIProvider(
			SPIConfigurationTemplate.getSPIProviderName());

		SPIConfiguration spiConfiguration = new SPIConfiguration(
			String.valueOf(spiDefinition.getSpiDefinitionId()),
			spiDefinition.getJavaExecutable(), spiDefinition.getJvmArguments(),
			spiDefinition.getAgentClassName(), spiDefinition.getConnectorPort(),
			spiDefinition.getBaseDir(),
			StringUtil.split(spiDefinition.getPortletIds()),
			StringUtil.split(spiDefinition.getServletContextNames()),
			spiDefinition.getPingInterval(), spiDefinition.getRegisterTimeout(),
			spiDefinition.getShutdownTimeout(),
			getExtraSettings(spiDefinition));

		try {
			return spiProvider.createSPI(spiConfiguration);
		}
		catch (PortalResiliencyException portalResiliencyException) {
			throw new PortalException(portalResiliencyException);
		}
	}

	protected String getExtraSettings(SPIDefinition spiDefinition) {
		StringBundler sb = new StringBundler(5);

		sb.append("maxThreads=");
		sb.append(String.valueOf(spiDefinition.getMaxThreads()));
		sb.append("\n");
		sb.append("minThreads=");
		sb.append(String.valueOf(spiDefinition.getMinThreads()));

		return sb.toString();
	}

	protected Tuple getPortletIdsAndServletContextNames(
		long skipSPIDefinitionId) {

		Set<String> portletIds = new HashSet<>();
		Set<String> servletContextNames = new HashSet<>();

		List<SPIDefinition> spiDefinitions = getSPIDefinitions();

		for (SPIDefinition spiDefinition : spiDefinitions) {
			if (spiDefinition.getSpiDefinitionId() == skipSPIDefinitionId) {
				continue;
			}

			portletIds.addAll(
				SetUtil.fromArray(
					StringUtil.split(spiDefinition.getPortletIds())));
			servletContextNames.addAll(
				SetUtil.fromArray(
					StringUtil.split(spiDefinition.getServletContextNames())));
		}

		return new Tuple(portletIds, servletContextNames);
	}

	protected String normalizeTypeSettings(String typeSettings) {
		UnicodeProperties typeSettingsProperties = new UnicodeProperties(true);

		typeSettingsProperties.fastLoad(typeSettings);

		typeSettingsProperties.setProperty(
			"agent-class-name",
			SPIConfigurationTemplate.getSPIAgentClassName());

		String javaExecutable = GetterUtil.getString(
			typeSettingsProperties.getProperty("java-executable"));

		if (Validator.isNull(javaExecutable)) {
			typeSettingsProperties.setProperty(
				"java-executable",
				SPIConfigurationTemplate.getJavaExecutable());
		}

		int maxThreads = GetterUtil.getInteger(
			typeSettingsProperties.getProperty("max-threads"));

		if (maxThreads <= 0) {
			typeSettingsProperties.setProperty(
				"max-threads",
				String.valueOf(SPIConfigurationTemplate.getMaxThreads()));
		}

		int minThreads = GetterUtil.getInteger(
			typeSettingsProperties.getProperty("min-threads"));

		if (minThreads <= 0) {
			typeSettingsProperties.setProperty(
				"min-threads",
				String.valueOf(SPIConfigurationTemplate.getMinThreads()));
		}

		long pingInterval = GetterUtil.getLong(
			typeSettingsProperties.getProperty("ping-interval"));

		if (pingInterval <= 0) {
			typeSettingsProperties.setProperty(
				"ping-interval",
				String.valueOf(SPIConfigurationTemplate.getSPIPingInterval()));
		}

		long registerTimeout = GetterUtil.getLong(
			typeSettingsProperties.getProperty("register-timeout"));

		if (registerTimeout <= 0) {
			typeSettingsProperties.setProperty(
				"register-timeout",
				String.valueOf(
					SPIConfigurationTemplate.getSPIRegisterTimeout()));
		}

		long shutdownTimeout = GetterUtil.getLong(
			typeSettingsProperties.getProperty("shutdown-timeout"));

		if (shutdownTimeout <= 0) {
			typeSettingsProperties.setProperty(
				"shutdown-timeout",
				String.valueOf(
					SPIConfigurationTemplate.getSPIShutdownTimeout()));
		}

		return typeSettingsProperties.toString();
	}

	protected void setJVMArguments(
		SPIDefinition spiDefinition, String jvmArguments) {

		if (Validator.isNull(jvmArguments)) {
			jvmArguments = SPIConfigurationTemplate.getJVMArguments();
		}

		if (!jvmArguments.contains("-Duser.timezone=")) {
			TimeZone jvmDefaultTimeZone = TimeZone.getDefault();

			jvmArguments = jvmArguments.concat(
				" -Duser.timezone="
			).concat(
				jvmDefaultTimeZone.getID()
			);
		}

		spiDefinition.setJvmArguments(jvmArguments);
	}

	protected void setPortletIdsAndServletContextNames(
		SPIDefinition spiDefinition, long skipSPIDefinitionId,
		String portletIds, String servletContextNames) {

		Tuple portletIdsAndServletContextNames =
			getPortletIdsAndServletContextNames(skipSPIDefinitionId);

		Set<String> portletIdsSet = SetUtil.fromArray(
			StringUtil.split(portletIds));

		portletIdsSet.removeAll(
			(Set<String>)portletIdsAndServletContextNames.getObject(0));

		spiDefinition.setPortletIds(StringUtil.merge(portletIdsSet));

		Set<String> servletContextNamesSet = SetUtil.fromArray(
			StringUtil.split(servletContextNames));

		servletContextNamesSet.removeAll(
			(Set<String>)portletIdsAndServletContextNames.getObject(1));

		spiDefinition.setServletContextNames(
			StringUtil.merge(servletContextNamesSet));
	}

	protected void validateConnector(String connectorAddress, int connectorPort)
		throws PortalException {

		if (Validator.isNull(connectorAddress) || (connectorPort < 0)) {
			throw new InvalidSPIDefinitionConnectorException();
		}

		SPIDefinition spiDefinition = spiDefinitionPersistence.fetchByCA_CP(
			connectorAddress, connectorPort);

		if (spiDefinition != null) {
			throw new DuplicateSPIDefinitionConnectorException();
		}
	}

	protected void validateName(long companyId, String name)
		throws PortalException {

		SPIDefinition spiDefinition = spiDefinitionPersistence.fetchByC_N(
			companyId, name);

		if (spiDefinition != null) {
			throw new DuplicateSPIDefinitionException();
		}
	}

	protected void validatePortalConfigurations() throws PortalException {
		if (PropsUtil.contains(PropsKeys.JDBC_DEFAULT_JNDI_NAME)) {
			throw new InvalidDatabaseConfigurationException();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SPIDefinitionLocalServiceImpl.class);

}