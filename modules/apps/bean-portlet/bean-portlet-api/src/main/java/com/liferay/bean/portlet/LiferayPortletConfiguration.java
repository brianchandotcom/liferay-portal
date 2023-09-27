/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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