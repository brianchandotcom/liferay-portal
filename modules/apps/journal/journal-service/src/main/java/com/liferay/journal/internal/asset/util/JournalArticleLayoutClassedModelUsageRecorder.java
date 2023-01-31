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

package com.liferay.journal.internal.asset.util;

import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.journal.model.JournalArticle;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.layout.util.LayoutClassedModelUsageRecorder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = "model.class.name=com.liferay.journal.model.JournalArticle",
	service = LayoutClassedModelUsageRecorder.class
)
public class JournalArticleLayoutClassedModelUsageRecorder
	implements LayoutClassedModelUsageRecorder {

	@Override
	public void record(long classNameId, long classPK) throws PortalException {
		if (_layoutClassedModelUsageLocalService.
				hasDefaultLayoutClassedModelUsage(classNameId, classPK)) {

			return;
		}

		InfoItemObjectProvider<JournalArticle> infoItemObjectProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemObjectProvider.class,
				_portal.getClassName(classNameId));

		JournalArticle article = infoItemObjectProvider.getInfoItem(
			new ClassPKInfoItemIdentifier(classPK));

		_layoutClassedModelUsageLocalService.addDefaultLayoutClassedModelUsage(
			article.getGroupId(), classNameId, classPK,
			ServiceContextThreadLocal.getServiceContext());
	}

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

	@Reference
	private Portal _portal;

}