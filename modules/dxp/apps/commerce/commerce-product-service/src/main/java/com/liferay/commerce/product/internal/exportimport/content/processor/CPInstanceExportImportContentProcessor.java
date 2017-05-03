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

package com.liferay.commerce.product.internal.exportimport.content.processor;

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalService;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.StagedModel;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	property = {
		"model.class.name=com.liferay.commerce.product.model.CPInstance"
	},
	service = {
		CPInstanceExportImportContentProcessor.class,
		ExportImportContentProcessor.class
	}
)
public class CPInstanceExportImportContentProcessor
	implements ExportImportContentProcessor<String> {

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content, boolean exportReferencedContent,
			boolean escapeContent)
		throws Exception {

		JSONArray jsonArray = _jsonFactory.createJSONArray(content);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			long cpDefinitionOptionRelId = jsonObject.getLong(
				"cpDefinitionOptionRelId");

			CPDefinitionOptionRel cpDefinitionOptionRel =
				_cpDefinitionOptionRelLocalService.getCPDefinitionOptionRel(
					cpDefinitionOptionRelId);

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, stagedModel, cpDefinitionOptionRel,
				PortletDataContext.REFERENCE_TYPE_STRONG);

			jsonObject.put(
				"cpDefinitionOptionRelId", cpDefinitionOptionRel.getUuid());

			long cpDefinitionOptionValueRelId = jsonObject.getLong(
				"cpDefinitionOptionValueRelId");

			CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
				_cpDefinitionOptionValueRelLocalService.
					getCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId);

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, stagedModel, cpDefinitionOptionValueRel,
				PortletDataContext.REFERENCE_TYPE_STRONG);

			jsonObject.put(
				"cpDefinitionOptionValueRelId",
				cpDefinitionOptionValueRel.getUuid());
		}

		return jsonArray.toJSONString();
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		JSONArray jsonArray = _jsonFactory.createJSONArray(content);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String cpDefinitionOptionRelUuid = jsonObject.getString(
				"cpDefinitionOptionRelId");

			CPDefinitionOptionRel cpDefinitionOptionRel =
				_cpDefinitionOptionRelLocalService.
					getCPDefinitionOptionRelByUuidAndGroupId(
						cpDefinitionOptionRelUuid,
						portletDataContext.getScopeGroupId());

			jsonObject.put(
				"cpDefinitionOptionRelId",
				cpDefinitionOptionRel.getCPDefinitionOptionRelId());

			String cpDefinitionOptionValueRelUuid = jsonObject.getString(
				"cpDefinitionOptionValueRelId");

			CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
				_cpDefinitionOptionValueRelLocalService.
					getCPDefinitionOptionValueRelByUuidAndGroupId(
						cpDefinitionOptionValueRelUuid,
						portletDataContext.getScopeGroupId());

			jsonObject.put(
				"cpDefinitionOptionValueRelId",
				cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId());
		}

		return jsonArray.toJSONString();
	}

	@Override
	public void validateContentReferences(long groupId, String content)
		throws PortalException {
	}

	@Reference
	private CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

	@Reference
	private CPDefinitionOptionValueRelLocalService
		_cpDefinitionOptionValueRelLocalService;

	@Reference
	private JSONFactory _jsonFactory;

}