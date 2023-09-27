/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectLayoutTab;

import java.util.List;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectLayoutImpl extends ObjectLayoutBaseImpl {

	@Override
	public List<ObjectLayoutTab> getObjectLayoutTabs() {
		return _objectLayoutTabs;
	}

	@Override
	public void setObjectLayoutTabs(List<ObjectLayoutTab> objectLayoutTabs) {
		_objectLayoutTabs = objectLayoutTabs;
	}

	private List<ObjectLayoutTab> _objectLayoutTabs;

}