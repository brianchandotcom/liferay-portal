/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.store.internal;

import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.store.model.CTSContent;
import com.liferay.change.tracking.store.service.CTSContentLocalService;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.portal.change.tracking.store.CTStoreFactory;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(service = CTStoreFactory.class)
public class CTStoreFactoryImpl implements CTStoreFactory {

	@Override
	public Store createCTStore(Store store, String storeType) {
		return new CTStore(
			_ctEntryLocalService,
			_classNameLocalService.getClassNameId(CTSContent.class),
			_ctsContentLocalService, store, storeType);
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private CTSContentLocalService _ctsContentLocalService;

}