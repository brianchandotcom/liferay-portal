/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.rss.model;

/**
 * @author Shuyang Zhou
 */
public interface SyndLink {

	public String getHref();

	public long getLength();

	public String getRel();

	public String getType();

	public void setHref(String href);

	public void setLength(long length);

	public void setRel(String rel);

	public void setType(String type);

}