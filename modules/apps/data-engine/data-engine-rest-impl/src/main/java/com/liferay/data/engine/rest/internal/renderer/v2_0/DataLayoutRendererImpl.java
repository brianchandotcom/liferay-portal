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

package com.liferay.data.engine.rest.internal.renderer.v2_0;

import com.liferay.data.engine.renderer.DataLayoutRenderer;
import com.liferay.data.engine.renderer.DataLayoutRendererContext;
import com.liferay.data.engine.rest.internal.dto.v2_0.util.MapToDDMFormValuesConverterUtil;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.auth.GuestOrUserUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(immediate = true, service = DataLayoutRenderer.class)
public class DataLayoutRendererImpl implements DataLayoutRenderer {

	@Override
	public String render(
			Long dataLayoutId,
			DataLayoutRendererContext dataLayoutRendererContext)
		throws Exception {

		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.getStructureLayout(dataLayoutId);

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.getDDMStructureVersion(
				ddmStructureLayout.getStructureVersionId());

		DDMStructure ddmStructure = ddmStructureVersion.getStructure();

		_ddmStructureModelResourcePermission.check(
			GuestOrUserUtil.getPermissionChecker(),
			ddmStructure.getPrimaryKey(), ActionKeys.VIEW);

		DDMForm ddmForm = ddmStructure.getDDMForm();

		return _ddmFormRenderer.render(
			ddmForm, ddmStructureLayout.getDDMFormLayout(),
			_toDDMFormRenderingContext(
				dataLayoutId, dataLayoutRendererContext, ddmForm,
				ddmStructure.getGroupId()));
	}

	private DDMFormRenderingContext _toDDMFormRenderingContext(
			Long dataLayoutId,
			DataLayoutRendererContext dataLayoutRendererContext,
			DDMForm ddmForm, long groupId)
		throws Exception {

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setContainerId(
			dataLayoutRendererContext.getContainerId());
		ddmFormRenderingContext.setDDMFormValues(
			MapToDDMFormValuesConverterUtil.toDDMFormValues(
				dataLayoutRendererContext.getDataRecordValues(), ddmForm,
				null));
		ddmFormRenderingContext.setDDMStructureLayoutId(dataLayoutId);
		ddmFormRenderingContext.setGroupId(groupId);
		ddmFormRenderingContext.setHttpServletRequest(
			dataLayoutRendererContext.getHttpServletRequest());
		ddmFormRenderingContext.setHttpServletResponse(
			dataLayoutRendererContext.getHttpServletResponse());

		Locale locale = null;

		String languageId = ParamUtil.get(
			dataLayoutRendererContext.getHttpServletRequest(), "languageId",
			StringPool.BLANK);

		if (Validator.isNull(languageId)) {
			locale = ddmForm.getDefaultLocale();
		}
		else {
			locale = LocaleUtil.fromLanguageId(languageId);
		}

		ddmFormRenderingContext.setLocale(locale);

		ddmFormRenderingContext.setPortletNamespace(
			dataLayoutRendererContext.getPortletNamespace());
		ddmFormRenderingContext.setReadOnly(
			dataLayoutRendererContext.isReadOnly());
		ddmFormRenderingContext.setShowSubmitButton(false);
		ddmFormRenderingContext.setViewMode(true);

		return ddmFormRenderingContext;
	}

	@Reference(target = "(ddm.form.deserializer.type=json)")
	private DDMFormDeserializer _ddmFormDeserializer;

	@Reference
	private DDMFormRenderer _ddmFormRenderer;

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.mapping.model.DDMStructure)"
	)
	private ModelResourcePermission<DDMStructure>
		_ddmStructureModelResourcePermission;

	@Reference
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	@Reference
	private Portal _portal;

}