/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.fragment.contributor.util.FragmentCollectionContributorRegistryUtil;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.util.FragmentRendererRegistryUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.headless.admin.site.dto.v1_0.DefaultFragmentReference;
import com.liferay.headless.admin.site.dto.v1_0.FragmentItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.FragmentReference;
import com.liferay.headless.admin.site.internal.util.LogUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

/**
 * @author Mikel Lorza
 */
public class FragmentEntryReferenceUtil {

	public static FragmentEntryReference getFragmentEntryReference(
			long companyId, FragmentReference fragmentReference,
			long scopeGroupId)
		throws Exception {

		if (fragmentReference == null) {
			throw new UnsupportedOperationException();
		}

		if (Objects.equals(
				fragmentReference.getFragmentReferenceType(),
				FragmentReference.FragmentReferenceType.
					FRAGMENT_ITEM_EXTERNAL_REFERENCE)) {

			FragmentItemExternalReference fragmentItemExternalReference =
				(FragmentItemExternalReference)fragmentReference;

			if (Validator.isNull(
					fragmentItemExternalReference.getExternalReferenceCode())) {

				throw new UnsupportedOperationException();
			}

			FragmentEntry fragmentEntry = null;

			Long groupId = ItemScopeUtil.getGroupId(
				companyId, fragmentItemExternalReference.getScope(),
				scopeGroupId);

			if (groupId != null) {
				fragmentEntry =
					FragmentEntryLocalServiceUtil.
						fetchFragmentEntryByExternalReferenceCode(
							GetterUtil.getString(
								fragmentItemExternalReference.
									getExternalReferenceCode()),
							groupId);
			}

			if (fragmentEntry == null) {
				LogUtil.logOptionalReference(
					fragmentItemExternalReference.getClassName(),
					fragmentItemExternalReference.getExternalReferenceCode(),
					fragmentItemExternalReference.getScope(), scopeGroupId);
			}

			return new FragmentEntryReference(
				fragmentItemExternalReference.getExternalReferenceCode(),
				ItemScopeUtil.getItemScopeExternalReferenceCode(
					fragmentItemExternalReference.getScope(), scopeGroupId),
				null);
		}

		DefaultFragmentReference defaultFragmentReference =
			(DefaultFragmentReference)fragmentReference;

		if (Validator.isNull(
				defaultFragmentReference.getDefaultFragmentKey())) {

			throw new UnsupportedOperationException();
		}

		FragmentEntry fragmentEntry =
			FragmentCollectionContributorRegistryUtil.getFragmentEntry(
				defaultFragmentReference.getDefaultFragmentKey());
		FragmentRenderer fragmentRenderer = null;

		if (fragmentEntry == null) {
			fragmentRenderer = FragmentRendererRegistryUtil.getFragmentRenderer(
				defaultFragmentReference.getDefaultFragmentKey());
		}

		if ((fragmentEntry == null) && (fragmentRenderer == null)) {
			LogUtil.logOptionalReference(
				DefaultFragmentReference.class,
				defaultFragmentReference.getDefaultFragmentKey(), scopeGroupId);
		}

		return new FragmentEntryReference(
			null, null, defaultFragmentReference.getDefaultFragmentKey());
	}

	public static class FragmentEntryReference {

		public FragmentEntryReference(
			String fragmentEntryERC, String fragmentEntryScopeERC,
			String rendererKey) {

			_fragmentEntryERC = fragmentEntryERC;
			_fragmentEntryScopeERC = fragmentEntryScopeERC;
			_rendererKey = rendererKey;
		}

		public String getFragmentEntryERC() {
			return _fragmentEntryERC;
		}

		public String getFragmentEntryScopeERC() {
			return _fragmentEntryScopeERC;
		}

		public String getRendererKey() {
			return _rendererKey;
		}

		private final String _fragmentEntryERC;
		private final String _fragmentEntryScopeERC;
		private final String _rendererKey;

	}

}