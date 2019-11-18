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

package com.liferay.wiki.engine.creole.util.test;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;
import com.liferay.wiki.engine.creole.internal.parser.ast.WikiPageNode;
import com.liferay.wiki.engine.creole.internal.parser.parser.Creole10Lexer;
import com.liferay.wiki.engine.creole.internal.parser.parser.Creole10Parser;
import com.liferay.wiki.engine.creole.internal.util.WikiEngineCreoleComponentProvider;

import java.io.IOException;
import java.io.InputStream;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import org.mockito.Mockito;

/**
 * @author Roberto Díaz
 */
public class CreoleTestUtil {

	public static WikiEngineCreoleComponentProvider
		getWikiEngineCreoleComponentProvider() {

		WikiEngineCreoleComponentProvider wikiEngineCreoleComponentProvider =
			new WikiEngineCreoleComponentProvider();

		ReflectionTestUtil.invoke(
			wikiEngineCreoleComponentProvider, "activate", new Class<?>[0]);

		WikiGroupServiceConfiguration wikiGroupServiceConfiguration =
			Mockito.mock(WikiGroupServiceConfiguration.class);

		Mockito.when(
			wikiGroupServiceConfiguration.parsersCreoleSupportedProtocols()
		).thenReturn(
			new String[] {"ftp://", "http://", "https://", "mailto", "mms://"}
		);

		wikiEngineCreoleComponentProvider.setWikiGroupServiceConfiguration(
			wikiGroupServiceConfiguration);

		return wikiEngineCreoleComponentProvider;
	}

	public static WikiPageNode getWikiPageNode(
		String fileName, Class<?> clazz) {

		Creole10Parser creole10Parser = null;

		try {
			creole10Parser = getCreole10Parser(fileName, clazz);

			creole10Parser.wikipage();
		}
		catch (IOException ioe) {
			throw new RuntimeException("File " + fileName + " does not exist");
		}
		catch (RecognitionException re) {
			throw new RuntimeException("Uanble to parse " + fileName);
		}

		return creole10Parser.getWikiPageNode();
	}

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

}