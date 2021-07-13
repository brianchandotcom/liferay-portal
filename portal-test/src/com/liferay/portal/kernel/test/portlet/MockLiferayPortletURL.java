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

package com.liferay.portal.kernel.test.portlet;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.io.IOException;
import java.io.Writer;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import javax.portlet.MutableRenderParameters;
import javax.portlet.MutableResourceParameters;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletSecurityException;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import javax.portlet.annotations.PortletSerializable;

/**
 * @author Cristina González
 */
public class MockLiferayPortletURL implements LiferayPortletURL {

	@Override
	public void addParameterIncludedInPath(String name) {
	}

	@Override
	public void addProperty(String name, String value) {
	}

	@Override
	public Appendable append(Appendable appendable) throws IOException {
		return null;
	}

	@Override
	public Appendable append(Appendable appendable, boolean escapeXML)
		throws IOException {

		return null;
	}

	@Override
	public String getCacheability() {
		return null;
	}

	@Override
	public String getLifecycle() {
		return null;
	}

	@Override
	public String getParameter(String name) {
		String[] parameters = _parameters.get(name);

		if (ArrayUtil.isEmpty(parameters)) {
			return null;
		}

		return parameters[0];
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return _parameters;
	}

	@Override
	public Set<String> getParametersIncludedInPath() {
		return null;
	}

	@Override
	public long getPlid() {
		return 0;
	}

	@Override
	public String getPortletId() {
		return _portletId;
	}

	@Override
	public PortletMode getPortletMode() {
		return null;
	}

	@Override
	public Set<String> getRemovedParameterNames() {
		return null;
	}

	@Override
	public MutableRenderParameters getRenderParameters() {
		return null;
	}

	@Override
	public String getResourceID() {
		return null;
	}

	@Override
	public MutableResourceParameters getResourceParameters() {
		return null;
	}

	@Override
	public WindowState getWindowState() {
		return null;
	}

	@Override
	public boolean isAnchor() {
		return false;
	}

	@Override
	public boolean isCopyCurrentRenderParameters() {
		return false;
	}

	@Override
	public boolean isEncrypt() {
		return false;
	}

	@Override
	public boolean isEscapeXml() {
		return false;
	}

	@Override
	public boolean isParameterIncludedInPath(String name) {
		return false;
	}

	@Override
	public boolean isSecure() {
		return false;
	}

	@Override
	public void removePublicRenderParameter(String name) {
	}

	@Override
	public void setAnchor(boolean anchor) {
	}

	@Override
	public void setBeanParameter(PortletSerializable portletSerializable) {
	}

	@Override
	public void setCacheability(String cacheLevel) {
	}

	@Override
	public void setCopyCurrentRenderParameters(
		boolean copyCurrentRenderParameters) {
	}

	@Override
	public void setDoAsGroupId(long doAsGroupId) {
	}

	@Override
	public void setDoAsUserId(long doAsUserId) {
	}

	@Override
	public void setDoAsUserLanguageId(String doAsUserLanguageId) {
	}

	@Override
	public void setEncrypt(boolean encrypt) {
	}

	@Override
	public void setEscapeXml(boolean escapeXml) {
	}

	@Override
	public void setLifecycle(String lifecycle) {
	}

	@Override
	public void setParameter(String name, String value) {
		_parameters.put(name, new String[] {value});
	}

	@Override
	public void setParameter(String name, String... values) {
		_parameters.put(name, values);
	}

	@Override
	public void setParameter(String name, String value, boolean append) {
		_parameters.put(name, new String[] {value});
	}

	@Override
	public void setParameter(String name, String[] values, boolean append) {
		_parameters.put(name, values);
	}

	@Override
	public void setParameters(Map<String, String[]> parameters) {
		_parameters = parameters;
	}

	@Override
	public void setPlid(long plid) {
	}

	@Override
	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	@Override
	public void setPortletMode(PortletMode portletMode)
		throws PortletModeException {
	}

	@Override
	public void setProperty(String name, String value) {
	}

	@Override
	public void setRefererGroupId(long refererGroupId) {
	}

	@Override
	public void setRefererPlid(long refererPlid) {
	}

	@Override
	public void setRemovedParameterNames(Set<String> removedParamNames) {
	}

	@Override
	public void setResourceID(String resourceID) {
	}

	@Override
	public void setSecure(boolean secure) throws PortletSecurityException {
	}

	@Override
	public void setWindowState(WindowState windowState)
		throws WindowStateException {
	}

	@Override
	public void setWindowStateRestoreCurrentView(
		boolean windowStateRestoreCurrentView) {
	}

	@Override
	public String toString() {
		Set<Map.Entry<String, String[]>> entries = _parameters.entrySet();

		StringBundler sb = new StringBundler();

		if (isSecure()) {
			sb.append("https");
		}
		else {
			sb.append("http");
		}

		sb.append("//localhost/test?");

		for (Map.Entry<String, String[]> entry : entries) {
			sb.append(_portletId);
			sb.append("_");
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(entry.getValue()[0]);
			sb.append(";");
		}

		if (!entries.isEmpty()) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	@Override
	public void visitReservedParameters(BiConsumer<String, String> biConsumer) {
	}

	@Override
	public void write(Writer writer) throws IOException {
	}

	@Override
	public void write(Writer writer, boolean escapeXML) throws IOException {
	}

	private Map<String, String[]> _parameters = new ConcurrentHashMap<>();
	private String _portletId = "param";

}