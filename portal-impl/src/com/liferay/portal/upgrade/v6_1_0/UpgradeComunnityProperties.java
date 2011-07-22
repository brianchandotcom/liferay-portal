/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PropsKeys;

import java.io.IOException;

import java.sql.SQLException;

/**
 * @author Miguel Pastor
 */
public class UpgradeComunnityProperties extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateCommunityFromAddressProperty();
		updateCommunityFromNameProperty();
		updateCommunityLogoProperty();
		updateCommunityMembershipReplyBodyProperty();
		updateCommunityMembershipReplySubjectProperty();
		updateCommunityMembershipRequestBodyProperty();
		updateCommunityMembershipRequestSubjectProperty();
		updatePortletPreferencesCommunityNameVariable();
		updatePortletPreferenceMBCommunityRole();
	}

	protected void updateCommunityFromAddressProperty()
		throws IOException, SQLException {

		updatePortalPreferences(
			COMMUNITIES_EMAIL_FROM_ADDRESS,
			PropsKeys.SITES_EMAIL_FROM_ADDRESS);
	}

	protected void updateCommunityFromNameProperty()
		throws IOException, SQLException {

		updatePortalPreferences(
			COMMUNITIES_EMAIL_FROM_NAME,
			PropsKeys.SITES_EMAIL_FROM_NAME);
	}

	protected void updateCommunityLogoProperty()
		throws IOException, SQLException {

		updatePortalPreferences(
			COMPANY_SECURITY_COMMUNITY_LOGO,
			PropsKeys.COMPANY_SECURITY_SITE_LOGO);
	}

	protected void updateCommunityMembershipRequestBodyProperty()
		throws IOException, SQLException {

		updatePortalPreferences(
			COMMUNITIES_EMAIL_MEMBERSHIP_REQUEST_BODY,
			PropsKeys.SITES_EMAIL_MEMBERSHIP_REQUEST_BODY);
	}

	protected void updateCommunityMembershipRequestSubjectProperty()
		throws IOException, SQLException {

		updatePortalPreferences(
			COMMUNITIES_EMAIL_MEMBERSHIP_REQUEST_SUBJECT,
			PropsKeys.SITES_EMAIL_MEMBERSHIP_REQUEST_SUBJECT);
	}

	protected void updateCommunityMembershipReplyBodyProperty()
		throws IOException, SQLException {

		updatePortalPreferences(
			COMMUNITIES_MEMBERSHIP_REPLY_BODY,
			PropsKeys.SITES_EMAIL_MEMBERSHIP_REPLY_BODY);
	}

	protected void updateCommunityMembershipReplySubjectProperty()
		throws IOException, SQLException {

		updatePortalPreferences(
			COMMUNITIES_MEMBERSHIP_REPLY_SUBJECT,
			PropsKeys.SITES_EMAIL_MEMBERSHIP_REPLY_SUBJECT );
	}

	protected void updatePortletPreferenceMBCommunityRole()
		throws IOException, SQLException {

		updatePortletPreferences(MB_COMMUNITY_ROLE, "site-role");
	}

	protected void updatePortletPreferencesCommunityNameVariable()
		throws IOException, SQLException {

		updatePortletPreferences(
			COMMUNITY_NAME_VARIABLE, SITE_NAME_VARIABLE);
	}

	protected void updatePreferences(
			String tableName, String oldValue, String newValue)
		throws IOException, SQLException {

		runSQL("update " + tableName + " set preferences = " +
			   	"replace(preferences, \"" + oldValue + "\", \"" + newValue +
				"\") where preferences like \"%" + oldValue + "%\"");
	}

	protected void updatePortalPreferences(String oldValue, String newValue)
		throws IOException, SQLException {

		updatePreferences("PortalPreferences", oldValue, newValue);
	}

	protected void updatePortletPreferences(String oldValue, String newValue)
		throws IOException, SQLException {

		updatePreferences("PortletPreferences", oldValue, newValue);
	}

	protected static final String COMMUNITIES_EMAIL_FROM_ADDRESS =
		"communities.email.from.address";

	protected static final String COMMUNITIES_EMAIL_FROM_NAME =
		"communities.email.from.name";

	protected static final String COMMUNITIES_EMAIL_MEMBERSHIP_REQUEST_BODY =
		"communities.email.membership.request.body";

	protected static final String COMMUNITIES_EMAIL_MEMBERSHIP_REQUEST_SUBJECT =
		"communities.email.membership.request.subject";

	protected static final String COMMUNITIES_MEMBERSHIP_REPLY_BODY =
		"communities.email.membership.reply.body";

	protected static final String COMMUNITIES_MEMBERSHIP_REPLY_SUBJECT =
		"communities.email.membership.reply.subject";

	protected static final String COMPANY_SECURITY_COMMUNITY_LOGO =
		"company.security.community.logo";

	protected static final String MB_COMMUNITY_ROLE= "community-role";

	protected static final String COMMUNITY_NAME_VARIABLE =
		"$COMMUNITY_NAME$";

	protected static final String SITE_NAME_VARIABLE =
		"$SITE_NAME$";

}