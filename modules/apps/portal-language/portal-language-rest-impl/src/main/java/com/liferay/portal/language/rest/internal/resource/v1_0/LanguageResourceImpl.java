/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.language.override.model.PLOEntry;
import com.liferay.portal.language.override.service.PLOEntryLocalService;
import com.liferay.portal.language.rest.dto.v1_0.Language;
import com.liferay.portal.language.rest.resource.v1_0.LanguageResource;
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
	properties = "OSGI-INF/liferay/rest/v1_0/language.properties",
	scope = ServiceScope.PROTOTYPE, service = LanguageResource.class
)
public class LanguageResourceImpl extends BaseLanguageResourceImpl {

	@Override
	public void deleteLanguageByKey(String key, String languageId) {
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
	public Page<Language> getLanguagesPage(String[] keys, String languageId) {
		List<Language> languages = new ArrayList<>();

		for (String key : keys) {
			languages.add(_getLanguageByKey(key, languageId));
		}

		return Page.of(languages);
	}

	@Override
	public void postLanguage(String languageId, MultipartBody multipartBody)
		throws Exception {

		BinaryFile file = multipartBody.getBinaryFile("file");

		if (file == null) {
			throw new BadRequestException("Unable to read file");
		}

		if (!Objects.equals(
				FileUtil.getExtension(file.getFileName()), "properties")) {

			throw new BadRequestException(
				"Please upload a Language.properties file");
		}

		Properties languageProperties = new Properties();

		languageProperties.load(
			new InputStreamReader(
				file.getInputStream(), StandardCharsets.UTF_8));

		if (languageProperties.isEmpty()) {
			return;
		}

		Enumeration<String> enumeration =
			(Enumeration<String>)languageProperties.propertyNames();

		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();

			_ploEntryLocalService.addOrUpdatePLOEntry(
				contextCompany.getCompanyId(), contextUser.getUserId(), key,
				languageId, languageProperties.getProperty(key));
		}
	}

	@Override
	public Language putLanguage(Language language) throws Exception {
		PLOEntry ploEntry = _ploEntryLocalService.addOrUpdatePLOEntry(
			contextCompany.getCompanyId(), contextUser.getUserId(),
			language.getKey(), language.getLanguageId(), language.getValue());

		language.setCreateDate(ploEntry::getCreateDate);
		language.setId(ploEntry::getPloEntryId);
		language.setModifiedDate(ploEntry::getModifiedDate);

		language.setOverride(() -> true);

		return language;
	}

	private Language _getLanguageByKey(String key, String languageId) {
		PLOEntry ploEntry = _ploEntryLocalService.fetchPLOEntry(
			contextCompany.getCompanyId(), key, languageId);

		Language language = new Language();

		language.setKey(() -> key);
		language.setLanguageId(() -> languageId);

		if (ploEntry != null) {
			language.setCreateDate(ploEntry::getCreateDate);
			language.setModifiedDate(ploEntry::getModifiedDate);
			language.setValue(ploEntry::getValue);
			language.setOverride(() -> true);
			language.setId(ploEntry::getPloEntryId);
		}
		else {
			language.setValue(
				() -> _language.get(
					LocaleUtil.fromLanguageId(languageId), key));
		}

		return language;
	}

	@Reference
	private com.liferay.portal.kernel.language.Language _language;

	@Reference
	private PLOEntryLocalService _ploEntryLocalService;

}