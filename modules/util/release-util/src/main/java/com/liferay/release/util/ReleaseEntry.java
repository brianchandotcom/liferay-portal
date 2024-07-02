/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.release.util;

/**
 * @author Drew Brokke
 */
public interface ReleaseEntry {

	public String getAppServerTomcatVersion();

	public String getBuildTimestamp();

	public String getBundleChecksumSHA512();

	public String getBundleURL();

	public String getGitHashLiferayDocker();

	public String getGitHashLiferayPortalEE();

	public String getLiferayDockerImage();

	public String getLiferayDockerTags();

	public String getLiferayProductVersion();

	public String getProduct();

	public String getProductGroupVersion();

	public String getProductVersion();

	public String getReleaseDate();

	public String getReleaseKey();

	public String getTargetPlatformVersion();

	public String getURL();

	public boolean isPromoted();

}