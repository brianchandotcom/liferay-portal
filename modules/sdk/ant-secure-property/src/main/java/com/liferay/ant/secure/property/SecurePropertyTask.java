/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ant.secure.property;

import com.liferay.jenkins.results.parser.SecretsUtil;

import org.apache.tools.ant.taskdefs.Property;

/**
 * @author Peter Yoo
 */
public class SecurePropertyTask extends Property {

	@Override
	protected void addProperty(String n, Object v) {
		if (SecretsUtil.isSecretProperty((String)v)) {
			super.addProperty(n, (Object)SecretsUtil.getSecret((String)v));

			return;
		}

		super.addProperty(n, v);
	}

}