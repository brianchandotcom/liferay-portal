/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
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
import java.util.Objects;

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

		JSONObject resourceJSONObject = LearnMessageUtil.getJSONObject(
			resource);

		if (Objects.isNull(resourceJSONObject)) {
			throw new BadRequestException(
				"Learn-resource name must have a \"resource\" file");
		}

		if (Validator.isNotNull(key)) {
			return Page.of(
				_getSingleLearnMessage(resourceJSONObject, key, languageId));
		}

		return Page.of(_getAllLearnMessages(resourceJSONObject, languageId));
	}

	private LearnMessage _createLearnMessage(
		String key, LearnMessageDetail detail) {

		LearnMessage learnMessage = new LearnMessage();

		learnMessage.setKey(() -> key);
		learnMessage.setLearnMessageDetails(
			() -> new LearnMessageDetail[] {detail});

		return learnMessage;
	}

	private LearnMessageDetail _createLearnMessageDetail(
		String languageId, JSONObject languageJSONObject) {

		LearnMessageDetail detail = new LearnMessageDetail();

		detail.setLanguageId(() -> languageId);
		detail.setMessage(() -> languageJSONObject.getString("message"));
		detail.setUrl(() -> languageJSONObject.getString("url"));

		return detail;
	}

	private List<LearnMessage> _getAllLearnMessages(
		JSONObject jsonObject, String languageId) {

		List<LearnMessage> learnMessages = new ArrayList<>();
		Iterator<String> keysIterator = jsonObject.keys();

		while (keysIterator.hasNext()) {
			String currentKey = keysIterator.next();

			JSONObject messageJSONObject = jsonObject.getJSONObject(currentKey);

			if (Objects.isNull(messageJSONObject)) {
				continue;
			}

			LearnMessageDetail detail = _getLearnMessageDetail(
				messageJSONObject, languageId);

			if (Objects.isNull(detail)) {
				continue;
			}

			learnMessages.add(_createLearnMessage(currentKey, detail));
		}

		return learnMessages;
	}

	private LearnMessageDetail _getLearnMessageDetail(
		JSONObject messageJSONObject, String languageId) {

		JSONObject languageJSONObject;

		if (Validator.isNotNull(languageId)) {
			languageJSONObject = messageJSONObject.getJSONObject(languageId);

			if (Objects.isNull(languageJSONObject)) {
				return null;
			}

			return _createLearnMessageDetail(languageId, languageJSONObject);
		}

		Iterator<String> languageKeysIterator = messageJSONObject.keys();

		if (!languageKeysIterator.hasNext()) {
			return null;
		}

		String firstLanguageId = languageKeysIterator.next();

		languageJSONObject = messageJSONObject.getJSONObject(firstLanguageId);

		if (Objects.isNull(languageJSONObject)) {
			return null;
		}

		return _createLearnMessageDetail(firstLanguageId, languageJSONObject);
	}

	private List<LearnMessage> _getSingleLearnMessage(
		JSONObject jsonObject, String key, String languageId) {

		JSONObject messageJSONObject = jsonObject.getJSONObject(key);

		if (Objects.isNull(messageJSONObject)) {
			return Collections.emptyList();
		}

		LearnMessageDetail detail = _getLearnMessageDetail(
			messageJSONObject, languageId);

		if (Objects.isNull(detail)) {
			return Collections.emptyList();
		}

		return Collections.singletonList(_createLearnMessage(key, detail));
	}

}