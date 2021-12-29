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

package com.liferay.journal.web.internal.info.item.provider;

import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemPermissionProvider;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(service = InfoItemPermissionProvider.class)
public class JournalArticleInfoItemPermissionProvider
	implements InfoItemPermissionProvider<JournalArticle> {

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker,
			InfoItemReference infoItemReference, String actionId)
		throws InfoItemPermissionException {

		return _hasPermission(
			permissionChecker, infoItemReference.getClassPK(), actionId);
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, JournalArticle journalArticle,
			String actionId)
		throws InfoItemPermissionException {

		return _hasPermission(
			permissionChecker, journalArticle.getId(), actionId);
	}

	private JournalArticle _getArticle(long classPK, String version)
		throws PortalException {

		if (Validator.isNull(version) ||
			Objects.equals(
				version, InfoItemIdentifier.VERSION_LATEST_APPROVED)) {

			return _journalArticleLocalService.fetchLatestArticle(classPK);
		}
		else if (Objects.equals(version, InfoItemIdentifier.VERSION_LATEST)) {
			JournalArticleResource articleResource =
				_journalArticleResourceLocalService.getArticleResource(classPK);

			return _journalArticleLocalService.fetchLatestArticle(
				articleResource.getGroupId(), articleResource.getArticleId(),
				WorkflowConstants.STATUS_ANY);
		}
		else {
			JournalArticleResource articleResource =
				_journalArticleResourceLocalService.getArticleResource(classPK);

			return _journalArticleLocalService.getArticle(
				articleResource.getGroupId(), articleResource.getArticleId(),
				GetterUtil.getDouble(version));
		}
	}

	private boolean _hasPermission(
			PermissionChecker permissionChecker, long id, String actionId)
		throws InfoItemPermissionException {

		try {
			return _journalArticleModelResourcePermission.contains(
				permissionChecker, id, actionId);
		}
		catch (PortalException portalException) {
			throw new InfoItemPermissionException(id, portalException);
		}
	}

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.journal.model.JournalArticle)"
	)
	private ModelResourcePermission<JournalArticle>
		_journalArticleModelResourcePermission;

	@Reference
	private JournalArticleResourceLocalService
		_journalArticleResourceLocalService;

}