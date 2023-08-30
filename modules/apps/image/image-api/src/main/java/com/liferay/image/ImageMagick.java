/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.image;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 * @author Alexander Chow
 * @author Ivica Cardic
 */
public interface ImageMagick {

	public Future<?> convert(List<String> arguments) throws Exception;

	public void destroy();

	public String getGlobalSearchPath() throws Exception;

	public Properties getResourceLimitsProperties() throws Exception;

	public String[] identify(List<String> arguments) throws Exception;

	public boolean isEnabled();

	public void reset();

	public byte[] scale(byte[] bytes, String mimeType, int width, int height)
		throws Exception;

}