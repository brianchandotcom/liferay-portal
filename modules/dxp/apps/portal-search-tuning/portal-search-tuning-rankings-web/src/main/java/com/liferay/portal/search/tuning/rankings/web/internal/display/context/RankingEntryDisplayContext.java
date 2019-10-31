/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.rankings.web.internal.display.context;

import java.util.Date;

/**
 * @author Wade Cao
 */
public class RankingEntryDisplayContext {

	public String getAliases() {
		return _aliases;
	}

	public Date getDisplayDate() {
		return null;
	}

	public String getHiddenResultsCount() {
		return _hiddenResultsCount;
	}

	public String getIndex() {
		return _index;
	}

	public String getKeywords() {
		return _keywords;
	}

	public Date getModifiedDate() {
		return null;
	}

	public String getPinnedResultsCount() {
		return _pinnedResultsCount;
	}

	public int getStatus() {
		return _status;
	}

	public String getUid() {
		return _uid;
	}

	public void setAliases(String aliases) {
		_aliases = aliases;
	}

	public void setHiddenResultsCount(String hiddenResultsCount) {
		_hiddenResultsCount = hiddenResultsCount;
	}

	public void setIndex(String index) {
		_index = index;
	}

	public void setKeywords(String keywords) {
		_keywords = keywords;
	}

	public void setPinnedResultsCount(String pinnedResultsCount) {
		_pinnedResultsCount = pinnedResultsCount;
	}

	public void setStatus(int status) {
		_status = status;
	}

	public void setUid(String uid) {
		_uid = uid;
	}

	private String _aliases;
	private String _hiddenResultsCount;
	private String _index;
	private String _keywords;
	private String _pinnedResultsCount;
	private int _status;
	private String _uid;

}