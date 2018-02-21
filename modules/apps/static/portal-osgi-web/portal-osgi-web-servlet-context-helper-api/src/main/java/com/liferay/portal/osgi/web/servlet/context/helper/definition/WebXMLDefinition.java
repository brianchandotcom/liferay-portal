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

package com.liferay.portal.osgi.web.servlet.context.helper.definition;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.osgi.web.servlet.context.helper.order.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class WebXMLDefinition {

	public void addListenerDefinition(ListenerDefinition listenerDefinition) {
		_listenerDefinitions.add(listenerDefinition);
	}

	@Override
	public Object clone() {
		WebXMLDefinition webXMLDefinition = new WebXMLDefinition();

		webXMLDefinition.setAbsoluteOrderingNames(_absoluteOrderingNames);
		webXMLDefinition.setContextParameters(_contextParameters);
		webXMLDefinition.setFilterDefinitions(_filterDefinitions);
		webXMLDefinition.setFragmentName(_fragmentName);
		webXMLDefinition.setJspTaglibMappings(_jspTaglibMappings);
		webXMLDefinition.setListenerDefinitions(_listenerDefinitions);
		webXMLDefinition.setMetadataComplete(_metadataComplete);
		webXMLDefinition.setOrder(_order);
		webXMLDefinition.setServletDefinitions(_servletDefinitions);
		webXMLDefinition.setWebResourceCollectionDefinitions(
			_webResourceCollectionDefinitions);

		return webXMLDefinition;
	}

	public List<String> getAbsoluteOrderingNames() {
		return _absoluteOrderingNames;
	}

	public Map<String, String> getContextParameters() {
		return _contextParameters;
	}

	public Exception getException() {
		return _exception;
	}

	public Map<String, FilterDefinition> getFilterDefinitions() {
		return _filterDefinitions;
	}

	public String getFragmentName() {
		return _fragmentName;
	}

	public Map<String, String> getJspTaglibMappings() {
		return _jspTaglibMappings;
	}

	public List<ListenerDefinition> getListenerDefinitions() {
		return _listenerDefinitions;
	}

	public Order getOrder() {
		return _order;
	}

	public Map<String, ServletDefinition> getServletDefinitions() {
		return _servletDefinitions;
	}

	public List<WebResourceCollectionDefinition>
		getWebResourceCollectionDefinitions() {

		return _webResourceCollectionDefinitions;
	}

	public boolean isMetadataComplete() {
		return _metadataComplete;
	}

	public void setAbsoluteOrderingNames(List<String> absoluteOrderingNames) {
		_absoluteOrderingNames = absoluteOrderingNames;
	}

	public void setContextParameter(String name, String value) {
		_contextParameters.put(name, value);
	}

	public void setContextParameters(Map<String, String> contextParameters) {
		_contextParameters = contextParameters;
	}

	public void setException(Exception exception) {
		_exception = exception;
	}

	public void setFilterDefinition(
		String name, FilterDefinition filterDefinition) {

		_filterDefinitions.put(name, filterDefinition);
	}

	public void setFilterDefinitions(
		Map<String, FilterDefinition> filterDefinitions) {

		_filterDefinitions = filterDefinitions;
	}

	public void setFragmentName(String fragmentName) {
		_fragmentName = fragmentName;
	}

	public void setJspTaglibMappings(Map<String, String> jspTaglibMappings) {
		_jspTaglibMappings.putAll(jspTaglibMappings);
	}

	public void setListenerDefinition(ListenerDefinition listenerDefinition) {
		_listenerDefinitions.add(listenerDefinition);
	}

	public void setListenerDefinitions(
		List<ListenerDefinition> listenerDefinitions) {

		_listenerDefinitions = listenerDefinitions;
	}

	public void setMetadataComplete(boolean metadataComplete) {
		_metadataComplete = metadataComplete;
	}

	public void setOrder(Order order) {
		_order = order;
	}

	public void setServletDefinition(
		String name, ServletDefinition servletDefinition) {

		_servletDefinitions.put(name, servletDefinition);
	}

	public void setServletDefinitions(
		Map<String, ServletDefinition> servletDefinitions) {

		_servletDefinitions = servletDefinitions;
	}

	public void setWebResourceCollectionDefinitions(
		List<WebResourceCollectionDefinition>
			webResourceCollectionDefinitions) {

		_webResourceCollectionDefinitions = webResourceCollectionDefinitions;
	}

	private List<String> _absoluteOrderingNames = new ArrayList<>();
	private Map<String, String> _contextParameters = new HashMap<>();
	private Exception _exception;
	private Map<String, FilterDefinition> _filterDefinitions = new HashMap<>();
	private String _fragmentName = StringPool.BLANK;
	private final Map<String, String> _jspTaglibMappings = new HashMap<>();
	private List<ListenerDefinition> _listenerDefinitions = new ArrayList<>();
	private boolean _metadataComplete;
	private Order _order = new Order();
	private Map<String, ServletDefinition> _servletDefinitions =
		new HashMap<>();
	private List<WebResourceCollectionDefinition>
		_webResourceCollectionDefinitions = new ArrayList<>();

}