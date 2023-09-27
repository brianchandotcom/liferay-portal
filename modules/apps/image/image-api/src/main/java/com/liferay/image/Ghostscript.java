/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.image;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Ivica Cardic
 */
public interface Ghostscript {

	public Future<?> execute(List<String> arguments) throws Exception;

	public boolean isEnabled();

	public void reset();

}