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

package com.liferay.redirect.test.util;

import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;

/**
 * @author Alejandro Tardín
 */
public class RedirectTestUtil {

	public static void withRedirectDisabled(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		_withRedirect(false, unsafeRunnable);
	}

	public static void withRedirectEnabled(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		_withRedirect(true, unsafeRunnable);
	}

	private static void _withRedirect(
			boolean enabled, UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		dictionary.put("enabled", enabled);

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.redirect.internal.configuration." +
						"FFRedirectConfiguration",
					dictionary)) {

			unsafeRunnable.run();
		}
	}

}