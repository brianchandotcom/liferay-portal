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

package com.liferay.exportimport.kernel.configuration;

import com.liferay.petra.string.StringPool;

/**
 * @author Daniel Kocsis
 * @author Akos Thurzo
 */
public class ExportImportConfigurationConstants {

	public static final int TYPE_EXPORT_LAYOUT = 0;

	public static final String TYPE_EXPORT_LAYOUT_LABEL = "export-layout";

	public static final int TYPE_EXPORT_PORTLET = 5;

	public static final String TYPE_EXPORT_PORTLET_LABEL = "export-portlet";

	public static final int TYPE_IMPORT_LAYOUT = 6;

	public static final String TYPE_IMPORT_LAYOUT_LABEL = "import-layout";

	public static final int TYPE_IMPORT_PORTLET = 7;

	public static final String TYPE_IMPORT_PORTLET_LABEL = "import-portlet";

	public static final int TYPE_PUBLISH_LAYOUT_LOCAL = 1;

	public static final String TYPE_PUBLISH_LAYOUT_LOCAL_LABEL =
		"publish-layout-local";

	public static final int TYPE_PUBLISH_LAYOUT_REMOTE = 2;

	public static final String TYPE_PUBLISH_LAYOUT_REMOTE_LABEL =
		"publish-layout-remote";

	public static final int TYPE_PUBLISH_PORTLET = 8;

	public static final String TYPE_PUBLISH_PORTLET_LABEL = "publish-portlet";

	public static final int TYPE_PUBLISH_PORTLET_LOCAL = 8;

	public static final String TYPE_PUBLISH_PORTLET_LOCAL_LABEL =
		"publish-portlet-local";

	public static final int TYPE_PUBLISH_PORTLET_REMOTE = 9;

	public static final String TYPE_PUBLISH_PORTLET_REMOTE_LABEL =
		"publish-portlet-remote";

	public static final int TYPE_SCHEDULED_PUBLISH_LAYOUT_LOCAL = 3;

	public static final String TYPE_SCHEDULED_PUBLISH_LAYOUT_LOCAL_LABEL =
		"scheduled-publish-layout-local";

	public static final int TYPE_SCHEDULED_PUBLISH_LAYOUT_REMOTE = 4;

	public static final String TYPE_SCHEDULED_PUBLISH_LAYOUT_REMOTE_LABEL =
		"scheduled-publish-layout-remote";

	public static String getTypeLabel(int type) {
		if (type == TYPE_EXPORT_LAYOUT) {
			return TYPE_EXPORT_LAYOUT_LABEL;
		}
		else if (type == TYPE_EXPORT_PORTLET) {
			return TYPE_EXPORT_PORTLET_LABEL;
		}
		else if (type == TYPE_IMPORT_LAYOUT) {
			return TYPE_IMPORT_LAYOUT_LABEL;
		}
		else if (type == TYPE_IMPORT_PORTLET) {
			return TYPE_IMPORT_PORTLET_LABEL;
		}
		else if (type == TYPE_PUBLISH_LAYOUT_LOCAL) {
			return TYPE_PUBLISH_LAYOUT_LOCAL_LABEL;
		}
		else if (type == TYPE_PUBLISH_LAYOUT_REMOTE) {
			return TYPE_PUBLISH_LAYOUT_REMOTE_LABEL;
		}
		else if (type == TYPE_PUBLISH_PORTLET_LOCAL) {
			return TYPE_PUBLISH_PORTLET_LOCAL_LABEL;
		}
		else if (type == TYPE_PUBLISH_PORTLET_REMOTE) {
			return TYPE_PUBLISH_PORTLET_REMOTE_LABEL;
		}
		else if (type == TYPE_SCHEDULED_PUBLISH_LAYOUT_LOCAL) {
			return TYPE_SCHEDULED_PUBLISH_LAYOUT_LOCAL_LABEL;
		}
		else if (type == TYPE_SCHEDULED_PUBLISH_LAYOUT_REMOTE) {
			return TYPE_SCHEDULED_PUBLISH_LAYOUT_REMOTE_LABEL;
		}

		return StringPool.BLANK;
	}

}