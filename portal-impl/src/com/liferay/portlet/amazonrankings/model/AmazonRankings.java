/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.amazonrankings.model;

import java.io.Serializable;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class AmazonRankings
	implements Comparable<AmazonRankings>, Serializable {

	public AmazonRankings(String isbn, String productName, String catalog,
							   String[] authors, Date releaseDate,
							   String releaseDateAsString, String manufacturer,
							   String smallImageURL, String mediumImageURL,
							   String largeImageURL, double listPrice,
							   double ourPrice, double usedPrice,
							   double collectiblePrice,
							   double thirdPartyNewPrice, int salesRank,
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

	public String getISBN() {
		return _isbn;
	}

	public void setISBN(String isbn) {
		_isbn = isbn;
	}

	public String getProductName() {
		return _productName;
	}

	public void setProductName(String productName) {
		_productName = productName;
	}

	public String getCatalog() {
		return _catalog;
	}

	public void setCatalog(String catalog) {
		_catalog = catalog;
	}

	public String[] getAuthors() {
		return _authors;
	}

	public void setAuthors(String[] authors) {
		_authors = authors;
	}

	public Date getReleaseDate() {
		return _releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		_releaseDate = releaseDate;
	}

	public String getReleaseDateAsString() {
		return _releaseDateAsString;
	}

	public void setReleaseDateAsString(String releaseDateAsString) {
		_releaseDateAsString = releaseDateAsString;
	}

	public String getManufacturer() {
		return _manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		_manufacturer = manufacturer;
	}

	public String getSmallImageURL() {
		return _smallImageURL;
	}

	public void setSmallImageURL(String smallImageURL) {
		_smallImageURL = smallImageURL;
	}

	public String getMediumImageURL() {
		return _mediumImageURL;
	}

	public void setMediumImageURL(String mediumImageURL) {
		_mediumImageURL = mediumImageURL;
	}

	public String getLargeImageURL() {
		return _largeImageURL;
	}

	public void setLargeImageURL(String largeImageURL) {
		_largeImageURL = largeImageURL;
	}

	public double getListPrice() {
		return _listPrice;
	}

	public void setListPrice(double listPrice) {
		_listPrice = listPrice;
	}

	public double getOurPrice() {
		return _ourPrice;
	}

	public void setOurPrice(double ourPrice) {
		_ourPrice = ourPrice;
	}

	public double getUsedPrice() {
		return _usedPrice;
	}

	public void setUsedPrice(double usedPrice) {
		_usedPrice = usedPrice;
	}

	public double getCollectiblePrice() {
		return _collectiblePrice;
	}

	public void setCollectiblePrice(double collectiblePrice) {
		_collectiblePrice = collectiblePrice;
	}

	public double getThirdPartyNewPrice() {
		return _thirdPartyNewPrice;
	}

	public void setThirdPartyNewPrice(double thirdPartyNewPrice) {
		_thirdPartyNewPrice = thirdPartyNewPrice;
	}

	public int getSalesRank() {
		return _salesRank;
	}

	public void setSalesRank(int salesRank) {
		_salesRank = salesRank;
	}

	public String getMedia() {
		return _media;
	}

	public void setMedia(String media) {
		_media = media;
	}

	public String getAvailability() {
		return _availability;
	}

	public void setAvailability(String availability) {
		_availability = availability;
	}

	private String _isbn;
	private String _productName;
	private String _catalog;
	private String[] _authors;
	private Date _releaseDate;
	private String _releaseDateAsString;
	private String _manufacturer;
	private String _smallImageURL;
	private String _mediumImageURL;
	private String _largeImageURL;
	private double _listPrice;
	private double _ourPrice;
	private double _usedPrice;
	private double _collectiblePrice;
	private double _thirdPartyNewPrice;
	private int _salesRank;
	private String _media;
	private String _availability;

}