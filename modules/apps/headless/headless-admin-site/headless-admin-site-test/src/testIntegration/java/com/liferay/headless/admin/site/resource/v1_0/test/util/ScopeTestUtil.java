/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test.util;

import com.liferay.headless.admin.site.client.dto.v1_0.Scope;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

/**
 * @author Lourdes Fernández Besada
 */
public class ScopeTestUtil {

	public static Scope getItemScope(long itemGroupId, long scopeGroupId) {
		if (itemGroupId == scopeGroupId) {
			return null;
		}

		Group group = GroupLocalServiceUtil.fetchGroup(itemGroupId);

		if (group == null) {
			return new Scope() {
				{
					setExternalReferenceCode(RandomTestUtil::randomString);
					setType(Type.SITE);
				}
			};
		}

		return new Scope() {
			{
				setExternalReferenceCode(group::getExternalReferenceCode);
				setType(
					() -> {
						if (group.getType() == GroupConstants.TYPE_DEPOT) {
							return Type.ASSET_LIBRARY;
						}

						return Type.SITE;
					});
			}
		};
	}

}