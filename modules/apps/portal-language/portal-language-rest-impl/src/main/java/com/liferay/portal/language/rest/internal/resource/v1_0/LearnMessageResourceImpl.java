/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.rest.internal.resource.v1_0;

import com.liferay.learn.LearnMessageUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.language.rest.dto.v1_0.LearnMessage;
import com.liferay.portal.language.rest.dto.v1_0.LearnMessageDetail;
import com.liferay.portal.language.rest.resource.v1_0.LearnMessageResource;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.BadRequestException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Thiago Buarque
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/learn-message.properties",
	scope = ServiceScope.PROTOTYPE, service = LearnMessageResource.class
)
public class LearnMessageResourceImpl extends BaseLearnMessageResourceImpl {

	@Override
	public Page<LearnMessage> getLearnMessagesResourcePage(
		String resource, String languageId, String key)
		throws Exception {

		JSONObject jsonObject = LearnMessageUtil.getJSONObject(resource);

		if (Validator.isNull(jsonObject)) {
			throw new BadRequestException(
				"Learn-resource name must have a \"resource\" file");
		}

		if (Validator.isNotNull(key)) {
			return Page.of(_getSingleLearnMessage(jsonObject, key, languageId));
		}

		return Page.of(_getAllLearnMessages(jsonObject, languageId));
	}

	private List<LearnMessage> _getSingleLearnMessage(
		JSONObject jsonObject, String key, String languageId) {

		JSONObject messageObject = jsonObject.getJSONObject(key);

		if (Validator.isNull(messageObject)) {
			return Collections.emptyList();
		}

		LearnMessageDetail detail = _getLearnMessageDetail(messageObject, languageId);

		if (Validator.isNull(detail)) {
			return Collections.emptyList();
		}

		return Collections.singletonList(_createLearnMessage(key, detail));
	}

	private List<LearnMessage> _getAllLearnMessages(
		JSONObject jsonObject, String languageId) {

		List<LearnMessage> learnMessages = new ArrayList<>();
		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String currentKey = keys.next();
			JSONObject messageObject = jsonObject.getJSONObject(currentKey);

			if (Validator.isNull(messageObject)) {
				continue;
			}

			LearnMessageDetail detail = _getLearnMessageDetail(messageObject, languageId);

			if (Validator.isNull(detail)) {
				continue;
			}

			learnMessages.add(_createLearnMessage(currentKey, detail));
		}

		return learnMessages;
	}

	private LearnMessageDetail _getLearnMessageDetail(
		JSONObject messageObject, String languageId) {

		JSONObject languageObject;

		if (Validator.isNotNull(languageId)) {
			languageObject = messageObject.getJSONObject(languageId);

			if (Validator.isNull(languageObject)) {
				return null;
			}

			return _createLearnMessageDetail(languageId, languageObject);
		}

		Iterator<String> languageKeys = messageObject.keys();

		if (!languageKeys.hasNext()) {
			return null;
		}

		String firstLanguageId = languageKeys.next();
		languageObject = messageObject.getJSONObject(firstLanguageId);

		if (Validator.isNull(languageObject)) {
			return null;
		}

		return _createLearnMessageDetail(firstLanguageId, languageObject);
	}

	private static LearnMessage _createLearnMessage(
		String key, LearnMessageDetail detail) {

		LearnMessage learnMessage = new LearnMessage();
		learnMessage.setKey(key);
		learnMessage.setLearnMessageDetails(new LearnMessageDetail[]{detail});

		return learnMessage;
	}

	private LearnMessageDetail _createLearnMessageDetail(
		String languageId, JSONObject languageObject) {

		LearnMessageDetail detail = new LearnMessageDetail();
		detail.setLanguageId(languageId);
		detail.setMessage(languageObject.getString("message"));
		detail.setUrl(languageObject.getString("url"));

		return detail;
	}

}