/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.http.servlet.internal.registration;

import org.eclipse.equinox.http.servlet.internal.servlet.Match;
import org.eclipse.equinox.http.servlet.internal.util.Const;

import org.osgi.dto.DTO;

/**
 * @author Dante Wang
 */
public abstract class MatchableRegistration<T, D extends DTO>
	extends Registration<T, D> {

	public MatchableRegistration(T target, D dto) {
		super(target, dto);
	}

	public abstract String match(
		String name, String servletPath, String pathInfo, String extension,
		Match match);

	protected boolean doMatch(
			String pattern, String servletPath, String pathInfo,
			String extension, Match match)
		throws IllegalArgumentException {

		if (match == Match.EXACT) {
			return pattern.equals(servletPath);
		}

		if (pattern.indexOf(Const.SLASH_STAR_DOT) == 0) {
			pattern = pattern.substring(1);
		}

		if (pattern.charAt(0) == '/') {
			if ((match == Match.DEFAULT_SERVLET) && (pattern.length() == 1)) {
				return true;
			}

			if ((match == Match.REGEX) &&
				isPathWildcardMatch(pattern, servletPath, pathInfo)) {

				return true;
			}
		}

		if (match == Match.EXTENSION) {
			int index = pattern.lastIndexOf(Const.STAR_DOT);

			String patternPrefix = Const.BLANK;

			if (index > 0) {
				patternPrefix = pattern.substring(0, index - 1);
			}

			if ((index != -1) && servletPath.equals(patternPrefix)) {
				return pattern.endsWith(Const.DOT + extension);
			}
		}

		return false;
	}

	protected boolean isPathWildcardMatch(
		String pattern, String servletPath, String pathInfo) {

		int cpl = pattern.length() - 2;

		if (pattern.endsWith(Const.SLASH_STAR) &&
			servletPath.regionMatches(0, pattern, 0, cpl)) {

			if ((pattern.length() > 2) && !pattern.startsWith(servletPath)) {
				return false;
			}

			if (servletPath.length() == cpl) {
				return true;
			}

			return false;
		}

		return false;
	}

}