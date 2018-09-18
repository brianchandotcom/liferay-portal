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

package com.liferay.message.boards.internal.security.permission.resource;

import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.constants.MBConstants;
import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBBanLocalService;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.DynamicInheritancePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.StagedModelPermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.WorkflowedModelPermissionLogic;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermission;
import com.liferay.portal.util.PropsValues;

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
public class MBMessagePermissionRegistrar {

	@Activate
	public void activate(BundleContext bundleContext) {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("model.class.name", MBMessage.class.getName());

		_serviceRegistration = bundleContext.registerService(
			ModelResourcePermission.class,
			ModelResourcePermissionFactory.create(
				MBMessage.class, MBMessage::getMessageId,
				(Long resourcePrimKey) -> {
					MBThread mbThread = _mbThreadLocalService.fetchThread(
						resourcePrimKey);

					if (mbThread == null) {
						return _mbMessageLocalService.getMessage(
							resourcePrimKey);
					}

					return _mbMessageLocalService.getMessage(
						mbThread.getRootMessageId());
				},
				_portletResourcePermission,
				(modelResourcePermission, consumer) -> {
					consumer.accept(
						(permissionChecker, name, message, actionId) -> {
							if (_mbBanLocalService.hasBan(
									message.getGroupId(),
									permissionChecker.getUserId())) {

								return false;
							}

							return null;
						});
					consumer.accept(
						new StagedModelPermissionLogic<>(
							_stagingPermission, MBPortletKeys.MESSAGE_BOARDS,
							MBMessage::getMessageId));
					consumer.accept(
						new WorkflowedModelPermissionLogic<>(
							_workflowPermission, modelResourcePermission,
							_groupLocalService, MBMessage::getMessageId));

					if (PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {
						consumer.accept(
							new DynamicInheritancePermissionLogic<>(
								_mbCategoryModelResourcePermission,
								_getFetchParentFunction(), false));
					}
				}),
			properties);
	}

	@Deactivate
	public void deactivate() {
		_serviceRegistration.unregister();
	}

	private UnsafeFunction<MBMessage, MBCategory, PortalException>
		_getFetchParentFunction() {

		return message -> {
			long categoryId = message.getCategoryId();

			if ((MBCategoryConstants.DISCUSSION_CATEGORY_ID == categoryId) ||
				(MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID ==
					categoryId)) {

				return null;
			}

			if (message.isInTrash()) {
				return _mbCategoryLocalService.fetchMBCategory(categoryId);
			}

			return _mbCategoryLocalService.getCategory(categoryId);
		};
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private MBBanLocalService _mbBanLocalService;

	@Reference
	private MBCategoryLocalService _mbCategoryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.message.boards.model.MBCategory)"
	)
	private ModelResourcePermission<MBCategory>
		_mbCategoryModelResourcePermission;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

	@Reference(target = "(resource.name=" + MBConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

	private ServiceRegistration<ModelResourcePermission> _serviceRegistration;

	@Reference
	private StagingPermission _stagingPermission;

	@Reference
	private WorkflowPermission _workflowPermission;

}