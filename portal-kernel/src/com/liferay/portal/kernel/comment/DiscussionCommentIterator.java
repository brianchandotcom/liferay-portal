/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.comment;

import java.util.Iterator;

/**
 * @author Adolfo Pérez
 */
public interface DiscussionCommentIterator extends Iterator<DiscussionComment> {

	public int getIndexPage();

}