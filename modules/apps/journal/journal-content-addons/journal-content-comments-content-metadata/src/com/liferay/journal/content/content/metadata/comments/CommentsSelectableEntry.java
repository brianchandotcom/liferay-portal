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

package com.liferay.journal.content.content.metadata.comments;

import com.liferay.journal.content.web.util.ContentMetadataEntry;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.BaseJSPSelectableEntry;
import com.liferay.portal.model.SelectableEntry;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julio Camarero
 */
@Component(
	immediate = true, service = ContentMetadataEntry.class
)
public class CommentsSelectableEntry
	extends BaseJSPSelectableEntry implements ContentMetadataEntry {

	@Override
	public String getIcon() {
		return "comments";
	}

	@Override
	public String getJSPPath() {
		return _JSP_PATH;
	}

	@Override
	public String getKey() {
		return "enableComments";
	}

	@Override
	public String getLabel() {
		return "comments";
	}

	@Override
	public Double getWeight() {
		return 3.0;
	}

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		request.setAttribute(WebKeys.SELECTABLE_ENTRY, this);

		super.include(request, response);
	}

	public boolean isCommentsRatingsSelected(HttpServletRequest request) {
		if (_commentRatingsSelectableEntry != null) {
			List<SelectableEntry> selectedEntries =
				(List<SelectableEntry>)request.getAttribute(
					"liferay-ui:entry-selector:selectedEntries");

			if (selectedEntries.contains(_commentRatingsSelectableEntry)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isEnabled() {
		if (!PropsValues.JOURNAL_ARTICLE_COMMENTS_ENABLED) {
			return false;
		}

		return super.isEnabled();
	}

	@Reference
	public void setCommentRatingsSelectableEntry(
		CommentRatingsSelectableEntry commentRatingsSelectableEntry) {

		_commentRatingsSelectableEntry = commentRatingsSelectableEntry;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.journal.content.content.metadata.comments)"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	private static final String _JSP_PATH = "/META-INF/resources/comments.jsp";

	private CommentRatingsSelectableEntry _commentRatingsSelectableEntry;

}