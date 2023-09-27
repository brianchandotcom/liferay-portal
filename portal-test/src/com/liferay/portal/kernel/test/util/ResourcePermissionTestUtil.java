/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.test.util;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.ResourcedModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Alberto Chaparro
 */
public class ResourcePermissionTestUtil {

	public static ResourcePermission addResourcePermission(
			long actionIds, String name, long roleId)
		throws Exception {

		return addResourcePermission(
			actionIds, name, RandomTestUtil.randomString(), roleId,
			RandomTestUtil.nextInt());
	}

	public static ResourcePermission addResourcePermission(
			long actionIds, String name, String primKey, int scope)
		throws Exception {

		return addResourcePermission(
			actionIds, name, primKey, RandomTestUtil.nextInt(), scope);
	}

	public static ResourcePermission addResourcePermission(
			long actionIds, String name, String primKey, long roleId, int scope)
		throws Exception {

		long resourcePermissionId = CounterLocalServiceUtil.increment(
			ResourcePermission.class.getName());

		ResourcePermission resourcePermission =
			ResourcePermissionLocalServiceUtil.createResourcePermission(
				resourcePermissionId);

		resourcePermission.setCompanyId(TestPropsValues.getCompanyId());
		resourcePermission.setName(name);
		resourcePermission.setScope(scope);
		resourcePermission.setPrimKey(primKey);
		resourcePermission.setPrimKeyId(GetterUtil.getLong(primKey));
		resourcePermission.setRoleId(roleId);
		resourcePermission.setActionIds(actionIds);
		resourcePermission.setViewActionId((actionIds % 2) == 1);

		return ResourcePermissionLocalServiceUtil.addResourcePermission(
			resourcePermission);
	}

	public static void deleteResourcePermissions(PersistedModel persistedModel)
		throws Exception {

		if (!(persistedModel instanceof BaseModel) ||
			!(persistedModel instanceof ShardedModel)) {

			return;
		}

		BaseModel<?> baseModel = (BaseModel)persistedModel;

		ShardedModel shardedModel = (ShardedModel)baseModel;

		ResourcePermissionLocalServiceUtil.deleteResourcePermissions(
			shardedModel.getCompanyId(), baseModel.getModelClassName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(baseModel.getPrimaryKeyObj()));

		if (!(persistedModel instanceof ResourcedModel)) {
			return;
		}

		ResourcedModel resourcedModel = (ResourcedModel)baseModel;

		ResourcePermissionLocalServiceUtil.deleteResourcePermissions(
			shardedModel.getCompanyId(), baseModel.getModelClassName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(resourcedModel.getResourcePrimKey()));
	}

}