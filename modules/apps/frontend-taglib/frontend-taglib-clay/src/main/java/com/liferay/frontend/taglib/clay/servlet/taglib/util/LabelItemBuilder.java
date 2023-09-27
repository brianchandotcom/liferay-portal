/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

import com.liferay.petra.function.UnsafeSupplier;

import java.util.Map;

/**
 * @author Hugo Huijser
 */
public class LabelItemBuilder {

	public static AfterPutDataStep putData(String key, String value) {
		LabelItemStep labelItemStep = new LabelItemStep();

		return labelItemStep.putData(key, value);
	}

	public static AfterPutDataStep putData(
		String key, UnsafeSupplier<String, Exception> valueUnsafeSupplier) {

		LabelItemStep labelItemStep = new LabelItemStep();

		return labelItemStep.putData(key, valueUnsafeSupplier);
	}

	public static AfterSetDataStep setData(Map<String, Object> data) {
		LabelItemStep labelItemStep = new LabelItemStep();

		return labelItemStep.setData(data);
	}

	public static AfterDismissibleStep setDismissible(boolean dismissible) {
		LabelItemStep labelItemStep = new LabelItemStep();

		return labelItemStep.setDismissible(dismissible);
	}

	public static AfterDismissibleStep setDismissible(
		UnsafeSupplier<Boolean, Exception> dismissibleUnsafeSupplier) {

		LabelItemStep labelItemStep = new LabelItemStep();

		return labelItemStep.setDismissible(dismissibleUnsafeSupplier);
	}

	public static AfterDisplayTypeStep setDisplayType(String displayType) {
		LabelItemStep labelItemStep = new LabelItemStep();

		return labelItemStep.setDisplayType(displayType);
	}

	public static AfterDisplayTypeStep setDisplayType(
		UnsafeSupplier<String, Exception> displayTypeUnsafeSupplier) {

		LabelItemStep labelItemStep = new LabelItemStep();

		return labelItemStep.setDisplayType(displayTypeUnsafeSupplier);
	}

	public static AfterLabelStep setLabel(String label) {
		LabelItemStep labelItemStep = new LabelItemStep();

		return labelItemStep.setLabel(label);
	}

	public static AfterLabelStep setLabel(
		UnsafeSupplier<String, Exception> labelUnsafeSupplier) {

		LabelItemStep labelItemStep = new LabelItemStep();

		return labelItemStep.setLabel(labelUnsafeSupplier);
	}

	public static AfterLargeStep setLarge(boolean large) {
		LabelItemStep labelItemStep = new LabelItemStep();

		return labelItemStep.setLarge(large);
	}

	public static AfterLargeStep setLarge(
		UnsafeSupplier<Boolean, Exception> largeUnsafeSupplier) {

		LabelItemStep labelItemStep = new LabelItemStep();

		return labelItemStep.setLarge(largeUnsafeSupplier);
	}

	public static AfterStatusStep setStatus(int status) {
		LabelItemStep labelItemStep = new LabelItemStep();

		return labelItemStep.setStatus(status);
	}

	public static AfterStatusStep setStatus(
		UnsafeSupplier<Integer, Exception> statusUnsafeSupplier) {

		LabelItemStep labelItemStep = new LabelItemStep();

		return labelItemStep.setStatus(statusUnsafeSupplier);
	}

