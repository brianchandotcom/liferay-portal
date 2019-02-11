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

package com.liferay.change.tracking.rest.internal.model.entry;

import com.liferay.change.tracking.model.CTEntry;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Daniel Kocsis
 */
@XmlRootElement
public class CTEntryModel {

	public static CTEntryModel forCTEntry(CTEntry ctEntry) {
		Builder builder = new Builder();

		return builder.setChangeType(
			ctEntry.getChangeType()
		).setClassNameId(
			ctEntry.getClassNameId()
		).setClassPK(
			ctEntry.getClassPK()
		).setCTEntryId(
			ctEntry.getCtEntryId()
		).setResourcePrimKey(
			ctEntry.getResourcePrimKey()
		).build();
	}

	@XmlElement
	public int getChangeType() {
		return _changeType;
	}

	@XmlElement
	public long getClassNameId() {
		return _classNameId;
	}

	@XmlElement
	public long getClassPK() {
		return _classPK;
	}

	@XmlElement
	public long getCtEntryId() {
		return _ctEntryId;
	}

	@XmlElement
	public long getResourcePrimKey() {
		return _resourcePrimKey;
	}

	public static class Builder {

		public CTEntryModel build() {
			return _ctEntryModel;
		}

		public CTEntryModel.Builder setChangeType(int changeType) {
			_ctEntryModel._changeType = changeType;

			return this;
		}

		public CTEntryModel.Builder setClassNameId(long classNameId) {
			_ctEntryModel._classNameId = classNameId;

			return this;
		}

		public CTEntryModel.Builder setClassPK(long classPK) {
			_ctEntryModel._classPK = classPK;

			return this;
		}

		public CTEntryModel.Builder setCTEntryId(long ctEntryId) {
			_ctEntryModel._ctEntryId = ctEntryId;

			return this;
		}

		public CTEntryModel.Builder setResourcePrimKey(long resourcePrimKey) {
			_ctEntryModel._resourcePrimKey = resourcePrimKey;

			return this;
		}

		private Builder() {
			_ctEntryModel = new CTEntryModel();
		}

		private final CTEntryModel _ctEntryModel;

	}

	private CTEntryModel() {
	}

	private int _changeType;
	private long _classNameId;
	private long _classPK;
	private long _ctEntryId;
	private long _resourcePrimKey;

}