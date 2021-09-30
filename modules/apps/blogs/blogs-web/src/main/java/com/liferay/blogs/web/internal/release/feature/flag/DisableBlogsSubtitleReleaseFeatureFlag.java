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

package com.liferay.blogs.web.internal.release.feature.flag;

import com.liferay.portal.kernel.release.feature.flag.ReleaseFeatureFlag;
import com.liferay.portal.kernel.util.ReleaseInfo;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = ReleaseFeatureFlag.class)
public class DisableBlogsSubtitleReleaseFeatureFlag
	implements ReleaseFeatureFlag {

	@Override
	public int getThreshold() {
		return ReleaseInfo.RELEASE_7_4_90_BUILD_NUMBER + 1;
	}

}