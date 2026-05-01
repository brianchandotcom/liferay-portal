/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ant.groovy;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Kenji Heigel
 */
public class GroovyValidateFileTask extends Task {

	public void addFileset(FileSet fileSet) {
		_fileSets.add(fileSet);
	}

	@Override
	public void execute() throws BuildException {
		Set<File> files = _getFiles();

		if (files.isEmpty()) {
			throw new BuildException(
				"Specify the 'file' attribute or a nested <fileset>");
		}

		int failedBlockCount = 0;
		int failedFileCount = 0;
		int fileCount = 0;
		int totalBlockCount = 0;

		List<String> failureSummaries = new ArrayList<>();

		for (File file : files) {
			List<GroovyBlock> groovyBlocks = _getGroovyBlocks(file);

			if (groovyBlocks.isEmpty()) {
				continue;
			}

			fileCount++;

			totalBlockCount += groovyBlocks.size();

			int fileFailureCount = 0;
			int issueNumber = 0;

			StringBuilder sb = new StringBuilder();

			for (GroovyBlock groovyBlock : groovyBlocks) {
				int startLineNumber = groovyBlock.getStartLineNumber();
				String text = groovyBlock.getText();

				List<String> errorMessages = new ArrayList<>();

				List<GroovyStyleChecker.Violation> missingSemicolonViolations =
					GroovyStyleChecker.checkMissingSemicolons(text);

				for (GroovyStyleChecker.Violation violation :
						missingSemicolonViolations) {

					int lineNumber =
						startLineNumber + violation.getLineNumber() - 1;

					errorMessages.add(
						"Missing semicolon at:\n" + file + ":" + lineNumber +
							"\n" + violation.getLine());
				}

				List<GroovyStyleChecker.Violation>
					unescapedDollarInterpolationViolations =
						GroovyStyleChecker.checkUnescapedDollarInterpolation(
							text);

				for (GroovyStyleChecker.Violation violation :
						unescapedDollarInterpolationViolations) {

					int lineNumber =
						startLineNumber + violation.getLineNumber() - 1;

					errorMessages.add(
						"Unescaped GString interpolation at:\n" + file + ":" +
							lineNumber + "\n" + violation.getLine() +
								"\nUse \\$ for a literal $, or use + " +
									"concatenation for runtime variables.");
				}

				GroovyTask groovyTask = new GroovyTask();

				groovyTask.addText(text);

				if (_classpathRef != null) {
					groovyTask.setClasspathRef(_classpathRef);
				}

				groovyTask.setCompileOnly(true);
				groovyTask.setOwningTarget(getOwningTarget());
				groovyTask.setProject(getProject());

				try {
					groovyTask.execute();
				}
				catch (BuildException buildException) {
					errorMessages.add(
						_formatCompileErrors(
							file, buildException.getMessage(),
							startLineNumber));
				}

				if (!errorMessages.isEmpty()) {
					fileFailureCount++;

					for (String errorMessage : errorMessages) {
						issueNumber++;

						sb.append("\n\n    ");
						sb.append(issueNumber);
						sb.append(". ");
						sb.append(errorMessage.replace("\n", "\n       "));
					}
				}
			}

			if (fileFailureCount > 0) {
				failedFileCount++;
				failedBlockCount += fileFailureCount;

				log(
					"FAIL  " + file + " (" + fileFailureCount + "/" +
						groovyBlocks.size() + " groovy block(s) failed)" + sb,
					Project.MSG_ERR);

				failureSummaries.add(
					file + " — " + fileFailureCount + "/" +
						groovyBlocks.size() + " groovy block(s) failed");
			}
		}

		log("");
		log(
			"Summary: " + totalBlockCount + " groovy block(s) across " +
				fileCount + " file(s) — " +
					(totalBlockCount - failedBlockCount) + " OK, " +
						failedBlockCount + " FAIL");

		if (failedFileCount > 0) {
			StringBuilder sb = new StringBuilder();

			sb.append("Groovy validation failed in ");
			sb.append(failedFileCount);
			sb.append(" file(s):");

			for (String failureSummary : failureSummaries) {
				sb.append("\n  ");
				sb.append(failureSummary);
			}

			throw new BuildException(sb.toString());
		}
	}

	public void setClasspathRef(String classpathRef) {
		_classpathRef = classpathRef;
	}

	public void setFile(File file) {
		_file = file;
	}

