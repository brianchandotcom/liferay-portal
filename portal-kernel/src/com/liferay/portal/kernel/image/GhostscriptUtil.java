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

package com.liferay.portal.kernel.image;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Ivica Cardic
 */
public class GhostscriptUtil {

	public static Future<?> execute(List<String> arguments) throws Exception {
		return getGhostscript().execute(arguments);
	}

	public static Ghostscript getGhostscript() {
		return _ghostscript;
	}

	public static boolean isEnabled() {
		return getGhostscript().isEnabled();
	}

	public static void reset() {
		getGhostscript().reset();
	}

	public void setGhostscript(Ghostscript ghostscript) {
		_ghostscript = ghostscript;
	}

	private static Ghostscript _ghostscript;

}