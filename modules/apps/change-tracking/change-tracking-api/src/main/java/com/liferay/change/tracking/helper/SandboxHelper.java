/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2025-06
 */

package com.liferay.change.tracking.helper;

import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Noor Najjar
 */
public interface SandboxHelper {

	public void sandbox(CTPreferences ctPreferences) throws PortalException;

}