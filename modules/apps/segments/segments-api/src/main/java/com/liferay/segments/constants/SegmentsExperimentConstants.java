/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.constants;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.exception.SegmentsExperimentStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * @author Eduardo García
 * @author Sarai Díaz
 */
public class SegmentsExperimentConstants {

	public static final int NOTIFICATION_TYPE_UPDATE_STATUS = 0;

	public static final int STATUS_COMPLETED = 2;

	public static final int STATUS_DELETED_ON_DXP_ONLY = 8;

	public static final int STATUS_DRAFT = 0;

	public static final int STATUS_FINISHED_NO_WINNER = 4;

	public static final int STATUS_FINISHED_WINNER = 3;

	public static final int STATUS_PAUSED = 5;

	public static final int STATUS_RUNNING = 1;

	public static final int STATUS_SCHEDULED = 7;

	public static final int STATUS_TERMINATED = 6;

	public enum Goal {

		BOUNCE_RATE("bounce-rate"), CLICK_RATE("click"),
		MAX_SCROLL_DEPTH("max-scroll-depth"), TIME_ON_PAGE("time-on-page");

		public static Goal parse(String label) {
			if (Validator.isNull(label)) {
				return null;
			}

			for (Goal goal : values()) {
				if (label.equals(goal.getLabel())) {
					return goal;
				}
			}

			return null;
		}

		public String getLabel() {
			return _label;
		}

		private Goal(String label) {
			_label = label;
		}

		private final String _label;

	}

	public enum Status {

		COMPLETED(
			STATUS_COMPLETED, "COMPLETED", "completed", true, true, false, true,
			false),
		DELETED_ON_DXP_ONLY(
			STATUS_DELETED_ON_DXP_ONLY, "DELETED_ON_DXP_ONLY",
			"deleted-on-dxp-only", true, true),
		DRAFT(STATUS_DRAFT, "DRAFT", "draft", true, true) {

			@Override
			public Set<Status> validTransitions() {
				return SetUtil.fromCollection(
					Arrays.asList(Status.RUNNING, Status.SCHEDULED));
			}

		},
		FINISHED_NO_WINNER(
			STATUS_FINISHED_NO_WINNER, "FINISHED_NO_WINNER", "no-winner", true,
			false, true, false, true) {

			@Override
			public Set<Status> validTransitions() {
				return Collections.singleton(Status.COMPLETED);
			}

		},
		FINISHED_WINNER_DECLARED(
			STATUS_FINISHED_WINNER, "FINISHED_WINNER", "winner", true, false,
			true, true, true) {

			@Override
			public Set<Status> validTransitions() {
				return Collections.singleton(Status.COMPLETED);
			}

		},
		PAUSED(STATUS_PAUSED, "PAUSED", "paused", true, false) {

			@Override
			public Set<Status> validTransitions() {
				return Collections.singleton(Status.RUNNING);
			}

		},
		RUNNING(
			STATUS_RUNNING, "RUNNING", "running", false, false, true, false,
			true) {

			@Override
			public Set<Status> validTransitions() {
				return SetUtil.fromCollection(
					Arrays.asList(
						Status.FINISHED_NO_WINNER,
						Status.FINISHED_WINNER_DECLARED, Status.PAUSED,
						Status.TERMINATED));
			}

		},
		SCHEDULED(STATUS_SCHEDULED, "SCHEDULED", "scheduled", true, false) {

			@Override
			public Set<Status> validTransitions() {
				return Collections.singleton(Status.RUNNING);
			}

		},
		TERMINATED(
			STATUS_TERMINATED, "TERMINATED", "terminated", true, false, false,
			false, false);

		public static int[] getExclusiveStatusValues() {
			return ArrayUtil.toIntArray(
				TransformUtil.transformToList(
					Status.values(),
					status -> {
						if (status.isExclusive()) {
							return status.getValue();
						}

						return null;
					}));
		}

		public static int[] getLockedStatusValues() {
			return ArrayUtil.toIntArray(
				TransformUtil.transformToList(
					Status.values(),
					status -> {
						if (!status.isEditable()) {
							return status.getValue();
						}

						return null;
					}));
		}

		public static int[] getNonexclusiveStatusValues() {
			return ArrayUtil.toIntArray(
				TransformUtil.transformToList(
					Status.values(),
					status -> {
						if (!status.isExclusive()) {
							return status.getValue();
						}

						return null;
					}));
		}

		public static int[] getSplitStatusValues() {
			return ArrayUtil.toIntArray(
				TransformUtil.transformToList(
					Status.values(),
					status -> {
						if (status.isSplit()) {
							return status.getValue();
						}

						return null;
					}));
		}

		public static Status parse(int value) {
			for (Status status : values()) {
				if (status.getValue() == value) {
					return status;
				}
			}

			return null;
		}

		public static Status parse(String stringValue) {
			if (Validator.isNull(stringValue)) {
				return null;
			}

			for (Status status : values()) {
				if (stringValue.equals(status.toString())) {
					return status;
				}
			}

			return null;
		}

		public static void validateTransition(
				int fromStatusValue, int toStatusValue)
			throws SegmentsExperimentStatusException {

			Status fromStatus = Status.parse(fromStatusValue);

			if (fromStatus == null) {
				throw new SegmentsExperimentStatusException(
					"Invalid initial status value " + fromStatusValue);
			}

			Status toStatus = Status.parse(toStatusValue);

			if (toStatus == null) {
				throw new SegmentsExperimentStatusException(
					"Invalid final status value " + toStatusValue);
			}

			if (Objects.equals(fromStatus, toStatus)) {
				return;
			}

			Set<Status> validTransitions = fromStatus.validTransitions();

			if (!validTransitions.contains(toStatus)) {
				throw new SegmentsExperimentStatusException(
					String.format(
						"Invalid status transition: from %s to %s",
						fromStatus.name(), toStatus.name()));
			}
		}

		public static Status valueOf(int value) {
			for (Status status : values()) {
				if (status.getValue() == value) {
					return status;
				}
			}

			throw new IllegalArgumentException("Invalid status value " + value);
		}

		public String getLabel() {
			return _label;
		}

		public int getValue() {
			return _value;
		}

		public boolean isDeletable() {
			return _deletable;
		}

		public boolean isEditable() {
			return _editable;
		}

		public boolean isExclusive() {
			return _exclusive;
		}

		public boolean isSplit() {
			return _split;
		}

		public boolean requiresWinnerExperience() {
			return _requiresWinnerExperience;
		}

		@Override
		public String toString() {
			return _stringValue;
		}

		public Set<Status> validTransitions() {
			return Collections.emptySet();
		}

		private Status(
			int value, String stringValue, String label, boolean deletable,
			boolean editable) {

			_value = value;
			_stringValue = stringValue;
			_label = label;
			_deletable = deletable;
			_editable = editable;

			_exclusive = true;
			_requiresWinnerExperience = false;
			_split = false;
		}

		private Status(
			int value, String stringValue, String label, boolean deletable,
			boolean editable, boolean exclusive,
			boolean requiresWinnerExperience, boolean split) {

			_value = value;
			_stringValue = stringValue;
			_label = label;
			_deletable = deletable;
			_editable = editable;
			_exclusive = exclusive;
			_requiresWinnerExperience = requiresWinnerExperience;
			_split = split;
		}

		private final boolean _deletable;
		private final boolean _editable;
		private final boolean _exclusive;
		private final String _label;
		private final boolean _requiresWinnerExperience;
		private final boolean _split;
		private final String _stringValue;
		private final int _value;

	}

}