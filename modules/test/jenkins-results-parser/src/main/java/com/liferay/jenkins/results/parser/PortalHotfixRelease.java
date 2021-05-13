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

package com.liferay.jenkins.results.parser;

import java.net.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class PortalHotfixRelease {

	public PortalHotfixRelease(
		URL portalHotfixReleaseURL, PortalFixpackRelease portalFixpackRelease,
		PortalRelease portalRelease) {

		_portalHotfixReleaseURL = portalHotfixReleaseURL;
		_portalFixpackRelease = portalFixpackRelease;
		_portalRelease = portalRelease;
	}

	public PortalFixpackRelease getPortalFixpackRelease() {
		return _portalFixpackRelease;
	}

	public String getPortalHotfixReleaseName() {
		String portalHotfixReleaseURLString = String.valueOf(
			_portalHotfixReleaseURL);

		Matcher matcher = _pattern.matcher(portalHotfixReleaseURLString);

		if (!matcher.find()) {
			return null;
		}

		return matcher.group("hotfixName");
	}

	public URL getPortalHotfixReleaseURL() {
		return _portalHotfixReleaseURL;
	}

	public String getPortalHotfixReleaseVersion() {
		String portalHotfixReleaseURLString = String.valueOf(
			_portalHotfixReleaseURL);

		Matcher matcher = _pattern.matcher(portalHotfixReleaseURLString);

		if (!matcher.find()) {
			return null;
		}

		return matcher.group("hotfixVersion");
	}

	public PortalRelease getPortalRelease() {
		return _portalRelease;
	}

	private static final Pattern _pattern = Pattern.compile(
		"https?://.+/(?<hotfixName>liferay-(hotfix|security-de|security-dxp)-" +
			"(?<hotfixVersion>\\d+)(-\\d{6}-\\d)?-\\d{4})");

	private final PortalFixpackRelease _portalFixpackRelease;
	private final URL _portalHotfixReleaseURL;
	private final PortalRelease _portalRelease;

}