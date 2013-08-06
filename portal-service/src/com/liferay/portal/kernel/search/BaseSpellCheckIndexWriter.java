/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.net.URL;

/**
 * @author Michael C. Han
 */
public abstract class BaseSpellCheckIndexWriter
	implements SpellCheckIndexWriter {

	@Override
	public void indexDictionaries(SearchContext searchContext)
		throws SearchException {

		try {
			for (String languageId : _SUPPORTED_LOCALES) {
				indexDictionary(searchContext.getCompanyId(), languageId);
			}
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	@Override
	public void indexDictionary(SearchContext searchContext)
		throws SearchException {

		try {
			indexDictionary(
				searchContext.getCompanyId(), searchContext.getLanguageId());
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	protected URL getResource(String name) {
		ClassLoader contextClassLoader =
			Thread.currentThread().getContextClassLoader();

		URL url = contextClassLoader.getResource(name);

		if (url == null) {
			ClassLoader portalClassLoader =
				PortalClassLoaderUtil.getClassLoader();

			url = portalClassLoader.getResource(name);
		}

		return url;
	}

	protected void indexDictionary(long companyId, String languageId)
		throws Exception {

		String[] dictionaryFileNames = PropsUtil.getArray(
			PropsKeys.INDEX_SEARCH_SPELL_CHECKER_DICTIONARY,
			new Filter(languageId));

		for (String dictionaryFileName : dictionaryFileNames) {
			InputStream inputStream = null;

			if (_log.isInfoEnabled()) {
				_log.info(
					"Start indexing dictionary for " + dictionaryFileName);
			}

			try {
				URL url = getResource(dictionaryFileName);

				if (url == null) {
					if (_log.isWarnEnabled()) {
						_log.warn("Unable to read " + dictionaryFileName);
					}

					continue;
				}

				inputStream = url.openStream();

				if (inputStream == null) {
					if (_log.isWarnEnabled()) {
						_log.warn("Unable to read " + dictionaryFileName);
					}

					continue;
				}

				indexDictionary(companyId, languageId, inputStream);
			}
			finally {
				StreamUtil.cleanUp(inputStream);
			}

			if (_log.isInfoEnabled()) {
				_log.info(
					"Finished indexing dictionary for " + dictionaryFileName);
			}
		}
	}

	protected abstract void indexDictionary(
			long companyId, String languageId, InputStream inputStream)
		throws Exception;

	private static final String[] _SUPPORTED_LOCALES = StringUtil.split(
		PropsUtil.get(PropsKeys.INDEX_SEARCH_SPELL_CHECKER_SUPPORTED_LOCALES));

	private static Log _log = LogFactoryUtil.getLog(
		BaseSpellCheckIndexWriter.class);

}