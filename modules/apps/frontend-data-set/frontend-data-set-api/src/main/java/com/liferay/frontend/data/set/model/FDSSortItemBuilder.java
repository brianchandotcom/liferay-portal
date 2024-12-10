/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.model;

import com.liferay.petra.function.UnsafeSupplier;

/**
 * @author Hugo Huijser
 */
public class FDSSortItemBuilder {

	public static AfterActiveStep setActive(Boolean active) {
		FDSSortItemStep fdsSortItemStep = new FDSSortItemStep();

		return fdsSortItemStep.setActive(active);
	}

	public static AfterActiveStep setActive(
		UnsafeSupplier<Boolean, Exception> activeUnsafeSupplier) {

		FDSSortItemStep fdsSortItemStep = new FDSSortItemStep();

		return fdsSortItemStep.setActive(activeUnsafeSupplier);
	}

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

	public static AfterLabelStep setLabel(String label) {
		FDSSortItemStep fdsSortItemStep = new FDSSortItemStep();

		return fdsSortItemStep.setLabel(label);
	}

	public static AfterLabelStep setLabel(
		UnsafeSupplier<String, Exception> labelUnsafeSupplier) {

		FDSSortItemStep fdsSortItemStep = new FDSSortItemStep();

		return fdsSortItemStep.setLabel(labelUnsafeSupplier);
	}

	public static class FDSSortItemStep
		implements ActiveStep, AfterActiveStep, AfterDirectionStep,
				   AfterKeyStep, AfterLabelStep, BuildStep, DirectionStep,
				   KeyStep, LabelStep {

		@Override
		public FDSSortItem build() {
			return _fdsSortItem;
		}

		@Override
		public AfterActiveStep setActive(Boolean active) {
			_fdsSortItem.setActive(active);

			return this;
		}

		@Override
		public AfterActiveStep setActive(
			UnsafeSupplier<Boolean, Exception> activeUnsafeSupplier) {

			try {
				Boolean active = activeUnsafeSupplier.get();

				if (active != null) {
					_fdsSortItem.setActive(active);
				}

				return this;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
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

		@Override
		public AfterLabelStep setLabel(String label) {
			_fdsSortItem.setLabel(label);

			return this;
		}

		@Override
		public AfterLabelStep setLabel(
			UnsafeSupplier<String, Exception> labelUnsafeSupplier) {

			try {
				String label = labelUnsafeSupplier.get();

				if (label != null) {
					_fdsSortItem.setLabel(label);
				}

				return this;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		private final FDSSortItem _fdsSortItem = new FDSSortItem();

	}

	public interface ActiveStep {

		public AfterActiveStep setActive(Boolean active);

		public AfterActiveStep setActive(
			UnsafeSupplier<Boolean, Exception> activeUnsafeSupplier);

	}

	public interface AfterActiveStep
		extends BuildStep, DirectionStep, KeyStep, LabelStep {
	}

	public interface AfterDirectionStep extends BuildStep, KeyStep, LabelStep {
	}

	public interface AfterKeyStep extends BuildStep, LabelStep {
	}

	public interface AfterLabelStep extends BuildStep {
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

	public interface LabelStep {

		public AfterLabelStep setLabel(String label);

		public AfterLabelStep setLabel(
			UnsafeSupplier<String, Exception> labelUnsafeSupplier);

	}

}