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

package com.liferay.lcs.messaging.analytics;

/**
 * @author Riccardo Ferrari
 */
public class ScreensAnalyticsEventsMessage extends AnalyticsEventsMessage {

	public static class Context extends AnalyticsEventsMessage.Context {

		public String getDeviceId() {
			return _deviceId;
		}

		public String getDeviceType() {
			return _deviceType;
		}

		public boolean isSignedIn() {
			return _signedIn;
		}

		public void setDeviceId(String deviceId) {
			_deviceId = deviceId;
		}

		public void setDeviceType(String deviceType) {
			_deviceType = deviceType;
		}

		public void setSignedIn(boolean signedIn) {
			_signedIn = signedIn;
		}

		private String _deviceId;
		private String _deviceType;
		private boolean _signedIn;

	}

	public static class Event extends AnalyticsEventsMessage.Event {

		public long getGroupId() {
			return _groupId;
		}

		public void setGroupId(long groupId) {
			_groupId = groupId;
		}

		private long _groupId;

	}

	public static class Referrer extends AnalyticsEventsMessage.Referrer {

		public String getElementId() {
			return _elementId;
		}

		public void setElementId(String elementId) {
			_elementId = elementId;
		}

		private String _elementId;

	}

}