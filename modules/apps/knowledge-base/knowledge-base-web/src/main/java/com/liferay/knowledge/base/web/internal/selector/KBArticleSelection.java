/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.web.internal.selector;

import com.liferay.knowledge.base.model.KBArticle;

/**
 * @author Adolfo Pérez
 */
public class KBArticleSelection {

	public KBArticleSelection(KBArticle kbArticle, boolean exactMatch) {
		_kbArticle = kbArticle;
		_exactMatch = exactMatch;

		_keywords = null;
	}

	public KBArticleSelection(KBArticle kbArticle, String[] keywords) {
		_kbArticle = kbArticle;
		_keywords = keywords;

		_exactMatch = false;
	}

	public KBArticle getKBArticle() {
		return _kbArticle;
	}

	public String[] getKeywords() {
		return _keywords;
	}

	public boolean isExactMatch() {
		return _exactMatch;
	}

	protected void setExactMatch(boolean exactMatch) {
		_exactMatch = exactMatch;
	}

	private boolean _exactMatch;
	private final KBArticle _kbArticle;
	private final String[] _keywords;

}