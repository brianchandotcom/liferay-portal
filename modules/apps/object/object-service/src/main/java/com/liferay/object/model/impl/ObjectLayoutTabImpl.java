/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectLayoutBox;

import java.util.Collections;
import java.util.List;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectLayoutTabImpl extends ObjectLayoutTabBaseImpl {

	@Override
	public List<ObjectLayoutBox> getObjectLayoutBoxes() {
		return _objectLayoutBoxes;
	}

	@Override
	public void setObjectLayoutBoxes(List<ObjectLayoutBox> objectLayoutBoxes) {
		_objectLayoutBoxes = objectLayoutBoxes;
	}

	private List<ObjectLayoutBox> _objectLayoutBoxes = Collections.emptyList();

}