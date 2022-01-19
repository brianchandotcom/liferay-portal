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

package com.liferay.commerce.term.service.impl;

import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.model.CommerceTermEntryRel;
import com.liferay.commerce.term.service.base.CommerceTermEntryRelLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.commerce.term.model.CommerceTermEntryRel",
	service = AopService.class
)
public class CommerceTermEntryRelLocalServiceImpl
	extends CommerceTermEntryRelLocalServiceBaseImpl {

	@Override
	public CommerceTermEntryRel addCommerceTermEntryRel(
			long userId, String className, long classPK,
			long commerceTermEntryId)
		throws PortalException {

		CommerceTermEntryRel commerceTermEntryRel =
			commerceTermEntryRelPersistence.create(
				counterLocalService.increment());

		User user = userLocalService.getUser(userId);

		commerceTermEntryRel.setCompanyId(user.getCompanyId());
		commerceTermEntryRel.setUserId(user.getUserId());
		commerceTermEntryRel.setUserName(user.getFullName());

		commerceTermEntryRel.setClassNameId(
			classNameLocalService.getClassNameId(className));
		commerceTermEntryRel.setClassPK(classPK);
		commerceTermEntryRel.setCommerceTermEntryId(commerceTermEntryId);

		commerceTermEntryRel = commerceTermEntryRelPersistence.update(
			commerceTermEntryRel);

		reindexCommerceTermEntry(commerceTermEntryId);

		return commerceTermEntryRel;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceTermEntryRel deleteCommerceTermEntryRel(
			CommerceTermEntryRel commerceTermEntryRel)
		throws PortalException {

		commerceTermEntryRelPersistence.remove(commerceTermEntryRel);

		reindexCommerceTermEntry(commerceTermEntryRel.getCommerceTermEntryId());

		return commerceTermEntryRel;
	}

	@Override
	public CommerceTermEntryRel deleteCommerceTermEntryRel(
			long commerceTermEntryRelId)
		throws PortalException {

		CommerceTermEntryRel commerceTermEntryRel =
			commerceTermEntryRelPersistence.findByPrimaryKey(
				commerceTermEntryRelId);

		return commerceTermEntryRelLocalService.deleteCommerceTermEntryRel(
			commerceTermEntryRel);
	}

	@Override
	public void deleteCommerceTermEntryRels(long commerceTermEntryId)
		throws PortalException {

		List<CommerceTermEntryRel> commerceTermEntryRels =
			commerceTermEntryRelPersistence.findByCommerceTermEntryId(
				commerceTermEntryId);

		for (CommerceTermEntryRel commerceTermEntryRel :
				commerceTermEntryRels) {

			commerceTermEntryRelLocalService.deleteCommerceTermEntryRel(
				commerceTermEntryRel);
		}
	}

	@Override
	public void deleteCommerceTermEntryRels(
			String className, long commerceTermEntryId)
		throws PortalException {

		List<CommerceTermEntryRel> commerceTermEntryRels =
			commerceTermEntryRelPersistence.findByC_C(
				classNameLocalService.getClassNameId(className),
				commerceTermEntryId);

		for (CommerceTermEntryRel commerceTermEntryRel :
				commerceTermEntryRels) {

			commerceTermEntryRelLocalService.deleteCommerceTermEntryRel(
				commerceTermEntryRel);
		}
	}

	@Override
	public CommerceTermEntryRel fetchCommerceTermEntryRel(
		String className, long classPK, long commerceTermEntryId) {

		return commerceTermEntryRelPersistence.fetchByC_C_C(
			classNameLocalService.getClassNameId(className), classPK,
			commerceTermEntryId);
	}

	@Override
	public List<CommerceTermEntryRel> getCommerceTermEntryRels(
		long commerceTermEntryId) {

		return commerceTermEntryRelPersistence.findByCommerceTermEntryId(
			commerceTermEntryId);
	}

	@Override
	public List<CommerceTermEntryRel> getCommerceTermEntryRels(
		long commerceTermEntryId, int start, int end,
		OrderByComparator<CommerceTermEntryRel> orderByComparator) {

		return commerceTermEntryRelPersistence.findByCommerceTermEntryId(
			commerceTermEntryId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTermEntryRelsCount(long commerceTermEntryId) {
		return commerceTermEntryRelPersistence.countByCommerceTermEntryId(
			commerceTermEntryId);
	}

	protected void reindexCommerceTermEntry(long commerceTermEntryId)
		throws PortalException {

		Indexer<CommerceTermEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommerceTermEntry.class);

		indexer.reindex(CommerceTermEntry.class.getName(), commerceTermEntryId);
	}

}