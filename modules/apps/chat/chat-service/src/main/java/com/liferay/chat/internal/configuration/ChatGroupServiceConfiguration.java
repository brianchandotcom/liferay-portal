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

package com.liferay.chat.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Peter Fellwock
 */
@ExtendedObjectClassDefinition(
	category = "community-tools",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.chat.internal.configuration.ChatGroupServiceConfiguration",
	localization = "content/Language", name = "chat-service-configuration-name"
)
public interface ChatGroupServiceConfiguration {

	/**
	 * Input a list of comma delimited values of allowed social relation types.
	 * This property will only be used if the property "buddy.list.strategy" is
	 * set to "sites,social" or "social".
	 *
	 * Valid bidirectional are 12 for "TYPE_BI_CONNECTION", 1 for
	 * "TYPE_BI_COWORKER", 2 for "TYPE_BI_FRIEND", 3 for
	 * "TYPE_BI_ROMANTIC_PARTNER", and 4 for "TYPE_BI_SIBLING" from
	 * com.liferay.social.kernel.model.SocialRelationConstants
	 */
	@Meta.AD(
		deflt = "2,12", name = "buddy-list-allowed-social-relation-types",
		required = false
	)
	public int[] buddyListAllowedSocialRelationTypes();

	@Meta.AD(deflt = "500", name = "buddy-list-max-buddies", required = false)
	public int buddyListMaxBuddies();

	/**
	 * Specify the strategy to generate the list of buddies available in the
	 * chat list. The value of "all" will include all users in a portal
	 * instance. The value of "sites" will include all users that belong to
	 * sites that a user also belongs to. The value of "social" will include all
	 * users based on the user's social relations and the allowed social
	 * relation types defined in the property
	 * "buddy.list.allowed.social.relation.types". The value of "sites,social"
	 * will include a combined list of users from the values of "sites" and
	 * "social".
	 */
	@Meta.AD(deflt = "all", name = "buddy-list-strategy", required = false)
	public String buddyListStrategy();

	/**
	 * Specify a list of comma delimited site names that are excluded from
	 * determining a user's list of buddies. This property is only used if the
	 * property "buddy.list.strategy" is set to "sites" or "friends|sites".
	 */
	@Meta.AD(deflt = "", name = "buddy-list-site-excludes", required = false)
	public String[] buddyListSiteExcludes();

	@Meta.AD(deflt = "false", name = "jabber-enabled", required = false)
	public boolean jabberEnabled();

	@Meta.AD(
		deflt = "true", name = "jabber-import-user-enabled", required = false
	)
	public boolean jabberImportUserEnabled();

	@Meta.AD(deflt = "", name = "jabber-host", required = false)
	public String jabberHost();

	@Meta.AD(deflt = "5222", name = "jabber-port", required = false)
	public int jabberPort();

	@Meta.AD(deflt = "Liferay", name = "jabber-resource", required = false)
	public String jabberResource();

	@Meta.AD(deflt = "", name = "jabber-service-name", required = false)
	public String jabberServiceName();

	@Meta.AD(
		deflt = "false", name = "jabber-sock5-proxy-enabled", required = false
	)
	public boolean jabberSock5ProxyEnabled();

	@Meta.AD(deflt = "-1", name = "jabber-sock5-proxy-port", required = false)
	public int jabberSock5ProxyPort();

}