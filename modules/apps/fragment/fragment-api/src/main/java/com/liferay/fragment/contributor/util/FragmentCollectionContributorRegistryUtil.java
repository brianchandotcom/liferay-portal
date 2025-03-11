/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.contributor.util;

import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.fragment.contributor.FragmentCollectionContributorRegistry;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.portal.kernel.module.service.Snapshot;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Lourdes Fernández Besada
 */
public class FragmentCollectionContributorRegistryUtil {

	public static FragmentCollectionContributor
		getFragmentCollectionContributor(String fragmentCollectionKey) {

		FragmentCollectionContributorRegistry
			fragmentCollectionContributorRegistry =
				getFragmentCollectionContributorRegistry();

		return fragmentCollectionContributorRegistry.
			getFragmentCollectionContributor(fragmentCollectionKey);
	}

	public static FragmentCollectionContributorRegistry
		getFragmentCollectionContributorRegistry() {

		return _fragmentCollectionContributorRegistrySnapshot.get();
	}

	public static List<FragmentCollectionContributor>
		getFragmentCollectionContributors() {

		FragmentCollectionContributorRegistry
			fragmentCollectionContributorRegistry =
				getFragmentCollectionContributorRegistry();

		return fragmentCollectionContributorRegistry.
			getFragmentCollectionContributors();
	}

	public static FragmentComposition getFragmentComposition(
		String fragmentCompositionKey) {

		FragmentCollectionContributorRegistry
			fragmentCollectionContributorRegistry =
				getFragmentCollectionContributorRegistry();

		return fragmentCollectionContributorRegistry.getFragmentComposition(
			fragmentCompositionKey);
	}

	public static Map<String, FragmentEntry> getFragmentEntries() {
		FragmentCollectionContributorRegistry
			fragmentCollectionContributorRegistry =
				getFragmentCollectionContributorRegistry();

		return fragmentCollectionContributorRegistry.getFragmentEntries();
	}

	public static Map<String, FragmentEntry> getFragmentEntries(Locale locale) {
		FragmentCollectionContributorRegistry
			fragmentCollectionContributorRegistry =
				getFragmentCollectionContributorRegistry();

		return fragmentCollectionContributorRegistry.getFragmentEntries(locale);
	}

	public static FragmentEntry getFragmentEntry(String fragmentEntryKey) {
		FragmentCollectionContributorRegistry
			fragmentCollectionContributorRegistry =
				getFragmentCollectionContributorRegistry();

		return fragmentCollectionContributorRegistry.getFragmentEntry(
			fragmentEntryKey);
	}

	private static final Snapshot<FragmentCollectionContributorRegistry>
		_fragmentCollectionContributorRegistrySnapshot = new Snapshot<>(
			FragmentCollectionContributorRegistryUtil.class,
			FragmentCollectionContributorRegistry.class);

}