/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarecatalog.model;

import com.liferay.portal.model.SaveableModel;

/**
 * The extended model interface for the SCProductEntry service. Represents a row in the &quot;SCProductEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see SCProductEntryModel
 * @see com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryImpl
 * @see com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl
 * @generated
 */
public interface SCProductEntry extends SCProductEntryModel, SaveableModel {
	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion getLatestVersion()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getLicenses()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductScreenshot> getScreenshots()
		throws com.liferay.portal.kernel.exception.SystemException;
}