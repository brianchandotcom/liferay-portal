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

package com.liferay.commerce.order.rule.service.impl;

import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry;
import com.liferay.commerce.order.rule.service.base.CommerceOrderRuleEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luca Pellizzon
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry",
	service = AopService.class
)
public class CommerceOrderRuleEntryLocalServiceImpl
	extends CommerceOrderRuleEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrderRuleEntry addCommerceOrderRuleEntry(
			String externalReferenceCode, long userId, boolean active,
			String description, String name, int priority, String type,
			String typeSettings)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long commerceOrderRuleEntryId = counterLocalService.increment();

		CommerceOrderRuleEntry commerceOrderRuleEntry =
			commerceOrderRuleEntryPersistence.create(commerceOrderRuleEntryId);

		commerceOrderRuleEntry.setExternalReferenceCode(externalReferenceCode);
		commerceOrderRuleEntry.setCompanyId(user.getCompanyId());
		commerceOrderRuleEntry.setUserId(user.getUserId());
		commerceOrderRuleEntry.setUserName(user.getFullName());
		commerceOrderRuleEntry.setActive(active);
		commerceOrderRuleEntry.setDescription(description);
		commerceOrderRuleEntry.setName(name);
		commerceOrderRuleEntry.setPriority(priority);
		commerceOrderRuleEntry.setType(type);

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				typeSettings
			).build();

		commerceOrderRuleEntry.setTypeSettingsProperties(
			typeSettingsUnicodeProperties);

		return commerceOrderRuleEntryPersistence.update(commerceOrderRuleEntry);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceOrderRuleEntry deleteCommerceOrderRuleEntry(
			long commerceOrderRuleEntryId)
		throws PortalException {

		return commerceOrderRuleEntryPersistence.remove(
			commerceOrderRuleEntryId);
	}

	@Override
	public List<CommerceOrderRuleEntry> getCommerceOrderRuleEntries(
		long companyId, boolean active, int start, int end) {

		return commerceOrderRuleEntryPersistence.findByC_A(
			companyId, active, start, end);
	}

	@Override
	public List<CommerceOrderRuleEntry> getCommerceOrderRuleEntries(
		long companyId, boolean active, String type, int start, int end) {

		return commerceOrderRuleEntryPersistence.findByC_A_LikeType(
			companyId, active, type, start, end);
	}

	@Override
	public List<CommerceOrderRuleEntry> getCommerceOrderRuleEntries(
		long companyId, String type, int start, int end) {

		return commerceOrderRuleEntryPersistence.findByC_LikeType(
			companyId, type, start, end);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrderRuleEntry updateCommerceOrderRuleEntry(
			long commerceOrderRuleEntryId, boolean active, String description,
			String name, int priority, String typeSettings)
		throws PortalException {

		CommerceOrderRuleEntry commerceOrderRuleEntry =
			commerceOrderRuleEntryLocalService.getCommerceOrderRuleEntry(
				commerceOrderRuleEntryId);

		commerceOrderRuleEntry.setActive(active);
		commerceOrderRuleEntry.setDescription(description);
		commerceOrderRuleEntry.setName(name);
		commerceOrderRuleEntry.setPriority(priority);

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				typeSettings
			).build();

		commerceOrderRuleEntry.setTypeSettingsProperties(
			typeSettingsUnicodeProperties);

		return commerceOrderRuleEntryPersistence.update(commerceOrderRuleEntry);
	}

}