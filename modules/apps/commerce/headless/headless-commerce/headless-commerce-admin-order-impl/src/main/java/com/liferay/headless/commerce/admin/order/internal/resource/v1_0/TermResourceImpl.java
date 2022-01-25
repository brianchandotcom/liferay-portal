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
import com.liferay.headless.commerce.admin.order.internal.dto.v1_0.converter.TermDTOConverter;
import com.liferay.headless.commerce.admin.order.internal.odata.entity.v1_0.TermEntityModel;
import com.liferay.headless.commerce.admin.order.internal.util.v1_0.TermOrderTypeUtil;
import com.liferay.headless.commerce.admin.order.resource.v1_0.TermResource;
import com.liferay.headless.commerce.core.util.DateConfig;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, properties = "OSGI-INF/liferay/rest/v1_0/term.properties",
	scope = ServiceScope.PROTOTYPE, service = TermResource.class
)
public class TermResourceImpl extends BaseTermResourceImpl {

	@Override
	public void deleteTerm(Long id) throws Exception {
		_commerceTermEntryService.deleteCommerceTermEntry(id);
	}

	@Override
	public void deleteTermByExternalReferenceCode(String externalReferenceCode)
		throws Exception {

		CommerceTermEntry commerceTermEntry =
			_commerceTermEntryService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceTermEntry == null) {
			throw new NoSuchTermEntryException(
				"Unable to find term with external reference code " +
					externalReferenceCode);
		}

		_commerceTermEntryService.deleteCommerceTermEntry(
			commerceTermEntry.getCommerceTermEntryId());
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public Term getTerm(Long id) throws Exception {
		return _toTerm(GetterUtil.getLong(id));
	}

	@Override
	public Term getTermByExternalReferenceCode(String externalReferenceCode)
		throws Exception {

		CommerceTermEntry commerceTermEntry =
			_commerceTermEntryService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceTermEntry == null) {
			throw new NoSuchTermEntryException(
				"Unable to find term with external reference code " +
					externalReferenceCode);
		}

