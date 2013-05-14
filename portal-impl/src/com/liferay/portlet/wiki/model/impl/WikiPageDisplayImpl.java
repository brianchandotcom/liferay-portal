/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.model.impl;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portlet.wiki.model.WikiPageDisplay;

import java.util.List;

/**
 * @author Jorge Ferrer
 */
public class WikiPageDisplayImpl implements WikiPageDisplay {

	public WikiPageDisplayImpl(
		long userId, long nodeId, String title, double version, String content,
		String formattedContent, String format, boolean head,
		List<FileEntry> attachmentsFileEntries) {

		_userId = userId;
		_nodeId = nodeId;
		_title = title;
		_version = version;
		_content = content;
		_formattedContent = formattedContent;
		_format = format;
		_head = head;
		_attachmentsFileEntries = attachmentsFileEntries;
	}

	@Override
	public List<FileEntry> getAttachmentsFileEntries() {
		return _attachmentsFileEntries;
	}

	@Override
	public String getContent() {
		return _content;
	}

	@Override
	public String getFormat() {
		return _format;
	}

	@Override
	public String getFormattedContent() {
		return _formattedContent;
	}

	@Override
	public boolean getHead() {
		return _head;
	}

	@Override
	public long getNodeId() {
		return _nodeId;
	}

	@Override
	public String getTitle() {
		return _title;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public double getVersion() {
		return _version;
	}

	@Override
	public boolean isHead() {
		return _head;
	}

	@Override
	public void setAttachmentsFileEntries(
		List<FileEntry> attachmentsFileEntries) {

		_attachmentsFileEntries = attachmentsFileEntries;
	}

	@Override
	public void setContent(String content) {
		_content = content;
	}

	@Override
	public void setFormat(String format) {
		_format = format;
	}

	@Override
	public void setFormattedContent(String formattedContent) {
		_formattedContent = formattedContent;
	}

	@Override
	public void setHead(boolean head) {
		_head = head;
	}

	@Override
	public void setNodeId(long nodeId) {
		_nodeId = nodeId;
	}

	@Override
	public void setTitle(String title) {
		_title = title;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	public void setVersion(double version) {
		_version = version;
	}

	private List<FileEntry> _attachmentsFileEntries;
	private String _content;
	private String _format;
	private String _formattedContent;
	private boolean _head;
	private long _nodeId;
	private String _title;
	private long _userId;
	private double _version;

}