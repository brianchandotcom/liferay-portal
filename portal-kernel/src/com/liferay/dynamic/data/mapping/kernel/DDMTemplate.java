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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.StagedGroupedModel;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Marcellus Tavares
 */
@ProviderType
public interface DDMTemplate extends StagedGroupedModel {

	public boolean getCacheable();

	public String getClassName();

	public long getClassNameId();

	public long getClassPK();

	public String getDescription();

	public String getDescription(Locale locale);

	public String getDescription(Locale locale, boolean useDefault);

	public String getDescription(String languageId);

	public String getDescription(String languageId, boolean useDefault);

	public String getDescriptionCurrentLanguageId();

	public String getDescriptionCurrentValue();

	public Map<Locale, String> getDescriptionMap();

	@Override
	public long getGroupId();

	public String getLanguage();

	public String getMode();

	@Override
	public Date getModifiedDate();

	public String getName();

	public String getName(Locale locale);

	public String getName(Locale locale, boolean useDefault);

	public String getName(String languageId);

	public String getName(String languageId, boolean useDefault);

	public String getNameCurrentLanguageId();

	public String getNameCurrentValue();

	public Map<Locale, String> getNameMap();

	public long getPrimaryKey();

	public long getResourceClassNameId();

	public String getScript();

	public boolean getSmallImage();

	public long getSmallImageId();

	public String getSmallImageType() throws PortalException;

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

	public String getWebDavURL(ThemeDisplay themeDisplay, String webDAVToken);

	public boolean isCacheable();

	public boolean isSmallImage();

	public String toXmlString();

}