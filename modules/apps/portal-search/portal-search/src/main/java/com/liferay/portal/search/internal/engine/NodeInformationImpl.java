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

package com.liferay.portal.search.internal.engine;

import com.liferay.portal.search.engine.NodeInformation;
import com.liferay.portal.search.engine.NodeInformationBuilder;

/**
 * @author Bryan Engler
 */
public class NodeInformationImpl implements NodeInformation {

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String getVersion() {
		return _version;
	}

	protected NodeInformationImpl(NodeInformationImpl nodeInformationImpl) {
		_name = nodeInformationImpl._name;
		_version = nodeInformationImpl._version;
	}

	protected void setName(String name) {
		_name = name;
	}

	protected void setVersion(String version) {
		_version = version;
	}

	protected static class Builder implements NodeInformationBuilder {

		@Override
		public NodeInformation build() {
			return new NodeInformationImpl(_nodeInformationImpl);
		}

		@Override
		public void name(String name) {
			_nodeInformationImpl.setName(name);
		}

		@Override
		public void version(String version) {
			_nodeInformationImpl.setVersion(version);
		}

		private final NodeInformationImpl _nodeInformationImpl =
			new NodeInformationImpl();

	}

	private NodeInformationImpl() {
	}

	private String _name;
	private String _version;

}