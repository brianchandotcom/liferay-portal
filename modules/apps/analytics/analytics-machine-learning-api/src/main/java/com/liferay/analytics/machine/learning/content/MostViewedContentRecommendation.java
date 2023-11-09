/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.machine.learning.content;

import java.util.Date;

/**
 * @author Riccardo Ferrari
 */
public class MostViewedContentRecommendation {

	public long[] getAssetCategoryIds() {
		return _assetCategoryIds;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public String getJobId() {
		return _jobId;
	}

	public long getRecommendedEntryClassPK() {
		return _recommendedEntryClassPK;
	}

	public float getScore() {
		return _score;
	}

	public void setAssetCategoryIds(long[] assetCategoryIds) {
		_assetCategoryIds = assetCategoryIds;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public void setJobId(String jobId) {
		_jobId = jobId;
	}

	public void setRecommendedEntryClassPK(long recommendedEntryClassPK) {
		_recommendedEntryClassPK = recommendedEntryClassPK;
	}

	public void setScore(float score) {
		_score = score;
	}

	private long[] _assetCategoryIds;
	private long _companyId;
	private Date _createDate;
	private String _jobId;
	private long _recommendedEntryClassPK;
	private float _score;

}