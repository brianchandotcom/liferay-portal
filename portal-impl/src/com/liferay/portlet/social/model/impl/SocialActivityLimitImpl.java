/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.social.model.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.social.kernel.model.SocialActivityCounterDefinition;
import com.liferay.social.kernel.util.SocialCounterPeriodUtil;

/**
 * @author Zsolt Berentey
 */
public class SocialActivityLimitImpl extends SocialActivityLimitBaseImpl {

	@Override
	public int getCount() {
		String value = getValue();

		if (!value.contains(StringPool.SLASH)) {
			return getCount(
				SocialActivityCounterDefinition.LIMIT_PERIOD_LIFETIME);
		}

		String[] valueParts = StringUtil.split(value, StringPool.SLASH);

		if (valueParts[0].contains(StringPool.DASH)) {
			return getCount(
				SocialActivityCounterDefinition.LIMIT_PERIOD_PERIOD);
		}

		return getCount(SocialActivityCounterDefinition.LIMIT_PERIOD_DAY);
	}

	@Override
	public int getCount(int limitPeriod) {
		String[] valueParts = StringUtil.split(getValue(), StringPool.SLASH);

		if ((limitPeriod !=
				SocialActivityCounterDefinition.LIMIT_PERIOD_LIFETIME) &&
			(valueParts.length < 2)) {

			return 0;
		}

		int count = GetterUtil.getInteger(valueParts[valueParts.length - 1]);

		if (limitPeriod == SocialActivityCounterDefinition.LIMIT_PERIOD_DAY) {
			int activityDay = SocialCounterPeriodUtil.getActivityDay();

			if (activityDay == GetterUtil.getInteger(valueParts[0])) {
				return count;
			}
		}
		else if (limitPeriod ==
					SocialActivityCounterDefinition.LIMIT_PERIOD_LIFETIME) {

			return count;
		}
		else if (limitPeriod ==
					SocialActivityCounterDefinition.LIMIT_PERIOD_PERIOD) {

			int activityDay = SocialCounterPeriodUtil.getActivityDay();

			String[] periodParts = StringUtil.split(
				valueParts[0], StringPool.DASH);

			int startPeriod = GetterUtil.getInteger(periodParts[0]);
			int endPeriod = GetterUtil.getInteger(periodParts[1]);

			if ((activityDay >= startPeriod) && (activityDay <= endPeriod)) {
				return count;
			}
		}

		return 0;
	}

	@Override
	public void setCount(int limitPeriod, int count) {
		if (limitPeriod == SocialActivityCounterDefinition.LIMIT_PERIOD_DAY) {
			setValue(
				String.valueOf(SocialCounterPeriodUtil.getActivityDay()) +
					StringPool.SLASH + String.valueOf(count));
		}
		else if (limitPeriod ==
					SocialActivityCounterDefinition.LIMIT_PERIOD_LIFETIME) {

			setValue(String.valueOf(count));
		}
		else if (limitPeriod ==
					SocialActivityCounterDefinition.LIMIT_PERIOD_PERIOD) {

			setValue(
				StringBundler.concat(
					SocialCounterPeriodUtil.getStartPeriod(), StringPool.DASH,
					SocialCounterPeriodUtil.getEndPeriod(), StringPool.SLASH,
					count));
		}
	}

}