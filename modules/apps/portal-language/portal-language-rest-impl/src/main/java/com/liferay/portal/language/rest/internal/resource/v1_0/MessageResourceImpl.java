/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.language.override.model.PLOEntry;
import com.liferay.portal.language.override.service.PLOEntryLocalService;
import com.liferay.portal.language.rest.dto.v1_0.Message;
import com.liferay.portal.language.rest.resource.v1_0.MessageResource;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;

import java.io.InputStreamReader;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.ws.rs.BadRequestException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Thiago Buarque
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/message.properties",
	scope = ServiceScope.PROTOTYPE, service = MessageResource.class
)
public class MessageResourceImpl extends BaseMessageResourceImpl {

	@Override
	public void deleteMessageByKey(String key, String languageId) {
		if (Validator.isNull(languageId)) {
			_ploEntryLocalService.deletePLOEntries(
				contextCompany.getCompanyId(), key);
		}
		else {
			_ploEntryLocalService.deletePLOEntry(
				contextCompany.getCompanyId(), key, languageId);
		}
	}

	@Override
	public Page<Message> getMessagesPage(String[] keys, String languageId) {
		List<Message> messages = new ArrayList<>();

		for (String key : keys) {
			messages.add(_getMessageByKey(key, languageId));
		}

		return Page.of(messages);
	}

	@Override
	public void postMessage(String languageId, MultipartBody multipartBody)
		throws Exception {

		BinaryFile binaryFile = multipartBody.getBinaryFile("file");

		if (binaryFile == null) {
			throw new BadRequestException("Unable to read file");
		}

		if (!Objects.equals(
				FileUtil.getExtension(binaryFile.getFileName()),
				"properties")) {

			throw new BadRequestException("Please upload a *.properties file");
		}

		Properties properties = new Properties();

		properties.load(
			new InputStreamReader(
				binaryFile.getInputStream(), StandardCharsets.UTF_8));

		if (properties.isEmpty()) {
			return;
		}

		Enumeration<String> enumeration =
			(Enumeration<String>)properties.propertyNames();

		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();

			_ploEntryLocalService.addOrUpdatePLOEntry(
				contextCompany.getCompanyId(), contextUser.getUserId(), key,
				languageId, properties.getProperty(key));
		}
	}

	@Override
	public Message putMessage(Message message) throws Exception {
		PLOEntry ploEntry = _ploEntryLocalService.addOrUpdatePLOEntry(
			contextCompany.getCompanyId(), contextUser.getUserId(),
			message.getKey(), message.getLanguageId(), message.getValue());

		message.setCreateDate(ploEntry::getCreateDate);
		message.setId(ploEntry::getPloEntryId);
		message.setModifiedDate(ploEntry::getModifiedDate);

		message.setOverride(() -> true);

		return message;
	}

	private Message _getMessageByKey(String key, String languageId) {
		PLOEntry ploEntry = _ploEntryLocalService.fetchPLOEntry(
			contextCompany.getCompanyId(), key, languageId);

		Message message = new Message();

		message.setKey(() -> key);
		message.setLanguageId(() -> languageId);

		if (ploEntry != null) {
			message.setCreateDate(ploEntry::getCreateDate);
			message.setModifiedDate(ploEntry::getModifiedDate);
			message.setValue(ploEntry::getValue);
			message.setOverride(() -> true);
			message.setId(ploEntry::getPloEntryId);
		}
		else {
			message.setValue(
				() -> _language.get(
					LocaleUtil.fromLanguageId(languageId), key));
		}

		return message;
	}

	@Reference
	private Language _language;

	@Reference
	private PLOEntryLocalService _ploEntryLocalService;

}