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

package com.liferay.knowledge.base.internal.util;

import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.util.AdminSubscriptionSender;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.SubscriptionSender;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true)
public class AdminSubscriptionSenderFactory {

	public static SubscriptionSender createSubscriptionSender(
		KBArticle kbArticle, ServiceContext serviceContext) {

		return new AdminSubscriptionSender(
			kbArticle, _kbArticleModelResourcePermission, serviceContext);
	}

	@Reference(
		target = "(model.class.name=com.liferay.knowledge.base.model.KBArticle)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<KBArticle> modelResourcePermission) {

		_kbArticleModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<KBArticle>
		_kbArticleModelResourcePermission;

}