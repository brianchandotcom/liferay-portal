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

package com.liferay.forms.apio.internal.resource;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.forms.apio.architect.identifier.FormInstanceIdentifier;
import com.liferay.forms.apio.architect.identifier.StructureIdentifier;
import com.liferay.forms.apio.internal.form.FormContextForm;
import com.liferay.forms.apio.internal.helper.FormInstanceRecordResourceHelper;
import com.liferay.person.apio.identifier.PersonIdentifier;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.site.apio.identifier.WebSiteIdentifier;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose FormInstance resources through a
 * web API. The resources are mapped from the internal model {@code
 * DDMFormInstance}.
 *
 * @author Victor Oliveira
 */
@Component(immediate = true)
public class FormInstanceNestedCollectionResource
	implements NestedCollectionResource<DDMFormInstance, Long,
		FormInstanceIdentifier, Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<DDMFormInstance, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<DDMFormInstance, Long> builder) {

		return builder.addGetter(
			this::_getPageItems, Company.class
		).build();
	}

	@Override
	public String getName() {
		return "form-instances";
	}

	@Override
	public ItemRoutes<DDMFormInstance, Long> itemRoutes(
		ItemRoutes.Builder<DDMFormInstance, Long> builder) {

		return builder.addGetter(
			_ddmFormInstanceService::getFormInstance
		).addUpdater(
			this::_evaluateContext, DDMFormRenderingContext.class,
			(credentials, aLong) -> true, FormContextForm::buildForm
		).build();
	}

	@Override
	public Representor<DDMFormInstance> representor(
		Representor.Builder<DDMFormInstance, Long> builder) {

		return builder.types(
			"FormInstance"
		).identifier(
			DDMFormInstance::getFormInstanceId
		).addBidirectionalModel(
			"interactionService", "formInstances", WebSiteIdentifier.class,
			DDMFormInstance::getGroupId
		).addDate(
			"dateCreated", DDMFormInstance::getCreateDate
		).addDate(
			"dateModified", DDMFormInstance::getModifiedDate
		).addDate(
			"datePublished", DDMFormInstance::getLastPublishDate
		).addLinkedModel(
			"author", PersonIdentifier.class, DDMFormInstance::getUserId
		).addLinkedModel(
			"structure", StructureIdentifier.class,
			DDMFormInstance::getStructureId
		).addNested(
			"settings", this::_getSettings,
			nestedBuilder -> nestedBuilder.types(
				"FormInstanceSettings"
			).addString(
				"emailFromAddress", DDMFormInstanceSettings::emailFromAddress
			).addString(
				"emailFromName", DDMFormInstanceSettings::emailFromName
			).addString(
				"emailSubject", DDMFormInstanceSettings::emailSubject
			).addString(
				"emailToAddress", DDMFormInstanceSettings::emailToAddress
			).addBoolean(
				"published", DDMFormInstanceSettings::published
			).addString(
				"redirectURL", DDMFormInstanceSettings::redirectURL
			).addBoolean(
				"requireAuthentication",
				DDMFormInstanceSettings::requireAuthentication
			).addBoolean(
				"requireCaptcha", DDMFormInstanceSettings::requireCaptcha
			).addBoolean(
				"sendEmailNotification",
				DDMFormInstanceSettings::sendEmailNotification
			).addString(
				"storageType", DDMFormInstanceSettings::storageType
			).addString(
				"workflowDefinition",
				DDMFormInstanceSettings::workflowDefinition
			).build()
		).addNested(
			"version", this::_getVersion,
			nestedBuilder -> nestedBuilder.types(
				"FormInstanceVersion"
			).addLinkedModel(
				"author", PersonIdentifier.class,
				DDMFormInstanceVersion::getUserId
			).addString(
				"name", DDMFormInstanceVersion::getVersion
			).build()
		).addLocalizedStringByLocale(
			"description", DDMFormInstance::getDescription
		).addLocalizedStringByLocale(
			"name", DDMFormInstance::getName
		).addString(
			"defaultLanguage", DDMFormInstance::getDefaultLanguageId
		).addStringList(
			"availableLanguages", this::_getAvailableLanguages
		).build();
	}

	private DDMFormInstance _evaluateContext(
			Long ddmFormInstanceId, FormContextForm formContextForm,
			DDMFormRenderingContext ddmFormRenderingContext)
		throws PortalException {

		Locale locale = LocaleUtil.fromLanguageId(
			formContextForm.getLanguageId());

		LocaleThreadLocal.setThemeDisplayLocale(locale);

		DDMFormInstance ddmFormInstance =
			_ddmFormInstanceService.getFormInstance(ddmFormInstanceId);

		if (_log.isDebugEnabled()) {
			JSONSerializer jsonSerializer = _jsonFactory.createJSONSerializer();

			DDMStructure ddmStructure = ddmFormInstance.getStructure();

			DDMForm ddmForm = ddmStructure.getDDMForm();
			DDMFormLayout ddmFormLayout = ddmStructure.getDDMFormLayout();

			DDMFormValues ddmFormValues =
				FormInstanceRecordResourceHelper.getDDMFormValues(
					formContextForm.getFieldValues(), ddmForm, locale);

			ddmFormRenderingContext.setDDMFormValues(ddmFormValues);

			ddmFormRenderingContext.setLocale(locale);

			Map<String, Object> templateContext =
				_ddmFormTemplateContextFactory.create(
					ddmForm, ddmFormLayout, ddmFormRenderingContext);

			String json = jsonSerializer.serializeDeep(templateContext);

			_log.debug(json);
		}

		return ddmFormInstance;
	}

	private List<String> _getAvailableLanguages(
		DDMFormInstance ddmFormInstance) {

		Stream<String> availableLanguagesStream = Arrays.stream(
			ddmFormInstance.getAvailableLanguageIds());

		return availableLanguagesStream.collect(Collectors.toList());
	}

	private PageItems<DDMFormInstance> _getPageItems(
		Pagination pagination, Long groupId, Company company) {

		List<DDMFormInstance> ddmFormInstances =
			_ddmFormInstanceService.getFormInstances(
				company.getCompanyId(), groupId, pagination.getStartPosition(),
				pagination.getEndPosition());

		int count = _ddmFormInstanceService.getFormInstancesCount(
			company.getCompanyId(), groupId);

		return new PageItems<>(ddmFormInstances, count);
	}

	private DDMFormInstanceSettings _getSettings(
		DDMFormInstance ddmFormInstance) {

		return Try.fromFallible(
			ddmFormInstance::getSettingsModel
		).orElse(
			null
		);
	}

	private DDMFormInstanceVersion _getVersion(
		DDMFormInstance ddmFormInstance) {

		return Try.fromFallible(
			ddmFormInstance::getVersion
		).map(
			ddmFormInstance::getFormInstanceVersion
		).orElse(
			null
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FormInstanceNestedCollectionResource.class);

	@Reference
	private DDMFormInstanceService _ddmFormInstanceService;

	@Reference
	private DDMFormTemplateContextFactory _ddmFormTemplateContextFactory;

	@Reference
	private JSONFactory _jsonFactory;

}