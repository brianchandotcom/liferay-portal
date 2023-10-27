/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.machine.learning.content;

import java.util.Date;

/**
 * @author Riccardo Ferrari
 */
public interface UserContentRecommendation {

	public long[] getAssetCategoryIds();

	public long getCompanyId();

	public Date getCreateDate();

	public long getEntryClassPK();

	public String getJobId();

	public long getRecommendedEntryClassPK();

	public float getScore();

	public void setAssetCategoryIds(long[] assetCategoryIds);

	public void setCompanyId(long companyId);

	public void setCreateDate(Date createDate);

	public void setEntryClassPK(long entryClassPK);

	public void setJobId(String jobId);

	public void setRecommendedEntryClassPK(long recommendedEntryClassPK);

	public void setScore(float score);

}