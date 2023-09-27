/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.bean;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enables HTML auto escaping of strings returned by annotated methods when a
 * bean is wrapped with {@link AutoEscapeBeanHandler}.
 *
 * <p>
 * For a usage example, see {@link com.liferay.portal.kernel.model.UserModel}.
 * </p>
 *
 * @author Shuyang Zhou
 * @see    AutoEscapeBeanHandler
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AutoEscape {
}