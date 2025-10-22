/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.scope;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.fields.NestedFieldsSupplier;

import java.util.Locale;

/**
 * @author Daniel Raposo
 */
public class ScopeUtil {

	public static Scope toScope(Group group, Locale locale) {
		if ((group == null) || group.isCompany()) {
			return null;
		}

		return new Scope() {
			{
				setExternalReferenceCode(group::getExternalReferenceCode);
				setKey(
					() -> NestedFieldsSupplier.supply(
						"scope.key", nestedField -> group.getGroupKey()));
				setLabel(
					() -> NestedFieldsSupplier.supply(
						"scope.label",
						nestedField -> _getGroupName(group, locale)));
				setType(() -> _getGroupType(group));
			}
		};
	}

	public static Scope toScope(long groupId, Locale locale)
		throws PortalException {

		if (groupId == 0) {
			return null;
		}

		return toScope(GroupLocalServiceUtil.getGroup(groupId), locale);
	}

	private static String _getGroupName(Group group, Locale locale) {
		try {
			String descriptiveName = group.getDescriptiveName(locale);

			if (Validator.isNotNull(descriptiveName)) {
				return descriptiveName;
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return group.getName(locale);
	}

	private static Scope.Type _getGroupType(Group group) {
		if (group.isDepot()) {
			return Scope.Type.ASSET_LIBRARY;
		}
		else if (group.isSite()) {
			return Scope.Type.SITE;
		}
		else if (group.isCMS()) {
			return Scope.Type.SPACE;
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(ScopeUtil.class);

}