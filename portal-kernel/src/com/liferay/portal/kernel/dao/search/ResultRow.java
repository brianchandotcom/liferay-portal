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

package com.liferay.portal.kernel.dao.search;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Raymond Augé
 */
public interface ResultRow {

	public void addButton(int index, String name, String href);

	public void addButton(
		int index, String align, String valign, int colspan, String name,
		String href);

	public void addButton(String name, String href);

	public void addButton(
		String align, String valign, int colspan, String name, String href);

	public void addButton(
		String align, String valign, String name, String href);

	public void addDate(Date date);

	public void addDate(Date date, PortletURL portletURL);

	public void addDate(Date date, String href);

	public void addDate(int index, Date date, String href);

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #addJSP(String,
	 *             String, int, String, ServletContext, HttpServletRequest ,
	 *             HttpServletResponse)}
	 */
	@Deprecated
	public void addJSP(int index, String path);

	public void addJSP(
		int index, String path, ServletContext servletContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse);

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public void addJSP(
		int index, String align, String valign, int colspan, String path);

	public void addJSP(
		int index, String align, String valign, int colspan, String path,
		ServletContext servletContext, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse);

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #addJSP(String,
	 *             String, ServletContext, HttpServletRequest ,
	 *             HttpServletResponse)}
	 */
	@Deprecated
	public void addJSP(String path);

	public void addJSP(
		String path, ServletContext servletContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse);

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #addJSP(String,
	 *             String, ServletContext, HttpServletRequest ,
	 *             HttpServletResponse)}
	 */
	@Deprecated
	public void addJSP(String path, String cssClass);

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #addJSP(String,
	 *             String, int, String, ServletContext, HttpServletRequest ,
	 *             HttpServletResponse)}
	 */
	@Deprecated
	public void addJSP(String align, String valign, int colspan, String path);

	public void addJSP(
		String align, String valign, int colspan, String path,
		ServletContext servletContext, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse);

	public void addJSP(
		String path, String cssClass, ServletContext servletContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse);

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #addJSP(String,
	 *             String, ServletContext, HttpServletRequest ,
	 *             HttpServletResponse)}
	 */
	@Deprecated
	public void addJSP(String align, String valign, String path);

	public void addJSP(
		String align, String valign, String path, ServletContext servletContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse);

	public void addSearchEntry(int index, SearchEntry searchEntry);

	public void addSearchEntry(SearchEntry searchEntry);

	public void addStatus(int status);

	public void addStatus(
		int index, int status, long statusByUserId, Date statusDate,
		String href);

	public void addStatus(
		int index, int status, String href, ServletContext servletContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse);

	public void addStatus(int status, long statusByUserId, Date statusDate);

	public void addStatus(
		int status, long statusByUserId, Date statusDate,
		PortletURL portletURL);

	public void addStatus(
		int status, long statusByUserId, Date statusDate, String href);

	public void addStatus(int status, PortletURL portletURL);

	public void addStatus(int status, String href);

	public void addText(int index, String name);

	public void addText(int index, String name, PortletURL portletURL);

	public void addText(int index, String name, String href);

	public void addText(
		int index, String align, String valign, int colspan, String name);

	public void addText(
		int index, String align, String valign, int colspan, String name,
		PortletURL portletURL);

	public void addText(
		int index, String align, String valign, int colspan, String name,
		String href);

	public void addText(String name);

	public void addText(String name, PortletURL portletURL);

	public void addText(String name, String href);

	public void addText(String align, String valign, int colspan, String name);

	public void addText(
		String align, String valign, int colspan, String name,
		PortletURL portletURL);

	public void addText(
		String align, String valign, int colspan, String name, String href);

	public void addText(String align, String valign, String name);

	public void addText(
		String align, String valign, String name, PortletURL portletURL);

	public void addText(String align, String valign, String name, String href);

	public String getClassHoverName();

	public String getClassName();

	public String getCssClass();

	public Map<String, Object> getData();

	public List<SearchEntry> getEntries();

	public Object getObject();

	public Object getParameter(String param);

	public int getPos();

	public String getPrimaryKey();

	public String getRowId();

	public String getState();

	public boolean isBold();

	public boolean isRestricted();

	public boolean isSkip();

	public void removeSearchEntry(int pos);

	public void setBold(boolean bold);

	public void setClassHoverName(String classHoverName);

	public void setClassName(String className);

	public void setCssClass(String cssClass);

	public void setData(Map<String, Object> data);

	public void setObject(Object obj);

	public void setParameter(String param, Object value);

	public void setPrimaryKey(String primaryKey);

	public void setRestricted(boolean restricted);

	public void setRowId(String rowId);

	public void setSkip(boolean skip);

	public void setState(String state);

}