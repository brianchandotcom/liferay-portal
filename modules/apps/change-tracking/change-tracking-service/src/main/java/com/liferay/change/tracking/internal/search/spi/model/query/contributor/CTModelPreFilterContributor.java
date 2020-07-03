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

package com.liferay.change.tracking.internal.search.spi.model.query.contributor;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.ExistsFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.model.uid.UIDFactory;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 * @author André de Oliveira
 */
@Component(
	immediate = true, property = "indexer.class.name=ALL",
	service = ModelPreFilterContributor.class
)
public class CTModelPreFilterContributor implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		String className = modelSearchSettings.getClassName();

		if (!_serviceTrackerMap.containsKey(className)) {
			return;
		}

		String ctCollectionIdString = GetterUtil.getString(
			searchContext.getAttribute(
				"com.liferay.change.tracking.filter.ctCollectionId"));

		if (Objects.equals("ALL", ctCollectionIdString)) {
			return;
		}

		long ctCollectionId = GetterUtil.getLong(
			ctCollectionIdString, CTCollectionThreadLocal.getCTCollectionId());

		if (ctCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			if (!GetterUtil.getBoolean(
					searchContext.getAttribute("relatedClassName"))) {

				booleanFilter.add(
					_CT_COLLECTION_ID_EXISTS_FILTER,
					BooleanClauseOccur.MUST_NOT);
			}
		}
		else {
			boolean added = false;

			List<Long> excludeModelClassPKs = new ArrayList<>();

			for (CTEntry ctEntry :
					_ctEntryLocalService.getCTEntries(
						ctCollectionId,
						_classNameLocalService.getClassNameId(className))) {

				int changeType = ctEntry.getChangeType();

				if (changeType == CTConstants.CT_CHANGE_TYPE_ADDITION) {
					added = true;
				}
				else {
					if (changeType == CTConstants.CT_CHANGE_TYPE_MODIFICATION) {
						added = true;
					}

					excludeModelClassPKs.add(ctEntry.getModelClassPK());
				}
			}

			if (added) {
				booleanFilter.add(
					new TermFilter(
						_CT_COLLECTION_ID, String.valueOf(ctCollectionId)),
					BooleanClauseOccur.MUST);
			}
			else {
				booleanFilter.add(
					_CT_COLLECTION_ID_EXISTS_FILTER,
					BooleanClauseOccur.MUST_NOT);
			}

			if (!excludeModelClassPKs.isEmpty()) {
				TermsFilter termsFilter = new TermsFilter(Field.UID);

				for (Long classPK : excludeModelClassPKs) {
					termsFilter.addValue(
						_uidFactory.getUID(
							className, String.valueOf(classPK),
							CTConstants.CT_COLLECTION_ID_PRODUCTION));
				}

				booleanFilter.add(termsFilter, BooleanClauseOccur.MUST_NOT);
			}
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, (Class<CTService<?>>)(Class<?>)CTService.class, null,
			(serviceReference, emitter) -> {
				CTService<?> ctService = bundleContext.getService(
					serviceReference);

				Class<?> modelClass = ctService.getModelClass();

				emitter.emit(modelClass.getName());
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final String _CT_COLLECTION_ID = "ctCollectionId";

	private static final Filter _CT_COLLECTION_ID_EXISTS_FILTER =
		new ExistsFilter(_CT_COLLECTION_ID);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	private ServiceTrackerMap<String, CTService<?>> _serviceTrackerMap;

	@Reference
	private UIDFactory _uidFactory;

}