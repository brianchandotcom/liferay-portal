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

/**
 * @author Riccardo Ferrari
 */
public interface ProductInteractionCommerceMLRecommendation
	extends CommerceMLRecommendation {

	public long getEntryClassPK();

	public int getRank();

	public void setEntryClassPK(long entryClassPK);

	public void setRank(int rank);

}