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

package com.liferay.journal.model;

import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public interface JournalArticleDisplay extends Serializable {

	public String getArticleDisplayImageURL(ThemeDisplay themeDisplay);

	public String getArticleId();

	public String[] getAvailableLocales();

	public long getCompanyId();

	public String getContent();

	public int getCurrentPage();

	public String getDDMStructureKey();

	public String getDDMTemplateKey();

	public String getDescription();

	public long getGroupId();

	public long getId();

	public int getNumberOfPages();

	public long getResourcePrimKey();

	public long getSmallImageId();

	public String getSmallImageURL();

	public String getTitle();

	public String getUrlTitle();

	public long getUserId();

	public double getVersion();

	public boolean isCacheable();

	public boolean isPaginate();

	public boolean isSmallImage();

	public void setCacheable(boolean cacheable);

	public void setContent(String content);

	public void setCurrentPage(int currentPage);

	public void setDDMStructureKey(String ddmStructureKey);

	public void setDDMTemplateKey(String ddmTemplateKey);

	public void setNumberOfPages(int numberOfPages);

	public void setPaginate(boolean paginate);

	public void setSmallImage(boolean smallImage);

	public void setSmallImageId(long smallImageId);

	public void setSmallImageURL(String smallImageURL);

}