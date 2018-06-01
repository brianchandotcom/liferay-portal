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

package com.liferay.portal.security.audit.event.generators.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRequestThreadLocal;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;

/**
 * @author Mika Koivisto
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class AuditMessageBuilder {

	public static AuditMessage buildAuditMessage(
		String eventType, String className, long classPK,
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
			additionalInfoJSONObject.put("doAsUserId", String.valueOf(userId));
			additionalInfoJSONObject.put(
				"doAsUserName",
				PortalUtil.getUserName(userId, StringPool.BLANK));
		}

		if (attributes != null) {
			additionalInfoJSONObject.put(
				"attributes", _getAttributesJSONArray(attributes));
		}

		return new AuditMessage(
			eventType, companyId, realUserId, realUserName, className,
			String.valueOf(classPK), null, additionalInfoJSONObject);
	}

	private static JSONArray _getAttributesJSONArray(
		List<Attribute> attributes) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Attribute attribute : attributes) {
			JSONObject attributeJSONObject = JSONFactoryUtil.createJSONObject();

			attributeJSONObject.put("name", attribute.getName());
			attributeJSONObject.put("newValue", attribute.getNewValue());
			attributeJSONObject.put("oldValue", attribute.getOldValue());

			jsonArray.put(attributeJSONObject);
		}

		return jsonArray;
	}

}