/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item.provider;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;
import java.util.Map;

/**
 * @author Rubén Pulido
 */
public interface InfoItemActionDetailsProvider<T> {

	public Map<Locale, String> getInfoItemActionErrorMessageMap(String fieldId)
		throws PortalException;

}