/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.type.facet.portlet;

import com.liferay.portal.kernel.util.KeyValuePair;

import java.util.List;
import java.util.Locale;

/**
 * @author Lino Alves
 */
public interface TypeFacetPortletPreferences {

	public static final String PREFERENCE_KEY_ASSET_TYPES = "assetTypes";

	public static final String PREFERENCE_KEY_FREQUENCIES_VISIBLE =
		"frequenciesVisible";

	public static final String PREFERENCE_KEY_FREQUENCY_THRESHOLD =
		"frequencyThreshold";

	public static final String PREFERENCE_KEY_ORDER = "order";

	public static final String PREFERENCE_KEY_PARAMETER_NAME = "parameterName";

	public String getAssetTypes();

	public List<KeyValuePair> getAvailableAssetTypes(
		long companyId, Locale locale);

	public List<KeyValuePair> getCurrentAssetTypes(
		long companyId, Locale locale);

	public String[] getCurrentAssetTypesArray(long companyId);

	public int getFrequencyThreshold();

	public String getOrder();

	public String getParameterName();

	public boolean isFrequenciesVisible();

}