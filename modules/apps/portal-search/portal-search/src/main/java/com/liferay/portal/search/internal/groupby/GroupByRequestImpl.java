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

package com.liferay.portal.search.internal.groupby;

import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.search.groupby.GroupByRequest;

import java.io.Serializable;

/**
 * @author Michael C. Han
 * @author Bryan Engler
 */
public class GroupByRequestImpl implements GroupByRequest, Serializable {

	public GroupByRequestImpl(String field) {
		_field = field;
	}

	@Override
	public int getDocsSize() {
		return _docsSize;
	}

	@Override
	public Sort[] getDocsSorts() {
		return _docsSorts;
	}

	@Override
	public int getDocsStart() {
		return _docsStart;
	}

	@Override
	public String getField() {
		return _field;
	}

	@Override
	public int getTermsSize() {
		return _termsSize;
	}

	@Override
	public Sort[] getTermsSorts() {
		return _termsSorts;
	}

	@Override
	public int getTermsStart() {
		return _termsStart;
	}

	@Override
	public void setDocsSize(int docsSize) {
		_docsSize = docsSize;
	}

	@Override
	public void setDocsSorts(Sort... docsSorts) {
		_docsSorts = docsSorts;
	}

	@Override
	public void setDocsStart(int docsStart) {
		_docsStart = docsStart;
	}

	@Override
	public void setField(String field) {
		_field = field;
	}

	@Override
	public void setTermsSize(int termsSize) {
		_termsSize = termsSize;
	}

	@Override
	public void setTermsSorts(Sort... termsSorts) {
		_termsSorts = termsSorts;
	}

	@Override
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