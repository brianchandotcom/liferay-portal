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

package com.liferay.portlet.messageboards.model;

import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the MBMessage service. Represents a row in the &quot;MBMessage&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see MBMessageModel
 * @see com.liferay.portlet.messageboards.model.impl.MBMessageImpl
 * @see com.liferay.portlet.messageboards.model.impl.MBMessageModelImpl
 * @generated
 */
public interface MBMessage extends MBMessageModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.messageboards.model.impl.MBMessageImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public java.lang.String[] getAssetTagNames()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getAttachmentsDir();

	public java.lang.String[] getAttachmentsFiles()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getBody(boolean translate);

	public com.liferay.portlet.messageboards.model.MBCategory getCategory()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getDeletedAttachmentsDir();

	public java.lang.String[] getDeletedAttachmentsFiles()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBThread getThread()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getThreadAttachmentsDir();

	public java.lang.String getWorkflowClassName();

	public boolean isDiscussion();

	public boolean isFormatBBCode();

	public boolean isInTrashThread()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public boolean isReply();

	public boolean isRoot();

	public void setAttachmentsDir(java.lang.String attachmentsDir);
}