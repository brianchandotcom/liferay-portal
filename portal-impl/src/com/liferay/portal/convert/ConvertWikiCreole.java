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

package com.liferay.portal.convert;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.MaintenanceUtil;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.translators.ClassicToCreoleTranslator;

import java.util.List;

/**
 * @author Jorge Ferrer
 */
public class ConvertWikiCreole extends BaseConvertProcess {

	@Override
	public String getDescription() {
		return "convert-wiki-pages-from-classic-wiki-to-creole-format";
	}

	@Override
	public boolean isEnabled() {
		boolean enabled = false;

		try {
			int pagesCount = WikiPageLocalServiceUtil.getPagesCount(
				"classic_wiki");

			if (pagesCount > 0) {
				enabled = true;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return enabled;
	}

	@Override
	protected void doConvert() throws Exception {
		List<WikiPage> pages = WikiPageLocalServiceUtil.getPages(
			"classic_wiki");

		ClassicToCreoleTranslator translator = new ClassicToCreoleTranslator();

		MaintenanceUtil.appendStatus(
			"Converting " + pages.size() +
				" Wiki pages from Classic Wiki to Creole format");

		for (int i = 0; i < pages.size(); i++) {
			if ((i > 0) && (i % (pages.size() / 4) == 0)) {
				MaintenanceUtil.appendStatus((i * 100. / pages.size()) + "%");
			}

			WikiPage page = pages.get(i);

			page.setFormat("creole");

			page.setContent(translator.translate(page.getContent()));

			WikiPageLocalServiceUtil.updateWikiPage(page);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ConvertWikiCreole.class);

}