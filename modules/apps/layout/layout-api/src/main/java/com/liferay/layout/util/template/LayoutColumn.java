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

package com.liferay.layout.util.template;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class LayoutColumn {

	public LayoutColumn(Layout layout) {
		_layout = layout;
	}

	public void addPortletsByColumnId(String columnId) {
		List<String> portletIds = LayoutTypeSettingsInspectorUtil.getPortletIds(
			_layout.getTypeSettingsProperties(), columnId);

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		for (String portletId : portletIds) {
			try {
				JSONObject editableValueJSONObject = JSONUtil.put(
					"instanceId", PortletIdCodec.decodeInstanceId(portletId)
				).put(
					"portletId", PortletIdCodec.decodePortletName(portletId)
				);

				FragmentEntryLink fragmentEntryLink =
					FragmentEntryLinkLocalServiceUtil.addFragmentEntryLink(
						serviceContext.getUserId(),
						serviceContext.getScopeGroupId(), 0, 0,
						PortalUtil.getClassNameId(Layout.class),
						_layout.getPlid(), StringPool.BLANK, StringPool.BLANK,
						StringPool.BLANK, StringPool.BLANK,
						editableValueJSONObject.toString(), StringPool.BLANK, 0,
						null, serviceContext);

				_fragmentEntryLinkIds.add(
					fragmentEntryLink.getFragmentEntryLinkId());
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	public void setSize(int size) {
		_size = size;
	}

	@Override
	public String toString() {
		JSONObject jsonObject = JSONUtil.put(
			"columnId",
			String.valueOf(
				CounterLocalServiceUtil.increment(
					LayoutColumn.class.getName())));

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (long fragmentEntryLinkId : _fragmentEntryLinkIds) {
			jsonArray.put(fragmentEntryLinkId);
		}

		jsonObject.put(
			"fragmentEntryLinkIds", jsonArray
		).put(
			"size", String.valueOf(_size)
		);

		return jsonObject.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(LayoutColumn.class);

	private final List<Long> _fragmentEntryLinkIds = new ArrayList<>();
	private final Layout _layout;
	private int _size = 12;

}