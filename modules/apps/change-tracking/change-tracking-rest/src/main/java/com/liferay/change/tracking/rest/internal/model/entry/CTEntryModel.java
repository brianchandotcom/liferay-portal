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

import com.liferay.change.tracking.definition.CTDefinitionRegistryUtil;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTEntryLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;

import java.io.Serializable;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Daniel Kocsis
 */
@XmlRootElement
public class CTEntryModel {

	public static final CTEntryModel EMPTY_CT_ENTRY_MODEL = new CTEntryModel();

	public static CTEntryModel forCTEntry(CTEntry ctEntry) {
		Builder builder = new Builder();

		return builder.setAffectedByCTEntriesCount(
			CTEntryLocalServiceUtil.getRelatedOwnerCTEntriesCount(
				ctEntry.getCtEntryId(), new QueryDefinition<>())
		).setChangeType(
			ctEntry.getChangeType()
		).setClassNameId(
			ctEntry.getModelClassNameId()
		).setClassPK(
			ctEntry.getModelClassPK()
		).setCollision(
			ctEntry.isCollision()
		).setContentType(
			CTDefinitionRegistryUtil.getVersionEntityContentTypeLanguageKey(
				ctEntry.getModelClassNameId())
		).setCTEntryId(
			ctEntry.getCtEntryId()
		).setModifiedDate(
			ctEntry.getModifiedDate()
		).setResourcePrimKey(
			ctEntry.getModelResourcePrimKey()
		).setSiteName(
			CTDefinitionRegistryUtil.getVersionEntitySiteName(
				ctEntry.getModelClassNameId(), ctEntry.getModelClassPK())
		).setTitle(
			CTDefinitionRegistryUtil.getVersionEntityTitle(
				ctEntry.getModelClassNameId(), ctEntry.getModelClassPK())
		).setUserName(
			ctEntry.getUserName()
		).setVersion(
			CTDefinitionRegistryUtil.getVersionEntityVersion(
				ctEntry.getModelClassNameId(), ctEntry.getModelClassPK())
		).build();
	}

	@XmlElement
	public int getAffectedByCTEntriesCount() {
		return _affectedByCTEntriesCount;
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
	public boolean getCollision() {
		return _collision;
	}

	@XmlElement
	public String getContentType() {
		return _contentType;
	}

	@XmlElement
	public long getCtEntryId() {
		return _ctEntryId;
	}

	@XmlElement
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	@XmlElement
	public long getResourcePrimKey() {
		return _resourcePrimKey;
	}

	@XmlElement
	public String getSiteName() {
		return _siteName;
	}

	@XmlElement
	public String getTitle() {
		return _title;
	}

	@XmlElement
	public String getUserName() {
		return _userName;
	}

	@XmlElement
	public Serializable getVersion() {
		return _version;
	}

	public static class Builder {

		public CTEntryModel build() {
			return _ctEntryModel;
		}

		public CTEntryModel.Builder setAffectedByCTEntriesCount(
			int affectedByCTEntriesCount) {

			_ctEntryModel._affectedByCTEntriesCount = affectedByCTEntriesCount;

			return this;
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

		public CTEntryModel.Builder setCollision(boolean collision) {
			_ctEntryModel._collision = collision;

			return this;
		}

		public CTEntryModel.Builder setContentType(String contentType) {
			_ctEntryModel._contentType = contentType;

			return this;
		}

		public CTEntryModel.Builder setCTEntryId(long ctEntryId) {
			_ctEntryModel._ctEntryId = ctEntryId;

			return this;
		}

		public CTEntryModel.Builder setModifiedDate(Date modifiedDate) {
			_ctEntryModel._modifiedDate = modifiedDate;

			return this;
		}

		public CTEntryModel.Builder setResourcePrimKey(long resourcePrimKey) {
			_ctEntryModel._resourcePrimKey = resourcePrimKey;

			return this;
		}

		public CTEntryModel.Builder setSiteName(String siteName) {
			_ctEntryModel._siteName = siteName;

			return this;
		}

		public CTEntryModel.Builder setTitle(String title) {
			_ctEntryModel._title = title;

			return this;
		}

		public CTEntryModel.Builder setUserName(String userName) {
			_ctEntryModel._userName = userName;

			return this;
		}

		public CTEntryModel.Builder setVersion(Serializable version) {
			_ctEntryModel._version = version;

			return this;
		}

		private Builder() {
			_ctEntryModel = new CTEntryModel();
		}

		private final CTEntryModel _ctEntryModel;

	}

	private CTEntryModel() {
	}

	private int _affectedByCTEntriesCount;
	private int _changeType;
	private long _classNameId;
	private long _classPK;
	private boolean _collision;
	private String _contentType;
	private long _ctEntryId;
	private Date _modifiedDate;
	private long _resourcePrimKey;
	private String _siteName;
	private String _title;
	private String _userName;
	private Serializable _version;

}