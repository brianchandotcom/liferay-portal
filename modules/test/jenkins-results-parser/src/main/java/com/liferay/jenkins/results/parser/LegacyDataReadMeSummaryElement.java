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

package com.liferay.jenkins.results.parser;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

/**
 * @author Peter Yoo
 */
public class LegacyDataReadMeSummaryElement extends DefaultElement {

	public LegacyDataReadMeSummaryElement(
		String summaryID, Commit commit, LegacyDataArchive.Status status,
		String dataArchiveType) {

		super("details");

		_summaryElement = Dom4JUtil.getNewElement("summary", this);
		_unorderedListElement = Dom4JUtil.getNewElement("ul", this);
		_status = status;
		_summaryContentElement = _getSummaryContentElement(
			commit, dataArchiveType);

		addAttribute("id", summaryID);

		_updateSummaryElement();
	}

	public void addLegacyDataArchive(LegacyDataArchive legacyDataArchive) {
		_lineItemElements.add(
			Dom4JUtil.getNewElement(
				"li", _unorderedListElement,
				_getLegacyDataArchiveElement(legacyDataArchive)));

		_updateSummaryElement();
	}

	private Element _getLegacyDataArchiveElement(
		LegacyDataArchive legacyDataArchive) {

		File legacyDataArchiveFile =
			legacyDataArchive.getLegacyDataArchiveFile();
		GitWorkingDirectory legacyGitWorkingDirectory =
			legacyDataArchive.getLegacyGitWorkingDirectory();

		if (_status == LegacyDataArchive.Status.STALE) {
			return Dom4JUtil.getNewAnchorElement(
				legacyGitWorkingDirectory.getGitHubFileURL(
					"master", legacyGitWorkingDirectory.getRemote("upstream"),
					legacyDataArchiveFile, false),
				JenkinsResultsParserUtil.getPathRelativeTo(
					legacyDataArchiveFile,
					legacyGitWorkingDirectory.getWorkingDirectory()));
		}
		else if (_status == LegacyDataArchive.Status.UPDATED) {
			LegacyDataArchiveUtil legacyDataArchiveUtil =
				legacyDataArchive.getLegacyDataArchiveUtil();

			GitWorkingDirectory.Branch dataArchiveBranch =
				legacyDataArchiveUtil.getDataArchiveBranch();

			return Dom4JUtil.getNewAnchorElement(
				legacyGitWorkingDirectory.getGitHubFileURL(
					dataArchiveBranch.getName(),
					legacyGitWorkingDirectory.getRemote("upstream"),
					legacyDataArchiveFile, false),
				JenkinsResultsParserUtil.getPathRelativeTo(
					legacyDataArchiveFile,
					legacyGitWorkingDirectory.getWorkingDirectory()));
		}

		return Dom4JUtil.getNewElement(
			"span", null,
			JenkinsResultsParserUtil.getPathRelativeTo(
				legacyDataArchiveFile,
				legacyGitWorkingDirectory.getWorkingDirectory()));
	}

	private Element _getSummaryContentElement(
		Commit commit, String dataArchiveType) {

		Element summaryContentElement = Dom4JUtil.getNewElement("span");

		if (commit != null) {
			Dom4JUtil.getNewAnchorElement(
				commit.getGitHubCommitURL(), summaryContentElement,
				commit.getAbbreviatedSHA());

			Dom4JUtil.getNewElement(
				"span", summaryContentElement, commit.getMessage());
		}
		else {
			Dom4JUtil.getNewElement(
				"span", summaryContentElement, dataArchiveType);
		}

		return summaryContentElement;
	}

	private Element _getSummaryCountElement() {
		return Dom4JUtil.getNewElement(
			"b", null,
			JenkinsResultsParserUtil.combine(
				"(", Integer.toString(_lineItemElements.size()), ") "));
	}

	private void _updateSummaryElement() {
		_summaryElement.remove(_summaryCountElement);
		_summaryElement.remove(_summaryContentElement);

		_summaryCountElement = _getSummaryCountElement();
		_summaryContentElement = (Element)_summaryContentElement.clone();

		_summaryElement.add(_summaryCountElement);
		_summaryElement.add(_summaryContentElement);
	}

	private final List<Element> _lineItemElements = new ArrayList<>();
	private final LegacyDataArchive.Status _status;
	private Element _summaryContentElement;
	private Element _summaryCountElement;
	private final Element _summaryElement;
	private final Element _unorderedListElement;

}