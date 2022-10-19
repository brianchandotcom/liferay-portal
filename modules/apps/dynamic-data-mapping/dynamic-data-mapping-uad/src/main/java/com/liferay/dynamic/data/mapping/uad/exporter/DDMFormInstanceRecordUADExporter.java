/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.dynamic.data.mapping.uad.exporter;

import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.uad.util.DDMUADUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.user.associated.data.exporter.UADExporter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = UADExporter.class)
public class DDMFormInstanceRecordUADExporter
	extends BaseDDMFormInstanceRecordUADExporter {

	@Override
	protected String toXmlString(DDMFormInstanceRecord ddmFormInstanceRecord) {
		return StringBundler.concat(
			StringUtil.removeSubstring(
				super.toXmlString(ddmFormInstanceRecord), "</model>"),
			"<column><column-name>",
			"formInstanceName</column-name><column-value><![CDATA[",
			_getFormInstanceName(ddmFormInstanceRecord),
			"]]></column-value></column>",
			_getFieldValuesXMLString(ddmFormInstanceRecord), "</model>");
	}

	private String _getFieldValuesXMLString(
		DDMFormInstanceRecord ddmFormInstanceRecord) {

		try {
			StringBundler sb = new StringBundler(10);

			sb.append("<column><model><model-name>");
			sb.append("com.liferay.dynamic.data.mapping.model.DDMContent");
			sb.append("</model-name>");

			DDMFormValuesSerializerSerializeResponse
				ddmFormValuesSerializerSerializeResponse =
					_ddmFormValuesSerializer.serialize(
						DDMFormValuesSerializerSerializeRequest.Builder.
							newBuilder(
								ddmFormInstanceRecord.getDDMFormValues()
							).build());

			JSONObject dataJSONObject = JSONFactoryUtil.createJSONObject(
				ddmFormValuesSerializerSerializeResponse.getContent());

			JSONArray fieldValuesJSONArray = dataJSONObject.getJSONArray(
				"fieldValues");

			fieldValuesJSONArray.forEach(
				fieldValue -> {
					JSONObject fieldValueJSONObject = (JSONObject)fieldValue;

					sb.append("<column><column-name>");
					sb.append(fieldValueJSONObject.get("name"));
					sb.append("</column-name>");
					sb.append("<column-value><![CDATA[");
					sb.append(fieldValueJSONObject.get("value"));
					sb.append("]]></column-value></column>");
				});

			sb.append("</model></column>");

			return sb.toString();
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to get field values from dynamic data mapping form " +
					"instance record " +
						ddmFormInstanceRecord.getFormInstanceRecordId(),
				portalException);
		}

		return null;
	}

	private String _getFormInstanceName(
		DDMFormInstanceRecord ddmFormInstanceRecord) {

		try {
			DDMFormInstance ddmFormInstance =
				ddmFormInstanceRecord.getFormInstance();

			Document document = DDMUADUtil.toDocument(
				ddmFormInstance.getName());

			Node firstChildNode = document.getFirstChild();

			NodeList nodeList = firstChildNode.getChildNodes();

			Node formInstanceNameNode = nodeList.item(0);

			return formInstanceNameNode.getTextContent();
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to get name from dynamic data mapping form instance " +
					ddmFormInstanceRecord.getFormInstanceId(),
				portalException);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceRecordUADExporter.class);

	@Reference(target = "(ddm.form.values.serializer.type=json)")
	private DDMFormValuesSerializer _ddmFormValuesSerializer;

}