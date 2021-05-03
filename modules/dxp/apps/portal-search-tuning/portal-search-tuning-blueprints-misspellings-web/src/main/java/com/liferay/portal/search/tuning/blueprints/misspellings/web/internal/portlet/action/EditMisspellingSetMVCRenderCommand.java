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

package com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.portlet.action;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.tuning.blueprints.misspellings.index.name.MisspellingsIndexNameBuilder;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.constants.MisspellingsMVCCommandNames;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.constants.MisspellingsPortletKeys;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.constants.MisspellingsWebKeys;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.display.context.EditMisspellingSetDisplayBuilder;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.index.MisspellingsIndexReader;

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
		"javax.portlet.name=" + MisspellingsPortletKeys.MISSPELLINGS,
		"mvc.command.name=" + MisspellingsMVCCommandNames.EDIT_MISSPELLING_SET
	},
	service = MVCRenderCommand.class
)
public class EditMisspellingSetMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		EditMisspellingSetDisplayBuilder editMisspellingSetDisplayBuilder =
			new EditMisspellingSetDisplayBuilder(
				_portal.getHttpServletRequest(renderRequest), _language,
				_portal, renderRequest, renderResponse,
				_misspellingsIndexNameBuilder, _misspellingsIndexReader);

		renderRequest.setAttribute(
			MisspellingsWebKeys.EDIT_MISSPELLING_SET_DISPLAY_CONTEXT,
			editMisspellingSetDisplayBuilder.build());

		return "/edit_misspelling_set.jsp";
	}

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private Language _language;

	@Reference
	private MisspellingsIndexNameBuilder _misspellingsIndexNameBuilder;

	@Reference
	private MisspellingsIndexReader _misspellingsIndexReader;

	@Reference
	private Portal _portal;

}