	public static class LabelItemStep
		implements AfterDismissibleStep, AfterDisplayTypeStep, AfterLabelStep,
				   AfterLargeStep, AfterPutDataStep, AfterSetDataStep,
				   AfterStatusStep, BuildStep, DismissibleStep, DisplayTypeStep,
				   LabelStep, LargeStep, PutDataStep, SetDataStep, StatusStep {

		@Override
		public LabelItem build() {
			return _labelItem;
		}

		@Override
		public AfterPutDataStep putData(String key, String value) {
			_labelItem.putData(key, value);

			return this;
		}

		@Override
		public AfterPutDataStep putData(
			String key, UnsafeSupplier<String, Exception> valueUnsafeSupplier) {

			try {
				String value = valueUnsafeSupplier.get();

				if (value != null) {
					_labelItem.putData(key, value);
				}

				return this;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		@Override
		public AfterSetDataStep setData(Map<String, Object> data) {
			_labelItem.setData(data);

			return this;
		}

		@Override
		public AfterDismissibleStep setDismissible(boolean dismissible) {
			_labelItem.setDismissible(dismissible);

			return this;
		}

		@Override
		public AfterDismissibleStep setDismissible(
			UnsafeSupplier<Boolean, Exception> dismissibleUnsafeSupplier) {

			try {
				Boolean dismissible = dismissibleUnsafeSupplier.get();

				if (dismissible != null) {
					_labelItem.setDismissible(dismissible.booleanValue());
				}

				return this;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		@Override
		public AfterDisplayTypeStep setDisplayType(String displayType) {
			_labelItem.setDisplayType(displayType);

			return this;
		}

		@Override
		public AfterDisplayTypeStep setDisplayType(
			UnsafeSupplier<String, Exception> displayTypeUnsafeSupplier) {

			try {
				String displayType = displayTypeUnsafeSupplier.get();

				if (displayType != null) {
					_labelItem.setDisplayType(displayType);
				}

				return this;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		@Override
		public AfterLabelStep setLabel(String label) {
			_labelItem.setLabel(label);

			return this;
		}

		@Override
		public AfterLabelStep setLabel(
			UnsafeSupplier<String, Exception> labelUnsafeSupplier) {

			try {
				String label = labelUnsafeSupplier.get();

				if (label != null) {
					_labelItem.setLabel(label);
				}

				return this;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		@Override
		public AfterLargeStep setLarge(boolean large) {
			_labelItem.setLarge(large);

			return this;
		}

		@Override
		public AfterLargeStep setLarge(
			UnsafeSupplier<Boolean, Exception> largeUnsafeSupplier) {

			try {
				Boolean large = largeUnsafeSupplier.get();

				if (large != null) {
					_labelItem.setLarge(large.booleanValue());
				}

				return this;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		@Override
		public AfterStatusStep setStatus(int status) {
			_labelItem.setStatus(status);

			return this;
		}

		@Override
		public AfterStatusStep setStatus(
			UnsafeSupplier<Integer, Exception> statusTypeUnsafeSupplier) {

			try {
				Integer status = statusTypeUnsafeSupplier.get();

				if (status != null) {
					_labelItem.setStatus(status.intValue());
				}

				return this;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		private final LabelItem _labelItem = new LabelItem();

	}

	public interface AfterDismissibleStep
		extends BuildStep, DisplayTypeStep, LabelStep, LargeStep, StatusStep {
	}

	public interface AfterDisplayTypeStep
		extends BuildStep, LabelStep, LargeStep, StatusStep {
	}

	public interface AfterLabelStep extends BuildStep, LargeStep, StatusStep {
	}

	public interface AfterLargeStep extends BuildStep, StatusStep {
	}

	public interface AfterPutDataStep
		extends BuildStep, DismissibleStep, DisplayTypeStep, LabelStep,
				LargeStep, PutDataStep, SetDataStep, StatusStep {
	}

	public interface AfterSetDataStep
		extends BuildStep, DismissibleStep, DisplayTypeStep, LabelStep,
				LargeStep, StatusStep {
	}

	public interface AfterStatusStep extends BuildStep {
	}

	public interface BuildStep {

		public LabelItem build();

	}

	public interface DismissibleStep {

		public AfterDismissibleStep setDismissible(boolean dismissible);

		public AfterDismissibleStep setDismissible(
			UnsafeSupplier<Boolean, Exception> dismissibleUnsafeSupplier);

	}

	public interface DisplayTypeStep {

		public AfterDisplayTypeStep setDisplayType(String displayType);

		public AfterDisplayTypeStep setDisplayType(
			UnsafeSupplier<String, Exception> displayTypeUnsafeSupplier);

	}

	public interface LabelStep {

		public AfterLabelStep setLabel(String label);

		public AfterLabelStep setLabel(
			UnsafeSupplier<String, Exception> labelUnsafeSupplier);

	}

	public interface LargeStep {

		public AfterLargeStep setLarge(boolean large);

		public AfterLargeStep setLarge(
			UnsafeSupplier<Boolean, Exception> largeUnsafeSupplier);

	}

	public interface PutDataStep {

		public AfterPutDataStep putData(String key, String value);

		public AfterPutDataStep putData(
			String key, UnsafeSupplier<String, Exception> valueUnsafeSupplier);

	}

	public interface SetDataStep {

		public AfterSetDataStep setData(Map<String, Object> data);

	}

	public interface StatusStep {

		public AfterStatusStep setStatus(int status);

		public AfterStatusStep setStatus(
			UnsafeSupplier<Integer, Exception> statusUnsafeSupplier);

	}

}