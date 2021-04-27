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
 * The extended model implementation for the CacheFieldEntry service. Represents a row in the &quot;CacheFieldEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.portal.tools.service.builder.test.model.CacheFieldEntry</code> interface.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class CacheFieldEntryImpl extends CacheFieldEntryBaseImpl {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. All methods that expect a cache field entry model instance should use the {@link com.liferay.portal.tools.service.builder.test.model.CacheFieldEntry} interface instead.
	 */
	public CacheFieldEntryImpl() {
	}

	@Override
	public String getNickName() {
		if (_nickName == null) {
			_nickName = "NickName_" + getName();
		}

		return _nickName;
	}

	@Override
	public void setNickName(String nickName) {
		_nickName = nickName;
	}

	@CacheField
	private String _nickName;

}