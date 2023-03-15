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

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.fasterxml.jackson.core.type.TypeReference;

import com.liferay.osb.faro.engine.client.model.Interest;
import com.liferay.osb.faro.engine.client.model.PageVisited;
import com.liferay.osb.faro.engine.client.model.Rels;
import com.liferay.osb.faro.web.internal.util.JSONUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Shinn Lok
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class InterestDisplay {

	public InterestDisplay() {
	}

	@SuppressWarnings("unchecked")
	public InterestDisplay(Interest interest) {
		_name = interest.getName();
		_pagesViewCount = interest.getViews();

		List<PageVisited> pagesVisited = getPagesVisited(interest);

		_relatedPagesCount = pagesVisited.size();

		_score = interest.getScore();
	}

	protected List<PageVisited> getPagesVisited(Interest interest) {
		Map<String, Object> embeddedResources = interest.getEmbeddedResources();

		if (embeddedResources.isEmpty()) {
			return Collections.emptyList();
		}

		Object pagesVisited = embeddedResources.get(
			Rels.Interests.PAGES_VISITED);

		if (pagesVisited == null) {
			return Collections.emptyList();
		}

		return JSONUtil.convertValue(
			pagesVisited,
			new TypeReference<List<PageVisited>>() {
			});
	}

	private String _name;
	private int _pagesViewCount;
	private int _relatedPagesCount;
	private double _score;

}