/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.repository.util;

import com.liferay.portal.kernel.repository.BaseRepository;

/**
 * @author Adolfo Pérez
 * @author Mika Koivisto
 */
public interface ExternalRepositoryFactory {

	public BaseRepository getInstance() throws Exception;

}