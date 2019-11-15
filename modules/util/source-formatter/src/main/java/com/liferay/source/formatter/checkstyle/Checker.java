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

package com.liferay.source.formatter.checkstyle;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.checks.configuration.SourceFormatterSuppressions;
import com.liferay.source.formatter.checkstyle.util.CheckstyleLogger;

import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.ModuleFactory;
import com.puppycrawl.tools.checkstyle.PackageNamesLoader;
import com.puppycrawl.tools.checkstyle.PackageObjectFactory;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.FilterSet;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Hugo Huijser
 */
public class Checker extends com.puppycrawl.tools.checkstyle.Checker {

	public Checker(
			Configuration configuration, AuditListener auditListener,
			CheckstyleLogger checkstyleLogger,
			SourceFormatterSuppressions sourceFormatterSuppressions)
		throws CheckstyleException {

		_configuration = configuration;
		_auditListener = auditListener;
		_checkstyleLogger = checkstyleLogger;

		_filterSet = sourceFormatterSuppressions.getCheckstyleFilterSet();

		Class<?> clazz = getClass();

		_classLoader = clazz.getClassLoader();

		setModuleClassLoader(_classLoader);

		_moduleFactory = new PackageObjectFactory(
			PackageNamesLoader.getPackageNames(_classLoader), _classLoader);

		addFilter(_filterSet);

		addListener(_auditListener);

		configure(_configuration);
	}

	@Override
	public void fireErrors(
		String fileName, SortedSet<LocalizedMessage> errors) {

		super.fireErrors(_normalizeFileName(fileName), errors);
	}

	@Override
	public void fireFileFinished(String fileName) {
		super.fireFileFinished(_normalizeFileName(fileName));
	}

	@Override
	public void fireFileStarted(String fileName) {
		super.fireFileStarted(_normalizeFileName(fileName));
	}

	public Set<SourceFormatterMessage> getSourceFormatterMessages() {
		return _checkstyleLogger.getSourceFormatterMessages();
	}

	public void processFileContents(List<String[]> fileContents)
		throws CheckstyleException, IOException {

		_fireAuditStarted();

		for (Configuration childConfiguration : _configuration.getChildren()) {
			Object childModule = _moduleFactory.createModule(
				childConfiguration.getName());

			if (!(childModule instanceof TreeWalker)) {
				continue;
			}

			TreeWalker treeWalker = (TreeWalker)childModule;

			_processFileContents(fileContents, _getChecks(childConfiguration));

			treeWalker.finishProcessing();

			treeWalker.destroy();
		}

		_fireAuditStarted();
	}

	private static List<String> _getLines(String s) throws IOException {
		List<String> lines = new ArrayList<>();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(s))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lines.add(line);
			}
		}

		return lines;
	}

	private void _fireAuditStarted() {
		_auditListener.auditStarted(new AuditEvent(this));
	}

	private List<AbstractCheck> _getChecks(Configuration configuration)
		throws CheckstyleException {

		List<AbstractCheck> checks = new ArrayList<>();

		for (Configuration checkConfiguration : configuration.getChildren()) {
			Object checkModule = _moduleFactory.createModule(
				checkConfiguration.getName());

			if (checkModule instanceof AbstractCheck) {
				AbstractCheck check = (AbstractCheck)checkModule;

				check.configure(checkConfiguration);

				checks.add(check);
			}
		}

		return checks;
	}

	private boolean _isRegisteredCheck(AbstractCheck check, int tokenType) {
		Set<String> tokenNames = check.getTokenNames();

		if (tokenNames.isEmpty()) {
			return ArrayUtil.contains(check.getDefaultTokens(), tokenType);
		}

		if (!ArrayUtil.contains(check.getAcceptableTokens(), tokenType)) {
			return false;
		}

		for (String tokenName : tokenNames) {
			if (tokenType == TokenUtil.getTokenId(tokenName)) {
				return true;
			}
		}

		return false;
	}

	private String _normalizeFileName(String fileName) {
		Path filePath = Paths.get(fileName);

		filePath = filePath.normalize();

		return StringUtil.replace(
			filePath.toString(), CharPool.BACK_SLASH, CharPool.SLASH);
	}

	private SortedSet<LocalizedMessage> _processContent(
			String fileName, String content, List<AbstractCheck> checks)
		throws CheckstyleException, IOException {

		List<String> lines = _getLines(content);

		FileText fileText = new FileText(new File(fileName), lines);

		FileContents fileContents = new FileContents(fileText);

		DetailAST rootDetailAST = JavaParser.parse(fileContents);

		return _walk(rootDetailAST, fileContents, checks);
	}

	private void _processFileContents(
			List<String[]> fileContents, List<AbstractCheck> checks)
		throws CheckstyleException, IOException {

		for (String[] fileContentArray : fileContents) {
			String content = fileContentArray[1];

			String fileName = fileContentArray[0];

			fireFileStarted(fileName);

			fireErrors(fileName, _processContent(fileName, content, checks));

			fireFileFinished(fileName);
		}
	}

	private SortedSet<LocalizedMessage> _walk(
		DetailAST rootDetailAST, FileContents fileContents,
		List<AbstractCheck> checks) {

		SortedSet<LocalizedMessage> messages = new TreeSet<>();

		for (AbstractCheck check : checks) {
			check.clearMessages();
			check.setFileContents(fileContents);

			check.beginTree(rootDetailAST);
		}

		DetailAST detailAST = rootDetailAST;

		while (detailAST != null) {
			for (AbstractCheck check : checks) {
				if (_isRegisteredCheck(check, detailAST.getType())) {
					check.visitToken(detailAST);
				}
			}

			DetailAST firstChildDetailAST = detailAST.getFirstChild();

			while ((detailAST != null) && (firstChildDetailAST == null)) {
				for (AbstractCheck check : checks) {
					if (_isRegisteredCheck(check, detailAST.getType())) {
						check.leaveToken(detailAST);
					}
				}

				firstChildDetailAST = detailAST.getNextSibling();

				detailAST = detailAST.getParent();
			}

			detailAST = firstChildDetailAST;
		}

		for (AbstractCheck check : checks) {
			check.finishTree(rootDetailAST);

			messages.addAll(check.getMessages());
		}

		return messages;
	}

	private final AuditListener _auditListener;
	private final CheckstyleLogger _checkstyleLogger;
	private final ClassLoader _classLoader;
	private final Configuration _configuration;
	private final FilterSet _filterSet;
	private final ModuleFactory _moduleFactory;

}