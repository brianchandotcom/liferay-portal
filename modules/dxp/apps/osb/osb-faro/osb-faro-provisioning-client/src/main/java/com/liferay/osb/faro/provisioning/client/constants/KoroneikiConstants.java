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

package com.liferay.osb.faro.provisioning.client.constants;

/**
 * @author Marcos Martins
 */
public class KoroneikiConstants {

	public static final String ACCOUNT_KEY_PREFIX = "KOR-";

	public static final String CONTACT_ROLE_NAME_MEMBER = "Member";

	public static final String DOMAIN_ANALYTICS_CLOUD = "analytics-cloud";

	public static final String DOMAIN_DOSSIERA = "dossiera";

	public static final String DOMAIN_WEB = "web";

	public static final String ENTITY_NAME_ACCOUNT = "account";

	public static final String ENTITY_NAME_CORP_PROJECT = "corp-project";

	public static final String ENTITY_NAME_WORKSPACE = "workspace";

	public static String translateContactRoleName(String roleName) {
		if (roleName.equals("OSB Corp Analytics Cloud Owner")) {
			return "Analytics Cloud Owner";
		}
		else if (roleName.equals("OSB Corp LCS User")) {
			return "LCS User";
		}

		return roleName;
	}

}