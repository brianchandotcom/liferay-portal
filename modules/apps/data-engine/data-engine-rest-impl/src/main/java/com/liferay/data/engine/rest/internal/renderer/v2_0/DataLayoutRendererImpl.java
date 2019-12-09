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
import com.liferay.data.engine.rest.internal.dto.v2_0.util.DataRecordValuesUtil;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.portal.kernel.util.Portal;

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

		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(
				ddmStructure.getDefinition());

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_ddmFormDeserializer.deserialize(builder.build());

		return _ddmFormRenderer.render(
			ddmFormDeserializerDeserializeResponse.getDDMForm(),
			ddmStructureLayout.getDDMFormLayout(),
			_toDDMFormRenderingContext(
				dataLayoutRendererContext,
				ddmFormDeserializerDeserializeResponse.getDDMForm()));
	}

	private DDMFormRenderingContext _toDDMFormRenderingContext(
		DataLayoutRendererContext dataLayoutRendererContext, DDMForm ddmForm) {

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setContainerId(
			dataLayoutRendererContext.getContainerId());
		ddmFormRenderingContext.setDDMFormValues(
			DataRecordValuesUtil.toDDMFormValues(
				dataLayoutRendererContext.getDataRecordValues(), ddmForm,
				_portal.getLocale(
					dataLayoutRendererContext.getHttpServletRequest())));
		ddmFormRenderingContext.setHttpServletRequest(
			dataLayoutRendererContext.getHttpServletRequest());
		ddmFormRenderingContext.setHttpServletResponse(
			dataLayoutRendererContext.getHttpServletResponse());
		ddmFormRenderingContext.setLocale(
			_portal.getLocale(
				dataLayoutRendererContext.getHttpServletRequest()));
		ddmFormRenderingContext.setPortletNamespace(
			dataLayoutRendererContext.getPortletNamespace());
		ddmFormRenderingContext.setShowSubmitButton(false);

		return ddmFormRenderingContext;
	}

	@Reference(target = "(ddm.form.deserializer.type=json)")
	private DDMFormDeserializer _ddmFormDeserializer;

	@Reference
	private DDMFormRenderer _ddmFormRenderer;

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	@Reference
	private Portal _portal;

}