/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.model;

import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the DLFileShortcut service. Represents a row in the &quot;DLFileShortcut&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileShortcutModel
 * @see com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutImpl
 * @see com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutModelImpl
 * @generated
 */
public interface DLFileShortcut extends DLFileShortcutModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public java.lang.String buildTreePath()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.documentlibrary.model.DLFolder getDLFolder()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.kernel.repository.model.Folder getFolder()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getToTitle();

	public boolean isInHiddenFolder();
}