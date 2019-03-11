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

package com.liferay.data.engine.rest.internal.resource.v1_0;

import com.liferay.data.engine.rest.dto.v1_0.DataRecordCollection;
import com.liferay.data.engine.rest.dto.v1_0.LocalizedValue;
import com.liferay.data.engine.rest.resource.v1_0.DataRecordCollectionResource;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/Data-record-collection.properties",
	scope = ServiceScope.PROTOTYPE, service = DataRecordCollectionResource.class
)
public class DataRecordCollectionResourceImpl
	extends BaseDataRecordCollectionResourceImpl {

	@Override
	public DataRecordCollection getDataRecordCollection(
			Long dataRecordCollectionId)
		throws Exception {

		return _toDataRecordCollection(
			_ddlRecordSetLocalService.getRecordSet(dataRecordCollectionId));
	}

	@Override
	public Page<DataRecordCollection> getDataRecordCollectionSearchPage(
			Long groupId, String keywords, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_ddlRecordSetLocalService.search(
					contextCompany.getCompanyId(), groupId, keywords,
					DDLRecordSetConstants.SCOPE_DATA_ENGINE,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toDataRecordCollection),
			pagination,
			_ddlRecordSetLocalService.searchCount(
				contextCompany.getCompanyId(), groupId, keywords,
				DDLRecordSetConstants.SCOPE_DATA_ENGINE));
	}

	@Override
	public Page<DataRecordCollection> getDataRecordCollectionsPage(
			Long groupId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_ddlRecordSetLocalService.getRecordSets(
					groupId, pagination.getStartPosition(),
					pagination.getEndPosition()),
				this::_toDataRecordCollection),
			pagination, _ddlRecordSetLocalService.getRecordSetsCount(groupId));
	}

	@Override
	public DataRecordCollection postDataRecordCollection(
			Long groupId, DataRecordCollection dataRecordCollection)
		throws Exception {

		return _toDataRecordCollection(
			_ddlRecordSetLocalService.addRecordSet(
				PrincipalThreadLocal.getUserId(), groupId,
				dataRecordCollection.getDataDefinitionId(), null,
				_toLocalizationMap(dataRecordCollection.getName()),
				_toLocalizationMap(dataRecordCollection.getDescription()), 0,
				DDLRecordSetConstants.SCOPE_DATA_ENGINE, new ServiceContext()));
	}

	@Override
	public DataRecordCollection putDataRecordCollection(
			Long dataRecordCollectionId,
			DataRecordCollection dataRecordCollection)
		throws Exception {

		return _toDataRecordCollection(
			_ddlRecordSetLocalService.updateRecordSet(
				dataRecordCollection.getId(),
				dataRecordCollection.getDataDefinitionId(),
				_toLocalizationMap(dataRecordCollection.getName()),
				_toLocalizationMap(dataRecordCollection.getDescription()), 0,
				new ServiceContext()));
	}

	private DataRecordCollection _toDataRecordCollection(
		DDLRecordSet ddlRecordSet) {

		DataRecordCollection dataRecordCollection = new DataRecordCollection();

		dataRecordCollection.setDataDefinitionId(
			ddlRecordSet.getDDMStructureId());
		dataRecordCollection.setId(ddlRecordSet.getRecordSetId());
		dataRecordCollection.setDescription(
			_toLocalizedValues(ddlRecordSet.getDescriptionMap()));
		dataRecordCollection.setName(
			_toLocalizedValues(ddlRecordSet.getNameMap()));

		return dataRecordCollection;
	}

	private Map<Locale, String> _toLocalizationMap(
		LocalizedValue[] localizedValues) {

		Map<Locale, String> localizationMap = new HashMap<>();

		for (LocalizedValue localizedValue : localizedValues) {
			localizationMap.put(
				LocaleUtil.fromLanguageId(localizedValue.getKey()),
				localizedValue.getValue());
		}

		return localizationMap;
	}

	private LocalizedValue[] _toLocalizedValues(Map<Locale, String> map) {
		List<LocalizedValue> localizedValues = new ArrayList<>();

		for (Map.Entry<Locale, String> entry : map.entrySet()) {
			LocalizedValue localizedValue = new LocalizedValue();

			localizedValue.setKey(String.valueOf(entry.getKey()));
			localizedValue.setValue(entry.getValue());

			localizedValues.add(localizedValue);
		}

		return localizedValues.toArray(new LocalizedValue[map.size()]);
	}

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

}