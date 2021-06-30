package com.liferay.remote.web.component.admin.web.internal.form;

import com.liferay.configuration.admin.definition.ConfigurationDDMFormDeclaration;
import com.liferay.remote.web.component.admin.web.configuration.RemoteWebComponentConfiguration;

import org.osgi.service.component.annotations.Component;

/**
 * @author Raymond Augé
 */
@Component(
	property = "configurationPid=com.liferay.remote.web.component.admin.web.configuration.RemoteWebComponentConfiguration",
	service = ConfigurationDDMFormDeclaration.class
)
public class RemoteWebComponentConfigurationForm
	implements ConfigurationDDMFormDeclaration {

	@Override
	public Class<?> getDDMFormClass() {
		return RemoteWebComponentConfiguration.class;
	}

}