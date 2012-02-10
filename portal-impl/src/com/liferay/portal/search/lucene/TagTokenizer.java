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

import com.liferay.portal.kernel.util.CharPool;

import java.io.Reader;

import org.apache.lucene.analysis.CharTokenizer;
import org.apache.lucene.util.AttributeSource;

/**
 * The purpose of this class is to tokenize tag fields. Tags may contain spaces
 * and are delimited by commas. Therefore <code>TagTokenizer</code> will
 * tokenize only on the comma (',') character.
 *
 * @author Jonathan Potter
 */
public class TagTokenizer extends CharTokenizer {

	public TagTokenizer(Reader input) {
		super(input);
	}

	public TagTokenizer(AttributeSource source, Reader input) {
		super(source, input);
	}

	public TagTokenizer(AttributeSource.AttributeFactory factory,
		Reader input) {

		super(factory, input);
	}

	@Override
	protected boolean isTokenChar(char c) {
		return c != CharPool.COMMA;
	}

}