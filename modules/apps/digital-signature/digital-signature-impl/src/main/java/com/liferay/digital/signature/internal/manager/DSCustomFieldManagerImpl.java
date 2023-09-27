/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.digital.signature.internal.manager;

import com.liferay.digital.signature.internal.http.DSHttp;
import com.liferay.digital.signature.manager.DSCustomFieldManager;
import com.liferay.digital.signature.model.DSCustomField;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Victor Trajano
 */
@Component(service = DSCustomFieldManager.class)
public class DSCustomFieldManagerImpl implements DSCustomFieldManager {

	@Override
	public List<DSCustomField> addDSCustomFields(
		long companyId, long groupId, String dsEnvelopeId,
		DSCustomField... dsCustomFields) {

		return _toDSCustomFields(
			_dsHttp.post(
				companyId, groupId,
				StringBundler.concat(
					"envelopes/", dsEnvelopeId, "/custom_fields"),
				JSONUtil.put(
					"textCustomFields",
					JSONUtil.toJSONArray(
						dsCustomFields,
						dsCustomField -> dsCustomField.toJSONObject(), _log))));
	}

	private List<DSCustomField> _toDSCustomFields(JSONObject jsonObject) {
		return JSONUtil.toList(
			jsonObject.getJSONArray("textCustomFields"),
			customFieldJSONObject -> new DSCustomField() {
				{
					dsCustomFieldId = customFieldJSONObject.getLong("fieldId");
					name = customFieldJSONObject.getString("name");
					required = customFieldJSONObject.getBoolean("required");
					show = customFieldJSONObject.getBoolean("show");
					value = customFieldJSONObject.getString("value");
				}
			},
			_log);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DSCustomFieldManagerImpl.class);

	@Reference
	private DSHttp _dsHttp;

}