/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Thiago Buarque
 */
public class ConfigurationFilterStringUtil {

	public static String getCompanyScopedFilterString(
		String companyId, String virtualInstanceId) {

		return StringBundler.concat(
			"(&(|(",
			ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey(),
			StringPool.EQUAL, GetterUtil.get(companyId, "*"),
			")(dxp.lxc.liferay.com.virtualInstanceId=",
			GetterUtil.get(virtualInstanceId, "*"), "))(!(",
			ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey(),
			"=*))(!(siteExternalReferenceCode=*))(!(",
			ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE.
				getPropertyKey(),
			"=*)))");
	}

	public static String getGroupScopedFilterString(
		String groupId, String siteExternalReferenceCode) {

		return StringBundler.concat(
			"(&(|(", ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey(),
			StringPool.EQUAL, GetterUtil.get(groupId, "*"),
			")(siteExternalReferenceCode=",
			GetterUtil.get(siteExternalReferenceCode, "*"), "))(!(",
			ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE.
				getPropertyKey(),
			"=*)))");
	}

	public static String getPortletScopedFilterString(
		String groupId, String portletInstanceId,
		String siteExternalReferenceCode) {

		return StringBundler.concat(
			"(&(|(", ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey(),
			StringPool.EQUAL, GetterUtil.get(groupId, "*"),
			")(siteExternalReferenceCode=",
			GetterUtil.get(siteExternalReferenceCode, "*"), "))(",
			ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE.
				getPropertyKey(),
			"=", GetterUtil.get(portletInstanceId, "*"), "))");
	}

	public static String getSystemScopedFilterString() {
		return StringBundler.concat(
			"(&(|(!(",
			ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey(),
			"=*))(",
			ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey(),
			"=0))(!(dxp.lxc.liferay.com.virtualInstanceId=*))(!(",
			ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey(),
			"=*))(!(siteExternalReferenceCode=*))(!(",
			ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE.
				getPropertyKey(),
			"=*)))");
	}

}