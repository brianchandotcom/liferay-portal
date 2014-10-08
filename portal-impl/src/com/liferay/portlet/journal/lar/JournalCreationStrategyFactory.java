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

package com.liferay.portlet.journal.lar;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Joel Kozikowski
 */
public class JournalCreationStrategyFactory {

	public static JournalCreationStrategy getInstance() {
		if (_journalCreationStrategy == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Instantiate " + PropsValues.JOURNAL_LAR_CREATION_STRATEGY);
			}

			ClassLoader classLoader = ClassLoaderUtil.getPortalClassLoader();

			try {
				_journalCreationStrategy =
					(JournalCreationStrategy)classLoader.loadClass(
						PropsValues.JOURNAL_LAR_CREATION_STRATEGY).
							newInstance();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Return " + _journalCreationStrategy.getClass().getName());
		}

		return _journalCreationStrategy;
	}

	public static void setInstance(
		JournalCreationStrategy journalCreationStrategy) {

		if (_log.isDebugEnabled()) {
			_log.debug("Set " + journalCreationStrategy.getClass().getName());
		}

		_journalCreationStrategy = journalCreationStrategy;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalCreationStrategyFactory.class);

	private static JournalCreationStrategy _journalCreationStrategy;

}