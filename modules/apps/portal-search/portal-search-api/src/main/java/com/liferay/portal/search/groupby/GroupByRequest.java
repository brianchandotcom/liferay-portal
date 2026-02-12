/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.groupby;

import com.liferay.portal.kernel.search.Sort;

/**
 * @author Bryan Engler
 * @author Michael C. Han
 */
public class GroupByRequest {

	public GroupByRequest(String field) {
		_field = field;
	}

	public int getDocsSize() {
		return _docsSize;
	}

	public Sort[] getDocsSorts() {
		return _docsSorts;
	}

	public int getDocsStart() {
		return _docsStart;
	}

	public String getField() {
		return _field;
	}

	public int getTermsSize() {
		return _termsSize;
	}

	public Sort[] getTermsSorts() {
		return _termsSorts;
	}

	public int getTermsStart() {
		return _termsStart;
	}

	public void setDocsSize(int docsSize) {
		_docsSize = docsSize;
	}

	public void setDocsSorts(Sort... docsSorts) {
		_docsSorts = docsSorts;
	}

	public void setDocsStart(int docsStart) {
		_docsStart = docsStart;
	}

	public void setField(String field) {
		_field = field;
	}

	public void setTermsSize(int termsSize) {
		_termsSize = termsSize;
	}

	public void setTermsSorts(Sort... termsSorts) {
		_termsSorts = termsSorts;
	}

	public void setTermsStart(int termsStart) {
		_termsStart = termsStart;
	}

	private int _docsSize;
	private Sort[] _docsSorts;
	private int _docsStart;
	private String _field;
	private int _termsSize;
	private Sort[] _termsSorts;
	private int _termsStart;

}