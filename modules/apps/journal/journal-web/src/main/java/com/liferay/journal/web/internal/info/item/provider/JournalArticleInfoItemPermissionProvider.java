/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.info.item.provider;

import com.liferay.info.exception.InfoItemPermissionException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
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

		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier)) {
			return false;
		}

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)
				infoItemReference.getInfoItemIdentifier();

		return hasPermission(
			permissionChecker,
			_getArticle(
				classPKInfoItemIdentifier.getClassPK(),
				classPKInfoItemIdentifier.getVersion()),
			actionId);
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
		throws InfoItemPermissionException {

		try {
			if (Validator.isNull(version) ||
				Objects.equals(
					version, InfoItemIdentifier.VERSION_LATEST_APPROVED)) {

				return _journalArticleLocalService.getLatestArticle(classPK);
			}
			else if (Objects.equals(
						version, InfoItemIdentifier.VERSION_LATEST)) {

				JournalArticleResource articleResource =
					_journalArticleResourceLocalService.getArticleResource(
						classPK);

				return _journalArticleLocalService.getLatestArticle(
					articleResource.getGroupId(),
					articleResource.getArticleId(),
					WorkflowConstants.STATUS_ANY);
			}

			JournalArticleResource articleResource =
				_journalArticleResourceLocalService.getArticleResource(classPK);

			return _journalArticleLocalService.getArticle(
				articleResource.getGroupId(), articleResource.getArticleId(),
				GetterUtil.getDouble(version));
		}
		catch (PortalException portalException) {
			throw new InfoItemPermissionException(classPK, portalException);
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