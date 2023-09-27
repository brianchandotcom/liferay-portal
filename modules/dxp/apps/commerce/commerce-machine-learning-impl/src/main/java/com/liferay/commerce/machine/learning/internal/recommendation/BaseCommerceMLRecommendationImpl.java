/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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