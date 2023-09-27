/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.social.model.impl;

import com.liferay.social.kernel.model.SocialActivityCounterConstants;
import com.liferay.social.kernel.util.SocialCounterPeriodUtil;

/**
 * @author Zsolt Berentey
 */
public class SocialActivityCounterImpl extends SocialActivityCounterBaseImpl {

	@Override
	public boolean isActivePeriod(int periodLength) {
		if ((periodLength ==
				SocialActivityCounterConstants.PERIOD_LENGTH_INFINITE) ||
			((periodLength !=
				SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM) &&
			 ((getStartPeriod() + periodLength) >
				 SocialCounterPeriodUtil.getActivityDay()))) {

			return true;
		}

		if ((getStartPeriod() == SocialCounterPeriodUtil.getStartPeriod()) &&
			((getEndPeriod() == -1) ||
			 (getEndPeriod() == SocialCounterPeriodUtil.getEndPeriod()))) {

			return true;
		}

		return false;
	}

}