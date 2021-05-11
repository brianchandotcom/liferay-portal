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

package com.liferay.digital.signature.internal.manager;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.digital.signature.internal.http.DSHttp;
import com.liferay.digital.signature.internal.model.DSEnvelopeImpl;
import com.liferay.digital.signature.manager.DSEnvelopeManager;
import com.liferay.digital.signature.model.DSEnvelope;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = DSEnvelopeManager.class)
public class DSEnvelopeManagerImpl implements DSEnvelopeManager {
	
	private DSEnvelope _toDSEnvelope(JSONObject jsonObject) {
		if(jsonObject != null) {
			DSEnvelope dsEnvelope = new DSEnvelopeImpl();
			
			dsEnvelope.setStatus(jsonObject.getString("status"));
			dsEnvelope.setEnvelopeId(jsonObject.getString("envelopeId"));
			dsEnvelope.setEmailSubject(jsonObject.getString("emailSubject"));
			return dsEnvelope;
		}
		
		return null;
	}
	
	private JSONObject _toJSONObject(DSEnvelope dsEnvelope) {
		JSONObject dsEnvelopeJSONObject = JSONFactoryUtil.createJSONObject();
		
		dsEnvelopeJSONObject.put("emailSubject", dsEnvelope.getEmailSubject());
		dsEnvelopeJSONObject.put("status", dsEnvelope.getStatus());
		
		
		return dsEnvelopeJSONObject;
	}
	
//	private JSONObject _createDSDocumentJSONObject(String fileName, int id) {
//		try {
//			InputStream inputStream = getClass().getResourceAsStream(fileName);
//			byte[] byteArray = IOUtils.toByteArray(inputStream);
//			String encode = Base64.encode(byteArray);
//			return JSONFactoryUtil.createJSONObject()
//					.put("documentBase64", encode)
//					.put("documentId", id)
//					.put("name", fileName);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	@Override
	public void addDSEnvelope(DSEnvelope dsEnvelope) {
		JSONObject jsonObject = _dsHttp.invokePost(
			0, "envelopes",
			JSONUtil.put(
				"emailSubject",
				dsEnvelope.getEmailSubject())
			.put(
				"status",
				dsEnvelope.getStatus()
			));

		if (_log.isDebugEnabled()) {
			String envelopeId = (String)jsonObject.get("envelopeId");

			_log.debug("Envelope ID " + envelopeId);
		}
	}
	
	public void getDSEnvelope(String envelopeId) {
		JSONObject jsonObject = _dsHttp.invokeGet(
				0, "envelopes/" + envelopeId);
		
		System.out.println("jsonObject: " + jsonObject);
		
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DSEnvelopeManagerImpl.class);

	@Reference
	private DSHttp _dsHttp;

}