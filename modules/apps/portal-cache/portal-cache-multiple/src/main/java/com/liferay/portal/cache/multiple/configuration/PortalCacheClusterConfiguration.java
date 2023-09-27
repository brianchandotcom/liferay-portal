/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.multiple.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.cluster.Priority;

/**
 * @author Tina Tian
 */
@ExtendedObjectClassDefinition(category = "infrastructure")
@Meta.OCD(
	id = "com.liferay.portal.cache.multiple.configuration.PortalCacheClusterConfiguration",
	name = "portal-cache-cluster-configuration-name"
)
public interface PortalCacheClusterConfiguration {

	@Meta.AD(deflt = "LEVEL1,LEVEL2", name = "priorities", required = false)
	public Priority[] priorities();

	@Meta.AD(deflt = "false", name = "using-coalesced-pipe", required = false)
	public boolean usingCoalescedPipe();

}