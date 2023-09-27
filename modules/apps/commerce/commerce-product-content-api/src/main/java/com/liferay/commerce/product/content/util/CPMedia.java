/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.util;

/**
 * @author Marco Leo
 */
public interface CPMedia {

	public String getDownloadURL();

	public long getId();

	public long getSize();

	public String getThumbnailURL();

	public String getTitle();

	public String getURL();

	public String mimeType();

}