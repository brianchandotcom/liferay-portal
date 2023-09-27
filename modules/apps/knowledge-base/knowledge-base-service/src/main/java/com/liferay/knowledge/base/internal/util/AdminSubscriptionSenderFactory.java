/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.util;

import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.SubscriptionSender;

/**
 * @author Preston Crary
 */
public class AdminSubscriptionSenderFactory {

	public static SubscriptionSender createSubscriptionSender(
		KBArticle kbArticle, ServiceContext serviceContext) {

		return new AdminSubscriptionSender(
			kbArticle, _kbArticleModelResourcePermissionSnapshot.get(),
			serviceContext);
	}

	private static final Snapshot<ModelResourcePermission<KBArticle>>
		_kbArticleModelResourcePermissionSnapshot = new Snapshot<>(
			AdminSubscriptionSenderFactory.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.knowledge.base.model.KBArticle)");

}