package com.liferay.object.storage.salesforce.internal.client;

import com.liferay.object.storage.salesforce.internal.configuration.SalesforceConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

import java.util.Map;

@Component(
	configurationPid = "com.liferay.object.storage.salesforce.internal.configuration.SalesforceConfiguration",
	enabled = false, immediate = true,
	service = SalesforceClient.class
)
public class SalesforceClient {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_salesforceConfiguration = ConfigurableUtil.createConfigurable(
			SalesforceConfiguration.class, properties);
	}

	private volatile SalesforceConfiguration _salesforceConfiguration;
}
