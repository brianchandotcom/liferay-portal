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

package com.liferay.portal.tools.service.builder.test.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.tools.service.builder.test.model.LVEntryLocalizationVersion;
import com.liferay.portal.tools.service.builder.test.service.base.LVEntryLocalServiceBaseImpl;

import java.util.List;

/**
 * The implementation of the lv entry local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portal.tools.service.builder.test.service.LVEntryLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LVEntryLocalServiceBaseImpl
 * @see com.liferay.portal.tools.service.builder.test.service.LVEntryLocalServiceUtil
 */
public class LVEntryLocalServiceImpl extends LVEntryLocalServiceBaseImpl {

	@Override
	public LVEntryLocalizationVersion fetchLVEntryLocalizationVersion(
		long lvEntryId, String languageId, int version) {

		return lvEntryLocalizationVersionPersistence.
			fetchByLvEntryId_LanguageId_Version(lvEntryId, languageId, version);
	}

	@Override
	public List<LVEntryLocalizationVersion> getLVEntryLocalizationVersions(
		long lvEntryId) {

		return lvEntryLocalizationVersionPersistence.findByLvEntryId(lvEntryId);
	}

	@Override
	public List<LVEntryLocalizationVersion> getLVEntryLocalizationVersions(
			long lvEntryId, String languageId)
		throws PortalException {

		return lvEntryLocalizationVersionPersistence.findByLvEntryId_LanguageId(
			lvEntryId, languageId);
	}

}