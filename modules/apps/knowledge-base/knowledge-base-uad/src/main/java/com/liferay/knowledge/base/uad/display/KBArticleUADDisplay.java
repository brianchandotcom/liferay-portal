/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.uad.display;

import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.util.KnowledgeBaseUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.user.associated.data.display.UADDisplay;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Balázs Sáfrány-Kovalik
 */
@Component(service = UADDisplay.class)
public class KBArticleUADDisplay extends BaseKBArticleUADDisplay {

	@Override
	public String getEditURL(
			KBArticle kbArticle, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		if (kbArticle.isInTrash()) {
			return StringPool.BLANK;
		}

		return KnowledgeBaseUtil.getKBArticleEditURL(
			liferayPortletRequest, true,
			_portal.getCurrentURL(liferayPortletRequest),
			kbArticle.getResourcePrimKey());
	}

	@Reference
	private Portal _portal;

}