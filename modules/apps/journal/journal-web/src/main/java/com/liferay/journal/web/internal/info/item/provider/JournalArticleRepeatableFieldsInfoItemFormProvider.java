/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.info.item.provider;

import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.info.item.provider.DDMStructureRepeatableFieldsInfoItemFieldSetProvider;
import com.liferay.info.exception.NoSuchFormVariationException;
import com.liferay.info.field.InfoFieldSetEntry;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.provider.RepeatableFieldsInfoItemFormProvider;
import com.liferay.info.localized.bundle.ModelResourceLocalizedValue;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.List;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(
	property = Constants.SERVICE_RANKING + ":Integer=10",
	service = RepeatableFieldsInfoItemFormProvider.class
)
public class JournalArticleRepeatableFieldsInfoItemFormProvider
	extends JournalArticleInfoItemFormProvider
	implements RepeatableFieldsInfoItemFormProvider<JournalArticle> {

	@Override
	public InfoForm getRepeatableFieldsInfoForm(String formVariationKey)
		throws NoSuchFormVariationException {

		try {
			return InfoForm.builder(
			).<NoSuchStructureException>infoFieldSetEntry(
				unsafeConsumer -> {
					long ddmStructureId = GetterUtil.getLong(formVariationKey);

					if (ddmStructureId != 0) {
						List<InfoFieldSetEntry> infoFieldSetEntries =
							_ddmStructureRepeatableFieldsInfoItemFieldSetProvider.
								getInfoItemFieldSet(ddmStructureId);

						for (InfoFieldSetEntry infoFieldSetEntry :
								infoFieldSetEntries) {

							unsafeConsumer.accept(infoFieldSetEntry);
						}
					}
				}
			).labelInfoLocalizedValue(
				new ModelResourceLocalizedValue(JournalArticle.class.getName())
			).name(
				JournalArticle.class.getName()
			).build();
		}
		catch (NoSuchStructureException noSuchStructureException) {
			throw new NoSuchFormVariationException(
				formVariationKey, noSuchStructureException);
		}
	}

	@Reference
	private DDMStructureRepeatableFieldsInfoItemFieldSetProvider
		_ddmStructureRepeatableFieldsInfoItemFieldSetProvider;

}