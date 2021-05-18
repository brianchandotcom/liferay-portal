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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeTable;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.documentlibrary.constants.DLConstants;
import com.liferay.portlet.documentlibrary.service.base.DLFileEntryTypeServiceBaseImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service for accessing, adding, deleting, and updating
 * file and folder file entry types. Its methods include permission checks.
 *
 * @author Alexander Chow
 */
public class DLFileEntryTypeServiceImpl extends DLFileEntryTypeServiceBaseImpl {

	@Override
	public DLFileEntryType addFileEntryType(
			long groupId, long dataDefinitionId, String fileEntryTypeKey,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_DOCUMENT_TYPE);

		return dlFileEntryTypeLocalService.addFileEntryType(
			getUserId(), groupId, dataDefinitionId, fileEntryTypeKey, nameMap,
			descriptionMap, serviceContext);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #addFileEntryType(long, String, Map, Map, long,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public DLFileEntryType addFileEntryType(
			long groupId, String fileEntryTypeKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, long[] ddmStructureIds,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_DOCUMENT_TYPE);

		return dlFileEntryTypeLocalService.addFileEntryType(
			getUserId(), groupId, fileEntryTypeKey, nameMap, descriptionMap,
			ddmStructureIds, serviceContext);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #addFileEntryType(long, String, Map, Map, long,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public DLFileEntryType addFileEntryType(
			long groupId, String name, String description,
			long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException {

		return addFileEntryType(
			groupId, null,
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), name
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), description
			).build(),
			ddmStructureIds, serviceContext);
	}

	@Override
	public void deleteFileEntryType(long fileEntryTypeId)
		throws PortalException {

		_dlFileEntryTypeModelResourcePermission.check(
			getPermissionChecker(), fileEntryTypeId, ActionKeys.DELETE);

		dlFileEntryTypeLocalService.deleteFileEntryType(fileEntryTypeId);
	}

	@Override
	public DLFileEntryType getFileEntryType(long fileEntryTypeId)
		throws PortalException {

		_dlFileEntryTypeModelResourcePermission.check(
			getPermissionChecker(), fileEntryTypeId, ActionKeys.VIEW);

		return dlFileEntryTypeLocalService.getFileEntryType(fileEntryTypeId);
	}

	@Override
	public List<DLFileEntryType> getFileEntryTypes(long[] groupIds) {
		return dlFileEntryTypePersistence.filterFindByGroupId(groupIds);
	}

	@Override
	public List<DLFileEntryType> getFileEntryTypes(
		long[] groupIds, int start, int end) {

		return dlFileEntryTypePersistence.filterFindByGroupId(
			groupIds, start, end);
	}

	@Override
	public int getFileEntryTypesCount(long[] groupIds) {
		return dlFileEntryTypePersistence.filterCountByGroupId(groupIds);
	}

	@Override
	public List<DLFileEntryType> getFolderFileEntryTypes(
			long[] groupIds, long folderId, boolean inherited)
		throws PortalException {

		return filterFileEntryTypes(
			dlFileEntryTypeLocalService.getFolderFileEntryTypes(
				groupIds, folderId, inherited));
	}

	@Override
	public List<DLFileEntryType> search(
			long companyId, long folderId, long[] groupIds, String keywords,
			boolean includeBasicFileEntryType, boolean inherited, int start,
			int end)
		throws PortalException {

		return dlFileEntryTypeFinder.filterFindByKeywords(
			companyId, folderId, groupIds, keywords, includeBasicFileEntryType,
			inherited, start, end);
	}

	@Override
	public List<DLFileEntryType> search(
		long companyId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType, int scope, int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator) {

		return dlFileEntryTypePersistence.dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(
					DLFileEntryTypeTable.INSTANCE),
				companyId, groupIds, keywords, includeBasicFileEntryType, scope
			).orderBy(
				DLFileEntryTypeTable.INSTANCE, orderByComparator
			).limit(
				start, end
			));
	}

	@Override
	public List<DLFileEntryType> search(
		long companyId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType, int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator) {

		return dlFileEntryTypeFinder.filterFindByKeywords(
			companyId, groupIds, keywords, includeBasicFileEntryType, start,
			end, orderByComparator);
	}

	@Override
	public int searchCount(
		long companyId, long folderId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType, boolean inherited) {

		return dlFileEntryTypeFinder.filterCountByKeywords(
			companyId, folderId, groupIds, keywords, includeBasicFileEntryType,
			inherited);
	}

	@Override
	public int searchCount(
		long companyId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType) {

		return dlFileEntryTypeFinder.filterCountByKeywords(
			companyId, groupIds, keywords, includeBasicFileEntryType);
	}

	@Override
	public int searchCount(
		long companyId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType, int scope) {

		return dlFileEntryTypePersistence.dslQueryCount(
			_getGroupByStep(
				DSLQueryFactoryUtil.countDistinct(
					DLFileEntryTypeTable.INSTANCE.fileEntryTypeId),
				companyId, groupIds, keywords, includeBasicFileEntryType,
				scope));
	}

	@Override
	public DLFileEntryType updateFileEntryType(
			long fileEntryTypeId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap)
		throws PortalException {

		_dlFileEntryTypeModelResourcePermission.check(
			getPermissionChecker(), fileEntryTypeId, ActionKeys.UPDATE);

		return dlFileEntryTypeLocalService.updateFileEntryType(
			fileEntryTypeId, nameMap, descriptionMap);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #updateFileEntryType(long, Map, Map)}
	 */
	@Deprecated
	@Override
	public void updateFileEntryType(
			long fileEntryTypeId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, long[] ddmStructureIds,
			ServiceContext serviceContext)
		throws PortalException {

		_dlFileEntryTypeModelResourcePermission.check(
			getPermissionChecker(), fileEntryTypeId, ActionKeys.UPDATE);

		dlFileEntryTypeLocalService.updateFileEntryType(
			getUserId(), fileEntryTypeId, nameMap, descriptionMap,
			ddmStructureIds, serviceContext);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #updateFileEntryType(long, Map, Map)}
	 */
	@Deprecated
	@Override
	public void updateFileEntryType(
			long fileEntryTypeId, String name, String description,
			long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException {

		updateFileEntryType(
			fileEntryTypeId,
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), name
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), description
			).build(),
			ddmStructureIds, serviceContext);
	}

	protected List<DLFileEntryType> filterFileEntryTypes(
			List<DLFileEntryType> fileEntryTypes)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		fileEntryTypes = ListUtil.copy(fileEntryTypes);

		Iterator<DLFileEntryType> iterator = fileEntryTypes.iterator();

		while (iterator.hasNext()) {
			DLFileEntryType fileEntryType = iterator.next();

			if ((fileEntryType.getFileEntryTypeId() > 0) &&
				!_dlFileEntryTypeModelResourcePermission.contains(
					permissionChecker, fileEntryType, ActionKeys.VIEW)) {

				iterator.remove();
			}
		}

		return fileEntryTypes;
	}

	private GroupByStep _getGroupByStep(
		FromStep fromStep, long companyId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType, int scope) {

		return fromStep.from(
			DLFileEntryTypeTable.INSTANCE
		).where(
			() -> {
				Predicate predicate =
					DLFileEntryTypeTable.INSTANCE.companyId.eq(companyId);

				Predicate groupIdsPredicate = null;

				for (long groupId : groupIds) {
					Predicate groupIdPredicate =
						DLFileEntryTypeTable.INSTANCE.groupId.eq(groupId);

					if (groupIdsPredicate == null) {
						groupIdsPredicate = groupIdPredicate;
					}
					else {
						groupIdsPredicate = groupIdsPredicate.or(
							groupIdPredicate);
					}
				}

				if (groupIdsPredicate != null) {
					predicate = predicate.and(
						groupIdsPredicate.withParentheses());
				}

				if (includeBasicFileEntryType) {
					predicate = predicate.withParentheses(
					).or(
						DLFileEntryTypeTable.INSTANCE.groupId.eq(0L)
					);
				}

				predicate = predicate.withParentheses(
				).and(
					DLFileEntryTypeTable.INSTANCE.scope.eq(scope)
				);

				Predicate keywordsPredicate = null;

				for (String keyword : CustomSQLUtil.keywords(keywords, true)) {
					if (keyword == null) {
						continue;
					}

					Predicate keywordPredicate = DSLFunctionFactoryUtil.lower(
						DLFileEntryTypeTable.INSTANCE.name
					).like(
						keyword
					).or(
						DSLFunctionFactoryUtil.lower(
							DLFileEntryTypeTable.INSTANCE.description
						).like(
							keyword
						)
					);

					if (keywordsPredicate == null) {
						keywordsPredicate = keywordPredicate;
					}
					else {
						keywordsPredicate = keywordsPredicate.or(
							keywordPredicate);
					}
				}

				if (keywordsPredicate != null) {
					predicate = predicate.and(
						keywordsPredicate.withParentheses());
				}

				return predicate;
			}
		);
	}

	private static volatile ModelResourcePermission<DLFileEntryType>
		_dlFileEntryTypeModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				DLFileEntryTypeServiceImpl.class,
				"_dlFileEntryTypeModelResourcePermission",
				DLFileEntryType.class);
	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				DLFileEntryTypeServiceImpl.class, "_portletResourcePermission",
				DLConstants.RESOURCE_NAME);

}