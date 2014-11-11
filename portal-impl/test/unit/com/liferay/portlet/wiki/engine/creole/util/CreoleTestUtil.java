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

package com.liferay.portlet.wiki.engine.creole.util;

import com.liferay.portal.parsers.creole.ast.WikiPageNode;
import com.liferay.portal.parsers.creole.parser.Creole10Lexer;
import com.liferay.portal.parsers.creole.parser.Creole10Parser;

import java.io.IOException;
import java.io.InputStream;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import org.junit.Assert;

/**
 * @author Roberto Díaz
 */
public class CreoleTestUtil {

	protected static Creole10Parser getCreole10Parser(
			String fileName, Class<?> clazz)
		throws IOException {

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		ANTLRInputStream antlrInputStream = new ANTLRInputStream(inputStream);

		Creole10Lexer creole10Lexer = new Creole10Lexer(antlrInputStream);

		CommonTokenStream commonTokenStream = new CommonTokenStream(
			creole10Lexer);

		return new Creole10Parser(commonTokenStream);
	}

	protected static WikiPageNode getWikiPageNode(
		String fileName, Class<?> clazz) {

		Creole10Parser creole10parser = null;

		try {
			creole10parser = getCreole10Parser(fileName, clazz);

			creole10parser.wikipage();
		}
		catch (IOException ioe) {
			Assert.fail("File does not exist");
		}
		catch (RecognitionException re) {
			Assert.fail("File could not be parsed");
		}

		return creole10parser.getWikiPageNode();
	}

}