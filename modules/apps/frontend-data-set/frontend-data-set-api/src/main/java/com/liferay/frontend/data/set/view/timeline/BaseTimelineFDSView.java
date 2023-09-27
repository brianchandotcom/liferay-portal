/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.view.timeline;

import com.liferay.frontend.data.set.constants.FDSConstants;
import com.liferay.frontend.data.set.view.FDSView;

/**
 * @author Marco Leo
 */
public abstract class BaseTimelineFDSView implements FDSView {

	@Override
	public String getContentRenderer() {
		return FDSConstants.TIMELINE;
	}

	public abstract String getDate();

	public abstract String getDescription();

	@Override
	public String getLabel() {
		return FDSConstants.TIMELINE;
	}

	@Override
	public String getName() {
		return FDSConstants.TIMELINE;
	}

	@Override
	public String getThumbnail() {
		return FDSConstants.TIMELINE;
	}

	public abstract String getTitle();

}