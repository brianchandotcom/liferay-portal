/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
		if (periodLength ==
				SocialActivityCounterConstants.PERIOD_LENGTH_INFINITE) {

			return true;
		}

		if ((periodLength !=
				SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM) &&
			((getStartPeriod() + periodLength) >
				SocialCounterPeriodUtil.getActivityDay())) {

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