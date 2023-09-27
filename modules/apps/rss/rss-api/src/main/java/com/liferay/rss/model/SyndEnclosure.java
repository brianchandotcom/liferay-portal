/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.rss.model;

/**
 * @author Shuyang Zhou
 */
public interface SyndEnclosure {

	public long getLength();

	public String getType();

	public String getUrl();

	public void setLength(long length);

	public void setType(String type);

	public void setUrl(String url);

}