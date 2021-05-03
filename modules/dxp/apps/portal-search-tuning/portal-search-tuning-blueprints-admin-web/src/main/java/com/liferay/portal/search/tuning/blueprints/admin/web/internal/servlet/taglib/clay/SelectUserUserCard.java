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

package com.liferay.portal.search.tuning.blueprints.admin.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseUserCard;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.model.User;

import javax.portlet.RenderRequest;

/**
 * @author David Arques
 */
public class SelectUserUserCard extends BaseUserCard {

	public SelectUserUserCard(
		User user, RenderRequest renderRequest, RowChecker rowChecker) {

		super(user, renderRequest, rowChecker);
	}

}