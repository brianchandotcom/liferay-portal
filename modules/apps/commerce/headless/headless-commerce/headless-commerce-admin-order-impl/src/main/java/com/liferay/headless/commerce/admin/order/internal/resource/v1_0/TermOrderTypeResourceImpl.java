/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.order.internal.resource.v1_0;

import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.service.CommerceOrderTypeService;
import com.liferay.commerce.term.exception.NoSuchTermEntryException;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.model.CommerceTermEntryRel;
import com.liferay.commerce.term.service.CommerceTermEntryRelService;
import com.liferay.commerce.term.service.CommerceTermEntryService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Term;
import com.liferay.headless.commerce.admin.order.dto.v1_0.TermOrderType;
import com.liferay.headless.commerce.admin.order.resource.v1_0.TermOrderTypeResource;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/term-order-type.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = TermOrderTypeResource.class
)
public class TermOrderTypeResourceImpl extends BaseTermOrderTypeResourceImpl {

	@Override
	public void deleteTermOrderType(Long id) throws Exception {
		_commerceTermEntryRelService.deleteCommerceTermEntryRel(id);
	}

	@Override
	public Page<TermOrderType> getTermByExternalReferenceCodeTermOrderTypesPage(
			String externalReferenceCode, Pagination pagination)
		throws Exception {

		CommerceTermEntry commerceTermEntry =
			_commerceTermEntryService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceTermEntry == null) {
			throw new NoSuchTermEntryException(
				"Unable to find term with external reference code " +
					externalReferenceCode);
		}

		return Page.of(
			transform(
				_commerceTermEntryRelService.
					getCommerceOrderTypeCommerceTermEntryRels(
						commerceTermEntry.getCommerceTermEntryId(), null,
						pagination.getStartPosition(),
						pagination.getEndPosition()),
				commerceTermEntryRel -> _toTermOrderType(commerceTermEntryRel)),
			pagination,
			_commerceTermEntryRelService.
				getCommerceOrderTypeCommerceTermEntryRelsCount(
					commerceTermEntry.getCommerceTermEntryId(), null));
	}

	@NestedField(parentClass = Term.class, value = "termOrderTypes")
	@Override
	public Page<TermOrderType> getTermIdTermOrderTypesPage(
			Long id, String search, Pagination pagination)
		throws Exception {

		CommerceTermEntry commerceTermEntry =
			_commerceTermEntryService.fetchCommerceTermEntry(id);

		if (commerceTermEntry == null) {
			throw new NoSuchTermEntryException(
				"Unable to find term with ID " + id);
		}

		return Page.of(
			transform(
				_commerceTermEntryRelService.
					getCommerceOrderTypeCommerceTermEntryRels(
						id, search, pagination.getStartPosition(),
						pagination.getEndPosition()),
				commerceTermEntryRel -> _toTermOrderType(commerceTermEntryRel)),
			pagination,
			_commerceTermEntryRelService.
				getCommerceOrderTypeCommerceTermEntryRelsCount(id, search));
	}

	@Override
	public TermOrderType postTermByExternalReferenceCodeTermOrderType(
			String externalReferenceCode, TermOrderType termOrderType)
		throws Exception {

		CommerceTermEntry commerceTermEntry =
			_commerceTermEntryService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceTermEntry == null) {
			throw new NoSuchTermEntryException(
				"Unable to find term with external reference code " +
					externalReferenceCode);
		}

		CommerceOrderType commerceOrderType = _getCommerceOrderType(
			termOrderType);

		return _toTermOrderType(
			_commerceTermEntryRelService.addCommerceTermEntryRel(
				CommerceOrderType.class.getName(),
				commerceOrderType.getCommerceOrderTypeId(),
				commerceTermEntry.getCommerceTermEntryId()));
	}

	@Override
	public TermOrderType postTermIdTermOrderType(
			Long id, TermOrderType termOrderType)
		throws Exception {

		CommerceOrderType commerceOrderType = _getCommerceOrderType(
			termOrderType);

		return _toTermOrderType(
			_commerceTermEntryRelService.addCommerceTermEntryRel(
				CommerceOrderType.class.getName(),
				commerceOrderType.getCommerceOrderTypeId(), id));
	}

	private Map<String, Map<String, String>> _getActions(
			CommerceTermEntryRel commerceTermEntryRel)
		throws Exception {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE", commerceTermEntryRel.getCommerceTermEntryRelId(),
				"deleteTermOrderType",
				_commerceTermEntryRelModelResourcePermission)
		).build();
	}

	private CommerceOrderType _getCommerceOrderType(TermOrderType termOrderType)
		throws Exception {

		CommerceOrderType commerceOrderType = null;

		if (termOrderType.getOrderTypeId() > 0) {
			commerceOrderType = _commerceOrderTypeService.getCommerceOrderType(
				termOrderType.getOrderTypeId());
		}
		else {
			commerceOrderType =
				_commerceOrderTypeService.fetchByExternalReferenceCode(
					termOrderType.getOrderTypeExternalReferenceCode(),
					contextCompany.getCompanyId());
		}

		return commerceOrderType;
	}

	private TermOrderType _toTermOrderType(
			CommerceTermEntryRel commerceTermEntryRel)
		throws Exception {

		return _termOrderTypeDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(commerceTermEntryRel), _dtoConverterRegistry,
				commerceTermEntryRel.getCommerceTermEntryRelId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	@Reference
	private CommerceOrderTypeService _commerceOrderTypeService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.term.model.CommerceTermEntryRel)"
	)
	private ModelResourcePermission<CommerceTermEntryRel>
		_commerceTermEntryRelModelResourcePermission;

	@Reference
	private CommerceTermEntryRelService _commerceTermEntryRelService;

	@Reference
	private CommerceTermEntryService _commerceTermEntryService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.order.internal.dto.v1_0.converter.TermOrderTypeDTOConverter)"
	)
	private DTOConverter<CommerceTermEntryRel, TermOrderType>
		_termOrderTypeDTOConverter;

}