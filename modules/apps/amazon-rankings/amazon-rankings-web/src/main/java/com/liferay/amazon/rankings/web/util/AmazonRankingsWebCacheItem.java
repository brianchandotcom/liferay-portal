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

package com.liferay.amazon.rankings.web.util;

import com.liferay.amazon.rankings.web.configuration.AmazonRankingsConfiguration;
import com.liferay.amazon.rankings.web.model.AmazonRankings;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Samuel Kong
 * @author Barrie Selack
 */
public class AmazonRankingsWebCacheItem implements WebCacheItem {

	public AmazonRankingsWebCacheItem(
		AmazonRankingsConfiguration amazonRankingsConfiguration, String isbn) {

		_amazonRankingsConfiguration = amazonRankingsConfiguration;
		_isbn = isbn;
	}

	@Override
	public Object convert(String key) {
		AmazonRankings amazonRankings = null;

		try {
			amazonRankings = doConvert(key);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return amazonRankings;
	}

	@Override
	public long getRefreshTime() {
		return _REFRESH_TIME;
	}

	protected AmazonRankings doConvert(String key) throws Exception {
		Map<String, String> parameters = new HashMap<>();

		parameters.put(
			"AssociateTag", _amazonRankingsConfiguration.amazonAssociateTag());
		parameters.put(
			"AWSAccessKeyId", _amazonRankingsConfiguration.amazonAccessKeyId());
		parameters.put("IdType", "ISBN");
		parameters.put("ItemId", _isbn);
		parameters.put("Operation", "ItemLookup");
		parameters.put(
			"ResponseGroup", "Images,ItemAttributes,Offers,SalesRank");
		parameters.put("SearchIndex", "Books");
		parameters.put("Service", "AWSECommerceService");
		parameters.put("Timestamp", AmazonRankingsUtil.getTimestamp());

		String urlWithSignature =
			AmazonSignedRequestsUtil.generateUrlWithSignature(
				_amazonRankingsConfiguration, parameters);

		String xml = HttpUtil.URLtoString(urlWithSignature);

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		if (rootElement == null) {
			return null;
		}

		if (hasErrorMessage(rootElement)) {
			return null;
		}

		Element itemsElement = rootElement.element("Items");

		if (itemsElement == null) {
			return null;
		}

		Element requestElement = itemsElement.element("Request");

		if (requestElement != null) {
			Element errorsElement = requestElement.element("Errors");

			if (hasErrorMessage(errorsElement)) {
				return null;
			}
		}

		Element itemElement = itemsElement.element("Item");

		if (itemElement == null) {
			return null;
		}

		Element itemAttributesElement = itemElement.element("ItemAttributes");

		if (itemAttributesElement == null) {
			return null;
		}

		String productName = itemAttributesElement.elementText("Title");
		String catalog = StringPool.BLANK;
		String[] authors = getAuthors(itemAttributesElement);
		String releaseDateAsString = itemAttributesElement.elementText(
			"PublicationDate");
		Date releaseDate = getReleaseDate(releaseDateAsString);
		String manufacturer = itemAttributesElement.elementText("Manufacturer");
		String smallImageURL = getImageURL(itemElement, "SmallImage");
		String mediumImageURL = getImageURL(itemElement, "MediumImage");
		String largeImageURL = getImageURL(itemElement, "LargeImage");
		double listPrice = getPrice(itemAttributesElement.element("ListPrice"));

		double ourPrice = 0;

		Element offerListingElement = getOfferListing(itemElement);

		if (offerListingElement != null) {
			ourPrice = getPrice(offerListingElement.element("Price"));
		}

		double usedPrice = 0;
		double collectiblePrice = 0;
		double thirdPartyNewPrice = 0;

		Element offerSummaryElement = itemElement.element("OfferSummary");

		if (offerSummaryElement != null) {
			usedPrice = getPrice(
				offerSummaryElement.element("LowestUsedPrice"));

			collectiblePrice = getPrice(
				offerSummaryElement.element("LowestCollectiblePrice"));

			thirdPartyNewPrice = getPrice(
				offerSummaryElement.element("LowestNewPrice"));
		}

		int salesRank = GetterUtil.getInteger(
			itemElement.elementText("SalesRank"));
		String media = StringPool.BLANK;
		String availability = getAvailability(offerListingElement);

		return new AmazonRankings(
			_isbn, productName, catalog, authors, releaseDate,
			releaseDateAsString, manufacturer, smallImageURL, mediumImageURL,
			largeImageURL, listPrice, ourPrice, usedPrice, collectiblePrice,
			thirdPartyNewPrice, salesRank, media, availability);
	}

	protected String[] getAuthors(Element itemAttributesElement) {
		List<String> authors = new ArrayList<>();

		for (Element authorElement : itemAttributesElement.elements("Author")) {
			authors.add(authorElement.getText());
		}

		return authors.toArray(new String[authors.size()]);
	}

	protected String getAvailability(Element offerListingElement) {
		if (offerListingElement == null) {
			return null;
		}

		Element availabilityElement = offerListingElement.element(
			"Availability");

		return availabilityElement.elementText("Availability");
	}

	protected String getImageURL(Element itemElement, String name) {
		String imageURL = null;

		Element imageElement = itemElement.element(name);

		if (imageElement != null) {
			imageURL = imageElement.elementText("URL");
		}

		return imageURL;
	}

	protected Element getOfferListing(Element itemElement) {
		Element offersElement = itemElement.element("Offers");

		if (offersElement == null) {
			return null;
		}

		Element offerElement = offersElement.element("Offer");

		if (offerElement == null) {
			return null;
		}

		return offerElement.element("OfferListing");
	}

	protected double getPrice(Element priceElement) {
		if (priceElement == null) {
			return 0;
		}

		return GetterUtil.getInteger(priceElement.elementText("Amount")) * 0.01;
	}

	protected Date getReleaseDate(String releaseDateAsString) {
		if (Validator.isNull(releaseDateAsString)) {
			return null;
		}

		DateFormat dateFormat = null;

		if (releaseDateAsString.length() > 7) {
			dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				"yyyy-MM-dd", LocaleUtil.US);
		}
		else {
			dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				"yyyy-MM", LocaleUtil.US);
		}

		return GetterUtil.getDate(releaseDateAsString, dateFormat);
	}

	protected boolean hasErrorMessage(Element element) {
		if (element == null) {
			return false;
		}

		Element errorElement = element.element("Error");

		if (errorElement == null) {
			return false;
		}

		Element messageElement = errorElement.element("Message");

		if (messageElement == null) {
			return false;
		}

		_log.error(messageElement.getText());

		return true;
	}

	private static final long _REFRESH_TIME = Time.MINUTE * 20;

	private static final Log _log = LogFactoryUtil.getLog(
		AmazonRankingsWebCacheItem.class);

	private final AmazonRankingsConfiguration _amazonRankingsConfiguration;
	private final String _isbn;

}