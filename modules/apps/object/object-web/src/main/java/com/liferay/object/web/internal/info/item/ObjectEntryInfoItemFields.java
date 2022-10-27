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

package com.liferay.object.web.internal.info.item;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.field.type.URLInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.object.model.ObjectEntry;

/**
 * @author Jorge Ferrer
 */
public class ObjectEntryInfoItemFields {

	public static final InfoField<TextInfoFieldType> authorInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"author"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				ObjectEntryInfoItemFields.class, "author")
		).build();
	public static final InfoField<DateInfoFieldType> createDateInfoField =
		BuilderHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"createDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				ObjectEntryInfoItemFields.class, "create-date")
		).build();
	public static final InfoField<URLInfoFieldType> displayPageURLInfoField =
		BuilderHolder._builder.infoFieldType(
			URLInfoFieldType.INSTANCE
		).name(
			"displayPageURL"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				"com.liferay.asset.info.display.impl", "display-page-url")
		).build();
	public static final InfoField externalReferenceCodeInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"externalReferenceCode"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				ObjectEntryInfoItemFields.class, "external-reference-code")
		).build();
	public static final InfoField<DateInfoFieldType> modifiedDateInfoField =
		BuilderHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"modifiedDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				ObjectEntryInfoItemFields.class, "modified-date")
		).build();
	public static final InfoField objectEntryIdInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"objectEntryId"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(ObjectEntryInfoItemFields.class, "id")
		).build();
	public static final InfoField<DateInfoFieldType> publishDateInfoField =
		BuilderHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"publishDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				ObjectEntryInfoItemFields.class, "publish-date")
		).build();
	public static final InfoField statusInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"status"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				ObjectEntryInfoItemFields.class, "status")
		).build();
	public static final InfoField userProfileImageInfoField =
		BuilderHolder._builder.infoFieldType(
			ImageInfoFieldType.INSTANCE
		).name(
			"userProfileImage"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				ObjectEntryInfoItemFields.class, "user-profile-image")
		).build();

	private static class BuilderHolder {

		private static final InfoField.NamespacedBuilder _builder =
			InfoField.builder(ObjectEntry.class.getSimpleName());

	}

}