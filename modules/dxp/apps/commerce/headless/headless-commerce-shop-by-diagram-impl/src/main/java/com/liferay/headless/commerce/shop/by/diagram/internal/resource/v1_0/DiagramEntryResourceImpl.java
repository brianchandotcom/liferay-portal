/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.headless.commerce.shop.by.diagram.internal.resource.v1_0;

import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramEntryService;
import com.liferay.headless.commerce.shop.by.diagram.dto.v1_0.DiagramEntry;
import com.liferay.headless.commerce.shop.by.diagram.internal.dto.v1_0.converter.DiagramEntryDTOConverter;
import com.liferay.headless.commerce.shop.by.diagram.resource.v1_0.DiagramEntryResource;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/diagram-entry.properties",
	scope = ServiceScope.PROTOTYPE, service = DiagramEntryResource.class
)
public class DiagramEntryResourceImpl extends BaseDiagramEntryResourceImpl {

	@Override
	public void deleteDiagramEntry(Long diagramEntryId) throws Exception {
		_cpDefinitionDiagramEntryService.deleteCPDefinitionDiagramEntry(
			diagramEntryId);
	}

	@Override
	public Page<DiagramEntry>
			getProductByExternalReferenceCodeDiagramEntriesPage(
				String externalReferenceCode, String search,
				Pagination pagination, Sort[] sorts)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with external reference code " +
					externalReferenceCode);
		}

		return _getDiagramEntriesPage(search, pagination, sorts);
	}

	@Override
	public Page<DiagramEntry> getProductIdDiagramEntriesPage(
			Long productId, String search, Pagination pagination, Sort[] sorts)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(productId);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with ID " + productId);
		}

		return _getDiagramEntriesPage(search, pagination, sorts);
	}

	@Override
	public DiagramEntry patchDiagramEntry(
			Long diagramEntryId, DiagramEntry diagramEntry)
		throws Exception {

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry =
			_cpDefinitionDiagramEntryService.getCPDefinitionDiagramEntry(
				diagramEntryId);

		_cpDefinitionDiagramEntryService.updateCPDefinitionDiagramEntry(
			cpDefinitionDiagramEntry.getCPDefinitionDiagramEntryId(),
			GetterUtil.get(
				diagramEntry.getSkuUuid(),
				cpDefinitionDiagramEntry.getCPInstanceUuid()),
			GetterUtil.get(
				diagramEntry.getProductId(),
				cpDefinitionDiagramEntry.getCProductId()),
			GetterUtil.get(
				diagramEntry.getDiagram(),
				cpDefinitionDiagramEntry.isDiagram()),
			GetterUtil.get(
				diagramEntry.getQuantity(),
				cpDefinitionDiagramEntry.getQuantity()),
			GetterUtil.get(
				diagramEntry.getSku(), cpDefinitionDiagramEntry.getSku()),
			GetterUtil.get(
				diagramEntry.getSequence(),
				cpDefinitionDiagramEntry.getSequence()),
			new ServiceContext());

		return _toDiagramEntry(
			cpDefinitionDiagramEntry.getCPDefinitionDiagramEntryId());
	}

	@Override
	public DiagramEntry postProductByExternalReferenceCodeDiagramEntry(
			String externalReferenceCode, DiagramEntry diagramEntry)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with external reference code " +
					externalReferenceCode);
		}

		return _addDiagramEntry(cpDefinition.getCPDefinitionId(), diagramEntry);
	}

	@Override
	public DiagramEntry postProductIdDiagramEntry(
			Long productId, DiagramEntry diagramEntry)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(productId);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with ID " + productId);
		}

		return _addDiagramEntry(cpDefinition.getCPDefinitionId(), diagramEntry);
	}

	private DiagramEntry _addDiagramEntry(
			long cpDefinitionId, DiagramEntry diagramEntry)
		throws Exception {

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry =
			_cpDefinitionDiagramEntryService.addCPDefinitionDiagramEntry(
				contextUser.getUserId(), cpDefinitionId,
				GetterUtil.getString(diagramEntry.getSkuUuid()),
				GetterUtil.getLong(diagramEntry.getProductId()),
				GetterUtil.getBoolean(diagramEntry.getDiagram()),
				GetterUtil.getInteger(diagramEntry.getQuantity()),
				GetterUtil.getString(diagramEntry.getSku()),
				GetterUtil.getString(diagramEntry.getSequence()),
				new ServiceContext());

		return _toDiagramEntry(
			cpDefinitionDiagramEntry.getCPDefinitionDiagramEntryId());
	}

	private Page<DiagramEntry> _getDiagramEntriesPage(
			String search, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			null,
			booleanQuery -> {
			},
			null, CPDefinitionDiagramEntry.class.getName(), search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setCompanyId(contextCompany.getCompanyId());

				if (Validator.isNotNull(search)) {
					searchContext.setKeywords(search);
				}
			},
			sorts,
			document -> _toDiagramEntry(
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))));
	}

	private DiagramEntry _toDiagramEntry(long cpDefinitionDiagramEntryId)
		throws Exception {

		return _diagramEntryDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				cpDefinitionDiagramEntryId,
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private CPDefinitionDiagramEntryService _cpDefinitionDiagramEntryService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private DiagramEntryDTOConverter _diagramEntryDTOConverter;

}