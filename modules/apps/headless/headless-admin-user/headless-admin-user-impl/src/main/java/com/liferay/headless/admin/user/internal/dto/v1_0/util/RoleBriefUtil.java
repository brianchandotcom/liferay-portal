/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.dto.v1_0.util;

import com.liferay.headless.admin.user.dto.v1_0.RoleBrief;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

/**
 * @author Crescenzo Rega
 */
public class RoleBriefUtil {

	public static RoleBrief toRoleBrief(
		DTOConverterContext dtoConverterContext, Role role) {

		return new RoleBrief() {
			{
				setExternalReferenceCode(role::getExternalReferenceCode);
				setId(role::getRoleId);
				setKey(role::getName);
				setName(
					() -> {
						if (dtoConverterContext == null) {
							return role.getName();
						}

						return role.getTitle(dtoConverterContext.getLocale());
					});
				setName_i18n(
					() -> {
						if (dtoConverterContext == null) {
							return null;
						}

						return LocalizedMapUtil.getI18nMap(
							dtoConverterContext.isAcceptAllLanguages(),
							role.getTitleMap());
					});
				setRoleType(role::getType);
			}
		};
	}

}