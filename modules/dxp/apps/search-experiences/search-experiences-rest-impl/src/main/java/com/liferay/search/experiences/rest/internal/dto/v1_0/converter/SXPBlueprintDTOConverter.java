/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.internal.dto.v1_0.converter;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.ElementDefinition;
import com.liferay.search.experiences.rest.dto.v1_0.ElementInstance;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;
import com.liferay.search.experiences.rest.dto.v1_0.SXPElement;
import com.liferay.search.experiences.rest.dto.v1_0.util.ConfigurationUtil;
import com.liferay.search.experiences.rest.dto.v1_0.util.ElementInstanceUtil;
import com.liferay.search.experiences.rest.internal.dto.v1_0.converter.util.SXPDTOConverterUtil;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;
import com.liferay.search.experiences.service.SXPElementLocalService;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.search.experiences.model.SXPBlueprint",
	service = DTOConverter.class
)
public class SXPBlueprintDTOConverter
	implements DTOConverter
		<com.liferay.search.experiences.model.SXPBlueprint, SXPBlueprint> {

	@Override
	public String getContentType() {
		return SXPBlueprint.class.getSimpleName();
	}

	@Override
	public SXPBlueprint toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		com.liferay.search.experiences.model.SXPBlueprint sxpBlueprint =
			_sxpBlueprintLocalService.getSXPBlueprint(
				(Long)dtoConverterContext.getId());

		return toDTO(dtoConverterContext, sxpBlueprint);
	}

	@Override
	public SXPBlueprint toDTO(
			DTOConverterContext dtoConverterContext,
			com.liferay.search.experiences.model.SXPBlueprint sxpBlueprint)
		throws Exception {

		return new SXPBlueprint() {
			{
				setConfiguration(
					() -> _toConfiguration(
						sxpBlueprint.getCompanyId(),
						dtoConverterContext.getLocale(),
						sxpBlueprint.getConfigurationJSON()));
				setCreateDate(sxpBlueprint::getCreateDate);
				setDescription(
					() -> _language.get(
						dtoConverterContext.getLocale(),
						sxpBlueprint.getDescription(
							dtoConverterContext.getLocale())));
				setDescription_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						dtoConverterContext.isAcceptAllLanguages(),
						sxpBlueprint.getDescriptionMap()));
				setElementInstances(
					() -> _translateElementInstances(
						_toElementInstances(
							sxpBlueprint.getElementInstancesJSON()),
						dtoConverterContext.getLocale()));
				setExternalReferenceCode(
					sxpBlueprint::getExternalReferenceCode);
				setId(sxpBlueprint::getSXPBlueprintId);
				setModifiedDate(sxpBlueprint::getModifiedDate);
				setSchemaVersion(sxpBlueprint::getSchemaVersion);
				setTitle(
					() -> _language.get(
						dtoConverterContext.getLocale(),
						sxpBlueprint.getTitle(
							dtoConverterContext.getLocale())));
				setTitle_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						dtoConverterContext.isAcceptAllLanguages(),
						sxpBlueprint.getTitleMap()));
				setUserName(sxpBlueprint::getUserName);
				setVersion(sxpBlueprint::getVersion);
			}
		};
	}

	@Override
	public SXPBlueprint toDTO(
		com.liferay.search.experiences.model.SXPBlueprint sxpBlueprint) {

		return new SXPBlueprint() {
			{
				setConfiguration(
					() -> _toConfiguration(
						sxpBlueprint.getCompanyId(),
						LocaleUtil.fromLanguageId(
							sxpBlueprint.getDefaultLanguageId()),
						sxpBlueprint.getConfigurationJSON()));
				setCreateDate(sxpBlueprint::getCreateDate);
				setDescription(sxpBlueprint::getDescription);
				setDescription_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						true, sxpBlueprint.getDescriptionMap()));
				setElementInstances(
					() -> _toElementInstances(
						sxpBlueprint.getElementInstancesJSON()));
				setExternalReferenceCode(
					sxpBlueprint::getExternalReferenceCode);
				setId(sxpBlueprint::getSXPBlueprintId);
				setModifiedDate(sxpBlueprint::getModifiedDate);
				setSchemaVersion(sxpBlueprint::getSchemaVersion);
				setTitle(sxpBlueprint::getTitle);
				setTitle_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						true, sxpBlueprint.getTitleMap()));
				setUserName(sxpBlueprint::getUserName);
				setVersion(sxpBlueprint::getVersion);
			}
		};
	}

	private void _setLocalizedDescriptionAndTitle(
		Map<Locale, String> descriptionMap, String fallbackDescription,
		String fallbackTitle, Locale locale, SXPElement sxpElement,
		Map<Locale, String> titleMap) {

		sxpElement.setDescription(
			() -> SXPDTOConverterUtil.translate(
				fallbackDescription, _language, locale, descriptionMap));
		sxpElement.setTitle(
			() -> SXPDTOConverterUtil.translate(
				fallbackTitle, _language, locale, titleMap));
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private Configuration _toConfiguration(
		long companyId, Locale locale, String json) {

		try {
			JSONObject configurationJSON =
				_jsonFactory.createJSONObject(json);

			if (configurationJSON == null) {
				return ConfigurationUtil.toConfiguration(json);
			}

			JSONObject generalConfigurationJSON =
				configurationJSON.getJSONObject("generalConfiguration");

			if (generalConfigurationJSON == null) {
				return ConfigurationUtil.toConfiguration(json);
			}

			String collectionProviderType =
				generalConfigurationJSON.getString("collectionProviderType");

			String[] searchableAssetTypeWithSubtype = StringUtil.split(
				collectionProviderType, StringPool.POUND);

			if (searchableAssetTypeWithSubtype.length == 1) {
				generalConfigurationJSON.put(
					"collectionProviderTypeName",
					searchableAssetTypeWithSubtype[0]);
			} else if (searchableAssetTypeWithSubtype.length == 3) {
				if (searchableAssetTypeWithSubtype[0].equals(
					DLFileEntry.class.getName())) {

					DLFileEntryType dlFileEntryType;

					if (searchableAssetTypeWithSubtype[1].equals(StringPool.BLANK)) {
						dlFileEntryType = _dlFileEntryTypeLocalService.
							getBasicDocumentDLFileEntryType();

					} else {

						Group group =
							_groupLocalService.getGroupByExternalReferenceCode(
								searchableAssetTypeWithSubtype[1], companyId);

						dlFileEntryType =
							_dlFileEntryTypeLocalService.
								getDLFileEntryTypeByExternalReferenceCode(
									searchableAssetTypeWithSubtype[2],
									group.getGroupId());
					}

					generalConfigurationJSON.put(
						"collectionProviderTypeName",
						searchableAssetTypeWithSubtype[0] + " - " +
						dlFileEntryType.getName(locale));

				} else if (searchableAssetTypeWithSubtype[0].equals(
					JournalArticle.class.getName())) {

					Group group =
						_groupLocalService.getGroupByExternalReferenceCode(
							searchableAssetTypeWithSubtype[1], companyId);

					DDMStructure ddmStructure =
						_ddmStructureLocalService.
							fetchStructureByExternalReferenceCode(
								searchableAssetTypeWithSubtype[2],
								group.getGroupId(),
								_classNameLocalService.getClassNameId(
									JournalArticle.class));

					generalConfigurationJSON.put(
						"collectionProviderTypeName",
						searchableAssetTypeWithSubtype[0] + " - " +
						ddmStructure.getName(locale));
				}
			}

			return ConfigurationUtil.toConfiguration(
				configurationJSON.toString());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			return null;
		}
	}

	private ElementInstance[] _toElementInstances(String json) {
		try {
			return ElementInstanceUtil.toElementInstances(json);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			return null;
		}
	}

	private ElementInstance[] _translateElementInstances(
		ElementInstance[] elementInstances, Locale locale) {

		if (elementInstances == null) {
			return null;
		}

		for (ElementInstance elementInstance : elementInstances) {
			SXPElement sxpElement = elementInstance.getSxpElement();

			ElementDefinition elementDefinition =
				sxpElement.getElementDefinition();

			sxpElement.setElementDefinition(
				() -> SXPDTOConverterUtil.translate(
					elementDefinition, _language, locale));

			try {
				com.liferay.search.experiences.model.SXPElement
					serviceBuilderSXPElement =
						_sxpElementLocalService.getSXPElement(
							(Long)sxpElement.getId());

				_setLocalizedDescriptionAndTitle(
					serviceBuilderSXPElement.getDescriptionMap(),
					serviceBuilderSXPElement.getFallbackDescription(),
					serviceBuilderSXPElement.getFallbackTitle(), locale,
					sxpElement, serviceBuilderSXPElement.getTitleMap());
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception);
				}
			}
		}

		return elementInstances;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SXPBlueprintDTOConverter.class);

	@Reference
	private Language _language;

	@Reference
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	@Reference
	private SXPElementLocalService _sxpElementLocalService;

}