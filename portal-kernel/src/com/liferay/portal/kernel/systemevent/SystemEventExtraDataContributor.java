/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.systemevent;

import com.liferay.portal.kernel.model.BaseModel;

/**
 * @author Daniel Raposo
 */
public interface SystemEventExtraDataContributor {

	public String contribute(BaseModel<?> baseModel, String extraData)
		throws Exception;

}