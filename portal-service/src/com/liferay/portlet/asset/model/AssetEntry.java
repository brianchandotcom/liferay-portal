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

package com.liferay.portlet.asset.model;

import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the AssetEntry service. Represents a row in the &quot;AssetEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryModel
 * @see com.liferay.portlet.asset.model.impl.AssetEntryImpl
 * @see com.liferay.portlet.asset.model.impl.AssetEntryModelImpl
 * @generated
 */
public interface AssetEntry extends AssetEntryModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.asset.model.impl.AssetEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories()
		throws com.liferay.portal.kernel.exception.SystemException;

	public long[] getCategoryIds()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String[] getTagNames()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags()
		throws com.liferay.portal.kernel.exception.SystemException;
}