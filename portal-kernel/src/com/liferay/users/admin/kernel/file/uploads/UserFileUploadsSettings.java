/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.kernel.file.uploads;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Drew Brokke
 */
@ProviderType
public interface UserFileUploadsSettings {

	public int getImageMaxHeight();

	public long getImageMaxSize();

	public int getImageMaxWidth();

	public boolean isImageCheckToken();

}