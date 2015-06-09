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

package com.liferay.portal.kernel.comment;

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.model.StagedModel;

/**
 * @author Adolfo Pérez
 */
public interface DiscussionStagingDataHandler {

	public <T extends StagedModel> void exportModelDiscussion(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException;

	public <T extends StagedModel> void importModelDiscussion(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException;

	public boolean isClassNameSupported(String className);

}