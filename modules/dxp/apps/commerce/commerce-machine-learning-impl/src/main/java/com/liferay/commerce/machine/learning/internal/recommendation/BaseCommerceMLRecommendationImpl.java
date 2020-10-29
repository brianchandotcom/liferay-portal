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

package com.liferay.commerce.machine.learning.internal.recommendation;

import com.liferay.commerce.machine.learning.recommendation.CommerceMLRecommendation;

import java.util.Date;

/**
 * @author Riccardo Ferrari
 */
public class BaseCommerceMLRecommendationImpl
	implements CommerceMLRecommendation {

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public String getJobId() {
		return _jobId;
	}

	@Override
	public long getRecommendedEntryClassPK() {
		return _recommendedEntryClassPK;
	}

	@Override
	public float getScore() {
		return _score;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@Override
	public void setJobId(String jobId) {
		_jobId = jobId;
	}

	@Override
	public void setRecommendedEntryClassPK(long recommendedEntryClassPK) {
		_recommendedEntryClassPK = recommendedEntryClassPK;
	}

	@Override
	public void setScore(float score) {
		_score = score;
	}

	private long _companyId;
	private Date _createDate;
	private String _jobId;
	private long _recommendedEntryClassPK;
	private float _score;

}