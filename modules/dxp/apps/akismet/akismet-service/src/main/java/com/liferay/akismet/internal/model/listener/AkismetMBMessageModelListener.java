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
@Component(immediate = true, service = ModelListener.class)
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