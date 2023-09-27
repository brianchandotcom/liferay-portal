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
 * configuration for multiple portlets.
 *
 * @author Neil Griffin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LiferayPortletConfigurations {

	/**
	 * An array of Liferay portlet configurations.
	 *
	 * @return An array of Liferay portlet configurations.
	 */
	LiferayPortletConfiguration[] value();

}