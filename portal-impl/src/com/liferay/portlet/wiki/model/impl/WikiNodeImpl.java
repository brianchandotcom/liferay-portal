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

package com.liferay.portlet.wiki.model.impl;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class WikiNodeImpl extends WikiNodeBaseImpl {

	public WikiNodeImpl() {
	}

	public List<DLFileEntry> getDeletedAttachmentsFiles()
		throws PortalException, SystemException {

		List<WikiPage> wikiPages = WikiPageLocalServiceUtil.getPages(
			getNodeId(), true, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		List<DLFileEntry> deletedAttachments = new ArrayList<DLFileEntry>();

		for (WikiPage wikiPage : wikiPages) {
			deletedAttachments.addAll(wikiPage.getDeletedAttachmentsFiles());
		}

		return deletedAttachments;
	}

}