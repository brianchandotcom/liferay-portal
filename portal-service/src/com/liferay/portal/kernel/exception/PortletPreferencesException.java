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

package com.liferay.portal.kernel.exception;

/**
 * @author Pei-Jung Lan
 */
public class PortletPreferencesException extends PortalException {

	public static class MustBeStrict extends PortletPreferencesException {

		public MustBeStrict(String portletId) {
			super(
				String.format(
					"Portlet preferences for portlet %s must be an instance " +
						"of StrictPortletPreferencesImpl",
					portletId));

			this.portletId = portletId;
		}

		public final String portletId;

	}

	private PortletPreferencesException(String msg) {
		super(msg);
	}

}