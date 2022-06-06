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

package com.liferay.commerce.qualifier.service.impl;

import com.liferay.commerce.qualifier.metadata.CommerceQualifierMetadata;
import com.liferay.commerce.qualifier.metadata.CommerceQualifierMetadataRegistry;
import com.liferay.commerce.qualifier.model.CommerceQualifierEntry;
import com.liferay.commerce.qualifier.service.base.CommerceQualifierEntryServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false,
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceQualifierEntry"
	},
	service = AopService.class
)
public class CommerceQualifierEntryServiceImpl
	extends CommerceQualifierEntryServiceBaseImpl {

	@Override
	public CommerceQualifierEntry addCommerceQualifierEntry(
			String sourceClassName, long sourceClassPK,
			String sourceCommerceQualifierMetadataKey, String targetClassName,
			long targetClassPK, String targetCommerceQualifierMetadataKey)
		throws PortalException {

		_checkPermission(sourceClassName, sourceClassPK, ActionKeys.UPDATE);

		return commerceQualifierEntryLocalService.addCommerceQualifierEntry(
			getUserId(), sourceClassName, sourceClassPK,
			sourceCommerceQualifierMetadataKey, targetClassName, targetClassPK,
			targetCommerceQualifierMetadataKey);
	}

	@Override
	public CommerceQualifierEntry deleteCommerceQualifierEntry(
			long commerceQualifierEntryId)
		throws PortalException {

		CommerceQualifierEntry commerceQualifierEntry =
			commerceQualifierEntryLocalService.getCommerceQualifierEntry(
				commerceQualifierEntryId);

		ModelResourcePermission<?> modelResourcePermission =
			_getModelResourcePermission(
				commerceQualifierEntry.getSourceClassNameId());

		modelResourcePermission.check(
			getPermissionChecker(), commerceQualifierEntry.getSourceClassPK(),
			ActionKeys.UPDATE);

		return commerceQualifierEntryLocalService.deleteCommerceQualifierEntry(
			commerceQualifierEntryId);
	}

	@Override
	public void deleteSourceCommerceQualifierEntries(
			String sourceClassName, long sourceClassPK)
		throws PortalException {

		_checkPermission(sourceClassName, sourceClassPK, ActionKeys.UPDATE);

		commerceQualifierEntryLocalService.deleteSourceCommerceQualifierEntries(
			sourceClassName, sourceClassPK);
	}

	@Override
	public void deleteTargetCommerceQualifierEntries(
			String targetClassName, long targetClassPK)
		throws PortalException {

		_checkPermission(targetClassName, targetClassPK, ActionKeys.UPDATE);

		commerceQualifierEntryLocalService.deleteTargetCommerceQualifierEntries(
			targetClassName, targetClassPK);
	}

	@Override
	public CommerceQualifierEntry fetchCommerceQualifierEntry(
			String sourceClassName, long sourceClassPK, String targetClassName,
			long targetClassPK)
		throws PortalException {

		_checkPermission(sourceClassName, sourceClassPK, ActionKeys.UPDATE);

		return commerceQualifierEntryLocalService.fetchCommerceQualifierEntry(
			sourceClassName, sourceClassPK, targetClassName, targetClassPK);
	}

	@Override
	public CommerceQualifierEntry getCommerceQualifierEntry(
			long commerceQualifierEntryId)
		throws PortalException {

		CommerceQualifierEntry commerceQualifierEntry =
			commerceQualifierEntryLocalService.getCommerceQualifierEntry(
				commerceQualifierEntryId);

		ModelResourcePermission<?> modelResourcePermission =
			_getModelResourcePermission(
				commerceQualifierEntry.getSourceClassNameId());

		modelResourcePermission.check(
			getPermissionChecker(), commerceQualifierEntry.getSourceClassPK(),
			ActionKeys.VIEW);

		return commerceQualifierEntry;
	}

	@Override
	public List<CommerceQualifierEntry> getSourceCommerceQualifierEntries(
			long companyId, String sourceClassName, long sourceClassPK,
			String targetCommerceQualifierMetadataKey, String keywords,
			int start, int end)
		throws PortalException {

		_checkPermission(sourceClassName, sourceClassPK, ActionKeys.VIEW);

		return commerceQualifierEntryLocalService.
			getSourceCommerceQualifierEntries(
				companyId, sourceClassName, sourceClassPK,
				targetCommerceQualifierMetadataKey, keywords, start, end);
	}

	@Override
	public int getSourceCommerceQualifierEntriesCount(
			long companyId, String sourceClassName, long sourceClassPK,
			String targetCommerceQualifierMetadataKey, String keywords)
		throws PortalException {

		_checkPermission(sourceClassName, sourceClassPK, ActionKeys.VIEW);

		return commerceQualifierEntryLocalService.
			getSourceCommerceQualifierEntriesCount(
				companyId, sourceClassName, sourceClassPK,
				targetCommerceQualifierMetadataKey, keywords);
	}

	@Override
	public List<CommerceQualifierEntry> getTargetCommerceQualifierEntries(
			long companyId, String sourceCommerceQualifierMetadataKey,
			String targetClassName, long targetClassPK, String keywords,
			int start, int end)
		throws PortalException {

		_checkPermission(targetClassName, targetClassPK, ActionKeys.VIEW);

		return commerceQualifierEntryLocalService.
			getTargetCommerceQualifierEntries(
				companyId, sourceCommerceQualifierMetadataKey, targetClassName,
				targetClassPK, keywords, start, end);
	}

	@Override
	public int getTargetCommerceQualifierEntriesCount(
			long companyId, String sourceCommerceQualifierMetadataKey,
			String targetClassName, long targetClassPK, String keywords)
		throws PortalException {

		_checkPermission(targetClassName, targetClassPK, ActionKeys.VIEW);

		return commerceQualifierEntryLocalService.
			getTargetCommerceQualifierEntriesCount(
				companyId, sourceCommerceQualifierMetadataKey, targetClassName,
				targetClassPK, keywords);
	}

	private void _checkPermission(String className, long classPK, String action)
		throws PortalException {

		ModelResourcePermission<?> modelResourcePermission =
			_getModelResourcePermission(className);

		modelResourcePermission.check(getPermissionChecker(), classPK, action);
	}

	private ModelResourcePermission<?> _getModelResourcePermission(
			long classNameId)
		throws PortalException {

		ClassName className = classNameLocalService.getClassName(classNameId);

		return _getModelResourcePermission(className.getClassName());
	}

	private ModelResourcePermission<?> _getModelResourcePermission(
		String className) {

		CommerceQualifierMetadata commerceQualifierMetadata =
			_commerceQualifierMetadataRegistry.
				getCommerceQualifierMetadataByClassName(className);

		return commerceQualifierMetadata.getModelResourcePermission();
	}

	@Reference
	private CommerceQualifierMetadataRegistry
		_commerceQualifierMetadataRegistry;

}