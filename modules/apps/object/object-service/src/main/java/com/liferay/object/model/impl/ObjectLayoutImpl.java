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

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectLayoutTab;

import java.util.List;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectLayoutImpl extends ObjectLayoutBaseImpl {

	public List<ObjectLayoutTab> getObjectLayoutTabs() {
		return _objectLayoutTabs;
	}

	public void setObjectLayoutTabs(List<ObjectLayoutTab> objectLayoutTabs) {
		_objectLayoutTabs = objectLayoutTabs;
	}

	private List<ObjectLayoutTab> _objectLayoutTabs;

}