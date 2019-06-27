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

package com.liferay.portal.kernel.seo;

import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Cristina González
 */
public class SEOLink {

	public String getHref() {
		return _href;
	}

	public Optional<String> getHrefLang() {
		return Optional.ofNullable(_hrefLang);
	}

	public SEOLinkDataSennaTrack getSEOLinkDataSennaTrack() {
		return _seoLinkDataSennaTrack;
	}

	public SEOLinkRel getSeoLinkRel() {
		return _seoLinkRel;
	}

	public static class AlternateSEOLink extends SEOLink {

		public AlternateSEOLink(String href, String hrefLang) {
			super(
				SEOLinkDataSennaTrack.TEMPORARY, href, hrefLang,
				SEOLinkRel.ALTERNATE);
		}

	}

	public static class CanonicalSEOLink extends SEOLink {

		public CanonicalSEOLink(String href) {
			super(
				SEOLinkDataSennaTrack.TEMPORARY, href, null,
				SEOLinkRel.CANONICAL);
		}

	}

	public static class XDefaultAlternateSEOLink extends AlternateSEOLink {

		public XDefaultAlternateSEOLink(String href) {
			super(href, "x-default");
		}

	}

	public enum SEOLinkDataSennaTrack {

		TEMPORARY {

			public String toString() {
				return "temporary";
			}

		}

	}

	public enum SEOLinkRel {

		ALTERNATE {

			public String toString() {
				return "alternate";
			}

		},
		CANONICAL {

			public String toString() {
				return "canonical";
			}

		}

	}

	protected SEOLink(
		SEOLinkDataSennaTrack seoLinkDataSennaTrack, String href,
		String hrefLang, SEOLinkRel seoLinkRel) {

		if (Objects.isNull(seoLinkDataSennaTrack)) {
			throw new IllegalArgumentException(
				"unable to create a new SEOLink with a null " +
					"seoLinkDataSennaTrack");
		}

		if (Validator.isNull(href)) {
			throw new IllegalArgumentException(
				"unable to create a new SEOLink with a null href");
		}

		if (Objects.isNull(seoLinkRel)) {
			throw new IllegalArgumentException(
				"unable to create a new SEOLink with a null seoLinkRel");
		}

		_seoLinkDataSennaTrack = seoLinkDataSennaTrack;
		_href = href;
		_hrefLang = hrefLang;
		_seoLinkRel = seoLinkRel;
	}

	private final String _href;
	private final String _hrefLang;
	private final SEOLinkDataSennaTrack _seoLinkDataSennaTrack;
	private final SEOLinkRel _seoLinkRel;

}