/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search.lucene;

import com.liferay.portal.search.lucene.spellcheck.SpellChecker;

import java.io.IOException;

import java.util.Locale;

/**
 * @author Michael C. Han
 */
public interface SpellCheckerIndexAccessor {

	public void close() throws IOException;

	public SpellChecker getSpellChecker();

	public void indexDictionaries() throws IOException;

	public void indexDictionary(Locale locale) throws IOException;

}