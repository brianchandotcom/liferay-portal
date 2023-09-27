/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Michael C. Han
 */
public abstract class BaseConfigurationProvider<T>
	implements ConfigurationProvider<T> {

	protected String getMetatypeId() {
		if (_factoryPid != null) {
			return _factoryPid;
		}

		Class<T> metatype = getMetatype();

		Meta.OCD metaOCD = metatype.getAnnotation(Meta.OCD.class);

		if (metaOCD == null) {
			return null;
		}

		_factoryPid = metaOCD.id();

		if (_factoryPid == null) {
			_factoryPid = metatype.getName();
		}

		return _factoryPid;
	}

	private String _factoryPid;

}