/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.asset.library.internal.jaxrs.exception.mapper;

import com.liferay.portal.kernel.exception.GroupFriendlyURLException;
import com.liferay.portal.kernel.exception.LayoutFriendlyURLException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Converts any {@code GroupFriendlyURLException} to a {@code 400} error.
 *
 * @author Sam Ziemer
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Asset.Library)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Headless.Asset.Library.GroupFriendlyURLExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class GroupFriendlyURLExceptionMapper
	extends BaseExceptionMapper<GroupFriendlyURLException> {

	@Override
	protected Problem getProblem(
		GroupFriendlyURLException groupFriendlyURLException) {

		Locale locale = _acceptLanguage.getPreferredLocale();

		return new Problem(
			null, Response.Status.BAD_REQUEST,
			_getLocalizedMessage(groupFriendlyURLException, locale),
			_getTypeName(groupFriendlyURLException.getType()));
	}

	private String _getLocalizedMessage(
		GroupFriendlyURLException groupFriendlyURLException, Locale locale) {

		int type = groupFriendlyURLException.getType();

		if (type == LayoutFriendlyURLException.ADJACENT_SLASHES) {
			return _language.get(
				locale,
				"please-enter-a-friendly-url-that-does-not-have-adjacent-" +
					"slashes");
		}

		if (type == LayoutFriendlyURLException.DOES_NOT_START_WITH_SLASH) {
			return _language.get(
				locale, "please-enter-a-friendly-url-that-begins-with-a-slash");
		}

		if (type == LayoutFriendlyURLException.DUPLICATE) {
			return _language.get(locale, "please-enter-a-unique-friendly-url");
		}

		if (type == LayoutFriendlyURLException.ENDS_WITH_DASH) {
			return _language.get(
				locale,
				"please-enter-a-friendly-url-that-does-not-end-with-a-dash");
		}

		if (type == LayoutFriendlyURLException.ENDS_WITH_SLASH) {
			return _language.get(
				locale,
				"please-enter-a-friendly-url-that-does-not-end-with-a-slash");
		}

		if (type == LayoutFriendlyURLException.INVALID_CHARACTERS) {
			return _language.get(
				locale, "please-enter-a-friendly-url-with-valid-characters");
		}

		if (type == LayoutFriendlyURLException.KEYWORD_CONFLICT) {
			return _language.format(
				locale,
				"please-enter-a-friendly-url-that-does-not-conflict-with-the-" +
					"keyword-x",
				groupFriendlyURLException.getKeywordConflict());
		}

		if (type == LayoutFriendlyURLException.POSSIBLE_DUPLICATE) {
			return _language.get(locale, "please-enter-a-unique-friendly-url");
		}

		if (type == LayoutFriendlyURLException.TOO_LONG) {
			return _language.format(
				locale,
				"please-enter-a-friendly-url-with-fewer-than-x-characters",
				ModelHintsUtil.getMaxLength(
					Group.class.getName(), "friendlyURL"));
		}

		if (type == LayoutFriendlyURLException.TOO_SHORT) {
			return _language.get(
				locale,
				"please-enter-a-friendly-url-that-is-at-least-two-characters-" +
					"long");
		}

		return _language.get(locale, "please-enter-a-friendly-url");
	}

	private String _getTypeName(int type) {
		if (type == LayoutFriendlyURLException.ADJACENT_SLASHES) {
			return "FRIENDLY_URL_ADJACENT_SLASHES";
		}

		if (type == LayoutFriendlyURLException.DOES_NOT_START_WITH_SLASH) {
			return "FRIENDLY_URL_DOES_NOT_START_WITH_SLASH";
		}

		if (type == LayoutFriendlyURLException.DUPLICATE) {
			return "FRIENDLY_URL_DUPLICATE";
		}

		if (type == LayoutFriendlyURLException.ENDS_WITH_DASH) {
			return "FRIENDLY_URL_ENDS_WITH_DASH";
		}

		if (type == LayoutFriendlyURLException.ENDS_WITH_SLASH) {
			return "FRIENDLY_URL_ENDS_WITH_SLASH";
		}

		if (type == LayoutFriendlyURLException.INVALID_CHARACTERS) {
			return "FRIENDLY_URL_INVALID_CHARACTERS";
		}

		if (type == LayoutFriendlyURLException.KEYWORD_CONFLICT) {
			return "FRIENDLY_URL_KEYWORD_CONFLICT";
		}

		if (type == LayoutFriendlyURLException.POSSIBLE_DUPLICATE) {
			return "FRIENDLY_URL_POSSIBLE_DUPLICATE";
		}

		if (type == LayoutFriendlyURLException.TOO_DEEP) {
			return "FRIENDLY_URL_TOO_DEEP";
		}

		if (type == LayoutFriendlyURLException.TOO_LONG) {
			return "FRIENDLY_URL_TOO_LONG";
		}

		if (type == LayoutFriendlyURLException.TOO_SHORT) {
			return "FRIENDLY_URL_TOO_SHORT";
		}

		return "FRIENDLY_URL";
	}

	@Context
	private AcceptLanguage _acceptLanguage;

	@Reference
	private Language _language;

}