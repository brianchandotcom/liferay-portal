/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ImageLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 * @author Juan Fernández
 */
public class BlogsEntryImpl extends BlogsEntryBaseImpl {

	public BlogsEntryImpl() {
	}

	public String getSmallImageType() throws PortalException, SystemException {
		if ((_smallImageType == null) && isSmallImage()) {
			Image smallImage = ImageLocalServiceUtil.getImage(
				getSmallImageId());

			_smallImageType = smallImage.getType();
		}

		return _smallImageType;
	}

	@Override
	public boolean isApproved() {
		if ((getStatus() == WorkflowConstants.STATUS_APPROVED) ||
			(getStatus() == WorkflowConstants.STATUS_SCHEDULED)) {

			return true;
		}
		else {
			return false;
		}
	}

	public boolean isVisible() {
		Date displayDate = getDisplayDate();

		if (isApproved() && displayDate.before(new Date())) {
			return true;
		}
		else {
			return false;
		}
	}

	public void setSmallImageType(String smallImageType) {
		_smallImageType = smallImageType;
	}

	private String _smallImageType;

}