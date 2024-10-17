/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.entry.processor.portlet.constants;

import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Jürgen Kappler
 */
public class FragmentEntryLinkPortletKeys {

	public static final String FRAGMENT_ENTRY_LINK_INSTANCEABLE_TEST_PORTLET =
		"com_liferay_test_portlet_FragmentEntryLinkInstanceableTestPortlet";

	public static final String
		FRAGMENT_ENTRY_LINK_INSTANCEABLE_TEST_PORTLET_ALIAS =
			StringUtil.toLowerCase(
				RandomTestUtil.randomString(
					NumericStringRandomizerBumper.INSTANCE,
					UniqueStringRandomizerBumper.INSTANCE));

	public static final String
		FRAGMENT_ENTRY_LINK_NONINSTANCEABLE_TEST_PORTLET =
			"com_liferay_test_portlet_" +
				"FragmentEntryLinkNoninstanceableTestPortlet";

	public static final String
		FRAGMENT_ENTRY_LINK_NONINSTANCEABLE_TEST_PORTLET_ALIAS =
			StringUtil.toLowerCase(
				RandomTestUtil.randomString(
					NumericStringRandomizerBumper.INSTANCE,
					UniqueStringRandomizerBumper.INSTANCE));

}