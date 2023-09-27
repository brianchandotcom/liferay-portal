/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alicia García
 */
@ExtendedObjectClassDefinition(category = "pages")
@Meta.OCD(
	id = "com.liferay.redirect.configuration.CrawlerUserAgentsConfiguration",
	localization = "content/Language",
	name = "crawler-user-agents-configuration-name"
)
public interface CrawlerUserAgentsConfiguration {

	@Meta.AD(
		deflt = "W3C_Validator,applebot,baiduspider,bingbot,bitlybot,bitrix link preview,chrome-lighthouse,discordbot,embedly,facebookexternalhit,flipboard,google page speed,googlebot,linkedinbot,nuzzel,outbrain,pinterest/0.,pinterestbot,quora link preview,qwantify,redditbot,rogerbot,screaming frog,showyoubot,skypeuripreview,slackbot,telegrambot,tumblr,twitterbot,vkShare,whatsapp,xing-contenttabreceiver,yandex",
		name = "crawler-user-agents-configuration-crawler-user-agent",
		required = false
	)
	public String[] crawlerUserAgents();

}