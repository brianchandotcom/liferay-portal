/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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