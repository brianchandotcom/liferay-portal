/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.web.internal.model.display;

import com.liferay.portal.kernel.model.BaseModel;

/**
 * @author Shinn Lok
 */
public abstract class FaroModelDisplay {

	public FaroModelDisplay() {
	}

	public FaroModelDisplay(BaseModel<?> baseModel) {
		_id = (Long)baseModel.getPrimaryKeyObj();
	}

	public long getId() {
		return _id;
	}

	private long _id;

}