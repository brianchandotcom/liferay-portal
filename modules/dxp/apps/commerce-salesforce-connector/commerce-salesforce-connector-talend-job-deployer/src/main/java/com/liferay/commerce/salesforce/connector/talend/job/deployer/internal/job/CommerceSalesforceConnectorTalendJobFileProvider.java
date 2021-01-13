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

package com.liferay.commerce.salesforce.connector.talend.job.deployer.internal.job;

import com.liferay.commerce.salesforce.connector.talend.job.deployer.configuration.CommerceSalesforceConnectorTalendJobConfiguration;
import com.liferay.commerce.talend.job.deployer.TalendJobFileProvider;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Danny Situ
 */
@Component(
	configurationPid = "com.liferay.commerce.salesforce.connector.talend.job.deployer.configuration.CommerceSalesforceConnectorTalendJobConfiguration",
	enabled = false, immediate = true, property = "service.ranking:Integer=1",
	service = TalendJobFileProvider.class
)
public class CommerceSalesforceConnectorTalendJobFileProvider
	implements TalendJobFileProvider {

	@Override
	public List<URL> getJobFileURLs() throws IOException {
		List<URL> urls = new ArrayList<>();

		Bundle bundle = _bundleContext.getBundle();

		String talendJobFilePath =
			_commerceSalesforceConnectorTalendJobConfiguration.
				salesforceTalendJobFilePath();

		Enumeration<URL> enumeration = bundle.findEntries(
			talendJobFilePath, "*.zip", true);

		if (enumeration != null) {
			while (enumeration.hasMoreElements()) {
				urls.add(enumeration.nextElement());
			}
		}

		return urls;
	}

	@Activate
	@Modified
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleContext = bundleContext;

		_commerceSalesforceConnectorTalendJobConfiguration =
			ConfigurableUtil.createConfigurable(
				CommerceSalesforceConnectorTalendJobConfiguration.class,
				properties);
	}

	@Deactivate
	protected void deactivate() {
		_bundleContext = null;
		_commerceSalesforceConnectorTalendJobConfiguration = null;
	}

	private BundleContext _bundleContext;
	private volatile CommerceSalesforceConnectorTalendJobConfiguration
		_commerceSalesforceConnectorTalendJobConfiguration;

}