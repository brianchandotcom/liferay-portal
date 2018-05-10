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

package com.liferay.apio.architect.representor;

/**
 * Holds information about the metadata supported for a nested resource.
 *
 * <p>
 * Instances of this interface should always be created by using a {@link
 * NestedRepresentor.Builder}.
 * </p>
 *
 * @author Alejandro Hernández
 * @param  <T> the model's type
 * @see    NestedRepresentor.Builder
 */
public class NestedRepresentor<T> extends BaseRepresentor<T> {

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
	public static class Builder<T>
		extends BaseBuilder<T, NestedRepresentor<T>> {

		public Builder() {
			super(new NestedRepresentor<>());
		}

		/**
		 * Adds a type for the model.
		 *
		 * @param  type the type name
		 * @param  types the rest of the types
		 * @return the builder's step
		 */
		public FirstStep types(String type, String... types) {
			baseRepresentor.addTypes(type, types);

			return new FirstStep();
		}

		public class FirstStep extends BaseFirstStep<FirstStep> {

			@Override
			public FirstStep getThis() {
				return this;
			}

		}

	}

	private NestedRepresentor() {
	}

}