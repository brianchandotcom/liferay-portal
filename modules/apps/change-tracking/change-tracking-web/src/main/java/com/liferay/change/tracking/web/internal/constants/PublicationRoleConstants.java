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

package com.liferay.change.tracking.web.internal.constants;

/**
 * @author Samuel Trong Tran
 */
public class PublicationRoleConstants {

	public static final String LABEL_ADMIN = "admin";

	public static final String LABEL_EDITOR = "editor";

	public static final String LABEL_PUBLISHER = "publisher";

	public static final String LABEL_VIEWER = "viewer";

	public static final String NAME_ADMIN =
		"com_liferay_change_tracking_web_portlet_PublicationsPortlet.admin";

	public static final String NAME_EDITOR =
		"com_liferay_change_tracking_web_portlet_PublicationsPortlet.editor";

	public static final String NAME_PUBLISHER =
		"com_liferay_change_tracking_web_portlet_PublicationsPortlet.publisher";

	public static final String NAME_VIEWER =
		"com_liferay_change_tracking_web_portlet_PublicationsPortlet.viewer";

	public static final int ROLE_ADMIN = 2;

	public static final int ROLE_EDITOR = 1;

	public static final int ROLE_PUBLISHER = 3;

	public static final int ROLE_VIEWER = 0;

	public static String getRoleName(int role) {
		if (role == ROLE_ADMIN) {
			return NAME_ADMIN;
		}
		else if (role == ROLE_EDITOR) {
			return NAME_EDITOR;
		}
		else if (role == ROLE_PUBLISHER) {
			return NAME_PUBLISHER;
		}

		return NAME_VIEWER;
	}

}