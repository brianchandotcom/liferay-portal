package com.liferay.portal.cluster.multiple.internal.jgroups.jmx;

import com.liferay.portal.cluster.multiple.internal.ClusterChannel;
import com.liferay.portal.cluster.multiple.internal.ClusterLinkImpl;
import com.liferay.portal.cluster.multiple.internal.jgroups.JGroupsClusterChannel;
import com.liferay.portal.kernel.cluster.ClusterLink;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.management.MBeanServer;

import org.jgroups.JChannel;
import org.jgroups.jmx.JmxConfigurator;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

@Component(
		immediate = true,
		service = JgroupsJMXBeansManager.class
	)
public class JgroupsJMXBeansManager {

	@Activate
	public void activate(Map<String, Object> properies) {
		
		_clusterEnabled = GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.CLUSTER_LINK_ENABLED));

		_clusterJmxEnabled = GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.CLUSTER_LINK_JMX_ENABLED));
		
		if(_clusterEnabled && _clusterJmxEnabled) {
			try {
				_registerAllChannels();
			}
			catch (Exception exception) {
				_log.error("Unable to register JGroups JMX beans", exception);
			}
		}
	}

	@Deactivate
	public void deactivate() {
		if(_clusterEnabled && _clusterJmxEnabled) {
			for (JChannel jChannel : _registeredChannels) {
				try {
					JmxConfigurator.unregisterChannel(
						jChannel, _mBeanServer, _domain, jChannel.getClusterName());
				}
				catch (Exception exception) {
					_log.warn(
						"Unable to unregister JGroups JMX beans for cluster " +
							jChannel.getClusterName(),
						exception);
				}
			}
	
			_registeredChannels.clear();
		}
	}

	private void _registerAllChannels() throws Exception {
		if (Validator.isNull(_clusterLink)) {
			_log.warn("ClusterLink is null");
			return;
		}

		if(!(_clusterLink instanceof ClusterLinkImpl)) {
			_log.warn("ClusterLink is not instance of ClusterLinkImpl");
			return;			
		}
		
		ClusterLinkImpl clusterLinkImpl = (ClusterLinkImpl) _clusterLink;
		
		List<ClusterChannel> clusterChannels = clusterLinkImpl.getChannels();

		if (clusterChannels.isEmpty()) {
			_log.warn("No cluster channels found");
			return;
		}

		for (ClusterChannel clusterChannel : clusterChannels) {
			if (clusterChannel == null) {
				continue;
			}

			if(clusterChannel instanceof JGroupsClusterChannel) {
				JGroupsClusterChannel jgroupsClusterChannel = (JGroupsClusterChannel) clusterChannel;
				JChannel jChannel = jgroupsClusterChannel.getJChannel();
				
				if (Validator.isNull(jChannel)) {
					_log.warn(
						"Unable to extract JChannel from Cluster Channel");
					continue;
				}

				_registerChannel(jChannel);
				
			} else {
				_log.warn("Cluster channel is not JGroups");
			}
			
		}
	}	

	private void _registerChannel(JChannel jChannel) throws Exception {
		String clusterName = jChannel.getClusterName();
		String logicalName = jChannel.getName();

		if (_log.isInfoEnabled()) {
			_log.info(
				"Registering JGroups JMX beans for cluster=" + clusterName +
					", logicalName=" + logicalName);
		}

		try {
			JmxConfigurator.registerChannel(
				jChannel, _mBeanServer, _domain, clusterName, true);

			_registeredChannels.add(jChannel);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Registered JGroups JMX beans for cluster=" + clusterName);
			}
		}
		catch (javax.management.InstanceAlreadyExistsException
					instanceAlreadyExistsException) {

			_log.info(
				"JGroups JMX beans already registered for cluster=" +
					clusterName);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JgroupsJMXBeansManager.class);

	protected boolean _clusterEnabled;
	protected boolean _clusterJmxEnabled;
	
	private final String _domain = "jgroups";
	private final MBeanServer _mBeanServer =
		ManagementFactory.getPlatformMBeanServer();
	private final List<JChannel> _registeredChannels = new ArrayList<>();

	@Reference
	private ClusterLink _clusterLink;
	
}
