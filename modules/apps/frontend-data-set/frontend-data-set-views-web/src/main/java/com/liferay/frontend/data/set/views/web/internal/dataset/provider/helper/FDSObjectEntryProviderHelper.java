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

package com.liferay.frontend.data.set.views.web.internal.dataset.provider.helper;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManagerProvider;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Sanz
 */
@Component(service = FDSObjectEntryProviderHelper.class)
public class FDSObjectEntryProviderHelper {

	public ObjectDefinition getFDSDateFilterObjectDefinition() {
		return getFDSDateFilterObjectDefinition(
			CompanyThreadLocal.getCompanyId());
	}

	public ObjectDefinition getFDSDateFilterObjectDefinition(long companyId) {
		return _objectDefinitionLocalService.fetchObjectDefinition(
			companyId, "C_FDSDateFilter");
	}

	public ObjectEntry getFDSEntry(ObjectEntry fdsView) {
		Map<String, Object> fdsViewProperties = fdsView.getProperties();

		Long fdsEntryId = (Long)fdsViewProperties.get(
			"r_fdsEntryFDSViewRelationship_c_fdsEntryId");

		ObjectDefinition fdsEntryObjectDefinition =
			getFDSEntryObjectDefinition();

		DTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(
				false, null, null, null, null, LocaleUtil.getSiteDefault(),
				null, null);

		try {
			DefaultObjectEntryManager defaultObjectEntryManager =
				DefaultObjectEntryManagerProvider.provide(
					_objectEntryManagerRegistry.getObjectEntryManager(
						fdsEntryObjectDefinition.getStorageType()));

			return defaultObjectEntryManager.getObjectEntry(
				dtoConverterContext, fdsEntryObjectDefinition, fdsEntryId);
		}
		catch (Exception exception) {
			_log.error(exception);

			return null;
		}
	}

	public ObjectDefinition getFDSEntryObjectDefinition() {
		return getFDSEntryObjectDefinition(CompanyThreadLocal.getCompanyId());
	}

	public ObjectDefinition getFDSEntryObjectDefinition(long companyId) {
		return _objectDefinitionLocalService.fetchObjectDefinition(
			companyId, "C_FDSEntry");
	}

	public ObjectDefinition getFDSFieldObjectDefinition() {
		return getFDSFieldObjectDefinition(CompanyThreadLocal.getCompanyId());
	}

	public ObjectDefinition getFDSFieldObjectDefinition(long companyId) {
		return _objectDefinitionLocalService.fetchObjectDefinition(
			companyId, "C_FDSField");
	}

	public Collection<ObjectEntry> getFDSFields(ObjectEntry fdsView) {
		return getFDSRelatedObjects(fdsView, "fdsViewFDSFieldRelationship");
	}

	public Collection<ObjectEntry> getFDSFilters(ObjectEntry fdsView) {
		Collection<ObjectEntry> fdsFilters = new ArrayList<>();

		fdsFilters.addAll(
			getFDSRelatedObjects(fdsView, "fdsViewFDSDateFilterRelationship"));

		fdsFilters.addAll(
			getFDSRelatedObjects(
				fdsView, "fdsViewFDSDynamicFilterRelationship"));

		return fdsFilters;
	}

	public Collection<ObjectEntry> getFDSRelatedObjects(
		ObjectEntry fdsView, String relationshipName) {

		ObjectDefinition fdsViewObjectDefinition = getFDSViewObjectDefinition();

		DTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(
				false, null, null, null, null, LocaleUtil.getSiteDefault(),
				null, null);

		try {
			DefaultObjectEntryManager defaultObjectEntryManager =
				DefaultObjectEntryManagerProvider.provide(
					_objectEntryManagerRegistry.getObjectEntryManager(
						fdsViewObjectDefinition.getStorageType()));

			Page<ObjectEntry> relatedObjectEntriesPage =
				defaultObjectEntryManager.getObjectEntryRelatedObjectEntries(
					dtoConverterContext, fdsViewObjectDefinition,
					fdsView.getId(), relationshipName, Pagination.of(1, 500));

			return relatedObjectEntriesPage.getItems();
		}
		catch (Exception exception) {
			_log.error(exception);

			return null;
		}
	}

