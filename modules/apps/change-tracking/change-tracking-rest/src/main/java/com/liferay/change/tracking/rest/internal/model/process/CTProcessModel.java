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

package com.liferay.change.tracking.rest.internal.model.process;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.rest.internal.model.collection.CTCollectionModel;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author Máté Thurzó
 */
public class CTProcessModel {

	public static CTProcessModel emptyCTProcessModel() {
		Builder builder = forCompany(0);

		return builder.setCTCollection(
			null
		).build();
	}

	public static CTProcessModel.Builder forCompany(long companyId) {
		return new Builder(companyId);
	}

	@XmlElement
	public long getCompanyId() {
		return _companyId;
	}

	@XmlElement
	public CTCollectionModel getCTCollection() {
		return _ctCollection;
	}

	@XmlElement
	public Date getDate() {
		return _date;
	}

	@XmlElement
	public String getUserInitials() {
		return _userInitials;
	}

	@XmlElement
	public String getUserName() {
		return _userName;
	}

	@XmlElement
	public String getUserPortraitURL() {
		return _userPortraitURL;
	}

	public static class Builder {

		public Builder(long companyId) {
			_ctProcessModel = new CTProcessModel();

			_ctProcessModel._companyId = companyId;
		}

		public CTProcessModel build() {
			return _ctProcessModel;
		}

		public Builder setCTCollection(CTCollection ctCollection) {
			if (ctCollection == null) {
				_ctProcessModel._ctCollection =
					CTCollectionModel.EMPTY_CT_COLLECTION_MODEL;

				return this;
			}

			CTCollectionModel.Builder builder =
				CTCollectionModel.forCTCollection(ctCollection);

			_ctProcessModel._ctCollection = builder.build();

			return this;
		}

		public Builder setDate(Date date) {
			_ctProcessModel._date = date;

			return this;
		}

		public Builder setUserInitials(String userInitials) {
			_ctProcessModel._userInitials = userInitials;

			return this;
		}

		public Builder setUserName(String userName) {
			_ctProcessModel._userName = userName;

			return this;
		}

		public Builder setUserPortraitURL(String userPortraitURL) {
			_ctProcessModel._userPortraitURL = userPortraitURL;

			return this;
		}

		private final CTProcessModel _ctProcessModel;

	}

	private CTProcessModel() {
	}

	private long _companyId;
	private CTCollectionModel _ctCollection;
	private Date _date;
	private String _userInitials;
	private String _userName;
	private String _userPortraitURL;

}