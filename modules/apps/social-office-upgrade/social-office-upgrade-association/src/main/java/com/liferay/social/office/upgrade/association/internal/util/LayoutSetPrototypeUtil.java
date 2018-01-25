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

package com.liferay.social.office.upgrade.association.internal.util;

import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.comparator.LayoutPriorityComparator;
import com.liferay.sites.kernel.util.Sites;
import com.liferay.sites.kernel.util.SitesUtil;
import com.liferay.social.office.upgrade.association.internal.constants.SocialOfficeConstants;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 * @author Jonathan Lee
 * @author Sergio González
 */
@Component(service = LayoutSetPrototypeUtil.class)
public class LayoutSetPrototypeUtil {

	public LayoutSetPrototype fetchLayoutSetPrototype(
			long companyId, String layoutSetPrototypeKey)
		throws PortalException {

		List<ExpandoValue> expandoValues =
			expandoValueLocalService.getColumnValues(
				companyId, LayoutSetPrototype.class.getName(),
				ExpandoTableConstants.DEFAULT_TABLE_NAME,
				SocialOfficeConstants.LAYOUT_SET_PROTOTYPE_KEY,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (ExpandoValue expandoValue : expandoValues) {
			if (!layoutSetPrototypeKey.equals(expandoValue.getString())) {
				continue;
			}

			return layoutSetPrototypeLocalService.fetchLayoutSetPrototype(
				expandoValue.getClassPK());
		}

		return null;
	}

	public void removeLayoutSetPrototype(
			Group group, boolean privateLayout, String layoutSetPrototypeKey)
		throws PortalException {

		LayoutSet layoutSet = layoutSetLocalService.getLayoutSet(
			group.getGroupId(), privateLayout);

		UnicodeProperties settingsProperties =
			layoutSet.getSettingsProperties();

		settingsProperties.remove(Sites.LAST_MERGE_TIME);

		layoutSet.setSettingsProperties(settingsProperties);

		layoutSet.setLayoutSetPrototypeUuid(StringPool.BLANK);
		layoutSet.setLayoutSetPrototypeLinkEnabled(false);

		layoutSetLocalService.updateLayoutSet(layoutSet);

		layoutSetLocalService.updateLookAndFeel(
			group.getGroupId(), null, null, StringPool.BLANK);

		LayoutSetPrototype layoutSetPrototype = fetchLayoutSetPrototype(
			group.getCompanyId(), layoutSetPrototypeKey);

		Group layoutSetPrototypeGroup = layoutSetPrototype.getGroup();

		List<Layout> layouts = layoutLocalService.getLayouts(
			layoutSetPrototypeGroup.getGroupId(), true);

		String[] layoutUuids = new String[layouts.size()];

		for (int i = 0; i < layouts.size(); i++) {
			Layout layout = layouts.get(i);

			layoutUuids[i] = layout.getUuid();
		}

		layouts = layoutLocalService.getLayouts(
			group.getGroupId(), privateLayout);

		layouts = ListUtil.sort(layouts, new LayoutPriorityComparator(false));

		for (Layout layout : layouts) {
			if (ArrayUtil.contains(
					layoutUuids, layout.getSourcePrototypeLayoutUuid())) {

				layoutLocalService.deleteLayout(
					layout.getGroupId(), privateLayout, layout.getLayoutId(),
					new ServiceContext());
			}
		}
	}

	public void updateLayoutSetPrototype(
			Group group, boolean privateLayout, String layoutSetPrototypeKey)
		throws Exception {

		LayoutSetPrototype layoutSetPrototype = fetchLayoutSetPrototype(
			group.getCompanyId(), layoutSetPrototypeKey);

		if (layoutSetPrototype != null) {
			layoutSetLocalService.updateLayoutSetPrototypeLinkEnabled(
				group.getGroupId(), privateLayout, true,
				layoutSetPrototype.getUuid());

			LayoutSet layoutSet = group.getPublicLayoutSet();

			if (privateLayout) {
				layoutSet = group.getPrivateLayoutSet();
			}

			SitesUtil.mergeLayoutSetPrototypeLayouts(group, layoutSet);

			layoutLocalService.updatePriorities(
				group.getGroupId(), privateLayout);
		}
	}

	@Reference
	protected ExpandoValueLocalService expandoValueLocalService;

	@Reference
	protected LayoutLocalService layoutLocalService;

	@Reference
	protected LayoutSetLocalService layoutSetLocalService;

	@Reference
	protected LayoutSetPrototypeLocalService layoutSetPrototypeLocalService;

}