/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.portlet.action;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexNameBuilder;
import com.liferay.portal.search.tuning.synonyms.web.internal.constants.SynonymsPortletKeys;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSet;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSetIndexReader;
import com.liferay.portal.search.tuning.synonyms.web.internal.storage.SynonymSetStorageAdapter;
import com.liferay.portal.search.tuning.synonyms.web.internal.synchronizer.IndexToFilterSynchronizer;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Filipe Oshiro
 */
@Component(
	property = {
		"javax.portlet.name=" + SynonymsPortletKeys.SYNONYMS,
		"mvc.command.name=/synonyms/delete_synonym_sets"
	},
	service = MVCActionCommand.class
)
public class DeleteSynonymSetsMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long companyId = _portal.getCompanyId(actionRequest);

		SynonymSetIndexName synonymSetIndexName =
			_synonymSetIndexNameBuilder.getSynonymSetIndexName(companyId);

		removeSynonymSets(
			synonymSetIndexName,
			getDeletedSynonymSets(actionRequest, synonymSetIndexName));

		_indexToFilterSynchronizer.copyToFilter(
			synonymSetIndexName, _indexNameBuilder.getIndexName(companyId),
			true);

		sendRedirect(actionRequest, actionResponse);
	}

	protected List<SynonymSet> getDeletedSynonymSets(
		ActionRequest actionRequest, SynonymSetIndexName synonymSetIndexName) {

		return TransformUtil.transformToList(
			ParamUtil.getStringValues(actionRequest, "rowIds"),
			id -> _synonymSetIndexReader.fetch(synonymSetIndexName, id));
	}

	protected void removeSynonymSets(
			SynonymSetIndexName synonymSetIndexName,
			List<SynonymSet> synonymSets)
		throws PortalException {

		for (SynonymSet synonymSet : synonymSets) {
			_synonymSetStorageAdapter.delete(
				synonymSetIndexName, synonymSet.getSynonymSetDocumentId());
		}
	}

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private IndexToFilterSynchronizer _indexToFilterSynchronizer;

	@Reference
	private Portal _portal;

	@Reference
	private SynonymSetIndexNameBuilder _synonymSetIndexNameBuilder;

	@Reference
	private SynonymSetIndexReader _synonymSetIndexReader;

	@Reference
	private SynonymSetStorageAdapter _synonymSetStorageAdapter;

}