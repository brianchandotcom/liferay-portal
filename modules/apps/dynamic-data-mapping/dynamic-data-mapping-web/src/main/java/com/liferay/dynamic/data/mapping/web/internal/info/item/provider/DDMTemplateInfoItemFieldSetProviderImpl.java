/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.web.internal.info.item.provider;

import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.info.item.provider.DDMTemplateInfoItemFieldSetProvider;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portlet.display.template.PortletDisplayTemplate;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@Component(service = DDMTemplateInfoItemFieldSetProvider.class)
public class DDMTemplateInfoItemFieldSetProviderImpl
	implements DDMTemplateInfoItemFieldSetProvider {

	@Override
	public InfoFieldSet getInfoItemFieldSet(long ddmStructureId)
		throws NoSuchStructureException {

		try {
			DDMStructure ddmStructure =
				_ddmStructureLocalService.getDDMStructure(ddmStructureId);

			return InfoFieldSet.builder(
			).infoFieldSetEntry(
				unsafeConsumer -> {
					for (DDMTemplate ddmTemplate :
							ddmStructure.getTemplates()) {

						unsafeConsumer.accept(
							InfoField.builder(
							).infoFieldType(
								TextInfoFieldType.INSTANCE
							).namespace(
								StringPool.BLANK
							).name(
								_getTemplateFieldName(ddmTemplate)
							).labelInfoLocalizedValue(
								InfoLocalizedValue.localize(
									getClass(),
									ddmTemplate.getName(
										LocaleThreadLocal.
											getThemeDisplayLocale()))
							).build());
					}
				}
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(getClass(), "templates")
			).name(
				"templates"
			).build();
		}
		catch (NoSuchStructureException noSuchStructureException) {
			throw noSuchStructureException;
		}
		catch (PortalException portalException) {
			throw new RuntimeException(
				"Unexpected exception occurred", portalException);
		}
	}

	private String _getTemplateFieldName(DDMTemplate ddmTemplate) {
		String templateKey = ddmTemplate.getTemplateKey();

		return PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
			templateKey.replaceAll("\\W", "_");
	}

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

}