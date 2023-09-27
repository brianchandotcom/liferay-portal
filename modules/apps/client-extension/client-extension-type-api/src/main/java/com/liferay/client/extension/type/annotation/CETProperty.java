/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Gregory Amerson
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface CETProperty {

	String defaultValue() default "";

	String name() default "";

	String label() default "";

	Type type();

	public enum Type {

		Boolean, String, StringList, URL(true), URLList(true);

		public boolean isURL() {
			return _url;
		}

		private Type() {
			this(false);
		}

		private Type(boolean url) {
			_url = url;
		}

		private boolean _url;

	}

}