/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.kernel.provider.util;

import com.liferay.layout.page.template.kernel.provider.LayoutPageTemplateEntryLayoutProvider;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Alberto Chaparro
 */
public class LayoutPageTemplateEntryLayoutProviderUtil {

	public static Layout getLayoutPageTemplateEntryLayout(
		long groupId, String externalReferenceCode, long plid) {

		LayoutPageTemplateEntryLayoutProvider
			layoutPageTemplateEntryLayoutProviderUtil =
				_layoutPageTemplateEntryLayoutProviderSnapshot.get();

		return layoutPageTemplateEntryLayoutProviderUtil.
			getLayoutPageTemplateEntryLayout(
				groupId, externalReferenceCode, plid);
	}

	public static LayoutPrototype getLayoutPageTemplateEntryLayoutPrototype(
		long groupId, String externalReferenceCode) {

		LayoutPageTemplateEntryLayoutProvider
			layoutPageTemplateEntryLayoutProviderUtil =
				_layoutPageTemplateEntryLayoutProviderSnapshot.get();

		return layoutPageTemplateEntryLayoutProviderUtil.
			getLayoutPageTemplateEntryLayoutPrototype(
				groupId, externalReferenceCode);
	}

	private static final Snapshot<LayoutPageTemplateEntryLayoutProvider>
		_layoutPageTemplateEntryLayoutProviderSnapshot = new Snapshot<>(
			LayoutPageTemplateEntryLayoutProviderUtil.class,
			LayoutPageTemplateEntryLayoutProvider.class);

}