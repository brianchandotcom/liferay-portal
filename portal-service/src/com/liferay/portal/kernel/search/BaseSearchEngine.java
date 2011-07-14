/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.cluster.Priority;

/**
 * @author Bruno Farache
 */
public class BaseSearchEngine implements SearchEngine {

	public Priority getClusteredWritePriority() {
		return _clusteredWritePriority;
	}

	public String getName() {
		return _name;
	}

	public IndexSearcher getSearcher() {
		return _searcher;
	}

	public IndexWriter getWriter() {
		return _writer;
	}

	public String getVendor() {
		return _vendor;
	}

	public boolean isClusteredWrite() {
		return _clusteredWrite;
	}

	public boolean isLuceneBased() {
		return _luceneBased;
	}

	public void setClusteredWrite(boolean clusteredWrite) {
		_clusteredWrite = clusteredWrite;
	}

	public void setClusteredWritePriority(Priority clusteredWritePriority) {
		_clusteredWritePriority = clusteredWritePriority;
	}

	public void setLuceneBased(boolean luceneBased) {
		_luceneBased = luceneBased;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setSearcher(IndexSearcher searcher) {
		_searcher = searcher;
	}

	public void setVendor(String vendor) {
		_vendor = vendor;
	}

	public void setWriter(IndexWriter writer) {
		_writer = writer;
	}

	private boolean _clusteredWrite;
	private Priority _clusteredWritePriority;
	private boolean _luceneBased;
	private String _name;
	private IndexSearcher _searcher;
	private String _vendor;
	private IndexWriter _writer;
}