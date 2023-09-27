/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.tree;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.TreeModel;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Shinn Lok
 */
@ProviderType
public interface TreeModelTasks<T extends TreeModel> {

	public List<T> findTreeModels(
		long previousId, long companyId, long parentPrimaryKey, int size);

	public void rebuildDependentModelsTreePaths(
			long parentPrimaryKey, String treePath)
		throws PortalException;

}