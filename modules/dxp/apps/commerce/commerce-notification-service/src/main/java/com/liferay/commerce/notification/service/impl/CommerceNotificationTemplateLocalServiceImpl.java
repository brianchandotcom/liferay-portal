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

package com.liferay.commerce.notification.service.impl;

import com.liferay.commerce.notification.exception.CommerceNotificationTemplateNameException;
import com.liferay.commerce.notification.exception.CommerceNotificationTemplateTypeException;
import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
import com.liferay.commerce.notification.service.base.CommerceNotificationTemplateLocalServiceBaseImpl;
import com.liferay.commerce.notification.type.CommerceNotificationType;
import com.liferay.commerce.notification.type.CommerceNotificationTypeRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceNotificationTemplateLocalServiceImpl
	extends CommerceNotificationTemplateLocalServiceBaseImpl {

	@Override
	public CommerceNotificationTemplate addCommerceNotificationTemplate(
			String name, String description, String cc, String ccn, String type,
			boolean enabled, Map<Locale, String> subjectMap,
			Map<Locale, String> bodyMap, ServiceContext serviceContext)
		throws PortalException {

		// Commerce notification template

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		validate(name, type);

		long commerceNotificationTemplateId = counterLocalService.increment();

		CommerceNotificationTemplate commerceNotificationTemplate =
			commerceNotificationTemplatePersistence.create(
				commerceNotificationTemplateId);

		commerceNotificationTemplate.setUuid(serviceContext.getUuid());
		commerceNotificationTemplate.setGroupId(groupId);
		commerceNotificationTemplate.setCompanyId(user.getCompanyId());
		commerceNotificationTemplate.setUserId(user.getUserId());
		commerceNotificationTemplate.setUserName(user.getFullName());
		commerceNotificationTemplate.setName(name);
		commerceNotificationTemplate.setDescription(description);
		commerceNotificationTemplate.setCc(cc);
		commerceNotificationTemplate.setCcn(ccn);
		commerceNotificationTemplate.setType(type);
		commerceNotificationTemplate.setEnabled(enabled);
		commerceNotificationTemplate.setSubjectMap(subjectMap);
		commerceNotificationTemplate.setBodyMap(bodyMap);
		commerceNotificationTemplate.setExpandoBridgeAttributes(serviceContext);

		commerceNotificationTemplatePersistence.update(
			commerceNotificationTemplate);

		// Resources

		resourceLocalService.addModelResources(
			commerceNotificationTemplate, serviceContext);

		return commerceNotificationTemplate;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceNotificationTemplate deleteCommerceNotificationTemplate(
			CommerceNotificationTemplate commerceNotificationTemplate)
		throws PortalException {

		// Commerce notification template user segment rels

		commerceNotificationTemplateUserSegmentRelLocalService.
			deleteCNTemplateUserSegmentRelsByCommerceNotificationTemplateId(
				commerceNotificationTemplate.
					getCommerceNotificationTemplateId());

		// Commerce notification template

		commerceNotificationTemplatePersistence.remove(
			commerceNotificationTemplate);

		// Resources

		resourceLocalService.deleteResource(
			commerceNotificationTemplate.getCompanyId(),
			CommerceNotificationTemplate.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			commerceNotificationTemplate.getCommerceNotificationTemplateId());

		// Expando

		expandoRowLocalService.deleteRows(
			commerceNotificationTemplate.getCommerceNotificationTemplateId());

		return commerceNotificationTemplate;
	}

	@Override
	public CommerceNotificationTemplate deleteCommerceNotificationTemplate(
			long commerceNotificationTemplateId)
		throws PortalException {

		CommerceNotificationTemplate commerceNotificationTemplate =
			commerceNotificationTemplatePersistence.findByPrimaryKey(
				commerceNotificationTemplateId);

		return commerceNotificationTemplateLocalService.
			deleteCommerceNotificationTemplate(commerceNotificationTemplate);
	}

	@Override
	public void deleteCommerceNotificationTemplates(long groupId)
		throws PortalException {

		List<CommerceNotificationTemplate> commerceNotificationTemplates =
			commerceNotificationTemplatePersistence.findByGroupId(groupId);

		for (CommerceNotificationTemplate commerceNotificationTemplate :
				commerceNotificationTemplates) {

			commerceNotificationTemplateLocalService.
				deleteCommerceNotificationTemplate(
					commerceNotificationTemplate);
		}
	}

	@Override
	public List<CommerceNotificationTemplate> getCommerceNotificationTemplates(
		long groupId, int start, int end,
		OrderByComparator<CommerceNotificationTemplate> orderByComparator) {

		return commerceNotificationTemplatePersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceNotificationTemplatesCount(long groupId) {
		return commerceNotificationTemplatePersistence.countByGroupId(groupId);
	}

	@Override
	public CommerceNotificationTemplate updateCommerceNotificationTemplate(
			long commerceNotificationTemplateId, String name,
			String description, String cc, String ccn, String type,
			boolean enabled, Map<Locale, String> subjectMap,
			Map<Locale, String> bodyMap, ServiceContext serviceContext)
		throws PortalException {

		CommerceNotificationTemplate commerceNotificationTemplate =
			commerceNotificationTemplatePersistence.findByPrimaryKey(
				commerceNotificationTemplateId);

		validate(name, type);

		commerceNotificationTemplate.setName(name);
		commerceNotificationTemplate.setDescription(description);
		commerceNotificationTemplate.setCc(cc);
		commerceNotificationTemplate.setCcn(ccn);
		commerceNotificationTemplate.setType(type);
		commerceNotificationTemplate.setEnabled(enabled);
		commerceNotificationTemplate.setSubjectMap(subjectMap);
		commerceNotificationTemplate.setBodyMap(bodyMap);
		commerceNotificationTemplate.setExpandoBridgeAttributes(serviceContext);

		commerceNotificationTemplatePersistence.update(
			commerceNotificationTemplate);

		return commerceNotificationTemplate;
	}

	protected void validate(String name, String type) throws PortalException {
		if (Validator.isNull(name)) {
			throw new CommerceNotificationTemplateNameException();
		}

		CommerceNotificationType commerceNotificationType =
			_commerceNotificationTypeRegistry.getCommerceNotificationType(type);

		if (commerceNotificationType == null) {
			throw new CommerceNotificationTemplateTypeException();
		}
	}

	@ServiceReference(type = CommerceNotificationTypeRegistry.class)
	private CommerceNotificationTypeRegistry _commerceNotificationTypeRegistry;

}