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

package com.liferay.bean.portlet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This is an annotation that provides a way to specify the Liferay-specific
 * configuration for a single portlet.
 *
 * @author Neil Griffin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LiferayPortletConfiguration {

	/**
	 * The portlet name that the portlet configuration is associated with.
	 *
	 * @return The portlet name that the portlet configuration is associated
	 *         with.
	 */
	public String portletName();

	/**
	 * The Liferay-specific portlet configuration. For more information see the
	 * documentation regarding the <a
	 * href="https://dev.liferay.com/develop/reference/-/knowledge_base/7-1/portlet-descriptor-to-osgi-service-property-map">
	 * Portlet Descriptor to OSGi Service Property Map</a>.
	 *
	 * @return The Liferay-specific portlet configuration.
	 */
	public String[] properties() default {};

}