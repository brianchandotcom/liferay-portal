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

package com.liferay.portal.workflow.metrics.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.workflow.metrics.internal.configuration.WorkflowMetricsConfiguration;

import java.util.Dictionary;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portal.workflow.metrics.internal.configuration.WorkflowMetricsConfiguration",
	service = ConfigurationModelListener.class
)
public class WorkflowMetricsConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		WorkflowMetricsConfiguration ddmFormWebConfiguration =
			ConfigurableUtil.createConfigurable(
				WorkflowMetricsConfiguration.class, new HashMapDictionary<>());

		try {
			int checkSLAJobInterval = GetterUtil.getInteger(
				properties.get("checkSLAJobInterval"),
				ddmFormWebConfiguration.checkSLAJobInterval());

			_validateCheckSLAJobInterval(checkSLAJobInterval);
		}
		catch (Exception e) {
			throw new ConfigurationModelListenerException(
				e.getMessage(), WorkflowMetricsConfiguration.class, getClass(),
				properties);
		}
	}

	private void _validateCheckSLAJobInterval(int checkSLAJobInterval)
		throws Exception {

		if (checkSLAJobInterval <= 0) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", LocaleThreadLocal.getThemeDisplayLocale(),
				getClass());

			String message = ResourceBundleUtil.getString(
				resourceBundle, "the-job-interval-must-be-greater-than-0");

			throw new Exception(message);
		}
	}

}