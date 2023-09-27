/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.web.internal.type.deployer;

import java.util.Dictionary;

/**
 * @author Iván Zaera Avellón
 */
public interface Registrable {

	public Dictionary<String, Object> getDictionary();

}