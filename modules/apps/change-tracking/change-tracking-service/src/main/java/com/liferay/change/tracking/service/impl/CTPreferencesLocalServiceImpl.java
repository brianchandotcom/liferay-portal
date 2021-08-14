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

package com.liferay.change.tracking.service.impl;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.base.CTPreferencesLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.change.tracking.CTAware;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.change.tracking.model.CTPreferences",
	service = AopService.class
)
@CTAware
public class CTPreferencesLocalServiceImpl
	extends CTPreferencesLocalServiceBaseImpl {

	@Override
	public CTPreferences addCTPreference(long companyId, long userId) {
		long ctPreferencesId = counterLocalService.increment(
			CTPreferences.class.getName());

		CTPreferences ctPreferences = ctPreferencesPersistence.create(
			ctPreferencesId);

		ctPreferences.setCompanyId(companyId);
		ctPreferences.setUserId(userId);
		ctPreferences.setCtCollectionId(
			CTConstants.CT_COLLECTION_ID_PRODUCTION);
		ctPreferences.setPreviousCtCollectionId(
			CTConstants.CT_COLLECTION_ID_PRODUCTION);

		return ctPreferencesPersistence.update(ctPreferences);
	}

	@Override
	public CTPreferences fetchCTPreferences(long companyId, long userId) {
		return ctPreferencesPersistence.fetchByC_U(companyId, userId);
	}

	@Override
	public CTPreferences getCTPreferences(long companyId, long userId) {
		CTPreferences ctPreferences = ctPreferencesPersistence.fetchByC_U(
			companyId, userId);

		if (ctPreferences == null) {
			ctPreferences = ctPreferencesLocalService.addCTPreference(
				companyId, userId);
		}

		return ctPreferences;
	}

	@Override
	public void resetCTPreferences(long ctCollectionId) {
		for (CTPreferences ctPreferences :
				ctPreferencesPersistence.findByCtCollectionId(ctCollectionId)) {

			ctPreferences.setCtCollectionId(
				CTConstants.CT_COLLECTION_ID_PRODUCTION);

			ctPreferencesPersistence.update(ctPreferences);
		}

		for (CTPreferences ctPreferences :
				ctPreferencesPersistence.findByPreviousCtCollectionId(
					ctCollectionId)) {

			ctPreferences.setPreviousCtCollectionId(
				CTConstants.CT_COLLECTION_ID_PRODUCTION);

			ctPreferencesPersistence.update(ctPreferences);
		}
	}

}