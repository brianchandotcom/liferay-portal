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

package com.liferay.portal.upgrade.internal.model.listener;

import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.upgrade.internal.release.ReleasePublisher;

/**
 * @author Shuyang Zhou
 */
public class ReleaseModelListener extends BaseModelListener<Release> {

	public ReleaseModelListener(ReleasePublisher releasePublisher) {
		_releasePublisher = releasePublisher;
	}

	@Override
	public void onAfterRemove(Release release) {
		_releasePublisher.unpublish(release);
	}

	private final ReleasePublisher _releasePublisher;

}