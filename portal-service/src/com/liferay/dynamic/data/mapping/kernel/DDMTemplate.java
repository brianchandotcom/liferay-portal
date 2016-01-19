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

package com.liferay.dynamic.data.mapping.kernel;

import com.liferay.portal.model.StagedGroupedModel;

import java.util.Date;
import java.util.Locale;

/**
 * @author Marcellus Tavares
 */
public interface DDMTemplate extends StagedGroupedModel {

	public boolean getCacheable();

	public long getClassNameId();

	public long getClassPK();

	public String getDescription();

	public String getDescription(Locale locale);

	@Override
	public long getGroupId();

	public String getLanguage();

	public String getMode();

	@Override
	public Date getModifiedDate();

	public String getName();

	public String getName(Locale locale);

	public long getPrimaryKey();

	public long getResourceClassNameId();

	public String getScript();

	public boolean getSmallImage();

	public long getSmallImageId();

	public String getSmallImageURL();

	public long getTemplateId();

	public String getTemplateKey();

	public String getType();

	@Override
	public long getUserId();

	@Override
	public String getUserName();

	public String getVersion();

	public long getVersionUserId();

	public String getVersionUserName();

	public boolean isCacheable();

	public boolean isSmallImage();

}