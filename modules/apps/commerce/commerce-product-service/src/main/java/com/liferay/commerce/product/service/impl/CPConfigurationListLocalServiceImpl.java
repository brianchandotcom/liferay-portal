/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.price.list.exception.CommercePriceListDisplayDateException;
import com.liferay.commerce.price.list.exception.CommercePriceListExpirationDateException;
import com.liferay.commerce.product.exception.CPConfigurationListCannotDeleteException;
import com.liferay.commerce.product.exception.CPConfigurationListParentCPConfigurationListGroupIdException;
import com.liferay.commerce.product.exception.DuplicateCPConfigurationListException;
import com.liferay.commerce.product.exception.NoSuchCPConfigurationListException;
import com.liferay.commerce.product.model.CPConfigurationList;
import com.liferay.commerce.product.service.CPConfigurationEntryLocalService;
import com.liferay.commerce.product.service.base.CPConfigurationListLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.Portal;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "model.class.name=com.liferay.commerce.product.model.CPConfigurationList",
	service = AopService.class
)
public class CPConfigurationListLocalServiceImpl
	extends CPConfigurationListLocalServiceBaseImpl {

	@Override
	public CPConfigurationList addCPConfigurationList(
			String externalReferenceCode, long groupId, long userId,
			long parentCPConfigurationListId, boolean masterCPConfigurationList,
			String name, double priority, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		_validate(
			groupId, 0, masterCPConfigurationList, parentCPConfigurationListId);

		Date expirationDate = null;

		Date displayDate = _portal.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			CommercePriceListDisplayDateException.class);

		if (!neverExpire) {
			expirationDate = _portal.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				CommercePriceListExpirationDateException.class);
		}

		long cpConfigurationListId = counterLocalService.increment();

		CPConfigurationList cpConfigurationList =
			cpConfigurationListPersistence.create(cpConfigurationListId);

		cpConfigurationList.setExternalReferenceCode(externalReferenceCode);
		cpConfigurationList.setGroupId(groupId);
		cpConfigurationList.setCompanyId(user.getCompanyId());
		cpConfigurationList.setUserId(user.getUserId());
		cpConfigurationList.setUserName(user.getFullName());
		cpConfigurationList.setParentCPConfigurationListId(
			parentCPConfigurationListId);
		cpConfigurationList.setMasterCPConfigurationList(
			masterCPConfigurationList);
		cpConfigurationList.setName(name);
		cpConfigurationList.setPriority(priority);
		cpConfigurationList.setDisplayDate(displayDate);
		cpConfigurationList.setExpirationDate(expirationDate);

		return cpConfigurationListPersistence.update(cpConfigurationList);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPConfigurationList deleteCPConfigurationList(
			CPConfigurationList cpConfigurationList)
		throws PortalException {

		if (cpConfigurationList.isMasterCPConfigurationList()) {
			throw new CPConfigurationListCannotDeleteException();
		}

		cpConfigurationListPersistence.remove(cpConfigurationList);

		_cpConfigurationEntryLocalService.deleteCPConfigurationEntries(
			cpConfigurationList.getCPConfigurationListId());

		return cpConfigurationList;
	}

	@Override
	public CPConfigurationList deleteCPConfigurationList(
			long cpConfigurationListId)
		throws PortalException {

		CPConfigurationList cpConfigurationList =
			cpConfigurationListPersistence.findByPrimaryKey(
				cpConfigurationListId);

		return cpConfigurationListLocalService.deleteCPConfigurationList(
			cpConfigurationList);
	}

	@Override
	public void deleteCPConfigurationLists(long companyId)
		throws PortalException {

		List<CPConfigurationList> cpConfigurationLists =
			cpConfigurationListPersistence.findByCompanyId(companyId);

		for (CPConfigurationList cpConfigurationList : cpConfigurationLists) {
			cpConfigurationListLocalService.forceDeleteCPConfigurationList(
				cpConfigurationList);
		}
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPConfigurationList forceDeleteCPConfigurationList(
			CPConfigurationList cpConfigurationList)
		throws PortalException {

		cpConfigurationListPersistence.remove(cpConfigurationList);

		_cpConfigurationEntryLocalService.deleteCPConfigurationEntries(
			cpConfigurationList.getCPConfigurationListId());

		return cpConfigurationList;
	}

	@Override
	public List<CPConfigurationList> getCPConfigurationLists(
		long groupId, long companyId) {

		return cpConfigurationListPersistence.findByG_C(groupId, companyId);
	}

	@Override
	public CPConfigurationList getMasterCPConfigurationList(long groupId)
		throws NoSuchCPConfigurationListException {

		return cpConfigurationListPersistence.findByG_MasterCPConfigurationList(
			groupId, true);
	}

	private void _validate(
			long groupId, long cpConfigurationListId,
			boolean masterConfigurationList, long parentCPConfigurationListId)
		throws PortalException {

		if (masterConfigurationList) {
			CPConfigurationList cpConfigurationList =
				cpConfigurationListPersistence.
					fetchByG_MasterCPConfigurationList(
						groupId, masterConfigurationList);

			if ((cpConfigurationList != null) &&
				(cpConfigurationList.getCPConfigurationListId() !=
					cpConfigurationListId)) {

				throw new DuplicateCPConfigurationListException();
			}
		}

		if (parentCPConfigurationListId > 0) {
			if (parentCPConfigurationListId == cpConfigurationListId) {
				throw new CPConfigurationListParentCPConfigurationListGroupIdException();
			}

			CPConfigurationList cpConfigurationList =
				cpConfigurationListLocalService.fetchCPConfigurationList(
					parentCPConfigurationListId);

			if ((cpConfigurationList != null) &&
				(cpConfigurationList.getGroupId() != groupId)) {

				throw new CPConfigurationListParentCPConfigurationListGroupIdException();
			}
		}
	}

	@Reference
	private CPConfigurationEntryLocalService _cpConfigurationEntryLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}