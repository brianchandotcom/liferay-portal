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

/**
 * @author Iván Zaera
 */
@Meta.OCD(id = "com.liferay.wiki.configuration.WikiServiceConfiguration")
public interface WikiServiceConfiguration {

	/**
	 * Set the default wiki format.
	 */
	@Meta.AD(
		deflt = "creole", required = false
	)
	public String defaultFormat();

	/**
	 * Set the location of the XML file containing the configuration of the
	 * default display templates for the Wiki portlet.
	 */
	@Meta.AD(
		deflt = "com/liferay/wiki/dependencies/portlet-display-templates.xml",
		required = false
	)
	public String displayTemplatesConfig();

	@Meta.AD(
		deflt = "", required = false
	)
	public String emailFromAddress();

	@Meta.AD(
		deflt = "", required = false
	)
	public String emailFromName();

	@Meta.AD(
		deflt =
			"${resource:com/liferay/wiki/dependencies" +
				"/email_page_added_body.tmpl}",
		required = false
	)
	public String emailPageAddedBody();

	@Meta.AD(
		deflt = "true", required = false
	)
	public boolean emailPageAddedEnabled();

	@Meta.AD(
		deflt =
			"${resource:com/liferay/wiki/dependencies" +
				"/email_page_added_subject.tmpl}",
		required = false
	)
	public String emailPageAddedSubject();

	@Meta.AD(
		deflt =
			"${resource:com/liferay/wiki/dependencies" +
				"/email_page_updated_body.tmpl}",
		required = false
	)
	public String emailPageUpdatedBody();

	@Meta.AD(
		deflt = "true", required = false
	)
	public boolean emailPageUpdatedEnabled();

	@Meta.AD(
		deflt =
			"${resource:com/liferay/wiki/dependencies" +
				"/email_page_updated_subject.tmpl}",
		required = false
	)
	public String emailPageUpdatedSubject();

	/**
	 * Set the name of the default page for a wiki node. The name for the
	 * default page must be a valid wiki word. A wiki word follows the format of
	 * having an upper case letter followed by a series of lower case letters
	 * followed by another upper case letter and another series of lower case
	 * letters. See http://www.usemod.com/cgi-bin/wiki.pl?WhatIsaWiki for more
	 * information on wiki naming conventions.
	 */
	@Meta.AD(
		deflt = "FrontPage", required = false
	)
	public String frontPageName();

	/**
	 * Set the name of the default node that will be automatically created when
	 * the Wiki portlet is first used in a site.
	 */
	@Meta.AD(
		deflt = "Main", required = false
	)
	public String initialNodeName();

	@Meta.AD(
		deflt = "false", required = false
	)
	public boolean pageCommentsEnabled();

	/**
	 * Set this to true to enable social activity notifications on minor edits
	 * of a wiki page.
	 */
	@Meta.AD(
		deflt = "true", required = false
	)
	public boolean pageMinorEditAddSocialActivity();

	/**
	 * Set this to true to enable email notifications on minor edits of a wiki
	 * page.
	 */
	@Meta.AD(
		deflt = "false", required = false
	)
	public boolean pageMinorEditSendEmail();

	/**
	 * Specify the requirements for the names of wiki pages. By default only a
	 * few characters are forbidden. Uncomment the regular expression below to
	 * allow only CamelCase titles.
	 */
	@Meta.AD(
		deflt = "([^\\\\\\\\\\\\[\\\\]\\\\|:;%<>]+)", required = false
	)
	public String pageTitlesRegexp();

	/**
	 * Specify the characters that will be automatically removed from the titles
	 * when importing wiki pages. This regexp should remove any characters that
	 * are forbidden in the regexp specified in the property
	 * "wiki.page.titles.regexp".
	 */
	@Meta.AD(
		deflt = "([\\\\\\\\\\\\[\\\\]\\\\|:;%<>]+)", required = false
	)
	public String pageTitlesRemoveRegexp();

	/**
	 * Specify the supported protocols for the Creole parser.
	 */
	@Meta.AD(
		deflt = "ftp://|http://|https://|mailto:|mms://", required = false
	)
	public String[] parsersCreoleSupportedProtocols();

	@Meta.AD(
		deflt = "200", required = false
	)
	public int rssAbstractLength();

}