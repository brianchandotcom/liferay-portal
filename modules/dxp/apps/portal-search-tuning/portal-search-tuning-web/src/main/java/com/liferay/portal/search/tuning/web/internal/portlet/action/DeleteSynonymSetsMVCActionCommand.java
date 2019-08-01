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

package com.liferay.portal.search.tuning.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.tuning.web.internal.constants.SearchTuningPortletKeys;
import com.liferay.portal.search.tuning.web.internal.synonym.SynonymIndexer;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Filipe Oshiro
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SearchTuningPortletKeys.SEARCH_TUNING,
		"mvc.command.name=deleteSynonymSet"
	},
	service = MVCActionCommand.class
)
public class DeleteSynonymSetsMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long companyId = portal.getCompanyId(actionRequest);

		String deletedSynonymSetsString = ParamUtil.getString(
			actionRequest, "deletedSynonymSetsString");

		String[] deletedSynonymSets = deletedSynonymSetsString.split(";");

		for (String filterName : _FILTER_NAMES) {
			String[] synonymSets = _synonymIndexer.getSynonymSets(
				companyId, filterName);

			for (String synonymToBeDeleted : deletedSynonymSets) {
				synonymSets = _removeSynonym(synonymSets, synonymToBeDeleted);
			}

			_synonymIndexer.updateSynonymSets(
				companyId, filterName, synonymSets);
		}

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		sendRedirect(actionRequest, actionResponse, redirect);
	}

	@Reference
	protected Portal portal;

	private String[] _removeSynonym(
		String[] synonymSets, String synonymToBeDeleted) {

		if (ArrayUtil.contains(synonymSets, synonymToBeDeleted, true)) {
			synonymSets = ArrayUtil.remove(synonymSets, synonymToBeDeleted);
		}

		return synonymSets;
	}

	private static final String[] _FILTER_NAMES = {
		"liferay_filter_synonym_en", "liferay_filter_synonym_es"
	};

	@Reference
	private SynonymIndexer _synonymIndexer;

}