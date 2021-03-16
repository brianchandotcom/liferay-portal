/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.portlet.action;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.name.KeywordIndexNameBuilder;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.constants.KeywordIndexMVCCommandNames;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.constants.KeywordIndexPortletKeys;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.constants.KeywordIndexWebKeys;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.display.context.ViewKeywordEntryDisplayBuilder;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.index.KeywordIndexReader;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + KeywordIndexPortletKeys.KEYWORD_INDEX_ADMIN,
		"mvc.command.name=" + KeywordIndexMVCCommandNames.VIEW_KEYWORD_ENTRY
	},
	service = MVCRenderCommand.class
)
public class ViewKeywordEntryMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		ViewKeywordEntryDisplayBuilder viewKeywordEntryDisplayBuilder =
			new ViewKeywordEntryDisplayBuilder(
				_portal.getHttpServletRequest(renderRequest), _language,
				_portal, renderRequest, renderResponse,
				_keywordIndexNameBuilder, _keywordIndexReader);

		renderRequest.setAttribute(
			KeywordIndexWebKeys.KEYWORD_ENTRY_DISPLAY_CONTEXT,
			viewKeywordEntryDisplayBuilder.build());

		return "/view_entry.jsp";
	}

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private KeywordIndexNameBuilder _keywordIndexNameBuilder;

	@Reference
	private KeywordIndexReader _keywordIndexReader;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}