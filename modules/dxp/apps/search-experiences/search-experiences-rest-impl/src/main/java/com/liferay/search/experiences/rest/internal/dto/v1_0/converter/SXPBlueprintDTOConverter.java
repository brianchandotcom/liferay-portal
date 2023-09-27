/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.internal.dto.v1_0.converter;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.ElementInstance;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;
import com.liferay.search.experiences.rest.dto.v1_0.SXPElement;
import com.liferay.search.experiences.rest.dto.v1_0.util.ConfigurationUtil;
import com.liferay.search.experiences.rest.dto.v1_0.util.ElementInstanceUtil;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;
import com.liferay.search.experiences.service.SXPElementLocalService;

import java.util.Locale;

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
				configuration = _toConfiguration(
					sxpBlueprint.getConfigurationJSON());
				createDate = sxpBlueprint.getCreateDate();
				description = _language.get(
					dtoConverterContext.getLocale(),
					sxpBlueprint.getDescription(
						dtoConverterContext.getLocale()));
				description_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					sxpBlueprint.getDescriptionMap());
				elementInstances = _translateElementInstances(
					_toElementInstances(sxpBlueprint.getElementInstancesJSON()),
					dtoConverterContext.getLocale());
				externalReferenceCode = sxpBlueprint.getExternalReferenceCode();
				id = sxpBlueprint.getSXPBlueprintId();
				modifiedDate = sxpBlueprint.getModifiedDate();
				schemaVersion = sxpBlueprint.getSchemaVersion();
				title = _language.get(
					dtoConverterContext.getLocale(),
					sxpBlueprint.getTitle(dtoConverterContext.getLocale()));
				title_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					sxpBlueprint.getTitleMap());
				userName = sxpBlueprint.getUserName();
				version = sxpBlueprint.getVersion();
			}
		};
	}

	@Override
	public SXPBlueprint toDTO(
		com.liferay.search.experiences.model.SXPBlueprint sxpBlueprint) {

		return new SXPBlueprint() {
			{
				configuration = _toConfiguration(
					sxpBlueprint.getConfigurationJSON());
				createDate = sxpBlueprint.getCreateDate();
				description = sxpBlueprint.getDescription();
				description_i18n = LocalizedMapUtil.getI18nMap(
					true, sxpBlueprint.getDescriptionMap());
				elementInstances = _toElementInstances(
					sxpBlueprint.getElementInstancesJSON());
				externalReferenceCode = sxpBlueprint.getExternalReferenceCode();
				id = sxpBlueprint.getSXPBlueprintId();
				modifiedDate = sxpBlueprint.getModifiedDate();
				schemaVersion = sxpBlueprint.getSchemaVersion();
				title = sxpBlueprint.getTitle();
				title_i18n = LocalizedMapUtil.getI18nMap(
					true, sxpBlueprint.getTitleMap());
				userName = sxpBlueprint.getUserName();
				version = sxpBlueprint.getVersion();
			}
		};
	}

	private Configuration _toConfiguration(String json) {
		try {
			return ConfigurationUtil.toConfiguration(json);
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

		try {
			for (ElementInstance elementInstance : elementInstances) {
				SXPElement sxpElement = elementInstance.getSxpElement();

				Long sxpElementId = (Long)sxpElement.getId();

				if (sxpElementId != null) {
					com.liferay.search.experiences.model.SXPElement
						serviceBuilderSXPElement =
							_sxpElementLocalService.getSXPElement(sxpElementId);

					sxpElement.setDescription(
						_language.get(
							locale,
							serviceBuilderSXPElement.getDescription(locale)));
					sxpElement.setTitle(
						_language.get(
							locale, serviceBuilderSXPElement.getTitle(locale)));
				}
				else {
					String descriptionXml = _localization.getXml(
						sxpElement.getDescription_i18n(),
						LocaleUtil.toLanguageId(LocaleUtil.getDefault()),
						"Description");
					String titleXml = _localization.getXml(
						sxpElement.getTitle_i18n(),
						LocaleUtil.toLanguageId(LocaleUtil.getDefault()),
						"Title");

					sxpElement.setDescription(
						_language.get(
							locale,
							_localization.getLocalization(
								descriptionXml,
								LocaleUtil.toLanguageId(locale))));
					sxpElement.setTitle(
						_language.get(
							locale,
							_localization.getLocalization(
								titleXml, LocaleUtil.toLanguageId(locale))));
				}
			}

			return elementInstances;
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SXPBlueprintDTOConverter.class);

	@Reference
	private Language _language;

	@Reference
	private Localization _localization;

	@Reference
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	@Reference
	private SXPElementLocalService _sxpElementLocalService;

}