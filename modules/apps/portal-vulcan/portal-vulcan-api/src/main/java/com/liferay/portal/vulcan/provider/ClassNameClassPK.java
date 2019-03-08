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

package com.liferay.portal.vulcan.provider;

import com.liferay.portal.kernel.model.ClassedModel;

/**
 * @author Javier Gamarra
 */
public interface ClassNameClassPK {

	public static ClassNameClassPK create(String className, long classPK) {
		return new ClassNameClassPK() {

			@Override
			public String getClassName() {
				return className;
			}

			@Override
			public long getClassPK() {
				return classPK;
			}

		};
	}

	public static <T extends ClassedModel> ClassNameClassPK create(T t) {
		return create(t.getModelClassName(), (long)t.getPrimaryKeyObj());
	}

	public String getClassName();

	public long getClassPK();

}