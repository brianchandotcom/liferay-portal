/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.akismet.service.impl;

import com.liferay.akismet.model.AkismetEntry;
import com.liferay.akismet.service.base.AkismetEntryLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Date;

/**
 * @author Jamie Sammons
 */
public class AkismetEntryLocalServiceImpl
	extends AkismetEntryLocalServiceBaseImpl {

	@Override
	public void deleteAkismetEntry(Date modifiedDate) {
		akismetEntryPersistence.removeByLtModifiedDate(modifiedDate);
	}

	@Override
	public void deleteAkismetEntry(String className, long classPK)
		throws PortalException {

		akismetEntryPersistence.removeByC_C(
			classNameLocalService.getClassNameId(className), classPK);
	}

	@Override
	public AkismetEntry fetchAkismetEntry(String className, long classPK) {
		return akismetEntryPersistence.fetchByC_C(
			classNameLocalService.getClassNameId(className), classPK);
	}

	@Override
	public AkismetEntry updateAkismetEntry(
		String className, long classPK, String type, String permalink,
		String referrer, String userAgent, String userIP, String userURL) {

		long classNameId = classNameLocalService.getClassNameId(className);

		AkismetEntry akismetEntry = akismetEntryPersistence.fetchByC_C(
			classNameId, classPK);

		if (akismetEntry == null) {
			long akismetEntryId = counterLocalService.increment();

			akismetEntry = akismetEntryPersistence.create(akismetEntryId);
		}

		akismetEntry.setModifiedDate(new Date());
		akismetEntry.setClassNameId(classNameId);
		akismetEntry.setClassPK(classPK);
		akismetEntry.setType(type);
		akismetEntry.setPermalink(permalink);
		akismetEntry.setReferrer(referrer);
		akismetEntry.setUserAgent(userAgent);
		akismetEntry.setUserIP(userIP);
		akismetEntry.setUserURL(userURL);

		return akismetEntryPersistence.update(akismetEntry);
	}

}