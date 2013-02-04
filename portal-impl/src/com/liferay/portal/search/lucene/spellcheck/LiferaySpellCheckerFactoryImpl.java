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

package com.liferay.portal.search.lucene.spellcheck;

import java.io.IOException;

import org.apache.lucene.search.spell.LevensteinDistance;
import org.apache.lucene.search.spell.SuggestWordQueue;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

/**
 * @author Michael C. Han
 */
public class LiferaySpellCheckerFactoryImpl implements SpellCheckerFactory {

	public SpellChecker createSpellChecker(Directory spellCheckerDirectory)
		throws IOException {

		return new LiferayLuceneSpellChecker(
			spellCheckerDirectory, new LevensteinDistance(),
			SuggestWordQueue.DEFAULT_COMPARATOR, _version);
	}

	public void setVersion(Version version) {
		_version = version;
	}

	private Version _version;

}