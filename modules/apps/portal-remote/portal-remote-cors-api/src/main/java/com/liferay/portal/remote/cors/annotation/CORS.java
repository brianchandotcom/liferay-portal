/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.cors.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Carlos Sierra Andrés
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface CORS {

	public boolean allowCredentials() default true;

	public String[] allowHeaders() default {"*"};

	public String[] allowMethods() default {"*"};

	public String allowOrigin() default "*";

	public String[] exposeHeaders() default {"*"};

	public long maxAge() default 0;

}