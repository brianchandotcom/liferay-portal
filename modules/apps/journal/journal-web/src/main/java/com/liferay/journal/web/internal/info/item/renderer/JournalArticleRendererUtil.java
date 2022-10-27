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

package com.liferay.journal.web.internal.info.item.renderer;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalArticleRendererUtil {

	public static boolean isShowArticle(
		HttpServletRequest httpServletRequest, JournalArticle article) {

		HttpServletRequest originalHttpServletRequest =
			PortalUtil.getOriginalServletRequest(httpServletRequest);

		String mode = ParamUtil.getString(
			PortalUtil.getOriginalServletRequest(originalHttpServletRequest),
			"p_l_mode", Constants.VIEW);

		if (Objects.equals(Constants.EDIT, mode) ||
			Objects.equals(Constants.PREVIEW, mode)) {

			return true;
		}

		if ((article == null) || article.isExpired()) {
			return false;
		}

		return true;
	}

}