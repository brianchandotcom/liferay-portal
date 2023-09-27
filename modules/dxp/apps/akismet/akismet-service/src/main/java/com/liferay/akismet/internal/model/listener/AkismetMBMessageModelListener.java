/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.akismet.internal.model.listener;

import com.liferay.akismet.service.AkismetEntryLocalService;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jamie Sammons
 */
@Component(service = ModelListener.class)
public class AkismetMBMessageModelListener
	extends BaseModelListener<MBMessage> {

	@Override
	public void onAfterRemove(MBMessage message) {
		try {
			_akismetEntryLocalService.deleteAkismetEntry(
				MBMessage.class.getName(), message.getMessageId());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AkismetMBMessageModelListener.class);

	@Reference
	private AkismetEntryLocalService _akismetEntryLocalService;

}