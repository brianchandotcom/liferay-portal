/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.document;

/**
 * @author Michael C. Han
 */
public class BulkDocumentItemResponse {

	public Exception getCause() {
		return _cause;
	}

	public String getFailureMessage() {
		return _failureMessage;
	}

	public String getId() {
		return _id;
	}

	public String getIndex() {
		return _index;
	}

	public String getResult() {
		return _result;
	}

	public int getStatus() {
		return _status;
	}

	public String getType() {
		return _type;
	}

	public long getVersion() {
		return _version;
	}

	public boolean isAborted() {
		return _aborted;
	}

	public void setAborted(boolean aborted) {
		_aborted = aborted;
	}

	public void setCause(Exception exception) {
		_cause = exception;
	}

	public void setFailureMessage(String failureMessage) {
		_failureMessage = failureMessage;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setIndex(String index) {
		_index = index;
	}

	public void setResult(String result) {
		_result = result;
	}

	public void setStatus(int status) {
		_status = status;
	}

	public void setType(String type) {
		_type = type;
	}

	public void setVersion(long version) {
		_version = version;
	}

	private boolean _aborted;
	private Exception _cause;
	private String _failureMessage;
	private String _id;
	private String _index;
	private String _result;
	private int _status;
	private String _type;
	private long _version;

}