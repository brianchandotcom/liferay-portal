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

package com.liferay.seo.impl;

import com.liferay.portal.kernel.seo.SEOLink;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Adolfo Pérez
 */
public class SEOLinkImpl implements SEOLink {

	public String getHref() {
		return _href;
	}

	public Optional<String> getHrefLang() {
		return Optional.ofNullable(_hrefLang);
	}

	public SEOLink.SEOLinkDataSennaTrack getSEOLinkDataSennaTrack() {
		return _seoLinkDataSennaTrack;
	}

	public SEOLink.SEOLinkRel getSeoLinkRel() {
		return _seoLinkRel;
	}

	public static class AlternateSEOLink extends SEOLinkImpl {

		public AlternateSEOLink(String href, String hrefLang) {
			super(
				SEOLinkDataSennaTrack.TEMPORARY, href, hrefLang,
				SEOLinkRel.ALTERNATE);
		}

	}

	public static class CanonicalSEOLink extends SEOLinkImpl {

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

	protected SEOLinkImpl(
		SEOLink.SEOLinkDataSennaTrack seoLinkDataSennaTrack, String href,
		String hrefLang, SEOLink.SEOLinkRel seoLinkRel) {

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
	private final SEOLink.SEOLinkDataSennaTrack _seoLinkDataSennaTrack;
	private final SEOLink.SEOLinkRel _seoLinkRel;

}