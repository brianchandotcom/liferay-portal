/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.ldap.configuration;

import aQute.bnd.annotation.metatype.Meta;

import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Michael C. Han
 */
public abstract class BaseConfigurationProvider<T>
	implements ConfigurationProvider<T> {

	protected String getMetatypeId() {
		Class<T> metatype = getMetatype();

		Meta.OCD metaOCD = metatype.getAnnotation(Meta.OCD.class);

		if (metaOCD == null) {
			return null;
		}

		String id = metaOCD.id();

		if (id == null) {
			id = metatype.getName();
		}

		return id;
	}

	protected abstract void setConfigurationAdmin(
		ConfigurationAdmin configurationAdmin);

	protected ConfigurationAdmin configurationAdmin;
	protected String factoryPid;

}