	private String _formatCompileErrors(
		File file, String message, int startLineNumber) {

		if (message == null) {
			return "";
		}

		message = message.replaceFirst(
			"^Groovy compilation failed:\\s*\\n", "");

		message = message.replaceFirst("\\n\\d+ errors?\\s*$", "");

		message = message.replaceAll(
			"GroovyScript_\\d+\\.groovy: \\d+: ([^\\n]+)", "$1 at:");

		Matcher matcher = _errorLocationPattern.matcher(message);

		StringBuffer sb = new StringBuffer(message.length());

		while (matcher.find()) {
			int blockLineNumber = Integer.parseInt(matcher.group(1));

			int lineNumber = blockLineNumber + startLineNumber - 1;

			matcher.appendReplacement(
				sb, Matcher.quoteReplacement(file + ":" + lineNumber));
		}

		matcher.appendTail(sb);

		return sb.toString();
	}

	private Set<File> _getFiles() {
		Set<File> files = new LinkedHashSet<>();

		if (_file != null) {
			if (!_file.exists()) {
				throw new BuildException(
					"Attribute 'file' points to a non-existent path: " + _file);
			}

			files.add(_file);
		}

		for (FileSet fileSet : _fileSets) {
			DirectoryScanner directoryScanner = fileSet.getDirectoryScanner(
				getProject());

			File baseDir = directoryScanner.getBasedir();

			for (String relativePath : directoryScanner.getIncludedFiles()) {
				files.add(new File(baseDir, relativePath));
			}
		}

		return files;
	}

	private List<GroovyBlock> _getGroovyBlocks(File file) {
		try {
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

			saxParserFactory.setFeature(_FEATURE_DISALLOW_DOCTYPE_DECL, true);
			saxParserFactory.setFeature(
				_FEATURE_EXTERNAL_GENERAL_ENTITIES, false);
			saxParserFactory.setFeature(
				_FEATURE_EXTERNAL_PARAMETER_ENTITIES, false);
			saxParserFactory.setFeature(_FEATURE_LOAD_EXTERNAL_DTD, false);
			saxParserFactory.setNamespaceAware(false);
			saxParserFactory.setXIncludeAware(false);

			SAXParser saxParser = saxParserFactory.newSAXParser();

			List<GroovyBlock> groovyBlocks = new ArrayList<>();

			saxParser.parse(
				file,
				new DefaultHandler() {

					@Override
					public void characters(char[] ch, int start, int length) {
						if (_sb != null) {
							_sb.append(ch, start, length);
						}
					}

					@Override
					public void endElement(
						String uri, String localName, String qName) {

						if ((_sb != null) && qName.equals("groovy")) {
							groovyBlocks.add(
								new GroovyBlock(
									_startLineNumber, _sb.toString()));

							_sb = null;
						}
					}

					@Override
					public void setDocumentLocator(Locator locator) {
						_locator = locator;
					}

					@Override
					public void startElement(
						String uri, String localName, String qName,
						Attributes attributes) {

						if (qName.equals("groovy")) {
							_startLineNumber = _locator.getLineNumber();
							_sb = new StringBuilder();
						}
					}

					private Locator _locator;
					private StringBuilder _sb;
					private int _startLineNumber;

				});

			return groovyBlocks;
		}
		catch (IOException | ParserConfigurationException | SAXException
					exception) {

			throw new BuildException(
				"Failed to parse " + file + ": " + exception.getMessage(),
				exception);
		}
	}

	private static final String _FEATURE_DISALLOW_DOCTYPE_DECL =
		"http://apache.org/xml/features/disallow-doctype-decl";

	private static final String _FEATURE_EXTERNAL_GENERAL_ENTITIES =
		"http://xml.org/sax/features/external-general-entities";

	private static final String _FEATURE_EXTERNAL_PARAMETER_ENTITIES =
		"http://xml.org/sax/features/external-parameter-entities";

	private static final String _FEATURE_LOAD_EXTERNAL_DTD =
		"http://apache.org/xml/features/nonvalidating/load-external-dtd";

	private static final Pattern _errorLocationPattern = Pattern.compile(
		" @ line (\\d+), column \\d+\\.");

	private String _classpathRef;
	private File _file;
	private final List<FileSet> _fileSets = new ArrayList<>();

	private static final class GroovyBlock {

		public int getStartLineNumber() {
			return _startLineNumber;
		}

		public String getText() {
			return _text;
		}

		private GroovyBlock(int startLineNumber, String text) {
			_startLineNumber = startLineNumber;
			_text = text;
		}

		private final int _startLineNumber;
		private final String _text;

	}

}