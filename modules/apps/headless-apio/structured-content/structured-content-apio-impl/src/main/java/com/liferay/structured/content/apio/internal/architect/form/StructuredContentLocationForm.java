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

package com.liferay.structured.content.apio.internal.architect.form;

import com.liferay.apio.architect.form.Form;

/**
 * @author Javier Gamarra
 */
public class StructuredContentLocationForm {

	public static Form<StructuredContentLocationForm> buildValuesForm(
		Form.Builder<StructuredContentLocationForm> builder) {

		return builder.title(
			__ -> "The structured content location form"
		).description(
			__ ->
				"This form is used to create the location form of a " +
					"structured form"
		).constructor(
			StructuredContentLocationForm::new
		).addOptionalDouble(
			"latitude", StructuredContentLocationForm::setLatitude
		).addOptionalDouble(
			"longitude", StructuredContentLocationForm::setLongitude
		).build();
	}

	public Double getLatitude() {
		return _latitude;
	}

	public Double getLongitude() {
		return _longitude;
	}

	public void setLatitude(Double latitude) {
		_latitude = latitude;
	}

	public void setLongitude(Double longitude) {
		_longitude = longitude;
	}

	private Double _latitude;
	private Double _longitude;

}