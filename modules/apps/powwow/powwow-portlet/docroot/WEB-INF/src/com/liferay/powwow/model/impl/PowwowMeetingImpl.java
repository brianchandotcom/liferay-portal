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

package com.liferay.powwow.model.impl;

import com.liferay.portal.kernel.json.JSONFactoryUtil;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Shinn Lok
 */
public class PowwowMeetingImpl extends PowwowMeetingBaseImpl {

	public PowwowMeetingImpl() {
	}

	@Override
	public Map<String, Serializable> getProviderTypeMetadataMap() {
		if (_providerTypeMetadataMap != null) {
			return _providerTypeMetadataMap;
		}

		String providerTypeMetadata = getProviderTypeMetadata();

		_providerTypeMetadataMap =
			(Map<String, Serializable>)JSONFactoryUtil.deserialize(
				providerTypeMetadata);

		return _providerTypeMetadataMap;
	}

	private Map<String, Serializable> _providerTypeMetadataMap;

}