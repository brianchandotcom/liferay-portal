/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.monitoring.internal.jmx;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.monitoring.Level;
import com.liferay.portal.kernel.monitoring.MethodSignature;
import com.liferay.portal.kernel.monitoring.MonitoringControl;
import com.liferay.portal.kernel.monitoring.PortalMonitoringControl;
import com.liferay.portal.kernel.monitoring.PortletMonitoringControl;
import com.liferay.portal.kernel.monitoring.ServiceMonitoringControl;
import com.liferay.portal.monitoring.internal.configuration.MonitoringConfiguration;

import java.util.Map;
import java.util.Set;

import javax.management.DynamicMBean;
import javax.management.NotCompliantMBeanException;
import javax.management.StandardMBean;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
@Component(
	configurationPid = "com.liferay.portal.monitoring.internal.configuration.MonitoringConfiguration",
	enabled = false,
	property = {
		"jmx.objectname=com.liferay.portal.monitoring:classification=monitoring_service,name=MonitoringConfigurationManager",
		"jmx.objectname.cache.key=MonitoringProcessorManager"
	},
	service = DynamicMBean.class
)
public class MonitoringConfigurationManager
	extends StandardMBean implements MonitoringConfigurationManagerMBean {

	public MonitoringConfigurationManager() throws NotCompliantMBeanException {
		super(MonitoringConfigurationManagerMBean.class);
	}

	@Override
	public void addServiceClass(String className) {
		_serviceMonitoringControl.addServiceClass(className);
	}

	@Override
	public void addServiceClassMethod(
		String className, String methodName, String[] parameterTypes) {

		_serviceMonitoringControl.addServiceClassMethod(
			className, methodName, parameterTypes);
	}

	@Override
	public String getLevel(String namespace) {
		Level level = _monitoringControl.getLevel(namespace);

		if (level == null) {
			level = Level.OFF;
		}

		return level.toString();
	}

	@Override
	public String[] getNamespaces() {
		Set<String> namespaces = _monitoringControl.getNamespaces();

		return namespaces.toArray(new String[0]);
	}

	@Override
	public Set<String> getServiceClasses() {
		return _serviceMonitoringControl.getServiceClasses();
	}

	@Override
	public Set<MethodSignature> getServiceClassMethods() {
		return _serviceMonitoringControl.getServiceClassMethods();
	}

	@Override
	public boolean isInclusiveMode() {
		return _serviceMonitoringControl.isInclusiveMode();
	}

	@Override
	public boolean isMonitorPortalRequest() {
		return _portalMonitoringControl.isMonitorPortalRequest();
	}

	@Override
	public boolean isMonitorPortletActionRequest() {
		return _portletMonitoringControl.isMonitorPortletActionRequest();
	}

	@Override
	public boolean isMonitorPortletEventRequest() {
		return _portletMonitoringControl.isMonitorPortletEventRequest();
	}

	@Override
	public boolean isMonitorPortletHeaderRequest() {
		return _portletMonitoringControl.isMonitorPortletHeaderRequest();
	}

	@Override
	public boolean isMonitorPortletRenderRequest() {
		return _portletMonitoringControl.isMonitorPortletRenderRequest();
	}

	@Override
	public boolean isMonitorPortletResourceRequest() {
		return _portletMonitoringControl.isMonitorPortletResourceRequest();
	}

	@Override
	public boolean isMonitorServiceRequest() {
		return _serviceMonitoringControl.isMonitorServiceRequest();
	}

	@Override
	public void setInclusiveMode(boolean inclusiveMode) {
		_serviceMonitoringControl.setInclusiveMode(inclusiveMode);
	}

	@Override
	public void setLevel(String namespace, String levelName) {
		Level level = Level.valueOf(levelName);

		_monitoringControl.setLevel(namespace, level);
	}

	@Override
	public void setMonitorPortalRequest(boolean monitorPortalRequest) {
		_portalMonitoringControl.setMonitorPortalRequest(monitorPortalRequest);
	}

	@Override
	public void setMonitorPortletActionRequest(
		boolean monitorPortletActionRequest) {

		_portletMonitoringControl.setMonitorPortletActionRequest(
			monitorPortletActionRequest);
	}

	@Override
	public void setMonitorPortletEventRequest(
		boolean monitorPortletEventRequest) {

		_portletMonitoringControl.setMonitorPortletEventRequest(
			monitorPortletEventRequest);
	}

	@Override
	public void setMonitorPortletHeaderRequest(
		boolean monitoringPortletHeaderRequest) {

		_portletMonitoringControl.setMonitorPortletHeaderRequest(
			monitoringPortletHeaderRequest);
	}

	@Override
	public void setMonitorPortletRenderRequest(
		boolean monitorPortletRenderRequest) {

		_portletMonitoringControl.setMonitorPortletRenderRequest(
			monitorPortletRenderRequest);
	}

	@Override
	public void setMonitorPortletRequests(boolean monitorPortletRequests) {
		setMonitorPortletActionRequest(monitorPortletRequests);
		setMonitorPortletEventRequest(monitorPortletRequests);
		setMonitorPortletHeaderRequest(monitorPortletRequests);
		setMonitorPortletRenderRequest(monitorPortletRequests);
		setMonitorPortletResourceRequest(monitorPortletRequests);
	}

	@Override
	public void setMonitorPortletResourceRequest(
		boolean monitorPortletResourceRequest) {

		_portletMonitoringControl.setMonitorPortletResourceRequest(
			monitorPortletResourceRequest);
	}

	@Override
	public void setMonitorServiceRequest(boolean monitorServiceRequest) {
		_serviceMonitoringControl.setMonitorServiceRequest(
			monitorServiceRequest);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_monitoringConfiguration = ConfigurableUtil.createConfigurable(
			MonitoringConfiguration.class, properties);

		setMonitorPortalRequest(
			_monitoringConfiguration.monitorPortalRequest());
		setMonitorPortletActionRequest(
			_monitoringConfiguration.monitorPortletActionRequest());
		setMonitorPortletEventRequest(
			_monitoringConfiguration.monitorPortletEventRequest());
		setMonitorPortletHeaderRequest(
			_monitoringConfiguration.monitorPortletHeaderRequest());
		setMonitorPortletRenderRequest(
			_monitoringConfiguration.monitorPortletRenderRequest());
		setMonitorPortletResourceRequest(
			_monitoringConfiguration.monitorPortletResourceRequest());
		setMonitorServiceRequest(
			_monitoringConfiguration.monitorServiceRequest());
	}

	private volatile MonitoringConfiguration _monitoringConfiguration;

	@Reference
	private MonitoringControl _monitoringControl;

	@Reference
	private PortalMonitoringControl _portalMonitoringControl;

	@Reference
	private PortletMonitoringControl _portletMonitoringControl;

	@Reference
	private ServiceMonitoringControl _serviceMonitoringControl;

}