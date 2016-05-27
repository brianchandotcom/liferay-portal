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

package com.liferay.amazon.rankings.web.model;

import java.io.Serializable;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class AmazonRankings
	implements Comparable<AmazonRankings>, Serializable {

	public AmazonRankings(
		String isbn, String productName, String catalog, String[] authors,
		Date releaseDate, String releaseDateAsString, String manufacturer,
		String smallImageURL, String mediumImageURL, String largeImageURL,
		double listPrice, double ourPrice, double usedPrice,
		double collectiblePrice, double thirdPartyNewPrice, int salesRank,
		String media, String availability) {

		_isbn = isbn;
		_productName = productName;
		_catalog = catalog;
		_authors = authors;
		_releaseDate = releaseDate;
		_releaseDateAsString = releaseDateAsString;
		_manufacturer = manufacturer;
		_smallImageURL = smallImageURL;
		_mediumImageURL = mediumImageURL;
		_largeImageURL = largeImageURL;
		_listPrice = listPrice;
		_ourPrice = ourPrice;
		_usedPrice = usedPrice;
		_collectiblePrice = collectiblePrice;
		_thirdPartyNewPrice = thirdPartyNewPrice;
		_salesRank = salesRank;
		_media = media;
		_availability = availability;
	}

	@Override
	public int compareTo(AmazonRankings amazonRankings) {
		if (amazonRankings == null) {
			return -1;
		}

		if (getSalesRank() > amazonRankings.getSalesRank()) {
			return 1;
		}
		else if (getSalesRank() < amazonRankings.getSalesRank()) {
			return -1;
		}
		else {
			return getReleaseDate().compareTo(amazonRankings.getReleaseDate());
		}
	}

	public String[] getAuthors() {
		return _authors;
	}

	public String getAvailability() {
		return _availability;
	}

	public String getCatalog() {
		return _catalog;
	}

	public double getCollectiblePrice() {
		return _collectiblePrice;
	}

	public String getISBN() {
		return _isbn;
	}

	public String getLargeImageURL() {
		return _largeImageURL;
	}

	public double getListPrice() {
		return _listPrice;
	}

	public String getManufacturer() {
		return _manufacturer;
	}

	public String getMedia() {
		return _media;
	}

	public String getMediumImageURL() {
		return _mediumImageURL;
	}

	public double getOurPrice() {
		return _ourPrice;
	}

	public String getProductName() {
		return _productName;
	}

	public Date getReleaseDate() {
		return _releaseDate;
	}

	public String getReleaseDateAsString() {
		return _releaseDateAsString;
	}

	public int getSalesRank() {
		return _salesRank;
	}

	public String getSmallImageURL() {
		return _smallImageURL;
	}

	public double getThirdPartyNewPrice() {
		return _thirdPartyNewPrice;
	}

	public double getUsedPrice() {
		return _usedPrice;
	}

	public void setAuthors(String[] authors) {
		_authors = authors;
	}

	public void setAvailability(String availability) {
		_availability = availability;
	}

	public void setCatalog(String catalog) {
		_catalog = catalog;
	}

	public void setCollectiblePrice(double collectiblePrice) {
		_collectiblePrice = collectiblePrice;
	}

	public void setISBN(String isbn) {
		_isbn = isbn;
	}

	public void setLargeImageURL(String largeImageURL) {
		_largeImageURL = largeImageURL;
	}

	public void setListPrice(double listPrice) {
		_listPrice = listPrice;
	}

	public void setManufacturer(String manufacturer) {
		_manufacturer = manufacturer;
	}

	public void setMedia(String media) {
		_media = media;
	}

	public void setMediumImageURL(String mediumImageURL) {
		_mediumImageURL = mediumImageURL;
	}

	public void setOurPrice(double ourPrice) {
		_ourPrice = ourPrice;
	}

	public void setProductName(String productName) {
		_productName = productName;
	}

	public void setReleaseDate(Date releaseDate) {
		_releaseDate = releaseDate;
	}

	public void setReleaseDateAsString(String releaseDateAsString) {
		_releaseDateAsString = releaseDateAsString;
	}

	public void setSalesRank(int salesRank) {
		_salesRank = salesRank;
	}

	public void setSmallImageURL(String smallImageURL) {
		_smallImageURL = smallImageURL;
	}

	public void setThirdPartyNewPrice(double thirdPartyNewPrice) {
		_thirdPartyNewPrice = thirdPartyNewPrice;
	}

	public void setUsedPrice(double usedPrice) {
		_usedPrice = usedPrice;
	}

	private String[] _authors;
	private String _availability;
	private String _catalog;
	private double _collectiblePrice;
	private String _isbn;
	private String _largeImageURL;
	private double _listPrice;
	private String _manufacturer;
	private String _media;
	private String _mediumImageURL;
	private double _ourPrice;
	private String _productName;
	private Date _releaseDate;
	private String _releaseDateAsString;
	private int _salesRank;
	private String _smallImageURL;
	private double _thirdPartyNewPrice;
	private double _usedPrice;

}