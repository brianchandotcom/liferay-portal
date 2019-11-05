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

package com.liferay.journal.web.internal.asset.util;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.web.internal.security.permission.resource.JournalArticlePermission;
import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.layout.util.LayoutClassedModelUsageActionMenuContributor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.journal.model.JournalArticle",
	service = LayoutClassedModelUsageActionMenuContributor.class
)
public class JournalArticleLayoutClassedModelUsageActionMenuContributor
	implements LayoutClassedModelUsageActionMenuContributor {

	@Override
	public List<DropdownItem> getLayoutClassedModelUsageActionDropdownItems(
		HttpServletRequest httpServletRequest,
		LayoutClassedModelUsage layoutClassedModelUsage) {

		JournalArticle article = _journalArticleLocalService.fetchLatestArticle(
			layoutClassedModelUsage.getClassPK(), WorkflowConstants.STATUS_ANY,
			false);

		return new DropdownItemList() {
			{
				JournalArticle approvedArticle =
					_journalArticleLocalService.fetchLatestArticle(
						layoutClassedModelUsage.getClassPK(),
						WorkflowConstants.STATUS_APPROVED);

				ThemeDisplay themeDisplay =
					(ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				ResourceBundle resourceBundle =
					_resourceBundleLoader.loadResourceBundle(
						themeDisplay.getLocale());

				if (approvedArticle != null) {
					add(
						dropdownItem -> {
							dropdownItem.setHref(
								_getURL(
									layoutClassedModelUsage,
									AssetRendererFactory.TYPE_LATEST_APPROVED,
									httpServletRequest));
							dropdownItem.setLabel(
								LanguageUtil.get(
									resourceBundle, "view-in-page"));
						});
				}

				if (article.isDraft() || article.isPending() ||
					article.isScheduled()) {

					try {
						if (JournalArticlePermission.contains(
								themeDisplay.getPermissionChecker(), article,
								ActionKeys.UPDATE)) {

							String key = "preview-draft-in-page";

							if (article.isPending()) {
								key = "preview-pending-in-page";
							}
							else if (article.isScheduled()) {
								key = "preview-scheduled-in-page";
							}

							String label = LanguageUtil.get(
								resourceBundle, key);

							add(
								dropdownItem -> {
									dropdownItem.setHref(
										_getURL(
											layoutClassedModelUsage,
											AssetRendererFactory.TYPE_LATEST,
											httpServletRequest));
									dropdownItem.setLabel(label);
								});
						}
					}
					catch (PortalException pe) {
						_log.error("Unable to check article permission", pe);
					}
				}
			}
		};
	}

	private String _getURL(
			LayoutClassedModelUsage layoutClassedModelUsage,
			int previewInfoItemType, HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String layoutURL = null;

		if (layoutClassedModelUsage.getContainerType() ==
				_portal.getClassNameId(FragmentEntryLink.class)) {

			Layout layout = _layoutLocalService.fetchLayout(
				layoutClassedModelUsage.getPlid());

			layoutURL = _portal.getLayoutFriendlyURL(layout, themeDisplay);

			layoutURL = _http.setParameter(
				layoutURL, "previewInfoItemClassNameId",
				String.valueOf(layoutClassedModelUsage.getClassNameId()));
			layoutURL = _http.setParameter(
				layoutURL, "previewInfoItemClassPK",
				String.valueOf(layoutClassedModelUsage.getClassPK()));
			layoutURL = _http.setParameter(
				layoutURL, "previewInfoItemType",
				String.valueOf(previewInfoItemType));
		}
		else {
			PortletURL portletURL = PortletURLFactoryUtil.create(
				httpServletRequest, layoutClassedModelUsage.getContainerKey(),
				layoutClassedModelUsage.getPlid(), PortletRequest.RENDER_PHASE);

			portletURL.setParameter(
				"previewInfoItemClassNameId",
				String.valueOf(layoutClassedModelUsage.getClassNameId()));
			portletURL.setParameter(
				"previewInfoItemClassPK",
				String.valueOf(layoutClassedModelUsage.getClassPK()));
			portletURL.setParameter(
				"previewInfoItemType", String.valueOf(previewInfoItemType));

			layoutURL = portletURL.toString();
		}

		String portletURLString = _http.setParameter(
			layoutURL, "p_l_back_url", themeDisplay.getURLCurrent());

		return portletURLString + "#portlet_" +
			layoutClassedModelUsage.getContainerKey();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleLayoutClassedModelUsageActionMenuContributor.class);

	@Reference
	private Http _http;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.journal.web)"
	)
	private volatile ResourceBundleLoader _resourceBundleLoader;

}