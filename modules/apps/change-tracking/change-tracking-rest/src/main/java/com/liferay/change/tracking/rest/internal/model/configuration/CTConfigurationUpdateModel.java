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

package com.liferay.change.tracking.rest.internal.model.configuration;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Máté Thurzó
 */
@XmlRootElement
public class CTConfigurationUpdateModel {

	public CTConfigurationUpdateModel() {
	}

	public boolean isChangeTrackingEnabled() {
		if (StringUtil.equalsIgnoreCase(
				StringPool.TRUE, _changeTrackingEnabled)) {

			return true;
		}
		else if (StringUtil.equalsIgnoreCase(
					StringPool.FALSE, _changeTrackingEnabled)) {

			return false;
		}

		throw new IllegalArgumentException(
			StringBundler.concat(
				"Invalid changeTrackingEnabled value ", _changeTrackingEnabled,
				". Value should be either true or false."));
	}

	@XmlElement
	public void setChangeTrackingEnabled(String changeTrackingEnabled) {
		_changeTrackingEnabled = changeTrackingEnabled;
	}

	private String _changeTrackingEnabled;

}