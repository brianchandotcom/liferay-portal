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

import com.liferay.change.tracking.constants.CTActionKeys;
import com.liferay.portal.kernel.security.permission.ActionKeys;

/**
 * @author Samuel Trong Tran
 */
public class PublicationRoleConstants {

	public static final String LABEL_EDIT = "edit";

	public static final String LABEL_PERMISSIONS = "permissions";

	public static final String LABEL_PUBLISH = "publish";

	public static final String LABEL_VIEW = "view";

	public static final String NAME_EDIT =
		"com_liferay_change_tracking_web_portlet_PublicationsPortlet.edit";

	public static final String NAME_PERMISSIONS =
		"com_liferay_change_tracking_web_portlet_PublicationsPortlet." +
			"permissions";

	public static final String NAME_PUBLISH =
		"com_liferay_change_tracking_web_portlet_PublicationsPortlet.publish";

	public static final String NAME_VIEW =
		"com_liferay_change_tracking_web_portlet_PublicationsPortlet.view";

	public static final int ROLE_EDIT = 1;

	public static final int ROLE_PERMISSIONS = 2;

	public static final int ROLE_PUBLISH = 3;

	public static final int ROLE_VIEW = 0;

	public static String[] getModelResourceActions(int role) {
		if (role == ROLE_EDIT) {
			return new String[] {ActionKeys.UPDATE, ActionKeys.VIEW};
		}
		else if (role == ROLE_PERMISSIONS) {
			return new String[] {
				ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW
			};
		}
		else if (role == ROLE_PUBLISH) {
			return new String[] {
				ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW,
				CTActionKeys.PUBLISH
			};
		}

		return new String[] {ActionKeys.VIEW};
	}

	public static String getNameLabel(String name) {
		if (name.equals(NAME_EDIT)) {
			return LABEL_EDIT;
		}
		else if (name.equals(NAME_PERMISSIONS)) {
			return LABEL_PERMISSIONS;
		}
		else if (name.equals(NAME_PUBLISH)) {
			return LABEL_PUBLISH;
		}

		return LABEL_VIEW;
	}

	public static int getNameRole(String name) {
		if (name.equals(NAME_EDIT)) {
			return ROLE_EDIT;
		}
		else if (name.equals(NAME_PERMISSIONS)) {
			return ROLE_PERMISSIONS;
		}
		else if (name.equals(NAME_PUBLISH)) {
			return ROLE_PUBLISH;
		}

		return ROLE_VIEW;
	}

	public static String getRoleName(int role) {
		if (role == ROLE_EDIT) {
			return NAME_EDIT;
		}
		else if (role == ROLE_PERMISSIONS) {
			return NAME_PERMISSIONS;
		}
		else if (role == ROLE_PUBLISH) {
			return NAME_PUBLISH;
		}

		return NAME_VIEW;
	}

}