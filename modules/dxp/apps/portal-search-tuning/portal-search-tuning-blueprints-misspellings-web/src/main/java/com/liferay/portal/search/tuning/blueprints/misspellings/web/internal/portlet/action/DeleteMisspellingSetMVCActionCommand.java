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

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.tuning.blueprints.misspellings.index.MisspellingSet;
import com.liferay.portal.search.tuning.blueprints.misspellings.index.name.MisspellingsIndexName;
import com.liferay.portal.search.tuning.blueprints.misspellings.index.name.MisspellingsIndexNameBuilder;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.constants.MisspellingsMVCCommandNames;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.constants.MisspellingsPortletKeys;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.constants.MisspellingsWebKeys;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.index.MisspellingsIndexReader;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.index.MisspellingsIndexWriter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + MisspellingsPortletKeys.MISSPELLINGS,
		"mvc.command.name=" + MisspellingsMVCCommandNames.DELETE_MISSPELLING_SET
	},
	service = MVCActionCommand.class
)
public class DeleteMisspellingSetMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		MisspellingsIndexName misspellingsIndexName =
			_misspellingsIndexNameBuilder.getMisspellingsIndexName(
				_portal.getCompanyId(actionRequest));

		_deleteMisspellingSets(
			misspellingsIndexName,
			_getMisspellingSets(actionRequest, misspellingsIndexName));

		sendRedirect(actionRequest, actionResponse);
	}

	private void _deleteMisspellingSets(
		MisspellingsIndexName misspellingsIndexName,
		List<MisspellingSet> misspellingSets) {

		for (MisspellingSet misspellingSet : misspellingSets) {
			_misspellingsIndexWriter.remove(
				misspellingsIndexName, misspellingSet.getMisspellingSetId());
		}
	}

	private String[] _getMisspellingSetIds(ActionRequest actionRequest) {
		return ParamUtil.getStringValues(
			actionRequest, MisspellingsWebKeys.ROW_IDS);
	}

	private List<MisspellingSet> _getMisspellingSets(
		ActionRequest actionRequest,
		MisspellingsIndexName misspellingsIndexName) {

		return Stream.of(
			_getMisspellingSetIds(actionRequest)
		).map(
			id -> _misspellingsIndexReader.fetchMisspellingSetOptional(
				misspellingsIndexName, id)
		).filter(
			Optional::isPresent
		).map(
			Optional::get
		).collect(
			Collectors.toList()
		);
	}

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private MisspellingsIndexNameBuilder _misspellingsIndexNameBuilder;

	@Reference
	private MisspellingsIndexReader _misspellingsIndexReader;

	@Reference
	private MisspellingsIndexWriter _misspellingsIndexWriter;

	@Reference
	private Portal _portal;

}