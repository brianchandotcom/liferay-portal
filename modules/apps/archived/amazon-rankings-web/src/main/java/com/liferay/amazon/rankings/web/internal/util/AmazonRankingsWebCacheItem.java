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

package com.liferay.amazon.rankings.web.internal.util;

import com.liferay.amazon.rankings.web.internal.configuration.AmazonRankingsConfiguration;
import com.liferay.amazon.rankings.web.internal.model.AmazonRankings;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
			amazonRankings = _convert();
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return amazonRankings;
	}

	@Override
	public long getRefreshTime() {
		return _REFRESH_TIME;
	}

	private AmazonRankings _convert() throws Exception {
		String urlWithSignature =
			AmazonSignedRequestsUtil.generateUrlWithSignature(
				_amazonRankingsConfiguration,
				HashMapBuilder.put(
					"AssociateTag",
					_amazonRankingsConfiguration.amazonAssociateTag()
				).put(
					"AWSAccessKeyId",
					_amazonRankingsConfiguration.amazonAccessKeyId()
				).put(
					"IdType", "ISBN"
				).put(
					"ItemId", _isbn
				).put(
					"Operation", "ItemLookup"
				).put(
					"ResponseGroup", "Images,ItemAttributes,Offers,SalesRank"
				).put(
					"SearchIndex", "Books"
				).put(
					"Service", "AWSECommerceService"
				).put(
					"Timestamp", AmazonRankingsUtil.getTimestamp()
				).build());

		String xml = HttpUtil.URLtoString(urlWithSignature);

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		if ((rootElement == null) || _hasErrorMessage(rootElement)) {
			return null;
		}

		Element itemsElement = rootElement.element("Items");

		if (itemsElement == null) {
			return null;
		}

		Element requestElement = itemsElement.element("Request");

		if (requestElement != null) {
			Element errorsElement = requestElement.element("Errors");

			if (_hasErrorMessage(errorsElement)) {
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
		String[] authors = _getAuthors(itemAttributesElement);

		String releaseDateAsString = itemAttributesElement.elementText(
			"PublicationDate");

		Date releaseDate = _getReleaseDate(releaseDateAsString);

		String manufacturer = itemAttributesElement.elementText("Manufacturer");
		String smallImageURL = _getImageURL(itemElement, "SmallImage");
		String mediumImageURL = _getImageURL(itemElement, "MediumImage");
		String largeImageURL = _getImageURL(itemElement, "LargeImage");
		double listPrice = _getPrice(
			itemAttributesElement.element("ListPrice"));

		double ourPrice = 0;

		Element offerListingElement = _getOfferListing(itemElement);

		if (offerListingElement != null) {
			ourPrice = _getPrice(offerListingElement.element("Price"));
		}

		double usedPrice = 0;
		double collectiblePrice = 0;
		double thirdPartyNewPrice = 0;

		Element offerSummaryElement = itemElement.element("OfferSummary");

		if (offerSummaryElement != null) {
			usedPrice = _getPrice(
				offerSummaryElement.element("LowestUsedPrice"));

			collectiblePrice = _getPrice(
				offerSummaryElement.element("LowestCollectiblePrice"));

			thirdPartyNewPrice = _getPrice(
				offerSummaryElement.element("LowestNewPrice"));
		}

		int salesRank = GetterUtil.getInteger(
			itemElement.elementText("SalesRank"));
		String media = StringPool.BLANK;

		return new AmazonRankings(
			_isbn, productName, catalog, authors, releaseDate,
			releaseDateAsString, manufacturer, smallImageURL, mediumImageURL,
			largeImageURL, listPrice, ourPrice, usedPrice, collectiblePrice,
			thirdPartyNewPrice, salesRank, media,
			_getAvailability(offerListingElement));
	}

	private String[] _getAuthors(Element itemAttributesElement) {
		List<String> authors = new ArrayList<>();

		for (Element authorElement : itemAttributesElement.elements("Author")) {
			authors.add(authorElement.getText());
		}

		return authors.toArray(new String[0]);
	}

	private String _getAvailability(Element offerListingElement) {
		if (offerListingElement == null) {
			return null;
		}

		Element availabilityElement = offerListingElement.element(
			"Availability");

		return availabilityElement.elementText("Availability");
	}

	private String _getImageURL(Element itemElement, String name) {
		String imageURL = null;

		Element imageElement = itemElement.element(name);

		if (imageElement != null) {
			imageURL = imageElement.elementText("URL");
		}

		return imageURL;
	}

	private Element _getOfferListing(Element itemElement) {
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

	private double _getPrice(Element priceElement) {
		if (priceElement == null) {
			return 0;
		}

		return GetterUtil.getInteger(priceElement.elementText("Amount")) * 0.01;
	}

	private Date _getReleaseDate(String releaseDateAsString) {
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

	private boolean _hasErrorMessage(Element element) {
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