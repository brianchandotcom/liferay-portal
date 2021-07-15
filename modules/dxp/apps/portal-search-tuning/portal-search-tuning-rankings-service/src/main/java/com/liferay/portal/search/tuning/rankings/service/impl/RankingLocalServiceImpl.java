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

package com.liferay.portal.search.tuning.rankings.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.model.uid.UIDFactory;
import com.liferay.portal.search.tuning.rankings.model.Ranking;
import com.liferay.portal.search.tuning.rankings.service.base.RankingLocalServiceBaseImpl;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the ranking local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.portal.search.tuning.rankings.service.RankingLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Bryan Engler
 * @see RankingLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.liferay.portal.search.tuning.rankings.model.Ranking",
	service = AopService.class
)
public class RankingLocalServiceImpl extends RankingLocalServiceBaseImpl {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Use <code>com.liferay.portal.search.tuning.rankings.service.RankingLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.portal.search.tuning.rankings.service.RankingLocalServiceUtil</code>.
	 */
	@Override
	public Ranking addRanking(
		List<String> aliases, List<String> hiddenDocumentIds, boolean inactive,
		String indexName, String name, Map<Integer, String> documentIdsMap,
		String queryString) {

		Ranking ranking = rankingPersistence.create(
			counterLocalService.increment());

		JSONObject jsonObject = JSONUtil.put(
			"indexName", indexName
		).put(
			"queryString", queryString
		).put(
			"rankingDocumentId", uidFactory.getUID(ranking)
		);

		ranking.setJson(jsonObject.toString());

		return updateRanking(
			ranking, aliases, hiddenDocumentIds, inactive, name,
			documentIdsMap);
	}

	@Override
	public Ranking addRanking(
		String indexName, String name, String queryString) {

		Ranking ranking = rankingPersistence.create(
			counterLocalService.increment());

		JSONObject jsonObject = JSONUtil.put(
			"indexName", indexName
		).put(
			"name", name
		).put(
			"queryString", queryString
		).put(
			"rankingDocumentId", uidFactory.getUID(ranking)
		);

		ranking.setJson(jsonObject.toString());

		return rankingPersistence.update(ranking);
	}

	@Override
	public List<Ranking> getRankingsByCompanyId(long companyId) {
		return rankingPersistence.findBycompanyId(companyId);
	}

	@Override
	public Ranking updateRanking(
			long rankingId, List<String> aliases,
			List<String> hiddenDocumentIds, boolean inactive, String name,
			Map<Integer, String> documentIdsMap)
		throws PortalException {

		return updateRanking(
			fetchRanking(rankingId), aliases, hiddenDocumentIds, inactive, name,
			documentIdsMap);
	}

	protected Ranking updateRanking(
		Ranking ranking, List<String> aliases, List<String> hiddenDocumentIds,
		boolean inactive, String name, Map<Integer, String> documentIdsMap) {

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				ranking.getJson());

			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

			for (Map.Entry<Integer, String> entry : documentIdsMap.entrySet()) {
				int position = entry.getKey();
				String documentId = entry.getValue();

				jsonArray.put(
					JSONUtil.put(
						"documentId", documentId
					).put(
						"position", position
					));
			}

			jsonObject.put(
				"aliases", JSONFactoryUtil.createJSONArray(aliases)
			).put(
				"hiddenDocumentIds",
				JSONFactoryUtil.createJSONArray(hiddenDocumentIds)
			).put(
				"inactive", inactive
			).put(
				"name", name
			).put(
				"pins", jsonArray
			);

			ranking.setJson(jsonObject.toString());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to set json for Ranking with ID: " +
						ranking.getRankingId());
			}
		}

		return rankingPersistence.update(ranking);
	}

	@Reference
	protected UIDFactory uidFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		RankingLocalServiceImpl.class);

}