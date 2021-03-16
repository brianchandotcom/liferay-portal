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

package com.liferay.portal.search.tuning.blueprints.constants;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.security.permission.ActionKeys;

/**
 * @author Petteri Karttunen
 */
public class BlueprintsActionKeys {

	public static final String ADD_BLUEPRINT = "ADD_BLUEPRINT";

	public static final String ADD_ELEMENT = "ADD_ELEMENT";

	public static final String APPLY_BLUEPRINT = "APPLY_BLUEPRINT";

	public static final String DELETE_BLUEPRINT = "DELETE_BLUEPRINT";

	public static final String DELETE_ELEMENT = "DELETE_ELEMENT";

	public static final String UPDATE_BLUEPRINT = "UPDATE_BLUEPRINT";

	public static final String UPDATE_ELEMENT = "UPDATE_ELEMENT";

	public static final String VIEW_BLUEPRINT = "VIEW_BLUEPRINT";

	public static final String VIEW_ELEMENT = "VIEW_ELEMENT";

	public static String getActionKeyForBlueprintType(int type, String action) {
		if (type == BlueprintTypes.BLUEPRINT) {
			if (ActionKeys.VIEW.equals(action)) {
				return BlueprintsActionKeys.VIEW_BLUEPRINT;
			}
			else if (ActionKeys.ADD_ENTRY.equals(action)) {
				return BlueprintsActionKeys.ADD_BLUEPRINT;
			}
			else if (ActionKeys.UPDATE.equals(action)) {
				return BlueprintsActionKeys.UPDATE_BLUEPRINT;
			}
			else if (ActionKeys.DELETE.equals(action)) {
				return BlueprintsActionKeys.DELETE_BLUEPRINT;
			}
			else {
				return action;
			}
		}
		else if ((type == BlueprintTypes.AGGREGATION_ELEMENT) ||
				 (type == BlueprintTypes.FACET_ELEMENT) ||
				 (type == BlueprintTypes.QUERY_ELEMENT) ||
				 (type == BlueprintTypes.SUGGESTER_ELEMENT)) {

			if (ActionKeys.VIEW.equals(action)) {
				return BlueprintsActionKeys.VIEW_ELEMENT;
			}
			else if (ActionKeys.ADD_ENTRY.equals(action)) {
				return BlueprintsActionKeys.ADD_ELEMENT;
			}
			else if (ActionKeys.UPDATE.equals(action)) {
				return BlueprintsActionKeys.UPDATE_ELEMENT;
			}
			else if (ActionKeys.DELETE.equals(action)) {
				return BlueprintsActionKeys.DELETE_ELEMENT;
			}
			else {
				return action;
			}
		}

		StringBundler sb = new StringBundler(5);

		sb.append("Unknown type ");
		sb.append(type);
		sb.append(" for ");
		sb.append(action);
		sb.append(".");

		throw new RuntimeException(sb.toString());
	}

}