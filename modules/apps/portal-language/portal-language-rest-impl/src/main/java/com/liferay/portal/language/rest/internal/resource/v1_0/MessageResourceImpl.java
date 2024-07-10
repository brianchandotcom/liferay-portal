/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.language.override.model.PLOEntry;
import com.liferay.portal.language.override.service.PLOEntryLocalService;
import com.liferay.portal.language.rest.dto.v1_0.Message;
import com.liferay.portal.language.rest.resource.v1_0.MessageResource;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.io.InputStreamReader;
import java.io.Serializable;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;

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
	public void deleteMessage(String key, String languageId) throws Exception {
		if (!FeatureFlagManagerUtil.isEnabled("LPD-27222")) {
			throw new UnsupportedOperationException();
		}

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
	public Message getMessage(String key, String languageId) throws Exception {
		if (!FeatureFlagManagerUtil.isEnabled("LPD-27222")) {
			throw new UnsupportedOperationException();
		}

		Message message = new Message();

		message.setKey(() -> key);
		message.setLanguageId(() -> languageId);

		PLOEntry ploEntry = _ploEntryLocalService.fetchPLOEntry(
			contextCompany.getCompanyId(), key, languageId);

		if (ploEntry != null) {
			message.setValue(ploEntry::getValue);
		}
		else {
			message.setValue(
				() -> _language.get(
					LocaleUtil.fromLanguageId(languageId), key));
		}

		return message;
	}

	@Override
	public Message postMessage(Message message) throws PortalException {
		if (!FeatureFlagManagerUtil.isEnabled("LPD-27222")) {
			throw new UnsupportedOperationException();
		}

		return _addOrUpdatePLOEntry(message);
	}

	@Override
	public void postMessageImport(
			String languageId, MultipartBody multipartBody)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-27222")) {
			throw new UnsupportedOperationException();
		}

		BinaryFile binaryFile = multipartBody.getBinaryFile("file");

		if (binaryFile == null) {
			throw new BadRequestException("Unable to read file");
		}

		if (!Objects.equals(
				FileUtil.getExtension(binaryFile.getFileName()),
				"properties")) {

			throw new BadRequestException(
				"File name must have a \"properties\" file extension");
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
	public Message putMessage(Message message) throws PortalException {
		if (!FeatureFlagManagerUtil.isEnabled("LPD-27222")) {
			throw new UnsupportedOperationException();
		}

		return _addOrUpdatePLOEntry(message);
	}

	@Override
	public Page<Message> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-27222")) {
			throw new UnsupportedOperationException();
		}

		List<String> keys = transform(
			_ploEntryLocalService.getPLOEntries(contextCompany.getCompanyId()),
			ploEntry -> ploEntry.getKey());

		String languageId = GetterUtil.getString(parameters.get("languageId"));

		ResourceBundle resourceBundle = LanguageResources.getResourceBundle(
			LocaleUtil.fromLanguageId(languageId, true, true));

		keys.addAll(resourceBundle.keySet());

		Collections.sort(keys);

		List<Message> messages = new ArrayList<>();

		for (int i = pagination.getStartPosition();
			 (i < keys.size()) && (i < pagination.getEndPosition()); i++) {

			Message message = new Message();

			String key = keys.get(i);

			message.setKey(() -> key);

			message.setLanguageId(() -> languageId);
			message.setValue(
				() -> ResourceBundleUtil.getString(resourceBundle, key));

			messages.add(message);

			if (i >= pagination.getEndPosition()) {
				break;
			}
		}

		return Page.of(messages, pagination, keys.size());
	}

	private Message _addOrUpdatePLOEntry(Message message)
		throws PortalException {

		PLOEntry ploEntry = _ploEntryLocalService.addOrUpdatePLOEntry(
			contextCompany.getCompanyId(), contextUser.getUserId(),
			message.getKey(), message.getLanguageId(), message.getValue());

		message.setKey(ploEntry::getKey);
		message.setLanguageId(ploEntry::getLanguageId);
		message.setValue(ploEntry::getValue);

		return message;
	}

	@Reference
	private Language _language;

	@Reference
	private PLOEntryLocalService _ploEntryLocalService;

}