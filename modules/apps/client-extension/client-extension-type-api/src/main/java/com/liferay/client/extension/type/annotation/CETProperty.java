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

	/**
	 * Configure the language label to use as the name of the property in the
	 * view client extension page.
	 *
	 * When left blank, the kebab case version of the field name is used.
	 *
	 * @review
	 */
	String label() default "";

	/**
	 * Describe the property data type.
	 *
	 * @review
	 */
	Type type();

	/**
	 * This enum describes property data types for CET fields.
	 *
	 * It can be used for multiple purposes like, for example, rendering the display
	 * of the data in the view client extension page.
	 *
	 * @author Iván Zaera Avellón
	 */
	public enum Type {

		Boolean, String, StringList, URL(true), URLList(true);

		/**
		 * Whether or not the values contained in the property are to be
		 * interpreted as URLs.
		 *
		 * URLs may have interpolation tokens inside them that must be replaced by
		 * their actual values during build or runtime.
		 *
		 * @review
		 */
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