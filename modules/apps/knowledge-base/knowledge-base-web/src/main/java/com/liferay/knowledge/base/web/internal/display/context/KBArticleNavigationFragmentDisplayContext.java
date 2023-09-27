/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.web.internal.display.context;

import com.liferay.friendly.url.info.item.provider.InfoItemFriendlyURLProvider;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalServiceUtil;
import com.liferay.knowledge.base.service.KBArticleServiceUtil;
import com.liferay.knowledge.base.util.comparator.KBArticlePriorityComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;
import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class KBArticleNavigationFragmentDisplayContext {

	public KBArticleNavigationFragmentDisplayContext(
		InfoItemFriendlyURLProvider<KBArticle> infoItemFriendlyURLProvider,
		KBArticle kbArticle, int maxNestingLevel) {

		_infoItemFriendlyURLProvider = infoItemFriendlyURLProvider;
		_kbArticle = kbArticle;
		_maxNestingLevel = maxNestingLevel;
	}

	public String getKBArticleCssClass(KBArticle kbArticle, int level)
		throws PortalException {

		if (isSelected(kbArticle)) {
			return "kb-article-selected";
		}

		if (_isKBArticleExpanded(level) && !_isMaxNestingLevelReached(level)) {
			return "kb-article-expanded";
		}

		return StringPool.BLANK;
	}

	public String getKBArticleFriendlyURL(KBArticle kbArticle) {
		String friendlyURL = _infoItemFriendlyURLProvider.getFriendlyURL(
			kbArticle, LanguageUtil.getLanguageId(LocaleUtil.getDefault()));

		return FriendlyURLResolverConstants.
			URL_SEPARATOR_KNOWLEDGE_BASE_ARTICLE + friendlyURL;
	}

	public long getKBArticleRootResourcePrimKey() {
		return _kbArticle.getRootResourcePrimKey();
	}

	public List<KBArticle> getKBArticles(long parentResourcePrimKey, int level)
		throws PortalException {

		if (level == 0) {
			return KBArticleServiceUtil.getKBArticles(
				_kbArticle.getGroupId(), _kbArticle.getKbFolderId(),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new KBArticlePriorityComparator(true));
		}

		if (_isMaxNestingLevelReached(level)) {
			return KBArticleServiceUtil.getAllDescendantKBArticles(
				_kbArticle.getGroupId(), parentResourcePrimKey,
				WorkflowConstants.STATUS_APPROVED,
				new KBArticlePriorityComparator(true));
		}

		return KBArticleServiceUtil.getKBArticles(
			_kbArticle.getGroupId(), parentResourcePrimKey,
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new KBArticlePriorityComparator(true));
	}

	public boolean isFurtherExpansionRequired(KBArticle kbArticle, int level)
		throws PortalException {

		List<Long> kbArticleAncestorResourcePrimaryKeys =
			_getKBArticleAncestorResourcePrimaryKeys();

		if (!_isMaxNestingLevelReached(level) &&
			kbArticleAncestorResourcePrimaryKeys.contains(
				kbArticle.getResourcePrimKey())) {

			return true;
		}

		return false;
	}

	public boolean isSelected(KBArticle kbArticle) {
		if (kbArticle.getResourcePrimKey() == _kbArticle.getResourcePrimKey()) {
			return true;
		}

		return false;
	}

	private List<Long> _getKBArticleAncestorResourcePrimaryKeys()
		throws PortalException {

		if (_kbArticleAncestorResourcePrimKeys != null) {
			return _kbArticleAncestorResourcePrimKeys;
		}

		KBArticle latestKBArticle =
			KBArticleLocalServiceUtil.getLatestKBArticle(
				_kbArticle.getResourcePrimKey(),
				WorkflowConstants.STATUS_APPROVED);

		List<Long> ancestorResourcePrimaryKeys =
			latestKBArticle.getAncestorResourcePrimaryKeys();

		Collections.reverse(ancestorResourcePrimaryKeys);

		_kbArticleAncestorResourcePrimKeys = ancestorResourcePrimaryKeys;

		return _kbArticleAncestorResourcePrimKeys;
	}

	private boolean _isKBArticleExpanded(int level) throws PortalException {
		List<Long> kbArticleAncestorResourcePrimaryKeys =
			_getKBArticleAncestorResourcePrimaryKeys();

		if ((kbArticleAncestorResourcePrimaryKeys.size() > 1) &&
			(level < kbArticleAncestorResourcePrimaryKeys.size())) {

			return true;
		}

		return false;
	}

	private boolean _isMaxNestingLevelReached(int level) {
		if ((_maxNestingLevel - level) < 1) {
			return true;
		}

		return false;
	}

	private final InfoItemFriendlyURLProvider<KBArticle>
		_infoItemFriendlyURLProvider;
	private final KBArticle _kbArticle;
	private List<Long> _kbArticleAncestorResourcePrimKeys;
	private final int _maxNestingLevel;

}