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

package com.liferay.commerce.notification.internal.model.listener;

import com.liferay.commerce.notification.service.CommerceNotificationTemplateUserSegmentRelLocalService;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = ModelListener.class)
public class CommerceUserSegmentEntryModelListener
	extends BaseModelListener<CommerceUserSegmentEntry> {

	@Override
	public void onBeforeRemove(
		CommerceUserSegmentEntry commerceUserSegmentEntry) {

		_commerceNotificationTemplateUserSegmentRelLocalService.
			deleteCNTemplateUserSegmentRelsByCommerceNotificationTemplateId(
				commerceUserSegmentEntry.getCommerceUserSegmentEntryId());
	}

	@Reference
	private CommerceNotificationTemplateUserSegmentRelLocalService
		_commerceNotificationTemplateUserSegmentRelLocalService;

}