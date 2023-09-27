/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.constants;

import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Alexander Chow
 */
public class JournalArticleConstants {

	public static final int[] ASSET_ENTRY_CREATION_STATUSES = {
		WorkflowConstants.STATUS_APPROVED, WorkflowConstants.STATUS_EXPIRED,
		WorkflowConstants.STATUS_SCHEDULED
	};

	public static final String CANONICAL_URL_SEPARATOR = "/-/";

	public static final long CLASS_NAME_ID_DEFAULT = 0;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #CLASS_NAME_ID_DEFAULT}
	 */
	@Deprecated
	public static final long CLASSNAME_ID_DEFAULT = 0;

	public static final long DDM_STRUCTURE_ID_ALL = -1;

	public static final String DISPLAY_PAGE = "display-page";

	public static final int NOTIFICATION_TYPE_MOVE_ENTRY_FROM_FOLDER = 2;

	public static final int NOTIFICATION_TYPE_MOVE_ENTRY_FROM_TRASH = 4;

	public static final int NOTIFICATION_TYPE_MOVE_ENTRY_TO_FOLDER = 3;

	public static final int NOTIFICATION_TYPE_MOVE_ENTRY_TO_TRASH = 5;

	public static final String PORTLET = "portlet";

	public static final int SMALL_IMAGE_SOURCE_DOCUMENTS_AND_MEDIA = 2;

	public static final int SMALL_IMAGE_SOURCE_NONE = 1;

	public static final int SMALL_IMAGE_SOURCE_URL = 3;

	public static final int SMALL_IMAGE_SOURCE_USER_COMPUTER = 4;

	public static final String STAND_ALONE = "stand-alone";

	public static final double VERSION_DEFAULT = 1.0;

}