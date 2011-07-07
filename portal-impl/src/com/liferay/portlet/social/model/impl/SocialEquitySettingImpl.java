/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portlet.social.model.SocialEquityActionMapping;
import com.liferay.portlet.social.model.SocialEquitySettingConstants;

/**
 * @author Zsolt Berentey
 */
public class SocialEquitySettingImpl extends SocialEquitySettingBaseImpl {

	public SocialEquitySettingImpl() {
	}

	public void update(SocialEquityActionMapping equityActionMapping) {
		if (getType() == SocialEquitySettingConstants.TYPE_INFORMATION) {
			setDailyLimit(equityActionMapping.getInformationDailyLimit());
			setLifespan(equityActionMapping.getInformationLifespan());
			setUniqueEntry(equityActionMapping.isUnique());
			setValue(equityActionMapping.getInformationValue());
		}
		else {
			setDailyLimit(equityActionMapping.getParticipationDailyLimit());
			setLifespan(equityActionMapping.getParticipationLifespan());
			setUniqueEntry(equityActionMapping.isUnique());
			setValue(equityActionMapping.getParticipationValue());
		}
	}

}