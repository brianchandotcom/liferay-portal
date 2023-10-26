/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.machine.learning.content;

/**
 * @author Riccardo Ferrari
 */
public interface UserContentRecommendation extends Recommendation {

	public long getEntryClassPK();

	public void setEntryClassPK(long entryClassPK);

}