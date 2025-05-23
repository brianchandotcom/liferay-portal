/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.fragment.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.frontend.taglib.react.servlet.taglib.ComponentTag;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.site.cms.site.initializer.internal.display.context.SpaceListDisplayContext;
import com.liferay.taglib.servlet.PageContextFactoryUtil;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Georgel Pop
 */
@Component(service = FragmentRenderer.class)
public class SpaceListFragmentRenderer extends BaseSectionFragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "space-list";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "space-list");
	}

	@Override
	public void render(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			PrintWriter printWriter = httpServletResponse.getWriter();

			printWriter.write("<div><span aria-hidden=\"true\" class=\"");
			printWriter.write("loading-animation\"></span>");

			ComponentTag componentTag = new ComponentTag();

			componentTag.setModule(
				"{SpaceList} from site-cms-site-initializer");
			componentTag.setPageContext(
				PageContextFactoryUtil.create(
					httpServletRequest, httpServletResponse));

			SpaceListDisplayContext spaceListDisplayContext =
				new SpaceListDisplayContext(
					_getObjectEntryGroupId(
						fragmentRendererContext.getContextInfoItemReference()),
					_groupLocalService, httpServletRequest);

			if (PortalRunMode.isTestMode()) {
				httpServletRequest.setAttribute(
					SpaceListDisplayContext.class.getName(),
					spaceListDisplayContext);
			}

			componentTag.setProps(spaceListDisplayContext.getProps());
			componentTag.setServletContext(_servletContext);

			componentTag.doStartTag();

			componentTag.doEndTag();

			printWriter.write("</div>");
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}
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
	private GroupLocalService _groupLocalService;

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private Language _language;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.cms.site.initializer)"
	)
	private ServletContext _servletContext;

}