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

package com.liferay.knowledge.base.internal.security.permission.resource;

import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.constants.KBConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBComment;
import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.knowledge.base.service.KBCommentLocalService;
import com.liferay.knowledge.base.service.KBTemplateLocalService;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class KBCommentModelResourcePermissionRegistrar {

	@Activate
	protected void activate(BundleContext bundleContext) {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("model.class.name", KBComment.class.getName());

		_serviceRegistration = bundleContext.registerService(
			ModelResourcePermission.class,
			ModelResourcePermissionFactory.create(
				KBComment.class, KBComment::getKbCommentId,
				_kbCommentLocalService::getKBComment,
				_portletResourcePermission,
				(modelResourcePermission, consumer) -> consumer.accept(
					(permissionChecker, name, kbComment, actionId) -> {
						if (permissionChecker.getUserId() ==
								kbComment.getUserId()) {

							return true;
						}

						if (actionId.equals(KBActionKeys.VIEW)) {
							return _portletResourcePermission.contains(
								permissionChecker, kbComment.getGroupId(),
								KBActionKeys.VIEW_SUGGESTIONS);
						}

						if (!actionId.equals(KBActionKeys.DELETE) &&
							!actionId.equals(KBActionKeys.UPDATE)) {

							return false;
						}

						String className = kbComment.getClassName();

						if (className.equals(KBArticle.class.getName())) {
							KBArticle kbArticle =
								_kbArticleLocalService.getLatestKBArticle(
									kbComment.getClassPK(),
									WorkflowConstants.STATUS_ANY);

							return permissionChecker.hasPermission(
								kbArticle.getGroupId(),
								KBArticle.class.getName(),
								kbArticle.getResourcePrimKey(),
								KBActionKeys.UPDATE);
						}

						if (className.equals(KBTemplate.class.getName())) {
							KBTemplate kbTemplate =
								_kbTemplateLocalService.getKBTemplate(
									kbComment.getClassPK());

							return permissionChecker.hasPermission(
								kbTemplate.getGroupId(),
								KBTemplate.class.getName(),
								kbTemplate.getPrimaryKey(),
								KBActionKeys.UPDATE);
						}

						return false;
					})),
			properties);
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	@Reference
	private KBArticleLocalService _kbArticleLocalService;

	@Reference
	private KBCommentLocalService _kbCommentLocalService;

	@Reference
	private KBTemplateLocalService _kbTemplateLocalService;

	@Reference(
		target = "(resource.name=" + KBConstants.RESOURCE_NAME_ADMIN + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	private ServiceRegistration<ModelResourcePermission> _serviceRegistration;

}