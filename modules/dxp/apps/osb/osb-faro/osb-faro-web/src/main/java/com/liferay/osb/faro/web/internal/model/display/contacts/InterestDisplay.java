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

import com.liferay.osb.faro.engine.client.model.Interest;

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
		_relatedPagesCount = interest.getRelatedPagesCount();
		_score = interest.getScore();
	}

	private String _name;
	private int _pagesViewCount;
	private int _relatedPagesCount;
	private double _score;

}