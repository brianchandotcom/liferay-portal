/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.cache.multiple.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.cluster.Priority;

/**
 * @author Tina Tian
 */
@ExtendedObjectClassDefinition(category = "foundation")
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