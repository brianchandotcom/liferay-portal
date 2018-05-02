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

package com.liferay.wiki.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;

/**
 * @author Iván Zaera
 */
@ExtendedObjectClassDefinition(
	category = "wiki", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.wiki.configuration.WikiGroupServiceConfiguration",
	localization = "content/Language",
	name = "wiki-group-service-configuration-name"
)
public interface WikiGroupServiceConfiguration {

	/**
	 * Set the default wiki format.
	 */
	@Meta.AD(deflt = "creole", name = "default-format", required = false)
	public String defaultFormat();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/admin.email.from.address}",
		name = "email-from-address", required = false
	)
	public String emailFromAddress();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/admin.email.from.name}",
		name = "email-from-name", required = false
	)
	public String emailFromName();

	@Meta.AD(
		deflt = "${resource:com/liferay/wiki/configuration/dependencies/email_page_added_body.tmpl}",
		name = "email-page-added-body", required = false
	)
	public LocalizedValuesMap emailPageAddedBody();

	@Meta.AD(
		deflt = "true", name = "email-page-added-enabled", required = false
	)
	public boolean emailPageAddedEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/wiki/configuration/dependencies/email_page_added_subject.tmpl}",
		name = "email-page-added-subject", required = false
	)
	public LocalizedValuesMap emailPageAddedSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/wiki/configuration/dependencies/email_page_updated_body.tmpl}",
		name = "email-page-updated-body", required = false
	)
	public LocalizedValuesMap emailPageUpdatedBody();

	@Meta.AD(
		deflt = "true", name = "email-page-updated-enabled", required = false
	)
	public boolean emailPageUpdatedEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/wiki/configuration/dependencies/email_page_updated_subject.tmpl}",
		name = "email-page-updated-subject", required = false
	)
	public LocalizedValuesMap emailPageUpdatedSubject();

	@Meta.AD(deflt = "true", name = "enable-rss", required = false)
	public boolean enableRss();

	/**
	 * Set the name of the default page for a wiki node. The name for the
	 * default page must be a valid wiki word. A wiki word follows the format of
	 * having an upper case letter followed by a series of lower case letters
	 * followed by another upper case letter and another series of lower case
	 * letters. See http://www.usemod.com/cgi-bin/wiki.pl?WhatIsaWiki for more
	 * information on wiki naming conventions.
	 */
	@Meta.AD(deflt = "FrontPage", name = "front-page-name", required = false)
	public String frontPageName();

	@Meta.AD(
		deflt = "alloyeditor_creole", name = "get-creole-editor",
		required = false
	)
	public String getCreoleEditor();

	@Meta.AD(deflt = "alloyeditor", name = "get-html-editor", required = false)
	public String getHTMLEditor();

	@Meta.AD(deflt = "simple", name = "get-media-wiki-editor", required = false)
	public String getMediaWikiEditor();

	/**
	 * Set the name of the default node that will be automatically created when
	 * the Wiki portlet is first used in a site.
	 */
	@Meta.AD(deflt = "Main", name = "initial-node-name", required = false)
	public String initialNodeName();

	@Meta.AD(deflt = "true", name = "page-comments-enabled", required = false)
	public boolean pageCommentsEnabled();

	/**
	 * Set this to <code>true</code> to enable social activity notifications on
	 * minor edits of a wiki page.
	 */
	@Meta.AD(
		deflt = "true", name = "page-minor-edit-add-social-activity",
		required = false
	)
	public boolean pageMinorEditAddSocialActivity();

	/**
	 * Set this to <code>true</code> to enable email notifications on minor
	 * edits of a wiki page.
	 */
	@Meta.AD(
		deflt = "false", name = "page-minor-edit-send-email", required = false
	)
	public boolean pageMinorEditSendEmail();

	/**
	 * Specify the supported protocols for the Creole parser.
	 */
	@Meta.AD(
		deflt = "ftp://|http://|https://|mailto:|mms://",
		name = "parsers-creole-supported-protocols", required = false
	)
	public String[] parsersCreoleSupportedProtocols();

	@Meta.AD(deflt = "200", name = "rss-abstract-length", required = false)
	public int rssAbstractLength();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/search.container.page.default.delta}",
		name = "rss-delta", required = false
	)
	public String rssDelta();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/rss.feed.display.style.default}",
		name = "rss-display-style", required = false
	)
	public String rssDisplayStyle();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/rss.feed.type.default}",
		name = "rss-feed-type", required = false
	)
	public String rssFeedType();

}