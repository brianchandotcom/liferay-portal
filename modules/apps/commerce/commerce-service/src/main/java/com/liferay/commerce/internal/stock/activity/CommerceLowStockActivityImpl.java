/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.stock.activity;

import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.stock.activity.CommerceLowStockActivity;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Luca Pellizzon
 */
@Component(
	property = {
		"commerce.low.stock.activity.key=" + CommerceLowStockActivityImpl.KEY,
		"commerce.low.stock.activity.priority:Integer=10"
	},
	service = CommerceLowStockActivity.class
)
public class CommerceLowStockActivityImpl implements CommerceLowStockActivity {

	public static final String KEY = "default";

	@Override
	public void execute(CPInstance cpInstance) throws PortalException {
		if (cpInstance.isPublished()) {
			cpInstance.setPublished(false);

			_cpInstanceLocalService.updateCPInstance(cpInstance);
		}
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "set-as-unpublished");
	}

	@Override
	public Map<Locale, String> getLabelMap() {
		Map<Locale, String> labelMap = new HashMap<>();

		for (Locale locale : _languageUtil.getAvailableLocales()) {
			labelMap.put(locale, getLabel(locale));
		}

		return labelMap;
	}

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private Language _language;

	@Reference
	private LanguageUtil _languageUtil;

}