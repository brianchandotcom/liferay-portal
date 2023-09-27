/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.storage.service.impl;

import com.liferay.analytics.message.storage.model.AnalyticsMessage;
import com.liferay.analytics.message.storage.service.base.AnalyticsMessageLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(
	property = "model.class.name=com.liferay.analytics.message.storage.model.AnalyticsMessage",
	service = AopService.class
)
public class AnalyticsMessageLocalServiceImpl
	extends AnalyticsMessageLocalServiceBaseImpl {

	@Override
	public AnalyticsMessage addAnalyticsMessage(
			long companyId, long userId, byte[] body)
		throws PortalException {

		AnalyticsMessage analyticsMessage = analyticsMessagePersistence.create(
			counterLocalService.increment());

		analyticsMessage.setCompanyId(companyId);
		analyticsMessage.setUserId(userId);

		User user = _userLocalService.getUser(userId);

		analyticsMessage.setUserName(user.getFullName());

		analyticsMessage.setCreateDate(new Date());
		analyticsMessage.setBody(
			new OutputBlob(new UnsyncByteArrayInputStream(body), body.length));

		return analyticsMessagePersistence.update(analyticsMessage);
	}

	@Override
	public void deleteAnalyticsMessages(
		List<AnalyticsMessage> analyticsMessages) {

		for (AnalyticsMessage analyticsMessage : analyticsMessages) {
			analyticsMessagePersistence.remove(analyticsMessage);
		}
	}

	@Override
	public void deleteAnalyticsMessages(long companyId) {
		analyticsMessagePersistence.removeByCompanyId(companyId);
	}

	@Override
	public List<AnalyticsMessage> getAnalyticsMessages(
		long companyId, int start, int end) {

		return analyticsMessagePersistence.findByCompanyId(
			companyId, start, end);
	}

	@Override
	public List<Long> getCompanyIds() {
		DynamicQuery dynamicQuery = dynamicQuery();

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.distinct(
				ProjectionFactoryUtil.property("companyId")));

		return analyticsMessagePersistence.findWithDynamicQuery(dynamicQuery);
	}

	@Reference
	private UserLocalService _userLocalService;

}