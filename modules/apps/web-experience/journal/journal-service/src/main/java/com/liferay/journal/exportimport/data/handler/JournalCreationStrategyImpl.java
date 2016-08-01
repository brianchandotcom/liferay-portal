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

package com.liferay.journal.exportimport.data.handler;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.journal.model.JournalArticle;

/**
 * Provides the strategy for creating new content when new Journal content is
 * imported into a layout set from a LAR. The default strategy implemented by
 * this class is to return zero for the author and approval user IDs, which
 * causes the default user ID import strategy to be used. Content will be added
 * as is with no transformations.
 *
 * <p>
 * For a better understanding of this class, see
 * <code>com.liferay.journal.content.web.lar.JournalContentPortletDataHandler</code>
 * located in Liferay Portal's external <code>modules</code> directory.
 * </p>
 *
 * @author Joel Kozikowski
 */
public class JournalCreationStrategyImpl implements JournalCreationStrategy {

	@Override
	public boolean addGroupPermissions(
			PortletDataContext context, Object journalObj)
		throws Exception {

		return false;
	}

	@Override
	public boolean addGuestPermissions(
			PortletDataContext context, Object journalObj)
		throws Exception {

		return false;
	}

	@Override
	public long getAuthorUserId(PortletDataContext context, Object journalObj)
		throws Exception {

		return JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY;
	}

	@Override
	public String getTransformedContent(
			PortletDataContext context, JournalArticle newArticle)
		throws Exception {

		return JournalCreationStrategy.ARTICLE_CONTENT_UNCHANGED;
	}

}