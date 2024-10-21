/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export enum EActions {
	CLEAN_UP_ORPHANED_PAGE_REVISION_PORTLET_PREFERENCES = 'Clean up orphaned page revision portlet preferences. Execute',
	CLEAN_UP_ORPHANED_THEME_PORTLET_PREFERENCES = 'Clean up orphaned theme portlet preferences. Execute',
	CLEAN_UP_PERMISSIONS = 'Clean up permissions. Execute',
	CLEAR_CLUSTER_CACHE = 'Clear content cached across the cluster. Execute',
	CLEAR_DATABASE_CACHE = 'Clear the database cache. Execute',
	CLEAR_DIRECT_SERVLET_CACHE = 'Clear the direct servlet cache. Execute',
	CLEAR_VM_CACHE = 'Clear content cached by this VM. Execute',
	GARBAGE_COLLECTION = 'Run the garbage collector to free up memory. Execute',
	GENERATE_THREAD_DUMP = 'Generate thread dump. Execute',
	REGENERATE_PREVIEW_AND_THUMBNAIL_OF_PDF_FILES = 'Regenerate preview and thumbnail of PDF files in documents and media. Execute',
	RESET_PREVIEW_AND_THUMBNAIL_FILES = 'Reset preview and thumbnail files for documents and media. Execute',
	VERIFY_MEMBERSHIP_POLICIES = 'Verify membership policies. Execute',
}
