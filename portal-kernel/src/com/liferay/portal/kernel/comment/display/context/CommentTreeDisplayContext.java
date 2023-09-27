/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.comment.display.context;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

/**
 * @author Adolfo Pérez
 */
public interface CommentTreeDisplayContext extends CommentDisplayContext {

	public String getPublishButtonLabel(Locale locale) throws PortalException;

	public boolean isActionControlsVisible() throws PortalException;

	public boolean isDeleteActionControlVisible() throws PortalException;

	public boolean isDiscussionVisible() throws PortalException;

	public boolean isEditActionControlVisible() throws PortalException;

	public boolean isEditControlsVisible() throws PortalException;

	public boolean isRatingsVisible() throws PortalException;

	public boolean isReplyActionControlVisible() throws PortalException;

	public boolean isWorkflowStatusVisible() throws PortalException;

}