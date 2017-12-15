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

import java.util.List;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

/**
 * @author Peter Yoo
 * @author Michael Hashimoto
 */
public class LegacyDataReadMeCommitGroupElement extends DefaultElement {

	public LegacyDataReadMeCommitGroupElement(LegacyDataArchive.Status status) {
		super("div");

		_status = status;

		String statusType;

		if (_status == LegacyDataArchive.Status.MISSING) {
			statusType = "Missing";
		}
		else if (_status == LegacyDataArchive.Status.STALE) {
			statusType = "Stale";
		}
		else if (_status == LegacyDataArchive.Status.UPDATED) {
			statusType = "Updated";
		}
		else {
			throw new RuntimeException("Invalid status " + _status);
		}

		Dom4JUtil.getNewElement("h4", this, statusType + " Data Archives:");
	}

	public void addLegacyDataArchive(LegacyDataArchive legacyDataArchive) {
		LegacyDataReadMeSummaryElement legacyDataReadMeSummaryElement =
			_getLegacyDataReadMeSummaryElement(legacyDataArchive);

		legacyDataReadMeSummaryElement.addLegacyDataArchive(legacyDataArchive);
	}

	private LegacyDataReadMeSummaryElement _getLegacyDataReadMeSummaryElement(
		LegacyDataArchive legacyDataArchive) {

		List<Element> summaryElements = elements();

		String summaryID = _getSummaryID(legacyDataArchive);

		for (Element summaryElement : summaryElements) {
			if (summaryID.equals(summaryElement.attributeValue("id"))) {
				return (LegacyDataReadMeSummaryElement)summaryElement;
			}
		}

		LegacyDataReadMeSummaryElement legacyDataReadMeSummaryElement =
			new LegacyDataReadMeSummaryElement(
				summaryID, _commit, _status,
				legacyDataArchive.getDataArchiveType());

		add(legacyDataReadMeSummaryElement);

		return legacyDataReadMeSummaryElement;
	}

	private String _getSummaryID(LegacyDataArchive legacyDataArchive) {
		if (_status != LegacyDataArchive.Status.MISSING) {
			_commit = legacyDataArchive.getCommit(); //COSTLY

			return _commit.getAbbreviatedSHA();
		}

		return legacyDataArchive.getDataArchiveType();
	}

	private Commit _commit;
	private final LegacyDataArchive.Status _status;

}