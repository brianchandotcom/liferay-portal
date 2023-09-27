/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.exception;

import com.liferay.portal.kernel.exception.PortalException;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Gergely Mathe
 */
public class ExportImportContentValidationException extends PortalException {

	public static final int ARTICLE_NOT_FOUND = 6;

	public static final int DEFAULT = 1;

	public static final int FILE_ENTRY_NOT_FOUND = 5;

	public static final int JOURNAL_FEED_NOT_FOUND = 7;

	public static final int LAYOUT_GROUP_NOT_FOUND = 2;

	public static final int LAYOUT_NOT_FOUND = 3;

	public static final int LAYOUT_WITH_URL_NOT_FOUND = 4;

	public ExportImportContentValidationException() {
	}

	public ExportImportContentValidationException(String className) {
		_className = className;
	}

	public ExportImportContentValidationException(
		String className, Throwable throwable) {

		super(throwable);

		_className = className;
	}

	public String getClassName() {
		return _className;
	}

	public String getDlReference() {
		return _dlReference;
	}

	public Map<String, String[]> getDlReferenceParameters() {
		return _dlReferenceParameters;
	}

	public String getGroupFriendlyURL() {
		return _groupFriendlyURL;
	}

	public String getJournalArticleFeedURL() {
		return _journalArticleFeedURL;
	}

	public Map<String, String> getLayoutReferenceParameters() {
		return _layoutReferenceParameters;
	}

	public String getLayoutURL() {
		return _layoutURL;
	}

	public String getStagedModelClassName() {
		return _stagedModelClassName;
	}

	public Serializable getStagedModelPrimaryKeyObj() {
		return _stagedModelPrimaryKeyObj;
	}

	public int getType() {
		return _type;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setDlReference(String dlReference) {
		_dlReference = dlReference;
	}

	public void setDlReferenceParameters(
		Map<String, String[]> dlReferenceParameters) {

		_dlReferenceParameters = dlReferenceParameters;
	}

	public void setGroupFriendlyURL(String groupFriendlyURL) {
		_groupFriendlyURL = groupFriendlyURL;
	}

	public void setJournalArticleFeedURL(String journalArticleFeedURL) {
		_journalArticleFeedURL = journalArticleFeedURL;
	}

	public void setLayoutReferenceParameters(
		Map<String, String> layoutReferenceParameters) {

		_layoutReferenceParameters = layoutReferenceParameters;
	}

	public void setLayoutURL(String layoutURL) {
		_layoutURL = layoutURL;
	}

	public void setStagedModelClassName(String stagedModelClassName) {
		_stagedModelClassName = stagedModelClassName;
	}

	public void setStagedModelPrimaryKeyObj(
		Serializable stagedModelPrimaryKeyObj) {

		_stagedModelPrimaryKeyObj = stagedModelPrimaryKeyObj;
	}

	public void setType(int type) {
		_type = type;
	}

	private String _className;
	private String _dlReference;
	private Map<String, String[]> _dlReferenceParameters;
	private String _groupFriendlyURL;
	private String _journalArticleFeedURL;
	private Map<String, String> _layoutReferenceParameters;
	private String _layoutURL;
	private String _stagedModelClassName;
	private Serializable _stagedModelPrimaryKeyObj;
	private int _type = DEFAULT;

}