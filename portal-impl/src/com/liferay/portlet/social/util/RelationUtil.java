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

package com.liferay.portlet.social.util;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;

/**
 * @author Adolfo Pérez
 */
public class RelationUtil {

	public static void validateRelations(
			ActionRequest actionRequest, String prefix, String postfix)
		throws Exception {

		boolean interactionsEnabled = ParamUtil.getBoolean(
			actionRequest, prefix + "interactionsEnabled" + postfix);

		if (!interactionsEnabled) {
			return;
		}

		boolean anyUserEnabled = ParamUtil.getBoolean(
			actionRequest, prefix + "interactionsAnyUser" + postfix);

		if (anyUserEnabled) {
			return;
		}

		boolean socialRelationTypesEnabled = ParamUtil.getBoolean(
			actionRequest,
			prefix + "interactionsSocialRelationTypesEnabled" + postfix);
		boolean sitesEnabled = ParamUtil.getBoolean(
			actionRequest, prefix + "interactionsSitesEnabled" + postfix);

		String socialRelationTypes = ParamUtil.getString(
			actionRequest, "settings--interactionsSocialRelationTypes--", null);

		if (socialRelationTypesEnabled && (socialRelationTypes == null)) {
			SessionErrors.add(actionRequest, "selectAtLeastOneRelation");
		}

		if (socialRelationTypesEnabled || sitesEnabled) {
			return;
		}

		SessionErrors.add(actionRequest, "restrictedRelationInvalid");
	}

}