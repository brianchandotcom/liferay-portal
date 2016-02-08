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

package com.liferay.expando.kernel.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.Serializable;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author Raymond Augé
 */
public interface ExpandoBridge {

	public void addAttribute(String name) throws PortalException;

	public void addAttribute(String name, boolean secure)
		throws PortalException;

	public void addAttribute(String name, int type) throws PortalException;

	public void addAttribute(String name, int type, boolean secure)
		throws PortalException;

	public void addAttribute(String name, int type, Serializable defaultValue)
		throws PortalException;

	public void addAttribute(
			String name, int type, Serializable defaultValue, boolean secure)
		throws PortalException;

	public Serializable getAttribute(String name);

	public Serializable getAttribute(String name, boolean secure);

	public Serializable getAttributeDefault(String name);

	public Enumeration<String> getAttributeNames();

	public UnicodeProperties getAttributeProperties(String name);

	public Map<String, Serializable> getAttributes();

	public Map<String, Serializable> getAttributes(boolean secure);

	public Map<String, Serializable> getAttributes(Collection<String> names);

	public Map<String, Serializable> getAttributes(
		Collection<String> names, boolean secure);

	public int getAttributeType(String name);

	public String getClassName();

	public long getClassPK();

	public long getCompanyId();

	public boolean hasAttribute(String name);

	public boolean isIndexEnabled();

	public void setAttribute(String name, Serializable value);

	public void setAttribute(String name, Serializable value, boolean secure);

	public void setAttributeDefault(String name, Serializable defaultValue);

	public void setAttributeProperties(
		String name, UnicodeProperties properties);

	public void setAttributeProperties(
		String name, UnicodeProperties properties, boolean secure);

	public void setAttributes(Map<String, Serializable> attributes);

	public void setAttributes(
		Map<String, Serializable> attributes, boolean secure);

	public void setAttributes(ServiceContext serviceContext);

	public void setAttributes(ServiceContext serviceContext, boolean secure);

	public void setClassName(String className);

	public void setClassPK(long classPK);

	public void setCompanyId(long companyId);

	public void setIndexEnabled(boolean indexEnabled);

}