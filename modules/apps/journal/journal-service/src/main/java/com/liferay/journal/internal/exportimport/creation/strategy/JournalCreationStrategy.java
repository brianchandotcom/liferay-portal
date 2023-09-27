/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.exportimport.creation.strategy;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.journal.model.JournalArticle;

/**
 * An interface defining how newly created content should be added to the
 * Journal when imported from a LAR file. A class implementing this interface
 * should be specified in <i>portal.properties</i> under the
 * <b>journal.lar.creation.strategy</b> property.
 *
 * @author Joel Kozikowski
 */
public interface JournalCreationStrategy {

	/**
	 * Constant returned by getTransformedContent() to indicate that the article
	 * text should remained unchanged.
	 */
	public static final String ARTICLE_CONTENT_UNCHANGED = null;

	/**
	 * Constant returned by getAuthorUserId() that indicates the default portlet
	 * data import user ID strategy that should be used to determine the user
	 * ID.
	 */
	public static final long USE_DEFAULT_USER_ID_STRATEGY = 0;

	/**
	 * Returns <code>true</code> if the default group permissions should be
	 * added when the specified journalObject is created.
	 *
	 * @param      context the portlet data context
	 * @param      journalObject the journal object
	 * @return     <code>true</code> if default group permissions should be
	 *             added to the specified journalObject
	 * @throws     Exception if an exception occurred
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public boolean addGroupPermissions(
			PortletDataContext context, Object journalObject)
		throws Exception;

	/**
	 * Returns <code>true</code> if the default guest permissions should be
	 * added when the specified journalObject is created.
	 *
	 * @param      context the portlet data context
	 * @param      journalObject the journal object
	 * @return     <code>true</code> if default guest permissions should be
	 *             added to the specified journalObject
	 * @throws     Exception if an exception occurred
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public boolean addGuestPermissions(
			PortletDataContext context, Object journalObject)
		throws Exception;

	/**
	 * Returns the author's user ID to assign to newly created content. If zero
	 * is returned, the default user ID import strategy will determine the
	 * author ID.
	 *
	 * @param  context the portlet data context
	 * @param  journalObject the journal object
	 * @return the author's user ID or USE_DEFAULT_USER_ID_STRATEGY to use the
	 *         default user ID strategy
	 * @throws Exception if an exception occurred
	 */
	public long getAuthorUserId(
			PortletDataContext context, Object journalObject)
		throws Exception;

	/**
	 * Gives the content creation strategy an opportunity to transform the
	 * content before the new article is saved to the database. Possible use
	 * cases include using Velocity to merge in group specific values into the
	 * text. Returns the new content to assign to the article. If
	 * <code>null</code> is returned, the article content will be added
	 * unchanged.
	 *
	 * @param  context the portlet data context
	 * @param  newArticle the new journal article
	 * @return the transformed content to save in the database or
	 *         ARTICLE_CONTENT_UNCHANGED if the content should be added
	 *         unchanged
	 * @throws Exception if an exception occurred
	 */
	public String getTransformedContent(
			PortletDataContext context, JournalArticle newArticle)
		throws Exception;

}