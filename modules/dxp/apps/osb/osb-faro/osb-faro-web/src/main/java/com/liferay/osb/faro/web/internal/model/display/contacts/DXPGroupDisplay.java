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

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.engine.client.model.DXPGroup;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class DXPGroupDisplay {

	public DXPGroupDisplay() {
	}

	public DXPGroupDisplay(DXPGroup dxpGroup) {
		_friendlyURL = dxpGroup.getFriendlyURL();
		_groupId = dxpGroup.getGroupId();
		_id = GetterUtil.getString(
			dxpGroup.getId(), String.valueOf(dxpGroup.getGroupId()));
		_name = GetterUtil.getString(
			dxpGroup.getDescriptiveName(), dxpGroup.getName());
	}

	private String _friendlyURL;
	private long _groupId;
	private String _id;
	private String _name;

}