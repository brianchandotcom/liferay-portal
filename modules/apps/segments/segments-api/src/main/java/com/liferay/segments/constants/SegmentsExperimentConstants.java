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

package com.liferay.segments.constants;

/**
 * @author Eduardo García
 * @author Sarai Díaz
 */
public class SegmentsExperimentConstants {

	public static final int STATUS_COMPLETED = 2;

	public static final int STATUS_DRAFT = 0;

	public static final int STATUS_FINISHED_NO_WINNER = 4;

	public static final int STATUS_FINISHED_WINNER = 3;

	public static final int STATUS_PAUSED = 5;

	public static final int STATUS_RUNNING = 1;

	public static final int STATUS_SCHEDULED = 7;

	public static final int STATUS_TERMINATED = 6;

	public enum Goal {

		BOUNCE_RATE("bounce-rate"), CLICK_RATE("click"),
		SCROLL_DEPTH("scroll-depth"), TIME_ON_PAGE("time-on-page");

		public static Goal parse(String label) {
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

		COMPLETED(STATUS_COMPLETED, "COMPLETED", "completed"),
		DRAFT(STATUS_DRAFT, "DRAFT", "draft"),
		FINISHED_NO_WINNER(
			STATUS_FINISHED_NO_WINNER, "FINISHED_NO_WINNER", "no-winner"),
		FINISHED_WINNER_DECLARED(
			STATUS_FINISHED_WINNER, "FINISHED_WINNER", "winner"),
		PAUSED(STATUS_PAUSED, "PAUSED", "paused"),
		RUNNING(STATUS_RUNNING, "RUNNING", "running"),
		SCHEDULED(STATUS_SCHEDULED, "SCHEDULED", "scheduled"),
		TERMINATED(STATUS_TERMINATED, "TERMINATED", "terminated");

		public static Status parse(int value) {
			for (Status status : values()) {
				if (status.getValue() == value) {
					return status;
				}
			}

			return null;
		}

		public static Status parse(String stringValue) {
			for (Status status : values()) {
				if (stringValue.equals(status.toString())) {
					return status;
				}
			}

			return null;
		}

		public String getLabel() {
			return _label;
		}

		public int getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _stringValue;
		}

		private Status(int value, String stringValue, String label) {
			_value = value;
			_stringValue = stringValue;
			_label = label;
		}

		private final String _label;
		private final String _stringValue;
		private final int _value;

	}

}