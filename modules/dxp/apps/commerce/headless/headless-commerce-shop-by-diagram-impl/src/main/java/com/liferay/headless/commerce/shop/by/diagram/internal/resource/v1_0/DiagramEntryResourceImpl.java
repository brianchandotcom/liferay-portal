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
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramEntryService;
import com.liferay.headless.commerce.shop.by.diagram.dto.v1_0.DiagramEntry;
import com.liferay.headless.commerce.shop.by.diagram.internal.dto.v1_0.converter.DiagramEntryDTOConverter;
import com.liferay.headless.commerce.shop.by.diagram.resource.v1_0.DiagramEntryResource;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

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
		_csDiagramEntryService.deleteCSDiagramEntry(diagramEntryId);
	}

	@Override
	public Page<DiagramEntry>
			getProductByExternalReferenceCodeDiagramEntriesPage(
				String externalReferenceCode, Pagination pagination)
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

		return _getDiagramEntriesPage(
			cpDefinition.getCPDefinitionId(), pagination);
	}

	@Override
	public Page<DiagramEntry> getProductIdDiagramEntriesPage(
			Long productId, Pagination pagination)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(productId);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with ID " + productId);
		}

		return _getDiagramEntriesPage(
			cpDefinition.getCPDefinitionId(), pagination);
	}

	@Override
	public DiagramEntry patchDiagramEntry(
			Long diagramEntryId, DiagramEntry diagramEntry)
		throws Exception {

		CSDiagramEntry csDiagramEntry =
			_csDiagramEntryService.getCSDiagramEntry(diagramEntryId);

		_csDiagramEntryService.updateCSDiagramEntry(
			csDiagramEntry.getCSDiagramEntryId(),
			GetterUtil.get(
				diagramEntry.getSkuUuid(), csDiagramEntry.getCPInstanceId()),
			GetterUtil.get(
				diagramEntry.getProductId(), csDiagramEntry.getCProductId()),
			GetterUtil.get(
				diagramEntry.getDiagram(), csDiagramEntry.isDiagram()),
			GetterUtil.get(
				diagramEntry.getQuantity(), csDiagramEntry.getQuantity()),
			GetterUtil.get(
				diagramEntry.getSequence(), csDiagramEntry.getSequence()),
			GetterUtil.get(diagramEntry.getSku(), csDiagramEntry.getSku()),
			new ServiceContext());

		return _toDiagramEntry(csDiagramEntry.getCSDiagramEntryId());
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

		long skuId = GetterUtil.getLong(diagramEntry.getSkuUuid());

		CPInstance cpInstance = _cpInstanceService.fetchByExternalReferenceCode(
			diagramEntry.getSkuExternalReferenceCode(),
			contextCompany.getCompanyId());

		if (cpInstance != null) {
			skuId = cpInstance.getCPInstanceId();
		}

		long productId = GetterUtil.getLong(diagramEntry.getProductId());

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					diagramEntry.getProductExternalReferenceCode(),
					contextCompany.getCompanyId());

		if (cpDefinition != null) {
			productId = cpDefinition.getCProductId();
		}

		CSDiagramEntry csDiagramEntry =
			_csDiagramEntryService.addCSDiagramEntry(
				cpDefinitionId, skuId, productId,
				GetterUtil.getBoolean(diagramEntry.getDiagram()),
				GetterUtil.getInteger(diagramEntry.getQuantity()),
				GetterUtil.getString(diagramEntry.getSequence()),
				GetterUtil.getString(diagramEntry.getSku()),
				new ServiceContext());

		return _toDiagramEntry(csDiagramEntry.getCSDiagramEntryId());
	}

	private Page<DiagramEntry> _getDiagramEntriesPage(
			long cpDefinitionId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_csDiagramEntryService.getCSDiagramEntries(
					cpDefinitionId, pagination.getStartPosition(),
					pagination.getEndPosition()),
				csDiagramEntry -> _toDiagramEntry(
					csDiagramEntry.getCSDiagramEntryId())),
			pagination,
			_csDiagramEntryService.getCSDiagramEntriesCount(cpDefinitionId));
	}

	private DiagramEntry _toDiagramEntry(long csDiagramEntryId)
		throws Exception {

		return _diagramEntryDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				csDiagramEntryId, contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference
	private CSDiagramEntryService _csDiagramEntryService;

	@Reference
	private DiagramEntryDTOConverter _diagramEntryDTOConverter;

}