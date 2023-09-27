/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.event.generators.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRequestThreadLocal;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.List;

/**
 * @author Mika Koivisto
 * @author Brian Wing Shun Chan
 */
public class AuditMessageBuilder {

	public static AuditMessage buildAuditMessage(
		long groupId, String eventType, String className, long classPK,
		List<Attribute> attributes) {

		return buildAuditMessage(
			groupId, eventType, className, String.valueOf(classPK), attributes);
	}

	public static AuditMessage buildAuditMessage(
		long groupId, String eventType, String className, String classPK,
		List<Attribute> attributes) {

		long companyId = CompanyThreadLocal.getCompanyId();

		long userId = 0;

		if (PrincipalThreadLocal.getName() != null) {
			userId = GetterUtil.getLong(PrincipalThreadLocal.getName());
		}

		AuditRequestThreadLocal auditRequestThreadLocal =
			AuditRequestThreadLocal.getAuditThreadLocal();

		long realUserId = auditRequestThreadLocal.getRealUserId();

		String realUserName = PortalUtil.getUserName(
			realUserId, StringPool.BLANK);

		JSONObject additionalInfoJSONObject =
			JSONFactoryUtil.createJSONObject();

		if ((realUserId > 0) && (userId != realUserId)) {
			additionalInfoJSONObject.put(
				"doAsUserId", String.valueOf(userId)
			).put(
				"doAsUserName", PortalUtil.getUserName(userId, StringPool.BLANK)
			);
		}

		if (attributes != null) {
			additionalInfoJSONObject.put(
				"attributes", _getAttributesJSONArray(attributes));
		}

		return new AuditMessage(
			eventType, companyId, groupId, realUserId, realUserName, className,
			classPK, null, null, additionalInfoJSONObject);
	}

	public static AuditMessage buildAuditMessage(
		String eventType, ClassedModel classedModel,
		List<Attribute> attributes) {

		long groupId = 0;

		if (classedModel instanceof GroupedModel) {
			GroupedModel groupedModel = (GroupedModel)classedModel;

			groupId = groupedModel.getGroupId();
		}

		return buildAuditMessage(
			groupId, eventType, classedModel.getModelClassName(),
			String.valueOf(classedModel.getPrimaryKeyObj()), attributes);
	}

	public static AuditMessage buildAuditMessage(
		String eventType, String className, long classPK,
		List<Attribute> attributes) {

		return buildAuditMessage(0, eventType, className, classPK, attributes);
	}

	private static JSONArray _getAttributesJSONArray(
		List<Attribute> attributes) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Attribute attribute : attributes) {
			jsonArray.put(
				JSONUtil.put(
					"name", attribute.getName()
				).put(
					"newValue", attribute.getNewValue()
				).put(
					"oldValue", attribute.getOldValue()
				));
		}

		return jsonArray;
	}

}