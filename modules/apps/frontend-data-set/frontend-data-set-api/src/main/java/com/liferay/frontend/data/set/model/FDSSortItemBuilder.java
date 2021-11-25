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

package com.liferay.frontend.data.set.model;

import com.liferay.petra.function.UnsafeSupplier;

/**
 * @author Hugo Huijser
 */
public class FDSSortItemBuilder {

	public static AfterDirectionStep setDirection(String direction) {
		FDSSortItemStep fdsSortItemStep = new FDSSortItemStep();

		return fdsSortItemStep.setDirection(direction);
	}

	public static AfterDirectionStep setDirection(
		UnsafeSupplier<String, Exception> directionUnsafeSupplier) {

		FDSSortItemStep fdsSortItemStep = new FDSSortItemStep();

		return fdsSortItemStep.setDirection(directionUnsafeSupplier);
	}

	public static AfterKeyStep setKey(String key) {
		FDSSortItemStep fdsSortItemStep = new FDSSortItemStep();

		return fdsSortItemStep.setKey(key);
	}

	public static AfterKeyStep setKey(
		UnsafeSupplier<String, Exception> keyUnsafeSupplier) {

		FDSSortItemStep fdsSortItemStep = new FDSSortItemStep();

		return fdsSortItemStep.setKey(keyUnsafeSupplier);
	}

	public static class FDSSortItemStep
		implements AfterDirectionStep, AfterKeyStep, BuildStep, DirectionStep,
				   KeyStep {

		@Override
		public FDSSortItem build() {
			return _fdsSortItem;
		}

		@Override
		public AfterDirectionStep setDirection(String direction) {
			_fdsSortItem.setDirection(direction);

			return this;
		}

		@Override
		public AfterDirectionStep setDirection(
			UnsafeSupplier<String, Exception> directionUnsafeSupplier) {

			try {
				String direction = directionUnsafeSupplier.get();

				if (direction != null) {
					_fdsSortItem.setDirection(direction);
				}

				return this;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		@Override
		public AfterKeyStep setKey(String key) {
			_fdsSortItem.setKey(key);

			return this;
		}

		@Override
		public AfterKeyStep setKey(
			UnsafeSupplier<String, Exception> keyUnsafeSupplier) {

			try {
				String key = keyUnsafeSupplier.get();

				if (key != null) {
					_fdsSortItem.setKey(key);
				}

				return this;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		private final FDSSortItem _fdsSortItem = new FDSSortItem();

	}

	public interface AfterDirectionStep extends BuildStep, KeyStep {
	}

	public interface AfterKeyStep extends BuildStep {
	}

	public interface BuildStep {

		public FDSSortItem build();

	}

	public interface DirectionStep {

		public AfterDirectionStep setDirection(String direction);

		public AfterDirectionStep setDirection(
			UnsafeSupplier<String, Exception> directionUnsafeSupplier);

	}

	public interface KeyStep {

		public AfterKeyStep setKey(String key);

		public AfterKeyStep setKey(
			UnsafeSupplier<String, Exception> keyUnsafeSupplier);

	}

}