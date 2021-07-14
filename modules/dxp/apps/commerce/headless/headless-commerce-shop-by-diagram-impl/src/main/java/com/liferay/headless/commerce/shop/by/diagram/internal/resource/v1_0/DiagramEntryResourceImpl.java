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
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		"api.version=v1.0", "batch.engine.task.item.delegate=true",
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Commerce.Admin.Catalog)",
		"osgi.jaxrs.resource=true"
	},
	scope = ServiceScope.PROTOTYPE, service = DiagramEntryResource.class
)
public class DiagramEntryResourceImpl extends BaseDiagramEntryResourceImpl {

	@Override
	public Response deleteDiagramEntry(@NotNull Long id) throws Exception {
		_cpDefinitionDiagramEntryService.deleteCPDefinitionDiagramEntry(id);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Page<DiagramEntry>
			getProductByExternalReferenceCodeDiagramEntriesPage(
				@NotNull String externalReferenceCode, String search,
				Pagination pagination, Sort[] sorts)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with externalReferenceCode: " +
					externalReferenceCode);
		}

		List<CPDefinitionDiagramEntry> cpDefinitionDiagramEntries =
			_cpDefinitionDiagramEntryService.getCPDefinitionDiagramEntries(
				cpDefinition.getCPDefinitionId(), pagination.getStartPosition(),
				pagination.getEndPosition());

		int cpDefinitionDiagramEntriesCount =
			_cpDefinitionDiagramEntryService.getCPDefinitionDiagramEntriesCount(
				cpDefinition.getCPDefinitionId());

		return Page.of(
			_toDiagramEntries(cpDefinitionDiagramEntries), pagination,
			cpDefinitionDiagramEntriesCount);
	}

	@Override
	public Page<DiagramEntry> getProductIdDiagramEntriesPage(
			@NotNull Long id, String search, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + id);
		}

		List<CPDefinitionDiagramEntry> cpDefinitionDiagramEntries =
			_cpDefinitionDiagramEntryService.getCPDefinitionDiagramEntries(
				cpDefinition.getCPDefinitionId(), pagination.getStartPosition(),
				pagination.getEndPosition());

		int cpDefinitionDiagramEntriesCount =
			_cpDefinitionDiagramEntryService.getCPDefinitionDiagramEntriesCount(
				cpDefinition.getCPDefinitionId());

		return Page.of(
			_toDiagramEntries(cpDefinitionDiagramEntries), pagination,
			cpDefinitionDiagramEntriesCount);
	}

	@Override
	public Response patchDiagramEntry(
			@NotNull Long id, DiagramEntry diagramEntry)
		throws Exception {

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry =
			_cpDefinitionDiagramEntryService.getCPDefinitionDiagramEntry(id);

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
				diagramEntry.getNumber(), cpDefinitionDiagramEntry.getNumber()),
			GetterUtil.get(
				diagramEntry.getQuantity(),
				cpDefinitionDiagramEntry.getQuantity()),
			GetterUtil.get(
				diagramEntry.getSku(), cpDefinitionDiagramEntry.getSku()),
			new ServiceContext());

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public DiagramEntry postProductByExternalReferenceCodeDiagramEntry(
			@NotNull String externalReferenceCode, DiagramEntry diagramEntry)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with externalReferenceCode: " +
					externalReferenceCode);
		}

		return _addDiagramEntry(cpDefinition.getCPDefinitionId(), diagramEntry);
	}

	@Override
	public DiagramEntry postProductIdDiagramEntry(
			@NotNull Long id, DiagramEntry diagramEntry)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + id);
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
				GetterUtil.getInteger(diagramEntry.getNumber()),
				GetterUtil.getInteger(diagramEntry.getQuantity()),
				GetterUtil.getString(diagramEntry.getSku()),
				new ServiceContext());

		return _diagramEntryDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				cpDefinitionDiagramEntry.getCPDefinitionDiagramEntryId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	private List<DiagramEntry> _toDiagramEntries(
			List<CPDefinitionDiagramEntry> cpDefinitionDiagramEntries)
		throws Exception {

		List<DiagramEntry> diagramEntries = new ArrayList<>();

		for (CPDefinitionDiagramEntry cpDefinitionDiagramEntry :
				cpDefinitionDiagramEntries) {

			diagramEntries.add(
				_diagramEntryDTOConverter.toDTO(
					new DefaultDTOConverterContext(
						cpDefinitionDiagramEntry.
							getCPDefinitionDiagramEntryId(),
						contextAcceptLanguage.getPreferredLocale())));
		}

		return diagramEntries;
	}

	@Reference
	private CPDefinitionDiagramEntryService _cpDefinitionDiagramEntryService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private DiagramEntryDTOConverter _diagramEntryDTOConverter;

}