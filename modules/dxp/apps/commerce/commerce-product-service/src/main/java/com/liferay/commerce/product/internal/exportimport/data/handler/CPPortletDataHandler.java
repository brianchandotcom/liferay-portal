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
import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.model.CPSpecificationOption;
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
	property = "javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
	service = PortletDataHandler.class
)
public class CPPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "commerce_product";

	@Activate
	protected void activate() {
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(CPAttachmentFileEntry.class),
			new StagedModelType(CPDefinition.class),
			new StagedModelType(CPDefinitionLink.class),
			new StagedModelType(CPDefinitionOptionRel.class),
			new StagedModelType(CPDefinitionOptionValueRel.class),
			new StagedModelType(CPDefinitionSpecificationOptionValue.class),
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
				NAMESPACE, "specifications", true, false, null,
				CPSpecificationOption.class.getName()),
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
		_cpDefinitionLinkStagedModelRepository.deleteStagedModels(
			portletDataContext);
		_cpDefinitionOptionRelStagedModelRepository.deleteStagedModels(
			portletDataContext);
		_cpDefinitionOptionValueRelStagedModelRepository.deleteStagedModels(
			portletDataContext);
		_cpDefinitionSpecificationOptionValueStagedModelRepository.
			deleteStagedModels(portletDataContext);
		_cpDefinitionStagedModelRepository.deleteStagedModels(
			portletDataContext);
		_cpInstanceStagedModelRepository.deleteStagedModels(portletDataContext);
		_cpOptionCategoryStagedModelRepository.deleteStagedModels(
			portletDataContext);
		_cpOptionStagedModelRepository.deleteStagedModels(portletDataContext);
		_cpOptionValueStagedModelRepository.deleteStagedModels(
			portletDataContext);
		_cpSpecificationOptionStagedModelRepository.deleteStagedModels(
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
			exportModels(
				portletDataContext, _cpDefinitionStagedModelRepository);
			exportModels(
				portletDataContext, _cpDefinitionLinkStagedModelRepository);
			exportModels(
				portletDataContext,
				_cpDefinitionOptionRelStagedModelRepository);
			exportModels(
				portletDataContext,
				_cpDefinitionOptionValueRelStagedModelRepository);
			exportModels(
				portletDataContext,
				_cpDefinitionSpecificationOptionValueStagedModelRepository);
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "skus")) {
			exportModels(portletDataContext, _cpInstanceStagedModelRepository);
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "options")) {
			exportModels(portletDataContext, _cpOptionStagedModelRepository);
		}

		if (portletDataContext.getBooleanParameter(
				NAMESPACE, "option-categories")) {

			exportModels(
				portletDataContext, _cpOptionCategoryStagedModelRepository);
		}

		if (portletDataContext.getBooleanParameter(
				NAMESPACE, "specifications")) {

			exportModels(
				portletDataContext,
				_cpSpecificationOptionStagedModelRepository);
		}

		if (portletDataContext.getBooleanParameter(
				NAMESPACE, "option-values")) {

			exportModels(
				portletDataContext, _cpOptionValueStagedModelRepository);
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "attachments")) {
			exportModels(
				portletDataContext,
				_cpAttachmentFileEntryStagedModelRepository);
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
			importModels(portletDataContext, CPDefinitionLink.class);
			importModels(portletDataContext, CPDefinitionOptionRel.class);
			importModels(portletDataContext, CPDefinitionOptionValueRel.class);
			importModels(
				portletDataContext, CPDefinitionSpecificationOptionValue.class);
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
				NAMESPACE, "specifications")) {

			importModels(portletDataContext, CPSpecificationOption.class);
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

		performModelsCount(
			portletDataContext, _cpAttachmentFileEntryStagedModelRepository);
		performModelsCount(
			portletDataContext, _cpDefinitionStagedModelRepository);
		performModelsCount(
			portletDataContext, _cpInstanceStagedModelRepository);
		performModelsCount(
			portletDataContext, _cpOptionCategoryStagedModelRepository);
		performModelsCount(portletDataContext, _cpOptionStagedModelRepository);
		performModelsCount(
			portletDataContext, _cpOptionValueStagedModelRepository);
		performModelsCount(
			portletDataContext, _cpSpecificationOptionStagedModelRepository);
	}

	protected void exportModels(
			PortletDataContext portletDataContext,
			StagedModelRepository<? extends StagedModel> stagedModelRepository)
		throws Exception {

		ActionableDynamicQuery actionableDynamicQuery =
			stagedModelRepository.getExportActionableDynamicQuery(
				portletDataContext);

		actionableDynamicQuery.performActions();
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

	protected void performModelsCount(
			PortletDataContext portletDataContext,
			StagedModelRepository<? extends StagedModel> stagedModelRepository)
		throws Exception {

		ActionableDynamicQuery actionableDynamicQuery =
			stagedModelRepository.getExportActionableDynamicQuery(
				portletDataContext);

		actionableDynamicQuery.performCount();
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPAttachmentFileEntry)"
	)
	private StagedModelRepository<CPAttachmentFileEntry>
		_cpAttachmentFileEntryStagedModelRepository;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPDefinitionLink)"
	)
	private StagedModelRepository<CPDefinitionLink>
		_cpDefinitionLinkStagedModelRepository;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPDefinitionOptionRel)"
	)
	private StagedModelRepository<CPDefinitionOptionRel>
		_cpDefinitionOptionRelStagedModelRepository;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPDefinitionOptionValueRel)"
	)
	private StagedModelRepository<CPDefinitionOptionValueRel>
		_cpDefinitionOptionValueRelStagedModelRepository;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue)"
	)
	private StagedModelRepository<CPDefinitionSpecificationOptionValue>
		_cpDefinitionSpecificationOptionValueStagedModelRepository;

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

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPSpecificationOption)"
	)
	private StagedModelRepository<CPSpecificationOption>
		_cpSpecificationOptionStagedModelRepository;

}