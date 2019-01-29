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

package com.liferay.change.tracking.rest.internal.model.collection;

import com.liferay.change.tracking.model.CTCollection;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Zoltan Csaszi
 */
@XmlRootElement
public class CTCollectionModel {

	public static final CTCollectionModel EMPTY_CT_COLLECTION_MODEL =
		new CTCollectionModel();

	public static Builder builder() {
		return new Builder();
	}

	public static Builder forCTCollection(CTCollection ctCollection) {
		Builder builder = new Builder();

		return builder.setCompanyId(
			ctCollection.getCompanyId()
		).setCtCollectionId(
			ctCollection.getCtCollectionId()
		).setDescription(
			ctCollection.getDescription()
		).setName(
			ctCollection.getName()
		).setStatusByUserName(
			ctCollection.getStatusByUserName()
		).setStatusDate(
			ctCollection.getStatusDate()
		);
	}

	@XmlElement
	public long getAdditionCount() {
		return _additionCount;
	}

	@XmlElement
	public long getCompanyId() {
		return _companyId;
	}

	@XmlElement
	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	@XmlElement
	public long getDeletionCount() {
		return _deletionCount;
	}

	@XmlElement
	public String getDescription() {
		return _description;
	}

	@XmlElement
	public long getModificationCount() {
		return _modificationCount;
	}

	@XmlElement
	public String getName() {
		return _name;
	}

	@XmlElement
	public String getStatusByUserName() {
		return _statusByUserName;
	}

	@XmlElement
	public Date getStatusDate() {
		return _statusDate;
	}

	public static class Builder {

		public CTCollectionModel build() {
			return _ctCollectionModel;
		}

		public Builder setAdditionCount(long additionCount) {
			_ctCollectionModel._additionCount = additionCount;

			return this;
		}

		public Builder setCompanyId(long companyId) {
			_ctCollectionModel._companyId = companyId;

			return this;
		}

		public Builder setCtCollectionId(long ctCollectionId) {
			_ctCollectionModel._ctCollectionId = ctCollectionId;

			return this;
		}

		public Builder setDeletionCount(long deletionCount) {
			_ctCollectionModel._deletionCount = deletionCount;

			return this;
		}

		public Builder setDescription(String description) {
			_ctCollectionModel._description = description;

			return this;
		}

		public Builder setModificationCount(long modificationCount) {
			_ctCollectionModel._modificationCount = modificationCount;

			return this;
		}

		public Builder setName(String name) {
			_ctCollectionModel._name = name;

			return this;
		}

		public Builder setStatusByUserName(String statusByUserName) {
			_ctCollectionModel._statusByUserName = statusByUserName;

			return this;
		}

		public Builder setStatusDate(Date statusDate) {
			_ctCollectionModel._statusDate = statusDate;

			return this;
		}

		private Builder() {
			_ctCollectionModel = new CTCollectionModel();
		}

		private final CTCollectionModel _ctCollectionModel;

	}

	private CTCollectionModel() {
	}

	private long _additionCount;
	private long _companyId;
	private long _ctCollectionId;
	private long _deletionCount;
	private String _description;
	private long _modificationCount;
	private String _name;
	private String _statusByUserName;
	private Date _statusDate;

}