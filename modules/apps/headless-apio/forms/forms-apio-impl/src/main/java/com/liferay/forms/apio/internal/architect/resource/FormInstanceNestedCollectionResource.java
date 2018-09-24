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

package com.liferay.forms.apio.internal.architect.resource;

import static java.util.function.Function.identity;

import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.language.AcceptLanguage;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.NestedRepresentor;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.content.space.apio.architect.identifier.ContentSpaceIdentifier;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.forms.apio.architect.identifier.FormContextIdentifier;
import com.liferay.forms.apio.architect.identifier.FormInstanceIdentifier;
import com.liferay.forms.apio.architect.identifier.FormInstanceRecordIdentifier;
import com.liferay.forms.apio.architect.identifier.StructureIdentifier;
import com.liferay.forms.apio.internal.architect.form.FetchLatestDraftForm;
import com.liferay.forms.apio.internal.architect.form.FormContextForm;
import com.liferay.forms.apio.internal.architect.form.MediaObjectCreatorForm;
import com.liferay.forms.apio.internal.architect.route.EvaluateContextRoute;
import com.liferay.forms.apio.internal.architect.route.FetchLatestDraftRoute;
import com.liferay.forms.apio.internal.architect.route.UploadFileRoute;
import com.liferay.forms.apio.internal.helper.EvaluateContextHelper;
import com.liferay.forms.apio.internal.helper.FetchLatestRecordHelper;
import com.liferay.forms.apio.internal.helper.UploadFileHelper;
import com.liferay.forms.apio.internal.model.FormContextWrapper;
import com.liferay.forms.apio.internal.util.FormInstanceRepresentorUtil;
import com.liferay.media.object.apio.architect.identifier.MediaObjectIdentifier;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.apio.user.CurrentUser;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose FormInstance resources through a
 * web API. The resources are mapped from the internal model {@code
 * DDMFormInstance}.
 *
 * @author Victor Oliveira
 */
