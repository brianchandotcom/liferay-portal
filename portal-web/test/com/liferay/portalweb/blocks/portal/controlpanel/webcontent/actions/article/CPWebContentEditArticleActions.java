/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.blocks.portal.controlpanel.webcontent.actions.article;

import com.liferay.portalweb.blocks.base.actions.BaseActionsImpl;
import com.liferay.portalweb.blocks.base.actions.LiferayActions;
import com.liferay.portalweb.blocks.base.units.AssertTextEqualsUnits;
import com.liferay.portalweb.blocks.portal.controlpanel.webcontent.paths.article.CPWebContentEditArticlePaths;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class CPWebContentEditArticleActions extends BaseActionsImpl
	implements LiferayActions {
	public CPWebContentEditArticleActions(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
		paths = CPWebContentEditArticlePaths.getPaths();
	}

	public void assertTextEquals(String param1, String param2)
		throws Exception {
		String[] params = getParams(param1, param2);

		AssertTextEqualsUnits assertTextEqualsUnits = new AssertTextEqualsUnits(selenium);

		if ((param1.equals("ARTICLE_CONTENT"))) {
			assertTextEqualsUnits.assertTextCKEditorWebContent(params[0],
				params[1]);
		}
		else if ((param1.equals("ARTICLE_TITLE"))) {
			assertTextEqualsUnits.assertValue(params[0], params[1]);
		}
		else {
			super.assertTextEquals(params[0], params[1]);
		}
	}
}