package com.liferay.ant.secure.property;

import com.liferay.jenkins.results.parser.SecretsUtil;

import org.apache.tools.ant.taskdefs.Property;

public class SecurePropertyTask extends Property {

	@Override
	protected void addProperty(String n, Object v) {
		if (SecretsUtil.isSecretProperty((String)v)) {
			super.addProperty(n, SecretsUtil.getSecret((String)v));

			return;
		}

		super.addProperty(n, v);
	}
}