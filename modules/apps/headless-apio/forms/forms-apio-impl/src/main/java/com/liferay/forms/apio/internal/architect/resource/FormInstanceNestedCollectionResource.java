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

import static com.liferay.portal.apio.permission.HasPermissionUtil.failOnException;

import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.language.AcceptLanguage;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.content.space.apio.architect.model.ContentSpace;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionService;
import com.liferay.forms.apio.architect.identifier.FormContextIdentifier;
import com.liferay.forms.apio.architect.identifier.FormInstanceIdentifier;
import com.liferay.forms.apio.architect.identifier.FormInstanceRecordIdentifier;
import com.liferay.forms.apio.architect.identifier.StructureIdentifier;
import com.liferay.forms.apio.internal.architect.form.FetchLatestDraftForm;
import com.liferay.forms.apio.internal.architect.form.FormContextForm;
import com.liferay.forms.apio.internal.architect.form.MediaObjectCreatorForm;
import com.liferay.forms.apio.internal.architect.route.EvaluateContextPostRoute;
import com.liferay.forms.apio.internal.architect.route.FetchLatestDraftRoute;
import com.liferay.forms.apio.internal.architect.route.UploadFilePostRoute;
import com.liferay.forms.apio.internal.helper.EvaluateContextHelper;
import com.liferay.forms.apio.internal.helper.FetchLatestRecordHelper;
import com.liferay.forms.apio.internal.helper.UploadFileHelper;
import com.liferay.forms.apio.internal.model.FormContextWrapper;
import com.liferay.media.object.apio.architect.identifier.MediaObjectIdentifier;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.apio.user.CurrentUser;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

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
		<DDMFormInstance, Long, FormInstanceIdentifier, Long, ContentSpace> {

	@Override
	public NestedCollectionRoutes<DDMFormInstance, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<DDMFormInstance, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems, Company.class
		).build();
	}

	@Override
	public String getName() {
		return "form";
	}

	@Override
	public ItemRoutes<DDMFormInstance, Long> itemRoutes(
		ItemRoutes.Builder<DDMFormInstance, Long> builder) {

		return builder.addGetter(
			_ddmFormInstanceService::getFormInstance
		).addCustomRoute(
			new EvaluateContextPostRoute(), this::_evaluateContext,
			AcceptLanguage.class, DDMFormRenderingContext.class,
			ThemeDisplay.class, FormContextIdentifier.class,
			_getPermissionBiFunction(), FormContextForm::buildForm
		).addCustomRoute(
			new FetchLatestDraftRoute(), this::_fetchDDMFormInstanceRecord,
			CurrentUser.class, FormInstanceRecordIdentifier.class,
			_getPermissionBiFunction(), FetchLatestDraftForm::buildForm
		).addCustomRoute(
			new UploadFilePostRoute(), this::_uploadFile,
			MediaObjectIdentifier.class, _getPermissionBiFunction(),
			MediaObjectCreatorForm::buildForm
		).build();
	}

	@Override
	public Representor<DDMFormInstance> representor(
		Representor.Builder<DDMFormInstance, Long> builder) {

		return builder.types(
			"Form"
		).identifier(
			DDMFormInstance::getFormInstanceId
		).addBidirectionalModel(
			"contentSpace", "forms", ContentSpace.class,
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
			"structure", StructureIdentifier.class, this::_getStructureId
		).addLocalizedStringByLocale(
			"description", DDMFormInstance::getDescription
		).addLocalizedStringByLocale(
			"name", DDMFormInstance::getName
		).addString(
			"defaultLanguage", DDMFormInstance::getDefaultLanguageId
		).addStringList(
			"availableLanguages",
			formInstance -> Arrays.asList(
				LocaleUtil.toW3cLanguageIds(
					formInstance.getAvailableLanguageIds()))
		).build();
	}

	private FormContextWrapper _evaluateContext(
		Long ddmFormInstanceId, FormContextForm formContextForm,
		AcceptLanguage acceptLanguage,
		DDMFormRenderingContext ddmFormRenderingContext,
		ThemeDisplay themeDisplay) {

		return Try.fromFallible(
			() -> _ddmFormInstanceService.getFormInstance(ddmFormInstanceId)
		).map(
			DDMFormInstance::getStructure
		).map(
			ddmStructure -> _evaluateContextHelper.evaluateContext(
				formContextForm.getFieldValues(), ddmStructure,
				ddmFormRenderingContext, acceptLanguage.getPreferredLocale())
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

	private BiFunction<Credentials, Long, Boolean> _getPermissionBiFunction() {
		return failOnException(
			_hasPermission.forAddingIn(FormInstanceRecordIdentifier.class));
	}

	private Long _getStructureId(DDMFormInstance ddmFormInstance) {
		return Try.fromFallible(
			() -> _ddmFormInstanceVersionService.getLatestFormInstanceVersion(
				ddmFormInstance.getFormInstanceId(),
				WorkflowConstants.STATUS_APPROVED)
		).map(
			DDMFormInstanceVersion::getStructureVersion
		).map(
			DDMStructureVersion::getStructureId
		).orElse(
			null
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
	private DDMFormInstanceVersionService _ddmFormInstanceVersionService;

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