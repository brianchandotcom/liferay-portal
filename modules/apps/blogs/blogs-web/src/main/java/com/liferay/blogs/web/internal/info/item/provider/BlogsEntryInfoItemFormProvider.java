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

package com.liferay.blogs.web.internal.info.item.provider;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.info.item.provider.AssetEntryInfoItemFieldSetProvider;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.web.internal.info.item.BlogsEntryInfoItemFields;
import com.liferay.expando.info.item.provider.ExpandoInfoItemFieldSetProvider;
import com.liferay.info.field.InfoFieldSetEntry;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;

import java.util.Arrays;
import java.util.Collection;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 * @author Jorge Ferrer
 */
@Component(
	immediate = true, property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemFormProvider.class
)
public class BlogsEntryInfoItemFormProvider
	implements InfoItemFormProvider<BlogsEntry> {

	@Override
	public InfoForm getInfoForm() {
		InfoForm infoForm = new InfoForm(BlogsEntry.class.getName());

		infoForm.addAll(_getBlogsEntryInfoFieldSetEntries());

		infoForm.add(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldSet(
				BlogsEntry.class.getName()));

		infoForm.add(
			_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
				AssetEntry.class.getName()));

		infoForm.add(
			_expandoInfoItemFieldSetProvider.getInfoFieldSet(
				BlogsEntry.class.getName()));

		return infoForm;
	}

	private Collection<InfoFieldSetEntry> _getBlogsEntryInfoFieldSetEntries() {
		return Arrays.asList(
			BlogsEntryInfoItemFields.titleInfoField,
			BlogsEntryInfoItemFields.subtitleInfoField,
			BlogsEntryInfoItemFields.descriptionInfoField,
			BlogsEntryInfoItemFields.smallImageInfoField,
			BlogsEntryInfoItemFields.coverImageInfoField,
			BlogsEntryInfoItemFields.coverImageCaptionInfoField,
			BlogsEntryInfoItemFields.authorNameInfoField,
			BlogsEntryInfoItemFields.authorProfileImageInfoField,
			BlogsEntryInfoItemFields.publishDateInfoField,
			BlogsEntryInfoItemFields.displayPageUrlInfoField,
			BlogsEntryInfoItemFields.contentInfoField);
	}

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Reference
	private AssetEntryInfoItemFieldSetProvider
		_assetEntryInfoItemFieldSetProvider;

	@Reference
	private ExpandoInfoItemFieldSetProvider _expandoInfoItemFieldSetProvider;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

}