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

package com.liferay.journal.web.social;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.journal.constants.JournalActivityKeys;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.web.util.JournalResourceBundleLoader;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionHelper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.social.kernel.model.BaseSocialActivityInterpreter;
import com.liferay.social.kernel.model.SocialActivity;
import com.liferay.social.kernel.model.SocialActivityConstants;
import com.liferay.social.kernel.model.SocialActivityInterpreter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Diaz
 * @author Zsolt Berentey
 */
@Component(
	property = "javax.portlet.name=" + JournalPortletKeys.JOURNAL,
	service = SocialActivityInterpreter.class
)
public class JournalArticleActivityInterpreter
	extends BaseSocialActivityInterpreter {

	@Override
	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected String getPath(
			SocialActivity activity, ServiceContext serviceContext)
		throws Exception {

		AssetRendererFactory journalArticleAssetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(
				JournalArticle.class);

		AssetRenderer journalArticleAssetRenderer =
			journalArticleAssetRendererFactory.getAssetRenderer(
				activity.getClassPK());

		LiferayPortletRequest liferayPortletRequest =
			serviceContext.getLiferayPortletRequest();

		LiferayPortletResponse liferayPortletResponse =
			serviceContext.getLiferayPortletResponse();

		if ((liferayPortletRequest != null) &&
			(liferayPortletResponse != null)) {

			return journalArticleAssetRenderer.getURLViewInContext(
				serviceContext.getLiferayPortletRequest(),
				serviceContext.getLiferayPortletResponse(), null);
		}

		JournalArticle article = _journalArticleLocalService.getLatestArticle(
			activity.getClassPK());

		Layout layout = article.getLayout();

		if (layout != null) {
			String groupFriendlyURL = _portal.getGroupFriendlyURL(
				layout.getLayoutSet(), serviceContext.getThemeDisplay());

			return groupFriendlyURL.concat(
				JournalArticleConstants.CANONICAL_URL_SEPARATOR).concat(
				article.getUrlTitle());
		}

		return null;
	}

	@Override
	protected ResourceBundleLoader getResourceBundleLoader() {
		return JournalResourceBundleLoader.INSTANCE;
	}

	@Override
	protected String getTitlePattern(
		String groupName, SocialActivity activity) {

		int activityType = activity.getType();

		if (activityType == JournalActivityKeys.ADD_ARTICLE) {
			if (Validator.isNull(groupName)) {
				return "activity-journal-article-add-web-content";
			}

			return "activity-journal-article-add-web-content-in";
		}
		else if (activityType == JournalActivityKeys.UPDATE_ARTICLE) {
			if (Validator.isNull(groupName)) {
				return "activity-journal-article-update-web-content";
			}

			return "activity-journal-article-update-web-content-in";
		}
		else if (activityType == SocialActivityConstants.TYPE_MOVE_TO_TRASH) {
			if (Validator.isNull(groupName)) {
				return "activity-journal-article-move-to-trash";
			}

			return "activity-journal-article-move-to-trash-in";
		}
		else if (activityType ==
					 SocialActivityConstants.TYPE_RESTORE_FROM_TRASH) {

			if (Validator.isNull(groupName)) {
				return "activity-journal-article-restore-from-trash";
			}

			return "activity-journal-article-restore-from-trash-in";
		}

		return null;
	}

	@Override
	protected boolean hasPermissions(
			PermissionChecker permissionChecker, SocialActivity activity,
			String actionId, ServiceContext serviceContext)
		throws Exception {

		int activityType = activity.getType();

		if (activityType == JournalActivityKeys.ADD_ARTICLE) {
			JournalArticle article =
				_journalArticleLocalService.getLatestArticle(
					activity.getClassPK());

			return ModelResourcePermissionHelper.contains(
				_journalFolderModelResourcePermission, permissionChecker,
				article.getGroupId(), article.getFolderId(),
				ActionKeys.ADD_ARTICLE);
		}
		else if (activityType == JournalActivityKeys.UPDATE_ARTICLE) {
			return _journalArticleModelResourcePermission.contains(
				permissionChecker, activity.getClassPK(), ActionKeys.UPDATE);
		}

		return _journalArticleModelResourcePermission.contains(
			permissionChecker, activity.getClassPK(), actionId);
	}

	@Reference(unbind = "-")
	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	private static final String[] _CLASS_NAMES =
		{JournalArticle.class.getName()};

	private JournalArticleLocalService _journalArticleLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.journal.model.JournalArticle)"
	)
	private ModelResourcePermission<JournalArticle>
		_journalArticleModelResourcePermission;

	@Reference(
		target = "(model.class.name=com.liferay.journal.model.JournalFolder)"
	)
	private ModelResourcePermission<JournalFolder>
		_journalFolderModelResourcePermission;

	@Reference
	private Portal _portal;

}