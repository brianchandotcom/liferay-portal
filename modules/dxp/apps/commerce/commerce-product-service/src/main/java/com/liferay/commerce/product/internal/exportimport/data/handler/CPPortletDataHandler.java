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

package com.liferay.commerce.product.internal.exportimport.data.handler;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.service.permission.CPPermission;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + CPPortletKeys.COMMERCE_PRODUCT_DEFINITIONS},
	service = PortletDataHandler.class
)
public class CPPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "commerce_product";

	@Activate
	protected void activate() {
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(CPAttachmentFileEntry.class),
			new StagedModelType(CPDefinition.class),
			new StagedModelType(CPDefinitionOptionRel.class),
			new StagedModelType(CPDefinitionOptionValueRel.class),
			new StagedModelType(CPInstance.class),
			new StagedModelType(CPOption.class),
			new StagedModelType(CPOptionCategory.class),
			new StagedModelType(CPOptionValue.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "products", true, false,
				new PortletDataHandlerControl[] {
					new PortletDataHandlerBoolean(
						NAMESPACE, "referenced-content")
				},
				CPDefinition.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "skus", true, false, null,
				CPInstance.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "options", true, false, null,
				CPOption.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "option-categories", true, false, null,
				CPOptionCategory.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "option-values", true, false, null,
				CPOptionValue.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "attachments", true, false, null,
				CPAttachmentFileEntry.class.getName()));
		setImportControls(getExportControls());
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				CPPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		_cpAttachmentFileEntryStagedModelRepository.deleteStagedModels(
			portletDataContext);
		_cpDefinitionStagedModelRepository.deleteStagedModels(
			portletDataContext);
		_cpInstanceStagedModelRepository.deleteStagedModels(portletDataContext);
		_cpOptionStagedModelRepository.deleteStagedModels(portletDataContext);
		_cpOptionCategoryStagedModelRepository.deleteStagedModels(
			portletDataContext);
		_cpOptionValueStagedModelRepository.deleteStagedModels(
			portletDataContext);

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPortletPermissions(CPPermission.RESOURCE_NAME);

		Element rootElement = addExportDataRootElement(portletDataContext);

		if (portletDataContext.getBooleanParameter(NAMESPACE, "products")) {
			ActionableDynamicQuery cpDefinitionActionableDynamicQuery =
				_cpDefinitionStagedModelRepository.
					getExportActionableDynamicQuery(portletDataContext);

			cpDefinitionActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "skus")) {
			ActionableDynamicQuery cpInstanceActionableDynamicQuery =
				_cpInstanceStagedModelRepository.
					getExportActionableDynamicQuery(portletDataContext);

			cpInstanceActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "options")) {
			ActionableDynamicQuery cpOptionActionableDynamicQuery =
				_cpOptionStagedModelRepository.getExportActionableDynamicQuery(
					portletDataContext);

			cpOptionActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(
				NAMESPACE, "option-categories")) {

			ActionableDynamicQuery cpOptionCategoryActionableDynamicQuery =
				_cpOptionCategoryStagedModelRepository.
					getExportActionableDynamicQuery(portletDataContext);

			cpOptionCategoryActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(
				NAMESPACE, "option-values")) {

			ActionableDynamicQuery cpOptionValueActionableDynamicQuery =
				_cpOptionValueStagedModelRepository.
					getExportActionableDynamicQuery(portletDataContext);

			cpOptionValueActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "attachments")) {
			ActionableDynamicQuery cpAttachmentFileEntryActionableDynamicQuery =
				_cpAttachmentFileEntryStagedModelRepository.
					getExportActionableDynamicQuery(portletDataContext);

			cpAttachmentFileEntryActionableDynamicQuery.performActions();
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPortletPermissions(CPPermission.RESOURCE_NAME);

		if (portletDataContext.getBooleanParameter(NAMESPACE, "products")) {
			importModels(portletDataContext, CPDefinition.class);
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "skus")) {
			importModels(portletDataContext, CPInstance.class);
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "options")) {
			importModels(portletDataContext, CPOption.class);
		}

		if (portletDataContext.getBooleanParameter(
				NAMESPACE, "option-categories")) {

			importModels(portletDataContext, CPOptionCategory.class);
		}

		if (portletDataContext.getBooleanParameter(
				NAMESPACE, "option-values")) {

			importModels(portletDataContext, CPOptionValue.class);
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "attachments")) {
			importModels(portletDataContext, CPAttachmentFileEntry.class);
		}

		return portletPreferences;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		ActionableDynamicQuery cpDefinitionActionableDynamicQuery =
			_cpDefinitionStagedModelRepository.getExportActionableDynamicQuery(
				portletDataContext);

		cpDefinitionActionableDynamicQuery.performCount();

		ActionableDynamicQuery cpInstanceActionableDynamicQuery =
			_cpInstanceStagedModelRepository.getExportActionableDynamicQuery(
				portletDataContext);

		cpInstanceActionableDynamicQuery.performCount();

		ActionableDynamicQuery cpOptionActionableDynamicQuery =
			_cpOptionStagedModelRepository.getExportActionableDynamicQuery(
				portletDataContext);

		cpOptionActionableDynamicQuery.performCount();

		ActionableDynamicQuery cpOptionCategoryActionableDynamicQuery =
			_cpOptionCategoryStagedModelRepository.
				getExportActionableDynamicQuery(portletDataContext);

		cpOptionCategoryActionableDynamicQuery.performCount();

		ActionableDynamicQuery cpOptionValueActionableDynamicQuery =
			_cpOptionValueStagedModelRepository.getExportActionableDynamicQuery(
				portletDataContext);

		cpOptionValueActionableDynamicQuery.performCount();

		ActionableDynamicQuery cpAttachmentFileEntryActionableDynamicQuery =
			_cpAttachmentFileEntryStagedModelRepository.
				getExportActionableDynamicQuery(portletDataContext);

		cpAttachmentFileEntryActionableDynamicQuery.performCount();
	}

	protected void importModels(
			PortletDataContext portletDataContext,
			Class<? extends StagedModel> clazz)
		throws Exception {

		Element modelsElement = portletDataContext.getImportDataGroupElement(
			clazz);

		List<Element> modelElements = modelsElement.elements();

		for (Element modelElement : modelElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, modelElement);
		}
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPAttachmentFileEntry)"
	)
	private StagedModelRepository<CPAttachmentFileEntry>
		_cpAttachmentFileEntryStagedModelRepository;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPDefinition)"
	)
	private StagedModelRepository<CPDefinition>
		_cpDefinitionStagedModelRepository;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPInstance)"
	)
	private StagedModelRepository<CPInstance> _cpInstanceStagedModelRepository;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPOptionCategory)"
	)
	private StagedModelRepository<CPOptionCategory>
		_cpOptionCategoryStagedModelRepository;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPOption)"
	)
	private StagedModelRepository<CPOption> _cpOptionStagedModelRepository;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPOptionValue)"
	)
	private StagedModelRepository<CPOptionValue>
		_cpOptionValueStagedModelRepository;

}