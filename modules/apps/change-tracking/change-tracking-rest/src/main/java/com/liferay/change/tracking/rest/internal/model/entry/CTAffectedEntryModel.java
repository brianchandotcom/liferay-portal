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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Gergely Mathe
 */
@XmlRootElement
public class CTAffectedEntryModel {

	public static CTAffectedEntryModel forCTEntry(CTEntry ctEntry) {
		Builder builder = new Builder();

		return builder.setContentType(
			CTDefinitionRegistryUtil.getVersionEntityContentTypeLanguageKey(
				ctEntry.getModelClassNameId())
		).setTitle(
			CTDefinitionRegistryUtil.getVersionEntityTitle(
				ctEntry.getModelClassNameId(), ctEntry.getModelClassPK())
		).build();
	}

	@XmlElement
	public String getContentType() {
		return _contentType;
	}

	@XmlElement
	public String getTitle() {
		return _title;
	}

	public static class Builder {

		public CTAffectedEntryModel build() {
			return _ctAffectedEntryModel;
		}

		public CTAffectedEntryModel.Builder setContentType(String contentType) {
			_ctAffectedEntryModel._contentType = contentType;

			return this;
		}

		public CTAffectedEntryModel.Builder setTitle(String title) {
			_ctAffectedEntryModel._title = title;

			return this;
		}

		private Builder() {
			_ctAffectedEntryModel = new CTAffectedEntryModel();
		}

		private final CTAffectedEntryModel _ctAffectedEntryModel;

	}

	private CTAffectedEntryModel() {
	}

	private String _contentType;
	private String _title;

}