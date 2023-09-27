/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.manager.v1_0;

import java.util.List;

/**
 * @author Guilherme Camacho
 */
public interface ObjectEntryManagerRegistry {

	public ObjectEntryManager getObjectEntryManager(String storageType);

	public List<ObjectEntryManager> getObjectEntryManagers(long companyId);

}