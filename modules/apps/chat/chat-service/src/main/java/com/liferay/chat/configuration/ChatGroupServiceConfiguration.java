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

package com.liferay.chat.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Peter Fellwock
 */
@ExtendedObjectClassDefinition(
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.chat.configuration.ChatConfiguration",
	localization = "content/Language", name = "chat.service.configuration.name"
)
public interface ChatGroupServiceConfiguration {

	/**
	 * Input a list of comma delimited values of allowed social relation types.
	 * This property will only be used if the property "buddy.list.strategy"
	 * is set to "sites,social" or "social".
	 *
	 * Valid bidirectional are 12 for "TYPE_BI_CONNECTION", 1 for
	 * "TYPE_BI_COWORKER", 2 for "TYPE_BI_FRIEND", 3 for
	 * "TYPE_BI_ROMANTIC_PARTNER", and 4 for "TYPE_BI_SIBLING" from
	 * com.liferay.social.kernel.model.SocialRelationConstants
	 */
	@Meta.AD(deflt = "2|12", required = false)
	public int[] buddyListAllowedSocialRelationTypes();

	@Meta.AD(deflt = "500", required = false)
	public int buddyListMaxBuddies();

	/**
	 * Specify the strategy to generate the list of buddies available in the chat
	 * list. The value of "all" will include all users in a portal instance. The
	 * value of "sites" will include all users that belong to sites that a user
	 * also belongs to. The value of "social" will include all users based on the
	 * user's social relations and the allowed social relation types defined in
	 * the property "buddy.list.allowed.social.relation.types". The value of
	 * "sites,social" will include a combined list of users from the values of
	 * "sites" and "social".
	 */
	@Meta.AD(deflt = "all", required = false)
	public String buddyListStrategy();

	/**
	 * Specify a list of comma delimited site names that are excluded from
	 * determining a user's list of buddies. This property is only used if the
	 * property "buddy.list.strategy" is set to "sites" or "friends|sites".
	 */
	@Meta.AD(deflt = "", required = false)
	public String[] buddyListSiteExcludes();

	@Meta.AD(deflt = "false", required = false)
	public boolean jabberEnabled();

	@Meta.AD(deflt = "true", required = false)
	public boolean jabberImportUserEnabled();

	@Meta.AD(deflt = "", required = false)
	public String jabberHost();

	@Meta.AD(deflt = "5222", required = false)
	public int jabberPort();

	@Meta.AD(deflt = "Liferay", required = false)
	public String jabberResource();

	@Meta.AD(deflt = "", required = false)
	public String jabberServiceName();

	@Meta.AD(deflt = "false", required = false)
	public boolean jabberSock5ProxyEnabled();

	@Meta.AD(deflt = "-1", required = false)
	public int jabberSock5ProxyPort();

}