/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.internal.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.scim.rest.internal.model.ScimUser;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.wso2.charon3.core.objects.Group;
import org.wso2.charon3.core.protocol.endpoints.AbstractResourceManager;
import org.wso2.charon3.core.schema.SCIMConstants;

/**
 * @author Olivér Kecskeméty
 */
public class ScimGroupUtil {

	public static Group toGroup(List<ScimUser> scimUsers, UserGroup userGroup)
		throws Exception {

		Group group = new Group();

		group.replaceDisplayName(userGroup.getName());

		Date createDate = userGroup.getCreateDate();

		group.setCreatedInstant(createDate.toInstant());

		group.setExternalId(userGroup.getExternalReferenceCode());
		group.setId(String.valueOf(userGroup.getPrimaryKey()));

		Date modifiedDate = userGroup.getModifiedDate();

		group.setLastModifiedInstant(modifiedDate.toInstant());

		group.setLocation(
			StringBundler.concat(
				AbstractResourceManager.getResourceEndpointURL(
					SCIMConstants.GROUP_ENDPOINT),
				CharPool.FORWARD_SLASH, userGroup.getPrimaryKey()));

		for (ScimUser scimUser : scimUsers) {
			group.setMember(
				ScimUserUtil.toUser(Collections.emptyList(), scimUser));
		}

		group.setResourceType(SCIMConstants.GROUP);
		group.setSchemas();

		return group;
	}

}