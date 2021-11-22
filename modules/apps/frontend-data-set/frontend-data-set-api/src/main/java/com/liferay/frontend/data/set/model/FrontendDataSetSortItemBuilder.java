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
public class FrontendDataSetSortItemBuilder {

	public static AfterDirectionStep setDirection(String direction) {
		FrontendDataSetSortItemStep frontendDataSetSortItemStep =
			new FrontendDataSetSortItemStep();

		return frontendDataSetSortItemStep.setDirection(direction);
	}

	public static AfterDirectionStep setDirection(
		UnsafeSupplier<String, Exception> directionUnsafeSupplier) {

		FrontendDataSetSortItemStep frontendDataSetSortItemStep =
			new FrontendDataSetSortItemStep();

		return frontendDataSetSortItemStep.setDirection(
			directionUnsafeSupplier);
	}

	public static AfterKeyStep setKey(String key) {
		FrontendDataSetSortItemStep frontendDataSetSortItemStep =
			new FrontendDataSetSortItemStep();

		return frontendDataSetSortItemStep.setKey(key);
	}

	public static AfterKeyStep setKey(
		UnsafeSupplier<String, Exception> keyUnsafeSupplier) {

		FrontendDataSetSortItemStep frontendDataSetSortItemStep =
			new FrontendDataSetSortItemStep();

		return frontendDataSetSortItemStep.setKey(keyUnsafeSupplier);
	}

	public static class FrontendDataSetSortItemStep
		implements AfterDirectionStep, AfterKeyStep, BuildStep, DirectionStep,
				   KeyStep {

		@Override
		public FrontendDataSetSortItem build() {
			return _frontendDataSetSortItem;
		}

		@Override
		public AfterDirectionStep setDirection(String direction) {
			_frontendDataSetSortItem.setDirection(direction);

			return this;
		}

		@Override
		public AfterDirectionStep setDirection(
			UnsafeSupplier<String, Exception> directionUnsafeSupplier) {

			try {
				String direction = directionUnsafeSupplier.get();

				if (direction != null) {
					_frontendDataSetSortItem.setDirection(direction);
				}

				return this;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		@Override
		public AfterKeyStep setKey(String key) {
			_frontendDataSetSortItem.setKey(key);

			return this;
		}

		@Override
		public AfterKeyStep setKey(
			UnsafeSupplier<String, Exception> keyUnsafeSupplier) {

			try {
				String key = keyUnsafeSupplier.get();

				if (key != null) {
					_frontendDataSetSortItem.setKey(key);
				}

				return this;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		private final FrontendDataSetSortItem _frontendDataSetSortItem =
			new FrontendDataSetSortItem();

	}

	public interface AfterDirectionStep extends BuildStep, KeyStep {
	}

	public interface AfterKeyStep extends BuildStep {
	}

	public interface BuildStep {

		public FrontendDataSetSortItem build();

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