	public ObjectDefinition getFDSSortObjectDefinition() {
		return getFDSSortObjectDefinition(CompanyThreadLocal.getCompanyId());
	}

	public ObjectDefinition getFDSSortObjectDefinition(long companyId) {
		return _objectDefinitionLocalService.fetchObjectDefinition(
			companyId, "C_FDSSort");
	}

	public Collection<ObjectEntry> getFDSSorts(ObjectEntry fdsView) {
		return getFDSRelatedObjects(fdsView, "fdsViewFDSSortRelationship");
	}

	public ObjectEntry getFDSView(
		String externalReferenceCode,
		FragmentRendererContext fragmentRendererContext) {

		try {
			ObjectDefinition fdsViewObjectDefinition =
				getFDSViewObjectDefinition(fragmentRendererContext);

			DTOConverterContext dtoConverterContext =
				new DefaultDTOConverterContext(
					false, null, null, null, null, LocaleUtil.getSiteDefault(),
					null, null);

			DefaultObjectEntryManager defaultObjectEntryManager =
				DefaultObjectEntryManagerProvider.provide(
					_objectEntryManagerRegistry.getObjectEntryManager(
						fdsViewObjectDefinition.getStorageType()));

			return defaultObjectEntryManager.getObjectEntry(
				fdsViewObjectDefinition.getCompanyId(), dtoConverterContext,
				externalReferenceCode, fdsViewObjectDefinition, null);
		}
		catch (Exception exception) {
			_log.error(exception);

			return null;
		}
	}

	public ObjectDefinition getFDSViewObjectDefinition() {
		return getFDSViewObjectDefinition(CompanyThreadLocal.getCompanyId());
	}

	public ObjectDefinition getFDSViewObjectDefinition(
		FragmentRendererContext fragmentRendererContext) {

		long companyId = CompanyThreadLocal.getCompanyId();

		FragmentEntryLink fragmentEntryLink =
			fragmentRendererContext.getFragmentEntryLink();

		if (fragmentEntryLink != null) {
			companyId = fragmentEntryLink.getCompanyId();
		}

		return getFDSViewObjectDefinition(companyId);
	}

	public ObjectDefinition getFDSViewObjectDefinition(long companyId) {
		return _objectDefinitionLocalService.fetchObjectDefinition(
			companyId, "C_FDSView");
	}

	public JSONArray getFieldNameJSONArray(ObjectEntry fdsField) {
		Map<String, Object> fdsFieldProperties = fdsField.getProperties();

		String fieldName = (String)fdsFieldProperties.get("name");

		JSONArray jsonArray = null;

		try {
			jsonArray = _jsonFactory.createJSONArray(
				StringUtil.split(fieldName, CharPool.PERIOD));
		}
		catch (Exception exception) {
			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Unable to build JSONArray from '", fieldName, "'"),
					exception);
			}
		}

		return jsonArray;
	}

	public boolean isFDSDateFilter(ObjectEntry fdsFilter) {
		ObjectDefinition fdsDateFilterObjectDefinition =
			getFDSDateFilterObjectDefinition();

		DTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(
				false, null, null, null, null, LocaleUtil.getSiteDefault(),
				null, null);

		try {
			DefaultObjectEntryManager defaultObjectEntryManager =
				DefaultObjectEntryManagerProvider.provide(
					_objectEntryManagerRegistry.getObjectEntryManager(
						fdsDateFilterObjectDefinition.getStorageType()));

			ObjectEntry fdsDateFilterObjectEntry =
				defaultObjectEntryManager.getObjectEntry(
					dtoConverterContext, fdsDateFilterObjectDefinition,
					fdsFilter.getId());

			return fdsFilter.equals(fdsDateFilterObjectEntry);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			return false;
		}
	}

	public boolean isRenderedByClientExtension(ObjectEntry fdsField) {
		Map<String, Object> fdsFieldProperties = fdsField.getProperties();

		String rendererType = (String)fdsFieldProperties.get("rendererType");

		return Objects.equals(rendererType, "clientExtension");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FDSObjectEntryProviderHelper.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

}