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