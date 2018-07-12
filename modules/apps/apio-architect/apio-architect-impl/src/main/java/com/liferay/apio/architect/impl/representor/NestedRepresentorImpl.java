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

package com.liferay.apio.architect.impl.representor;

import com.liferay.apio.architect.representor.NestedRepresentor;

/**
 * @author Alejandro Hernández
 */
public class NestedRepresentorImpl<T>
	extends BaseRepresentorImpl<T> implements NestedRepresentor<T> {

	@Override
	public boolean isNested() {
		return true;
	}

	/**
	 * Creates generic representations of your domain models that Apio
	 * hypermedia writers can understand.
	 *
	 * @param <T> the model's type
	 */
	public static class BuilderImpl<T>
		extends BaseBuilderImpl<T, NestedRepresentorImpl<T>>
		implements Builder<T> {

		public BuilderImpl() {
			super(new NestedRepresentorImpl<>());
		}

		@Override
		public FirstStep<T> types(String type, String... types) {
			baseRepresentor.addTypes(type, types);

			return new FirstStepImpl();
		}

		public class FirstStepImpl
			extends BaseFirstStepImpl<NestedRepresentor<T>, FirstStep<T>>
			implements FirstStep<T> {

			@Override
			public FirstStepImpl getThis() {
				return this;
			}

		}

	}

	private NestedRepresentorImpl() {
	}

}