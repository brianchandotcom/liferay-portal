/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.fragment.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.site.cms.site.initializer.internal.display.context.SpaceListDisplayContext;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Georgel Pop
 */
@Component(service = FragmentRenderer.class)
public class SpaceListFragmentRenderer
	extends BaseComponentSectionFragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "space-list";
	}

	@Override
	protected String getLabelKey() {
		return "space-list";
	}

	@Override
	protected String getModuleName() {
		return "SpaceList";
	}

	@Override
	protected Map<String, Object> getProps(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest)
		throws Exception {

		SpaceListDisplayContext spaceListDisplayContext =
			new SpaceListDisplayContext(
				_getObjectEntryGroupId(
					fragmentRendererContext.getContextInfoItemReference()),
				groupLocalService, httpServletRequest);

		if (PortalRunMode.isTestMode()) {
			httpServletRequest.setAttribute(
				SpaceListDisplayContext.class.getName(),
				spaceListDisplayContext);
		}

		return spaceListDisplayContext.getProps();
	}

	private long _getObjectEntryGroupId(InfoItemReference infoItemReference) {
		if (infoItemReference == null) {
			return 0;
		}

		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		InfoItemObjectProvider<Object> infoItemObjectProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemObjectProvider.class, infoItemReference.getClassName(),
				infoItemIdentifier.getInfoItemServiceFilter());

		try {
			ObjectEntry infoItem =
				(ObjectEntry)infoItemObjectProvider.getInfoItem(
					infoItemIdentifier);

			if (infoItem != null) {
				return infoItem.getGroupId();
			}
		}
		catch (NoSuchInfoItemException noSuchInfoItemException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get object with info item reference " +
						infoItemReference,
					noSuchInfoItemException);
			}
		}

		return 0;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SpaceListFragmentRenderer.class);

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

}