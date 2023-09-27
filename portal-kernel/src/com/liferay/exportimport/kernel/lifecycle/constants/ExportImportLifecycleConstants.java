/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.lifecycle.constants;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Daniel Kocsis
 */
@ProviderType
public interface ExportImportLifecycleConstants {

	public static final int EVENT_LAYOUT_EXPORT_FAILED = 1;

	public static final int EVENT_LAYOUT_EXPORT_STARTED = 3;

	public static final int EVENT_LAYOUT_EXPORT_SUCCEEDED = 2;

	public static final int EVENT_LAYOUT_IMPORT_FAILED = 4;

	public static final int EVENT_LAYOUT_IMPORT_STARTED = 6;

	public static final int EVENT_LAYOUT_IMPORT_SUCCEEDED = 5;

	public static final int EVENT_PORTLET_EXPORT_FAILED = 7;

	public static final int EVENT_PORTLET_EXPORT_STARTED = 9;

	public static final int EVENT_PORTLET_EXPORT_SUCCEEDED = 8;

	public static final int EVENT_PORTLET_IMPORT_FAILED = 10;

	public static final int EVENT_PORTLET_IMPORT_STARTED = 12;

	public static final int EVENT_PORTLET_IMPORT_SUCCEEDED = 11;

	public static final int EVENT_PUBLICATION_LAYOUT_LOCAL_FAILED = 13;

	public static final int EVENT_PUBLICATION_LAYOUT_LOCAL_STARTED = 15;

	public static final int EVENT_PUBLICATION_LAYOUT_LOCAL_SUCCEEDED = 14;

	public static final int EVENT_PUBLICATION_LAYOUT_REMOTE_FAILED = 16;

	public static final int EVENT_PUBLICATION_LAYOUT_REMOTE_STARTED = 18;

	public static final int EVENT_PUBLICATION_LAYOUT_REMOTE_SUCCEEDED = 17;

	public static final int EVENT_PUBLICATION_PORTLET_LOCAL_FAILED = 19;

	public static final int EVENT_PUBLICATION_PORTLET_LOCAL_STARTED = 21;

	public static final int EVENT_PUBLICATION_PORTLET_LOCAL_SUCCEEDED = 20;

	public static final int EVENT_PUBLICATION_PORTLET_REMOTE_FAILED = 37;

	public static final int EVENT_PUBLICATION_PORTLET_REMOTE_STARTED = 38;

	public static final int EVENT_PUBLICATION_PORTLET_REMOTE_SUCCEEDED = 39;

	public static final int EVENT_STAGED_MODEL_EXPORT_FAILED = 22;

	public static final int EVENT_STAGED_MODEL_EXPORT_STARTED = 24;

	public static final int EVENT_STAGED_MODEL_EXPORT_SUCCEEDED = 23;

	public static final int EVENT_STAGED_MODEL_IMPORT_FAILED = 25;

	public static final int EVENT_STAGED_MODEL_IMPORT_STARTED = 27;

	public static final int EVENT_STAGED_MODEL_IMPORT_SUCCEEDED = 26;

	public static final int PROCESS_FLAG_LAYOUT_EXPORT_IN_PROCESS = 30;

	public static final int PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS = 31;

	public static final int PROCESS_FLAG_LAYOUT_STAGING_IN_PROCESS = 32;

	public static final int PROCESS_FLAG_PORTLET_EXPORT_IN_PROCESS = 34;

	public static final int PROCESS_FLAG_PORTLET_IMPORT_IN_PROCESS = 35;

	public static final int PROCESS_FLAG_PORTLET_STAGING_IN_PROCESS = 36;

}