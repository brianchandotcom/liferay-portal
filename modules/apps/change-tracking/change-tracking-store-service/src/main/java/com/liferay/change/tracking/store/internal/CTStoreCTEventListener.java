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

package com.liferay.change.tracking.store.internal;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.exception.CTEventException;
import com.liferay.change.tracking.listener.CTEventListener;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.store.model.CTSContent;
import com.liferay.change.tracking.store.service.CTSContentLocalService;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.portal.change.tracking.sql.CTSQLModeThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(service = CTEventListener.class)
public class CTStoreCTEventListener implements CTEventListener {

	@Override
	public void onAfterPublish(long ctCollectionId) throws CTEventException {
		List<CTEntry> ctEntries = _ctEntryLocalService.getCTEntries(
			ctCollectionId, _ctsContentClassNameId);

		if (ctEntries.isEmpty()) {
			return;
		}

		List<CTEntry> deletedCTEnties = new ArrayList<>();

		List<CTEntry> addOrModifiedCTEntries = new ArrayList<>();

		for (CTEntry ctEntry : ctEntries) {
			if (ctEntry.getChangeType() ==
					CTConstants.CT_CHANGE_TYPE_DELETION) {

				deletedCTEnties.add(ctEntry);
			}
			else {
				addOrModifiedCTEntries.add(ctEntry);
			}
		}

		// Deleted CTEntries need to read CTSContent from CTCollection

		if (!deletedCTEnties.isEmpty()) {
			try (SafeClosable safeClosable1 = CTSQLModeThreadLocal.setCTSQLMode(
					CTSQLModeThreadLocal.CTSQLMode.CT_ONLY);
				SafeClosable safeClosable2 =
					CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

				for (CTEntry ctEntry : deletedCTEnties) {
					CTSContent ctsContent =
						_ctsContentLocalService.getCTSContent(
							ctEntry.getModelClassPK());

					Store store = _storeServiceTrackerMap.getService(
						ctsContent.getStoreType());

					store.deleteFile(
						ctsContent.getCompanyId(), ctsContent.getRepositoryId(),
						ctsContent.getPath(), ctsContent.getVersion());
				}
			}
			catch (PortalException pe) {
				throw new CTEventException(pe);
			}
		}

		// Add or modifed CTEntries need to read CTSContent from production

		if (!addOrModifiedCTEntries.isEmpty()) {
			try (SafeClosable safeClosable =
					CTCollectionThreadLocal.setCTCollectionId(
						CTConstants.CT_COLLECTION_ID_PRODUCTION)) {

				for (CTEntry ctEntry : addOrModifiedCTEntries) {
					CTSContent ctsContent =
						_ctsContentLocalService.getCTSContent(
							ctEntry.getModelClassPK());

					Store store = _storeServiceTrackerMap.getService(
						ctsContent.getStoreType());

					store.addFile(
						ctsContent.getCompanyId(), ctsContent.getRepositoryId(),
						ctsContent.getPath(), ctsContent.getVersion(),
						_ctsContentLocalService.openCTSContentInputStream(
							ctsContent.getCtsContentId()));
				}
			}
			catch (PortalException pe) {
				throw new CTEventException(pe);
			}
		}
	}

	@Override
	public void onBeforeRemove(long ctCollectionId) throws CTEventException {
		List<Long> ctsContentIds =
			_ctEntryLocalService.getExclusiveModelClassPKs(
				ctCollectionId, _ctsContentClassNameId);

		if (ctsContentIds.isEmpty()) {
			return;
		}

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					CTConstants.CT_COLLECTION_ID_PRODUCTION)) {

			for (long ctsContentId : ctsContentIds) {
				CTSContent ctsContent = _ctsContentLocalService.fetchCTSContent(
					ctsContentId);

				if (ctsContent != null) {
					_ctsContentLocalService.deleteCTSContent(ctsContent);
				}
			}
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_ctsContentClassNameId = _classNameLocalService.getClassNameId(
			CTSContent.class);

		_storeServiceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, Store.class, "store.type");
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	private long _ctsContentClassNameId;

	@Reference
	private CTSContentLocalService _ctsContentLocalService;

	private ServiceTrackerMap<String, Store> _storeServiceTrackerMap;

}