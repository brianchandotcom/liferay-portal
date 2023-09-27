/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.rest.internal.resource.v1_0;

import com.liferay.change.tracking.rest.dto.v1_0.CTCollection;
import com.liferay.change.tracking.rest.dto.v1_0.CTEntry;
import com.liferay.change.tracking.rest.internal.odata.entity.v1_0.CTEntryEntityModel;
import com.liferay.change.tracking.rest.resource.v1_0.CTEntryResource;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author David Truong
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/ct-entry.properties",
	scope = ServiceScope.PROTOTYPE, service = CTEntryResource.class
)
public class CTEntryResourceImpl extends BaseCTEntryResourceImpl {

	@Override
	public Page<CTEntry> getCtCollectionCTEntriesPage(
			Long ctCollectionId, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> booleanQuery.getPreBooleanFilter(), filter,
			com.liferay.change.tracking.model.CTEntry.class.getName(), search,
			pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute("ctCollectionId", ctCollectionId);
				searchContext.setCompanyId(contextCompany.getCompanyId());

				if (Validator.isNotNull(search)) {
					searchContext.setKeywords(search);
				}
			},
			sorts,
			document -> _toCTEntry(
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))));
	}

	@Override
	public CTEntry getCTEntry(Long ctEntryId) throws Exception {
		return _toCTEntry(ctEntryId);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	private DefaultDTOConverterContext _getDTOConverterContext(
			com.liferay.change.tracking.model.CTEntry ctEntry)
		throws Exception {

		return new DefaultDTOConverterContext(
			contextAcceptLanguage.isAcceptAllLanguages(),
			HashMapBuilder.put(
				"get",
				addAction(
					ActionKeys.VIEW, "getCTEntry", CTCollection.class.getName(),
					ctEntry.getCtCollectionId())
			).build(),
			null, contextHttpServletRequest, ctEntry.getCtCollectionId(),
			contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
			contextUser);
	}

	private CTEntry _toCTEntry(Long ctEntryId) throws Exception {
		com.liferay.change.tracking.model.CTEntry ctEntry =
			_ctEntryLocalService.getCTEntry(ctEntryId);

		return _ctEntryDTOConverter.toDTO(
			_getDTOConverterContext(ctEntry), ctEntry);
	}

	private static final EntityModel _entityModel = new CTEntryEntityModel();

	@Reference(
		target = "(component.name=com.liferay.change.tracking.rest.internal.dto.v1_0.converter.CTEntryDTOConverter)"
	)
	private DTOConverter<com.liferay.change.tracking.model.CTEntry, CTEntry>
		_ctEntryDTOConverter;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

}