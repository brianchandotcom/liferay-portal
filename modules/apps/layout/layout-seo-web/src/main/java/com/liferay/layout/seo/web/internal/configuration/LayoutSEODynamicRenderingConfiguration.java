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

package com.liferay.layout.seo.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Jamie Sammons
 */
@ExtendedObjectClassDefinition(category = "pages")
@Meta.OCD(
	description = "layout-seo-dynamic-rendering-configuration-description",
	id = "com.liferay.layout.seo.web.internal.configuration.LayoutSEODynamicRenderingConfiguration",
	localization = "content/Language",
	name = "layout-seo-dynamic-rendering-configuration-name"
)
public interface LayoutSEODynamicRenderingConfiguration {

	@Meta.AD(
		deflt = "false",
		name = "layout-seo-dynamic-rendering-configuration-enable-service",
		required = false
	)
	public boolean enabled();

	@Meta.AD(
		deflt = "http://service.prerender.io",
		name = "layout-seo-dynamic-rendering-configuration-service-url",
		required = false
	)
	public String serviceUrl();

	@Meta.AD(
		deflt = "",
		name = "layout-seo-dynamic-rendering-configuration-service-token",
		required = false
	)
	public String serviceToken();

	@Meta.AD(
		deflt = "", name = "layout-seo-dynamic-rendering-configuration-path",
		required = false
	)
	public String[] pathList();

	@Meta.AD(
		deflt = "20", name = "default-max-connections-per-route",
		required = false
	)
	public int defaultMaxConnectionsPerRoute();

	@Meta.AD(deflt = "20", name = "max-total-connections", required = false)
	public int maxTotalConnections();

	@Meta.AD(
		deflt = "60000", name = "connection-manager-timeout", required = false
	)
	public int connectionManagerTimeout();

	@Meta.AD(deflt = "60000", name = "so-timeout", required = false)
	public int soTimeout();

	@Meta.AD(
		deflt = "googlebot,bingbot,yandex,baiduspider,facebookexternalhit,twitterbot,rogerbot,linkedinbot,embedly,quora link preview,showyoubot,outbrain,pinterest/0.,pinterestbot,slackbot,vkShare,W3C_Validator,whatsapp,redditbot,applebot,flipboard,tumblr,bitlybot,skypeuripreview,nuzzel,discordbot,google page speed,qwantify,bitrix link preview,xing-contenttabreceiver,chrome-lighthouse,telegrambot,screaming frog",
		name = "layout-seo-dynamic-rendering-configuration-crawler-user-agents",
		required = false
	)
	public String[] crawlerUserAgents();

	@Meta.AD(
		deflt = ".js,.css,.xml,.less,.png,.jpg,.jpeg,.gif,.pdf,.doc,.txt,.ico,.rss,.zip,.mp3,.rar,.exe,.wmv,.doc,.avi,.ppt,.mpg,.mpeg,.tif,.wav,.mov,.psd,.ai,.xls,.mp4,.m4a,.swf,.dat,.dmg,.iso,.flv,.m4v,.torrent,.ttf,.woff,.svg,.eot",
		name = "layout-seo-dynamic-rendering-configuration-extension-ignore-list",
		required = false
	)
	public String[] extensionIgnoreList();

}