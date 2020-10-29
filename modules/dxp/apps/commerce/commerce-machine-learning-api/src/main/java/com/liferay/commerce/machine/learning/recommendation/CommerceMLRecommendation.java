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

package com.liferay.commerce.machine.learning.recommendation;

import java.util.Date;

/**
 * @author Riccardo Ferrari
 */
public interface CommerceMLRecommendation {

	public long getCompanyId();

	public Date getCreateDate();

	public String getJobId();

	public long getRecommendedEntryClassPK();

	public float getScore();

	public void setCompanyId(long companyId);

	public void setCreateDate(Date createDate);

	public void setJobId(String jobId);

	public void setRecommendedEntryClassPK(long recommendedEntryClassPK);

	public void setScore(float score);

}