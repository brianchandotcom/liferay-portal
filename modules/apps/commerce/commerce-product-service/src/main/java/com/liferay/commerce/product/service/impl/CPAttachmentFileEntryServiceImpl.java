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

package com.liferay.commerce.product.service.impl;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.commerce.product.constants.CPActionKeys;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.base.CPAttachmentFileEntryServiceBaseImpl;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPAttachmentFileEntryServiceImpl
	extends CPAttachmentFileEntryServiceBaseImpl {

	@Override
	public CPAttachmentFileEntry addCPAttachmentFileEntry(
			long userId, long groupId, long classNameId, long classPK,
			long fileEntryId, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			Map<Locale, String> titleMap, String json, double priority,
			int type, ServiceContext serviceContext)
		throws PortalException {

		checkCPAttachmentFileEntryPermissions(
			serviceContext.getScopeGroupId(), classNameId, classPK, type);

		return cpAttachmentFileEntryLocalService.addCPAttachmentFileEntry(
			userId, groupId, classNameId, classPK, fileEntryId,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, titleMap, json, priority, type, null, serviceContext);
	}

	@Override
	public void deleteCPAttachmentFileEntry(long cpAttachmentFileEntryId)
		throws PortalException {

		checkCPAttachmentFileEntryPermissions(cpAttachmentFileEntryId);

		cpAttachmentFileEntryLocalService.deleteCPAttachmentFileEntry(
			cpAttachmentFileEntryId);
	}

	@Override
	public CPAttachmentFileEntry fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException {

		CPAttachmentFileEntry cpAttachmentFileEntry =
			cpAttachmentFileEntryLocalService.fetchByExternalReferenceCode(
				companyId, externalReferenceCode);

		if (cpAttachmentFileEntry != null) {
			long cpDefinitionClassNameId = _portal.getClassNameId(
				CPDefinition.class);

			if (cpDefinitionClassNameId ==
					cpAttachmentFileEntry.getClassNameId()) {

				_checkCommerceCatalog(
					cpAttachmentFileEntry.getClassPK(), ActionKeys.VIEW);
			}
			else {
				checkCPAttachmentFileEntryPermissions(cpAttachmentFileEntry);
			}
		}

		return cpAttachmentFileEntry;
	}

	@Override
	public CPAttachmentFileEntry fetchCPAttachmentFileEntry(
			long cpAttachmentFileEntryId)
		throws PortalException {

		CPAttachmentFileEntry cpAttachmentFileEntry =
			cpAttachmentFileEntryLocalService.fetchCPAttachmentFileEntry(
				cpAttachmentFileEntryId);

		if (cpAttachmentFileEntry != null) {
			long cpDefinitionClassNameId = _portal.getClassNameId(
				CPDefinition.class);

			long assetCategoryClassNameId = _portal.getClassNameId(
				AssetCategory.class);

			if (cpDefinitionClassNameId ==
					cpAttachmentFileEntry.getClassNameId()) {

				_checkCommerceCatalog(
					cpAttachmentFileEntry.getClassPK(), ActionKeys.VIEW);
			}
			else if (assetCategoryClassNameId ==
						cpAttachmentFileEntry.getClassNameId()) {

				AssetCategoryPermission.check(
					getPermissionChecker(), cpAttachmentFileEntry.getClassPK(),
					ActionKeys.VIEW);
			}
			else {
				checkCPAttachmentFileEntryPermissions(cpAttachmentFileEntryId);
			}
		}

		return cpAttachmentFileEntry;
	}

	@Override
	public List<CPAttachmentFileEntry> getCPAttachmentFileEntries(
			long classNameId, long classPK, int type, int status, int start,
			int end)
		throws PortalException {

		checkCPAttachmentFileEntryPermissions(
			classNameId, classPK, ActionKeys.VIEW);

		List<CPAttachmentFileEntry> filteredCPAttachmentFileEntries =
			new ArrayList<>();

		List<CPAttachmentFileEntry> cpAttachmentFileEntries =
			cpAttachmentFileEntryLocalService.getCPAttachmentFileEntries(
				classNameId, classPK, type, status, start, end);

		for (CPAttachmentFileEntry cpAttachmentFileEntry :
				cpAttachmentFileEntries) {

			DLFileEntry dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(
				cpAttachmentFileEntry.getFileEntryId());

			if ((dlFileEntry != null) &&
				_dlFileEntryModelResourcePermission.contains(
					getPermissionChecker(), dlFileEntry, ActionKeys.VIEW)) {

				filteredCPAttachmentFileEntries.add(cpAttachmentFileEntry);
			}
		}

		return filteredCPAttachmentFileEntries;
	}

	@Override
	public List<CPAttachmentFileEntry> getCPAttachmentFileEntries(
			long classNameId, long classPK, int type, int status, int start,
			int end, OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws PortalException {

		checkCPAttachmentFileEntryPermissions(
			classNameId, classPK, ActionKeys.VIEW);

		List<CPAttachmentFileEntry> filteredCPAttachmentFileEntries =
			new ArrayList<>();

		List<CPAttachmentFileEntry> cpAttachmentFileEntries =
			cpAttachmentFileEntryLocalService.getCPAttachmentFileEntries(
				classNameId, classPK, type, status, start, end,
				orderByComparator);

		for (CPAttachmentFileEntry cpAttachmentFileEntry :
				cpAttachmentFileEntries) {

			DLFileEntry dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(
				cpAttachmentFileEntry.getFileEntryId());

			if ((dlFileEntry != null) &&
				_dlFileEntryModelResourcePermission.contains(
					getPermissionChecker(), dlFileEntry, ActionKeys.VIEW)) {

				filteredCPAttachmentFileEntries.add(cpAttachmentFileEntry);
			}
		}

		return filteredCPAttachmentFileEntries;
	}

	@Override
	public int getCPAttachmentFileEntriesCount(
			long classNameId, long classPK, int type, int status)
		throws PortalException {

		checkCPAttachmentFileEntryPermissions(
			classNameId, classPK, ActionKeys.VIEW);

		return cpAttachmentFileEntryLocalService.
			getCPAttachmentFileEntriesCount(classNameId, classPK, type, status);
	}

	@Override
	public CPAttachmentFileEntry getCPAttachmentFileEntry(
			long cpAttachmentFileEntryId)
		throws PortalException {

		CPAttachmentFileEntry cpAttachmentFileEntry =
			cpAttachmentFileEntryLocalService.getCPAttachmentFileEntry(
				cpAttachmentFileEntryId);

		if (cpAttachmentFileEntry != null) {
			long cpDefinitionClassNameId = _portal.getClassNameId(
				CPDefinition.class);

			long assetCategoryClassNameId = _portal.getClassNameId(
				AssetCategory.class);

			if (cpDefinitionClassNameId ==
					cpAttachmentFileEntry.getClassNameId()) {

				_checkCommerceCatalog(
					cpAttachmentFileEntry.getClassPK(), ActionKeys.VIEW);
			}
			else if (assetCategoryClassNameId ==
						cpAttachmentFileEntry.getClassNameId()) {

				AssetCategoryPermission.check(
					getPermissionChecker(), cpAttachmentFileEntry.getClassPK(),
					ActionKeys.VIEW);
			}
			else {
				checkCPAttachmentFileEntryPermissions(cpAttachmentFileEntryId);
			}
		}

		return cpAttachmentFileEntry;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPAttachmentFileEntry updateCPAttachmentFileEntry(
			long cpAttachmentFileEntryId, long fileEntryId,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, Map<Locale, String> titleMap, String json,
			double priority, int type, ServiceContext serviceContext)
		throws PortalException {

		checkCPAttachmentFileEntryPermissions(cpAttachmentFileEntryId);

		return cpAttachmentFileEntryLocalService.updateCPAttachmentFileEntry(
			cpAttachmentFileEntryId, fileEntryId, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, titleMap,
			json, priority, type, serviceContext);
	}

	/**
	 * @param      classNameId
	 * @param      classPK
	 * @param      fileEntryId
	 * @param      displayDateMonth
	 * @param      displayDateDay
	 * @param      displayDateYear
	 * @param      displayDateHour
	 * @param      displayDateMinute
	 * @param      expirationDateMonth
	 * @param      expirationDateDay
	 * @param      expirationDateYear
	 * @param      expirationDateHour
	 * @param      expirationDateMinute
	 * @param      neverExpire
	 * @param      titleMap
	 * @param      json
	 * @param      priority
	 * @param      type
	 * @param      externalReferenceCode
	 * @param      serviceContext
	 * @throws     PortalException
	 * @deprecated As of Athanasius (7.3.x), use {@link
	 *             #upsertCPAttachmentFileEntry(long, long, long, long, int,
	 *             int, int, int, int, int, int, int, int, int, boolean, Map,
	 *             String, double, int, String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public CPAttachmentFileEntry upsertCPAttachmentFileEntry(
			long groupId, long classNameId, long classPK, long fileEntryId,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, Map<Locale, String> titleMap, String json,
			double priority, int type, String externalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException {

		CPAttachmentFileEntry cpAttachmentFileEntry =
			cpAttachmentFileEntryLocalService.fetchByExternalReferenceCode(
				serviceContext.getCompanyId(), externalReferenceCode);

		if (cpAttachmentFileEntry == null) {
			checkCPAttachmentFileEntryPermissions(
				serviceContext.getScopeGroupId(), classNameId, classPK, type);
		}
		else {
			checkCPAttachmentFileEntryPermissions(cpAttachmentFileEntry);
		}

		return cpAttachmentFileEntryLocalService.upsertCPAttachmentFileEntry(
			groupId, classNameId, classPK, 0, fileEntryId, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, titleMap,
			json, priority, type, externalReferenceCode, serviceContext);
	}

	@Override
	public CPAttachmentFileEntry upsertCPAttachmentFileEntry(
			long groupId, long classNameId, long classPK,
			long cpAttachmentFileEntryId, long fileEntryId,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, Map<Locale, String> titleMap, String json,
			double priority, int type, String externalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException {

		CPAttachmentFileEntry cpAttachmentFileEntry = null;

		if (cpAttachmentFileEntryId != 0) {
			cpAttachmentFileEntry =
				cpAttachmentFileEntryPersistence.fetchByPrimaryKey(
					cpAttachmentFileEntryId);
		}
		else if (Validator.isNotNull(externalReferenceCode)) {
			cpAttachmentFileEntry =
				cpAttachmentFileEntryPersistence.fetchByC_ERC(
					serviceContext.getCompanyId(), externalReferenceCode);
		}

		if (cpAttachmentFileEntry == null) {
			checkCPAttachmentFileEntryPermissions(
				serviceContext.getScopeGroupId(), classNameId, classPK, type);
		}
		else {
			checkCPAttachmentFileEntryPermissions(cpAttachmentFileEntry);
		}

		return cpAttachmentFileEntryLocalService.upsertCPAttachmentFileEntry(
			groupId, classNameId, classPK, cpAttachmentFileEntryId, fileEntryId,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, titleMap, json, priority, type, externalReferenceCode,
			serviceContext);
	}

	protected void checkCPAttachmentFileEntryPermissions(
			CPAttachmentFileEntry cpAttachmentFileEntry)
		throws PortalException {

		checkCPAttachmentFileEntryPermissions(
			cpAttachmentFileEntry.getGroupId(),
			cpAttachmentFileEntry.getClassNameId(),
			cpAttachmentFileEntry.getClassPK(),
			cpAttachmentFileEntry.getType());
	}

	protected void checkCPAttachmentFileEntryPermissions(
			long cpAttachmentFileEntryId)
		throws PortalException {

		checkCPAttachmentFileEntryPermissions(
			cpAttachmentFileEntryLocalService.getCPAttachmentFileEntry(
				cpAttachmentFileEntryId));
	}

	protected void checkCPAttachmentFileEntryPermissions(
			long scopeGroupId, long classNameId, long classPK, int type)
		throws PortalException {

		String actionKey = getActionKeyByCPAttachmentFileEntryType(type);

		_portletResourcePermission.check(
			getPermissionChecker(), scopeGroupId, actionKey);

		checkCPAttachmentFileEntryPermissions(
			classNameId, classPK, ActionKeys.UPDATE);
	}

	protected void checkCPAttachmentFileEntryPermissions(
			long classNameId, long classPK, String actionId)
		throws PortalException {

		long cpDefinitionClassNameId = _portal.getClassNameId(
			CPDefinition.class);

		if (classNameId == cpDefinitionClassNameId) {
			_checkCommerceCatalog(classPK, actionId);
		}
	}

	protected String getActionKeyByCPAttachmentFileEntryType(int type) {
		if (type == CPAttachmentFileEntryConstants.TYPE_OTHER) {
			return CPActionKeys.MANAGE_COMMERCE_PRODUCT_ATTACHMENTS;
		}

		return CPActionKeys.MANAGE_COMMERCE_PRODUCT_IMAGES;
	}

	private void _checkCommerceCatalog(long cpDefinitionId, String actionId)
		throws PortalException {

		CPDefinition cpDefinition = cpDefinitionLocalService.fetchCPDefinition(
			cpDefinitionId);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException();
		}

		CommerceCatalog commerceCatalog =
			commerceCatalogLocalService.fetchCommerceCatalogByGroupId(
				cpDefinition.getGroupId());

		if (commerceCatalog == null) {
			throw new PrincipalException();
		}

		_commerceCatalogModelResourcePermission.check(
			getPermissionChecker(), commerceCatalog, actionId);
	}

	private static volatile ModelResourcePermission<CommerceCatalog>
		_commerceCatalogModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CPAttachmentFileEntryServiceImpl.class,
				"_commerceCatalogModelResourcePermission",
				CommerceCatalog.class);
	private static volatile ModelResourcePermission<DLFileEntry>
		_dlFileEntryModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CPAttachmentFileEntryServiceImpl.class,
				"_dlFileEntryModelResourcePermission", DLFileEntry.class);
	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				CPAttachmentFileEntryServiceImpl.class,
				"_portletResourcePermission", CPConstants.RESOURCE_NAME);

	@ServiceReference(type = Portal.class)
	private Portal _portal;

}