/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.cluster;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface ClusterRequest<T extends ClusterResponse> {

	public T accept(ClusterRequestExecutor clusterRequestExecutor);

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement. This
	 *             method should not be in the parent interface.  Only certain
	 *             ClusterRequests work with index names.
	 */
	@Deprecated
	public String[] getIndexNames();

}