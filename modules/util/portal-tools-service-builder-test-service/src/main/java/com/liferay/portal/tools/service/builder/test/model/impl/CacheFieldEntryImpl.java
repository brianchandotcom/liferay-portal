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

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.portal.kernel.model.cache.CacheField;

/**
 * @author Brian Wing Shun Chan
 */
public class CacheFieldEntryImpl extends CacheFieldEntryBaseImpl {

	@Override
	public String getNickname() {
		if (_nickname == null) {
			_nickname = "Nickname_" + getName();
		}

		return _nickname;
	}

	@Override
	public void setNickname(String nickname) {
		_nickname = nickname;
	}

	@CacheField(propagateToInterface = true)
	private String _nickname;

}