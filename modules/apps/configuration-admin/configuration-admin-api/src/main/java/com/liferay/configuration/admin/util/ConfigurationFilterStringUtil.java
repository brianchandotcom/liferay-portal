/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.framework.Constants;
import org.osgi.service.cm.ConfigurationAdmin;

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

	public static String getCompanyScopedFilterString(
		String companyId, String pid, String virtualInstanceId) {

		return StringBundler.concat(
			StringPool.OPEN_PARENTHESIS, StringPool.AMPERSAND,
			_getScopedFilterString(pid),
			getCompanyScopedFilterString(companyId, virtualInstanceId),
			StringPool.CLOSE_PARENTHESIS);
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

	public static String getGroupScopedFilterString(
		String groupId, String pid, String siteExternalReferenceCode) {

		return StringBundler.concat(
			StringPool.OPEN_PARENTHESIS, StringPool.AMPERSAND,
			_getScopedFilterString(pid),
			getGroupScopedFilterString(groupId, siteExternalReferenceCode),
			StringPool.CLOSE_PARENTHESIS);
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

	public static String getPortletScopedFilterString(
		String groupId, String pid, String portletInstanceId,
		String siteExternalReferenceCode) {

		return StringBundler.concat(
			StringPool.OPEN_PARENTHESIS, StringPool.AMPERSAND,
			_getScopedFilterString(pid),
			getPortletScopedFilterString(
				groupId, portletInstanceId, siteExternalReferenceCode),
			StringPool.CLOSE_PARENTHESIS);
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

	public static String getSystemScopedFilterString(String pid) {
		String filterString = StringBundler.concat(
			StringPool.OPEN_PARENTHESIS, StringPool.PIPE,
			_getPropertyFilterString(
				ConfigurationAdmin.SERVICE_FACTORYPID, pid),
			_getPropertyFilterString(Constants.SERVICE_PID, pid),
			StringPool.CLOSE_PARENTHESIS);

		if (pid.contains("~")) {
			filterString = StringBundler.concat(
				StringPool.OPEN_PARENTHESIS, StringPool.AMPERSAND,
				_getPropertyFilterString(
					ConfigurationAdmin.SERVICE_FACTORYPID,
					ConfigurationPidUtil.getRawPid(pid)),
				_getPropertyFilterString(Constants.SERVICE_PID, pid),
				StringPool.CLOSE_PARENTHESIS);
		}

		return StringBundler.concat(
			StringPool.OPEN_PARENTHESIS, StringPool.AMPERSAND, filterString,
			getSystemScopedFilterString(), StringPool.CLOSE_PARENTHESIS);
	}

	private static String _getPropertyFilterString(String key, String value) {
		if (Validator.isNull(key) || Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		return StringBundler.concat(
			StringPool.OPEN_PARENTHESIS, key, StringPool.EQUAL, value,
			StringPool.CLOSE_PARENTHESIS);
	}

	private static String _getScopedFilterString(String pid) {
		String rawPid = ConfigurationPidUtil.getRawPid(pid);

		String filterString = StringBundler.concat(
			StringPool.OPEN_PARENTHESIS, StringPool.PIPE,
			_getPropertyFilterString(
				ConfigurationAdmin.SERVICE_FACTORYPID, rawPid),
			_getPropertyFilterString(
				ConfigurationAdmin.SERVICE_FACTORYPID, rawPid + ".scoped"),
			StringPool.CLOSE_PARENTHESIS);

		if (pid.contains("~")) {
			filterString = StringBundler.concat(
				StringPool.OPEN_PARENTHESIS, StringPool.AMPERSAND, filterString,
				_getPropertyFilterString(Constants.SERVICE_PID, pid),
				StringPool.CLOSE_PARENTHESIS);
		}

		return filterString;
	}

}