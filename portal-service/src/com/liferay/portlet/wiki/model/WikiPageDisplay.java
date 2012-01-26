/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.model;

import java.io.Serializable;

/**
 * @author Jorge Ferrer
 */
public interface WikiPageDisplay extends Serializable {

	public String[] getAttachments();

	public String getContent();

	public String getFormat();

	public String getFormattedContent();

	public boolean getHead();

	public long getNodeId();

	public String getTitle();

	public long getUserId();

	public double getVersion();

	public boolean isHead();

	public void setAttachments(String[] attachments);

	public void setContent(String content);

	public void setFormat(String format);

	public void setFormattedContent(String formattedContent);

	public void setHead(boolean head);

	public void setNodeId(long nodeId);

	public void setTitle(String title);

	public void setUserId(long userId);

	public void setVersion(double version);

}