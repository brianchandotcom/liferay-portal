/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.repository.event;

import com.liferay.portal.kernel.repository.model.FileVersion;

/**
 * @author Roberto Díaz
 * @author Adolfo Pérez
 */
public interface FileVersionPreviewEventListener {

	public void onFailure(FileVersion fileVersion);

	public void onSuccess(FileVersion fileVersion);

}