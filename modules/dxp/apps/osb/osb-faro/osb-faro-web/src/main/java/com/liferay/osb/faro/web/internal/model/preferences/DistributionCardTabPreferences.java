/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.web.internal.model.preferences;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class DistributionCardTabPreferences {

	public String getContext() {
		return _context;
	}

	public String getId() {
		return _id;
	}

	public int getNumberOfBins() {
		return _numberOfBins;
	}

	public String getPropertyId() {
		return _propertyId;
	}

	public String getPropertyType() {
		return _propertyType;
	}

	public String getTitle() {
		return _title;
	}

	public void setContext(String context) {
		_context = context;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setNumberOfBins(int numberOfBins) {
		_numberOfBins = numberOfBins;
	}

	public void setPropertyId(String propertyId) {
		_propertyId = propertyId;
	}

	public void setPropertyType(String propertyType) {
		_propertyType = propertyType;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private String _context;
	private String _id;
	private int _numberOfBins;
	private String _propertyId;
	private String _propertyType;
	private String _title;

}