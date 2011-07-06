/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.amazonrankings.util;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.amazonrankings.model.AmazonRankings;

/**
 * @author Brian Wing Shun Chan
 * @author Samuel Kong
 */
public class AmazonRankingsWebCacheItem implements WebCacheItem {

	public AmazonRankingsWebCacheItem(String isbn) {
		_isbn = isbn;
	}

	public Object convert(String key) {
		String isbn = _isbn;

		AmazonRankings amazonRankings = null;

		try {

			Map<String, String> parameters = new TreeMap<String, String>();
			parameters.put("Service", "AWSECommerceService");
			parameters.put("Operation", "ItemLookup");
			parameters.put("IdType", "ASIN");
			parameters.put("ItemId", isbn);
			parameters.put("ResponseGroup", "Images,ItemAttributes,Offers,SalesRank");
			parameters.put("AWSAccessKeyId", AmazonRankingsUtil.getAmazonAccessKeyId());
			parameters.put("Timestamp", AmazonRankingsUtil.getTimestamp());

			String urlWithSignature = 
				AmazonSignedRequestsHelper.generateUrlWithSignature(parameters);
						
			String xml = HttpUtil.URLtoString(urlWithSignature);

			Document doc = SAXReaderUtil.read(xml);

			Element root = doc.getRootElement();

			if (root == null) {
				return null;
			}

			Element items = root.element("Items");

			if (items == null) {
				return null;
			}

			// check for any error and if found, log it
			
			Element request = items.element("Request");
			if (request != null) {
				Element errors = request.element("Errors");
				if (errors != null) {
					Element error = errors.element("Error");
					if (error != null) {
						Element message = error.element("Message");
						if (message != null) {
							_log.error("Amazon Error: " + message.getText());
							return null;
						}
					}
				}
			}
			
			Element item = items.element("Item");

			if (item == null) {
				return null;
			}

			Element itemAttributes = item.element("ItemAttributes");

			if (itemAttributes == null) {
				return null;
			}

			String productName = itemAttributes.elementText("Title");
			String catalog = StringPool.BLANK;
			String[] authors = getAuthors(itemAttributes);
			String releaseDateAsString = itemAttributes.elementText(
				"PublicationDate");
			Date releaseDate = getReleaseDate(releaseDateAsString);
			String manufacturer = itemAttributes.elementText("Manufacturer");
			String smallImageURL = getImageURL(item, "SmallImage");
			String mediumImageURL = getImageURL(item, "MediumImage");
			String largeImageURL = getImageURL(item, "LargeImage");
			double listPrice = getPrice(itemAttributes.element("ListPrice"));

			double ourPrice = 0;

			Element offerListing = getOfferListing(item);

			if (offerListing != null) {
				ourPrice = getPrice(offerListing.element("Price"));
			}

			double usedPrice = 0;
			double collectiblePrice = 0;
			double thirdPartyNewPrice = 0;

			Element offerSummary = item.element("OfferSummary");

			if (offerSummary != null) {
				usedPrice = getPrice(offerSummary.element("LowestUsedPrice"));

				collectiblePrice = getPrice(
					offerSummary.element("LowestCollectiblePrice"));

				thirdPartyNewPrice = getPrice(
					offerSummary.element("LowestNewPrice"));
			}

			int salesRank = GetterUtil.getInteger(
				item.elementText("SalesRank"));
			String media = StringPool.BLANK;
			String availability = getAvailability(offerListing);

			amazonRankings = new AmazonRankings(
				isbn, productName, catalog, authors, releaseDate,
				releaseDateAsString, manufacturer, smallImageURL,
				mediumImageURL, largeImageURL, listPrice, ourPrice, usedPrice,
				collectiblePrice, thirdPartyNewPrice, salesRank, media,
				availability);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return amazonRankings;
	}

	public long getRefreshTime() {
		return _REFRESH_TIME;
	}

	protected String[] getAuthors(Element itemAttributes) {
		List<String> authors = new ArrayList<String>();

		for (Element author : itemAttributes.elements("Author")) {
			authors.add(author.getText());
		}

		return authors.toArray(new String[authors.size()]);
	}

	protected String getAvailability(Element offerListing) {
		if (offerListing == null) {
			return null;
		}

		Element availability = offerListing.element(
			"Availability");

		return availability.elementText("Availability");
	}

	protected String getImageURL(Element item, String name) {
		String imageURL = null;

		Element image = item.element(name);

		if (image != null) {
			imageURL = image.elementText("URL");
		}

		return imageURL;
	}

	protected Element getOfferListing(Element item) {
		Element offers = item.element("Offers");

		if (offers == null) {
			return null;
		}

		Element offer = offers.element("Offer");

		if (offer == null) {
			return null;
		}

		return offer.element("OfferListing");
	}

	protected double getPrice(Element price) {
		if (price == null) {
			return 0;
		}

		return GetterUtil.getInteger(price.elementText("Amount")) * 0.01;
	}

	protected Date getReleaseDate(String releaseDateAsString) {
		if (Validator.isNull(releaseDateAsString)) {
			return null;
		}

		DateFormat dateFormat = null;

		if (releaseDateAsString.length() > 7) {
			dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				"yyyy-MM-dd", Locale.US);
		}
		else {
			dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				"yyyy-MM", Locale.US);
		}

		return GetterUtil.getDate(releaseDateAsString, dateFormat);
	}

	private static final long _REFRESH_TIME = Time.MINUTE * 20;

	private String _isbn;
	
	private static Log _log = LogFactoryUtil.getLog(AmazonRankingsWebCacheItem.class);

}