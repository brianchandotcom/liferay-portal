/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.model;

/**
 * @author Hugo Huijser
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class DLFileEntryTypeConstants {

	public static final long COMPANY_ID_BASIC_DOCUMENT = 0;

	public static final long FILE_ENTRY_TYPE_ID_ALL = -1;

	public static final long FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT = 0;

	public static final String FILE_ENTRY_TYPE_KEY_CONTRACT = "CONTRACT";

	public static final String FILE_ENTRY_TYPE_KEY_IG_IMAGE =
		"IMAGE GALLERY IMAGE";

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public static final String FILE_ENTRY_TYPE_KEY_MARKETING_BANNER =
		"MARKETING BANNER";

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public static final String FILE_ENTRY_TYPE_KEY_ONLINE_TRAINING =
		"ONLINE TRAINING";

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public static final String FILE_ENTRY_TYPE_KEY_SALES_PRESENTATION =
		"SALES PRESENTATION";

	public static final int FILE_ENTRY_TYPE_SCOPE_DEFAULT = 0;

	public static final int FILE_ENTRY_TYPE_SCOPE_SYSTEM = 1;

	public static final String NAME_BASIC_DOCUMENT = "basic-document";

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public static final String NAME_CONTRACT = "contract";

	public static final String NAME_IG_IMAGE = "image-gallery-image";

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public static final String NAME_MARKETING_BANNER = "marketing-banner";

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public static final String NAME_ONLINE_TRAINING = "online-training";

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public static final String NAME_SALES_PRESENTATION = "sales-presentation";

}