@Component(immediate = true, service = NestedCollectionResource.class)
public class FormInstanceNestedCollectionResource
	implements NestedCollectionResource
		<DDMFormInstance, Long, FormInstanceIdentifier, Long,
		 ContentSpaceIdentifier> {

	@Override
	public NestedCollectionRoutes<DDMFormInstance, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<DDMFormInstance, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems, Company.class
		).build();
	}

	@Override
	public String getName() {
		return "form-instance";
	}

	@Override
	public ItemRoutes<DDMFormInstance, Long> itemRoutes(
		ItemRoutes.Builder<DDMFormInstance, Long> builder) {

		return builder.addGetter(
			_ddmFormInstanceService::getFormInstance
		).addCustomRoute(
			new EvaluateContextRoute(), this::_evaluateContext,
			AcceptLanguage.class, DDMFormRenderingContext.class,
			ThemeDisplay.class, FormContextIdentifier.class,
			this::_hasPermission, FormContextForm::buildForm
		).addCustomRoute(
			new FetchLatestDraftRoute(), this::_fetchDDMFormInstanceRecord,
			CurrentUser.class, FormInstanceRecordIdentifier.class,
			this::_hasPermission, FetchLatestDraftForm::buildForm
		).addCustomRoute(
			new UploadFileRoute(), this::_uploadFile,
			MediaObjectIdentifier.class, this::_hasPermission,
			MediaObjectCreatorForm::buildForm
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
			"contentSpace", "formInstances", ContentSpaceIdentifier.class,
			DDMFormInstance::getGroupId
		).addDate(
			"dateCreated", DDMFormInstance::getCreateDate
		).addDate(
			"dateModified", DDMFormInstance::getModifiedDate
		).addDate(
			"datePublished", DDMFormInstance::getLastPublishDate
		).addLinkedModel(
			"creator", PersonIdentifier.class, DDMFormInstance::getUserId
		).addLinkedModel(
			"structure", StructureIdentifier.class,
			DDMFormInstance::getStructureId
		).addNested(
			"settings", FormInstanceRepresentorUtil::getSettings,
			FormInstanceNestedCollectionResource::_buildSettings
		).addNested(
			"version", FormInstanceRepresentorUtil::getVersion,
			nestedBuilder -> nestedBuilder.types(
				"FormInstanceVersion"
			).addLinkedModel(
				"creator", PersonIdentifier.class,
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
			"availableLanguages",
			FormInstanceRepresentorUtil::getAvailableLanguages
		).build();
	}

	private static NestedRepresentor<DDMFormInstanceSettings> _buildSettings(
		NestedRepresentor.Builder<DDMFormInstanceSettings> builder) {

		return builder.types(
			"FormInstanceSettings"
		).addBoolean(
			"isPublished", DDMFormInstanceSettings::published
		).addBoolean(
			"isRequireAuthentication",
			DDMFormInstanceSettings::requireAuthentication
		).addBoolean(
			"isRequireCaptcha", DDMFormInstanceSettings::requireCaptcha
		).addNested(
			"emailNotification", identity(),
			emailSettingsBuilder -> emailSettingsBuilder.types(
				"EmailMessage"
			).addBoolean(
				"isEnabled", DDMFormInstanceSettings::sendEmailNotification
			).addNested(
				"sender", identity(),
				senderBuilder -> senderBuilder.types(
					"ContactPoint"
				).addString(
					"email", DDMFormInstanceSettings::emailFromAddress
				).addString(
					"name", DDMFormInstanceSettings::emailFromName
				).build()
			).addNested(
				"toRecipient", identity(),
				toRecipientBuilder -> toRecipientBuilder.types(
					"ContactPoint"
				).addString(
					"email", DDMFormInstanceSettings::emailToAddress
				).build()
			).addString(
				"about", DDMFormInstanceSettings::emailSubject
			).build()
		).addString(
			"redirectURL", DDMFormInstanceSettings::redirectURL
		).addString(
			"storageType", DDMFormInstanceSettings::storageType
		).addString(
			"workflowDefinition", DDMFormInstanceSettings::workflowDefinition
		).build();
	}

	private FormContextWrapper _evaluateContext(
		Long ddmFormInstanceId, FormContextForm formContextForm,
		AcceptLanguage language,
		DDMFormRenderingContext ddmFormRenderingContext,
		ThemeDisplay themeDisplay) {

		return Try.fromFallible(
			() -> _ddmFormInstanceService.getFormInstance(ddmFormInstanceId)
		).map(
			DDMFormInstance::getStructure
		).map(
			ddmStructure -> _evaluateContextHelper.evaluateContext(
				formContextForm.getFieldValues(), ddmStructure,
				ddmFormRenderingContext, language.getPreferredLocale())
		).orElse(
			null
		);
	}

	private DDMFormInstanceRecord _fetchDDMFormInstanceRecord(
		Long ddmFormInstanceId, FetchLatestDraftForm fetchLatestDraftForm,
		CurrentUser currentUser) {

		return Try.fromFallible(
			() -> _ddmFormInstanceService.getFormInstance(ddmFormInstanceId)
		).map(
			ddmFormInstance ->
				_fetchLatestRecordVersionHelper.fetchLatestDraftRecord(
					ddmFormInstance, currentUser)
		).orElse(
			null
		);
	}

	private PageItems<DDMFormInstance> _getPageItems(
		Pagination pagination, long groupId, Company company) {

		List<DDMFormInstance> ddmFormInstances =
			_ddmFormInstanceService.getFormInstances(
				company.getCompanyId(), groupId, pagination.getStartPosition(),
				pagination.getEndPosition());
		int count = _ddmFormInstanceService.getFormInstancesCount(
			company.getCompanyId(), groupId);

		return new PageItems<>(ddmFormInstances, count);
	}

	private Boolean _hasPermission(
		Credentials credentials, Long formInstanceId) {

		return Try.fromFallible(
			() -> _hasPermission.forAddingIn(
				FormInstanceRecordIdentifier.class
			).apply(
				credentials, formInstanceId
			)
		).orElse(
			false
		);
	}

	private FileEntry _uploadFile(
		Long ddmFormInstanceId, MediaObjectCreatorForm mediaObjectCreatorForm) {

		return Try.fromFallible(
			() -> _ddmFormInstanceService.getFormInstance(ddmFormInstanceId)
		).map(
			ddmFormInstance -> _uploadFileHelper.uploadFile(
				ddmFormInstance, mediaObjectCreatorForm)
		).orElse(
			null
		);
	}

	@Reference
	private DDMFormInstanceService _ddmFormInstanceService;

	@Reference
	private EvaluateContextHelper _evaluateContextHelper;

	@Reference
	private FetchLatestRecordHelper _fetchLatestRecordVersionHelper;

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord)"
	)
	private HasPermission<Long> _hasPermission;

	@Reference
	private UploadFileHelper _uploadFileHelper;

}