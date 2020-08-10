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

package com.liferay.commerce.talend.job.deployer.salesforce.internal.job;

import com.liferay.commerce.talend.job.deployer.TalendJobFileProvider;
import com.liferay.commerce.talend.job.deployer.salesforce.configuration.SalesforceTalendJobConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.io.IOException;
import java.io.InputStream;

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
	configurationPid = "com.liferay.commerce.talend.job.deployer.configuration.SalesforceTalendJobConfiguration",
	immediate = true, property = "service.ranking:Integer=1",
	service = TalendJobFileProvider.class
)
public class SalesforceTalendJobFileProvider implements TalendJobFileProvider {

	@Override
	public List<InputStream> getJobFileInputStreams() throws IOException {
		List<InputStream> inputStreams = new ArrayList<>();

		Bundle bundle = _bundleContext.getBundle();

		String talendJobFilePath =
			_salesforceTalendjobConfiguration.salesforceTalendJobFilePath();

		Enumeration<URL> urls = bundle.findEntries(
			talendJobFilePath, "*.zip", true);

		if (urls != null) {
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();

				inputStreams.add(url.openStream());
			}
		}

		return inputStreams;
	}

	@Activate
	@Modified
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleContext = bundleContext;

		_salesforceTalendjobConfiguration = ConfigurableUtil.createConfigurable(
			SalesforceTalendJobConfiguration.class, properties);
	}

	@Deactivate
	protected void deactivate() {
		_bundleContext = null;
		_salesforceTalendjobConfiguration = null;
	}

	private BundleContext _bundleContext;
	private volatile SalesforceTalendJobConfiguration
		_salesforceTalendjobConfiguration;

}