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

package com.liferay.osb.faro.web.internal.activator;

import com.liferay.osb.faro.web.internal.application.AsahApplication;
import com.liferay.osb.faro.web.internal.application.ContactsApplication;
import com.liferay.osb.faro.web.internal.application.MainApplication;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Kong
 */
@Component(service = {})
public class ContactsActivator {

	@Activate
	protected void activate() throws Exception {
		addConfigurations();
	}

	protected void addConfigurations() throws Exception {

		// CXF

		Configuration cxfConfiguration = getConfiguration(
			_PID_CXF_ENDPOINT_PUBLISHER_CONFIGURATION, "contextPath");

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(
			"authVerifierProperties",
			new String[] {
				"auth.verifier.BasicAuthHeaderAuthVerifier.urls.includes=*",
				"auth.verifier.PortalSessionAuthVerifier.check.csrf.token=" +
					"false",
				"auth.verifier.PortalSessionAuthVerifier.urls.includes=*"
			});
		properties.put("contextPath", _CONTEXT_PATH);

		cxfConfiguration.update(properties);

		// REST

		Configuration restConfiguration = getConfiguration(
			_PID_REST_EXTENDER_CONFIGURATION, "contextPaths");

		properties = new Hashtable<>();

		properties.put("contextPaths", new String[] {_CONTEXT_PATH});
		properties.put(
			"jaxRsApplicationFilterStrings",
			new String[] {"(jaxrs.application=true)"});
		properties.put(
			"jaxRsServiceFilterStrings",
			new String[] {
				getServiceFilterString(AsahApplication.class.getName()),
				getServiceFilterString(ContactsApplication.class.getName()),
				getServiceFilterString(MainApplication.class.getName())
			});

		restConfiguration.update(properties);
	}

	protected Configuration getConfiguration(
			String pid, String contextPathPropertyName)
		throws Exception {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			getPidFilterString(pid));

		if (configurations != null) {
			for (Configuration configuration : configurations) {
				String contextPath = StringPool.BLANK;

				Dictionary<String, Object> properties =
					configuration.getProperties();

				Object contextPathObject = properties.get(
					contextPathPropertyName);

				if (contextPathObject instanceof String) {
					contextPath = (String)contextPathObject;
				}
				else if (contextPathObject instanceof String[]) {
					String[] contextPaths = (String[])contextPathObject;

					if (contextPaths.length > 0) {
						contextPath = contextPaths[0];
					}
					else {
						continue;
					}
				}

				if (contextPath.equals(_CONTEXT_PATH)) {
					return configuration;
				}
			}
		}

		return _configurationAdmin.createFactoryConfiguration(
			pid, StringPool.QUESTION);
	}

	protected String getPidFilterString(String pid) {
		StringBundler sb = new StringBundler(5);

		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(ConfigurationAdmin.SERVICE_FACTORYPID);
		sb.append(StringPool.EQUAL);
		sb.append(pid);
		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	protected String getServiceFilterString(String className) {
		StringBundler sb = new StringBundler(3);

		sb.append("(component.name=");
		sb.append(className);
		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	private static final String _CONTEXT_PATH = "/faro";

	private static final String _PID_CXF_ENDPOINT_PUBLISHER_CONFIGURATION =
		"com.liferay.portal.remote.cxf.common.configuration." +
			"CXFEndpointPublisherConfiguration";

	private static final String _PID_REST_EXTENDER_CONFIGURATION =
		"com.liferay.portal.remote.rest.extender.configuration." +
			"RestExtenderConfiguration";

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}