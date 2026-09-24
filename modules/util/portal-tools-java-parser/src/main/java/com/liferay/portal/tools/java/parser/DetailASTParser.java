/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.java.parser;

import com.liferay.petra.string.StringBundler;

import com.puppycrawl.tools.checkstyle.CheckstyleParserErrorStrategy;
import com.puppycrawl.tools.checkstyle.JavaAstVisitor;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.grammar.java.JavaLanguageLexer;
import com.puppycrawl.tools.checkstyle.grammar.java.JavaLanguageParser;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 * @author Drew Brokke
 */
public class DetailASTParser {

	public static DetailAST parse(FileContents fileContents)
		throws CheckstyleException {

		FileText fileText = fileContents.getText();

		JavaLanguageLexer javaLanguageLexer = new JavaLanguageLexer(
			CharStreams.fromString(String.valueOf(fileText.getFullText())),
			true);

		javaLanguageLexer.removeErrorListeners();
		javaLanguageLexer.setCommentListener(fileContents);

		CommonTokenStream commonTokenStream = new CommonTokenStream(
			javaLanguageLexer);

		JavaLanguageParser javaLanguageParser = new JavaLanguageParser(
			commonTokenStream, _CLEAR_DFA_LIMIT);

		javaLanguageParser.removeErrorListeners();
		javaLanguageParser.addErrorListener(new ParserErrorListener());
		javaLanguageParser.setErrorHandler(new CheckstyleParserErrorStrategy());

		try {
			JavaAstVisitor javaAstVisitor = new JavaAstVisitor(
				commonTokenStream);

			return javaAstVisitor.visit(javaLanguageParser.compilationUnit());
		}
		catch (IllegalStateException illegalStateException) {
			throw new CheckstyleException(
				"Unable to parse " + fileContents.getFileName(),
				illegalStateException);
		}
	}

	public static DetailAST parseWithComments(FileContents fileContents)
		throws CheckstyleException {

		return JavaParser.appendHiddenCommentNodes(parse(fileContents));
	}

	// Checkstyle clears ANTLR's shared prediction cache every 500 parses to
	// bound memory, roughly halving parse speed

	private static final int _CLEAR_DFA_LIMIT = 15000;

	private static class ParserErrorListener extends BaseErrorListener {

		@Override
		public void syntaxError(
			Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
			int charPositionInLine, String msg,
			RecognitionException recognitionException) {

			throw new IllegalStateException(
				StringBundler.concat(line, ":", charPositionInLine, ": ", msg),
				recognitionException);
		}

	}

}