		return _toTerm(commerceTermEntry.getCommerceTermEntryId());
	}

	@Override
	public Page<Term> getTermsPage(
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			null, booleanQuery -> booleanQuery.getPreBooleanFilter(), filter,
			CommerceTermEntry.class.getName(), search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			new UnsafeConsumer() {

				public void accept(Object object) throws Exception {
					SearchContext searchContext = (SearchContext)object;

					searchContext.setAttribute(
						"status", WorkflowConstants.STATUS_ANY);
					searchContext.setCompanyId(contextCompany.getCompanyId());
				}

			},
			sorts,
			document -> _toTerm(
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))));
	}

	@Override
	public Term patchTerm(Long id, Term term) throws Exception {
		return _toTerm(
			_updateTerm(
				_commerceTermEntryService.getCommerceTermEntry(id), term));
	}

	@Override
	public Term patchTermByExternalReferenceCode(
			String externalReferenceCode, Term term)
		throws Exception {

		CommerceTermEntry commerceTermEntry =
			_commerceTermEntryService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceTermEntry == null) {
			throw new NoSuchTermEntryException(
				"Unable to find term with external reference code " +
					externalReferenceCode);
		}

		return _toTerm(_updateTerm(commerceTermEntry, term));
	}

	@Override
	public Term postTerm(Term term) throws Exception {
		CommerceTermEntry commerceTermEntry = _addCommerceTermEntry(term);

		return _toTerm(commerceTermEntry.getCommerceTermEntryId());
	}

	private CommerceTermEntry _addCommerceTermEntry(Term term)
		throws Exception {

		ServiceContext serviceContext =
			_serviceContextHelper.getServiceContext();

		DateConfig displayDateConfig = DateConfig.toDisplayDateConfig(
			term.getDisplayDate(), serviceContext.getTimeZone());
		DateConfig expirationDateConfig = DateConfig.toExpirationDateConfig(
			term.getExpirationDate(), serviceContext.getTimeZone());

		CommerceTermEntry commerceTermEntry =
			_commerceTermEntryService.addCommerceTermEntry(
				term.getExternalReferenceCode(),
				GetterUtil.getBoolean(term.getActive()),
				LanguageUtils.getLocalizedMap(term.getDescription()),
				displayDateConfig.getMonth(), displayDateConfig.getDay(),
				displayDateConfig.getYear(), displayDateConfig.getHour(),
				displayDateConfig.getMinute(), expirationDateConfig.getMonth(),
				expirationDateConfig.getDay(), expirationDateConfig.getYear(),
				expirationDateConfig.getHour(),
				expirationDateConfig.getMinute(),
				GetterUtil.getBoolean(term.getNeverExpire(), true),
				LanguageUtils.getLocalizedMap(term.getLabel()), term.getName(),
				GetterUtil.getDouble(term.getPriority()), term.getType(),
				term.getTypeSettings(), serviceContext);

		return _updateNestedResources(commerceTermEntry, term);
	}

	private Map<String, Map<String, String>> _getActions(
			CommerceTermEntry commerceTermEntry)
		throws Exception {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"DELETE", commerceTermEntry.getCommerceTermEntryId(),
				"deleteTerm", _commerceTermEntryModelResourcePermission)
		).put(
			"get",
			addAction(
				"VIEW", commerceTermEntry.getCommerceTermEntryId(), "getTerm",
				_commerceTermEntryModelResourcePermission)
		).put(
			"permissions",
			addAction(
				"PERMISSIONS", commerceTermEntry.getCommerceTermEntryId(),
				"patchTerm", _commerceTermEntryModelResourcePermission)
		).put(
			"update",
			addAction(
				"UPDATE", commerceTermEntry.getCommerceTermEntryId(),
				"patchTerm", _commerceTermEntryModelResourcePermission)
		).build();
	}

	private Term _toTerm(CommerceTermEntry commerceTermEntry) throws Exception {
		return _toTerm(commerceTermEntry.getCommerceTermEntryId());
	}

	private Term _toTerm(Long commerceTermEntryId) throws Exception {
		CommerceTermEntry commerceTermEntry =
			_commerceTermEntryService.getCommerceTermEntry(commerceTermEntryId);

		return _termDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(commerceTermEntry), _dtoConverterRegistry,
				commerceTermEntryId, contextAcceptLanguage.getPreferredLocale(),
				contextUriInfo, contextUser));
	}

	private CommerceTermEntry _updateNestedResources(
			CommerceTermEntry commerceTermEntry, Term term)
		throws Exception {

		// Term order types

		TermOrderType[] termOrderTypes = term.getTermOrderType();

		if (termOrderTypes != null) {
			for (TermOrderType termOrderType : termOrderTypes) {
				CommerceTermEntryRel commerceTermEntryRel =
					_commerceTermEntryRelService.fetchCommerceTermEntryRel(
						CommerceOrderType.class.getName(),
						termOrderType.getTermId(),
						commerceTermEntry.getCommerceTermEntryId());

				if (commerceTermEntryRel != null) {
					continue;
				}

				TermOrderTypeUtil.addCommerceTermEntryCommerceOrderTypeRel(
					_commerceOrderTypeService, commerceTermEntry,
					_commerceTermEntryRelService, termOrderType);
			}
		}

		return commerceTermEntry;
	}

	private CommerceTermEntry _updateTerm(
			CommerceTermEntry commerceTermEntry, Term term)
		throws Exception {

		Map<String, String> descriptionMap = term.getDescription();

		if ((commerceTermEntry != null) && (descriptionMap == null)) {
			descriptionMap = commerceTermEntry.getLanguageIdToDescriptionMap();
		}

		ServiceContext serviceContext =
			_serviceContextHelper.getServiceContext();

		DateConfig displayDateConfig = DateConfig.toDisplayDateConfig(
			term.getDisplayDate(), serviceContext.getTimeZone());
		DateConfig expirationDateConfig = DateConfig.toExpirationDateConfig(
			term.getExpirationDate(), serviceContext.getTimeZone());

		Map<String, String> labelMap = term.getLabel();

		if ((commerceTermEntry != null) && (descriptionMap == null)) {
			labelMap = commerceTermEntry.getLanguageIdToLabelMap();
		}

		commerceTermEntry = _commerceTermEntryService.updateCommerceTermEntry(
			commerceTermEntry.getCommerceTermEntryId(),
			GetterUtil.getBoolean(
				term.getActive(), commerceTermEntry.isActive()),
			LanguageUtils.getLocalizedMap(descriptionMap),
			displayDateConfig.getMonth(), displayDateConfig.getDay(),
			displayDateConfig.getYear(), displayDateConfig.getHour(),
			displayDateConfig.getMinute(), expirationDateConfig.getMonth(),
			expirationDateConfig.getDay(), expirationDateConfig.getYear(),
			expirationDateConfig.getHour(), expirationDateConfig.getMinute(),
			GetterUtil.getBoolean(term.getNeverExpire(), true),
			LanguageUtils.getLocalizedMap(labelMap),
			GetterUtil.getString(term.getName(), commerceTermEntry.getName()),
			GetterUtil.getDouble(
				term.getPriority(), commerceTermEntry.getPriority()),
			GetterUtil.get(
				term.getTypeSettings(), commerceTermEntry.getTypeSettings()),
			serviceContext);

		return _updateNestedResources(commerceTermEntry, term);
	}

	private static final EntityModel _entityModel = new TermEntityModel();

	@Reference
	private CommerceOrderTypeService _commerceOrderTypeService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.term.model.CommerceTermEntry)"
	)
	private ModelResourcePermission<CommerceTermEntry>
		_commerceTermEntryModelResourcePermission;

	@Reference
	private CommerceTermEntryRelService _commerceTermEntryRelService;

	@Reference
	private CommerceTermEntryService _commerceTermEntryService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

	@Reference
	private TermDTOConverter _termDTOConverter;

}