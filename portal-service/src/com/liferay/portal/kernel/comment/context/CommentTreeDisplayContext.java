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

package com.liferay.portal.kernel.comment.context;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

/**
 * @author Adolfo Pérez
 */
public interface CommentTreeDisplayContext {

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