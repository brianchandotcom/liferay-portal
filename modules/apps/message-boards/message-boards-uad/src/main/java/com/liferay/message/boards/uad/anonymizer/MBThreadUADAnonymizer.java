/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.uad.anonymizer;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.message.boards.exception.RequiredThreadException;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = UADAnonymizer.class)
public class MBThreadUADAnonymizer extends BaseMBThreadUADAnonymizer {

	@Override
	public void delete(MBThread mbThread) throws PortalException {
		MBMessage message = _mbMessageLocalService.getMessage(
			mbThread.getRootMessageId());

		if (message.isDiscussion()) {
			AssetEntry assetEntry = assetEntryLocalService.fetchEntry(
				message.getClassName(), message.getClassPK());

			if (assetEntry.getUserId() != mbThread.getUserId()) {
				throw new RequiredThreadException();
			}
		}

		super.delete(mbThread);
	}

	@Override
	public Map<Class<?>, String> getExceptionMessageMap(Locale locale) {
		return HashMapBuilder.<Class<?>, String>put(
			RequiredThreadException.class,
			_language.get(
				locale, "thread-cannot-be-deleted.-anonymize-it-instead")
		).build();
	}

	@Reference
	private Language _language;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

}