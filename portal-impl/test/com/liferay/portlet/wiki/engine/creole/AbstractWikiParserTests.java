/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.engine.creole;

import com.liferay.portal.parsers.creole.ast.WikiPageNode;
import com.liferay.portal.parsers.creole.parser.Creole10Lexer;
import com.liferay.portal.parsers.creole.parser.Creole10Parser;
import com.liferay.portal.util.BaseTestCase;

import java.io.IOException;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import org.junit.Assert;

import org.springframework.core.io.ClassPathResource;

/**
 * @author Miguel Pastor
 */
public abstract class AbstractWikiParserTests extends BaseTestCase {

	protected Creole10Parser buildParser(String sourceCode)
		throws IOException {

		ANTLRInputStream input = new ANTLRInputStream(
			new ClassPathResource(sourceCode).getInputStream());

		Creole10Lexer lexer = new Creole10Lexer(input);

		CommonTokenStream tokens = new CommonTokenStream(lexer);

		return new Creole10Parser(tokens);
	}

	protected WikiPageNode parseFile(String sourceCode) {
		try {
			creole10Parser = buildParser(sourceCode);
			creole10Parser.wikipage();
		}
		catch (IOException e) {
			Assert.fail(
				"The file you want to parse does not exists. Review it");
		}
		catch (RecognitionException e) {
			Assert.fail("File has not been parsed correctly");
		}

		return creole10Parser.getWikiPageNode();
	}

	protected static final String ESCAPE_FILES_PREFIX =
		"com/liferay/portlet/wiki/engine/creole/escape/";

	protected static final String HEADING_FILES_PREFIX =
		"com/liferay/portlet/wiki/engine/creole/heading/";

	protected static final String HORIZONTAL_FILES_PREFIX =
		"com/liferay/portlet/wiki/engine/creole/horizontal/";

	protected static final String IMAGE_FILES_PREFIX =
		"com/liferay/portlet/wiki/engine/creole/image/";

	protected static final String LINK_FILES_PREFIX =
		"com/liferay/portlet/wiki/engine/creole/link/";

	protected static final String LISTS_FILES_PREFIX =
		"com/liferay/portlet/wiki/engine/creole/lists/";

	protected static final String NOWIKI_FILES_PREFIX =
		"com/liferay/portlet/wiki/engine/creole/nowiki/";

	protected static final String TABLE_FILES_PREFIX =
		"com/liferay/portlet/wiki/engine/creole/table/";

	protected static final String TEXT_FILES_PREFIX =
		"com/liferay/portlet/wiki/engine/creole/text/";

	protected Creole10Parser creole10Parser = null;

}