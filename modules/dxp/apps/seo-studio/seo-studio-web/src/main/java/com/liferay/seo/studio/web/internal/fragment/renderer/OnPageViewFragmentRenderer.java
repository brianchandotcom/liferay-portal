/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.web.internal.fragment.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.seo.studio.web.internal.constants.SEOStudioFDSNames;
import com.liferay.seo.studio.web.internal.display.context.OnPageViewDisplayContext;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Noor Najjar
 */
@Component(service = FragmentRenderer.class)
public class OnPageViewFragmentRenderer
	extends BaseFragmentRenderer<OnPageViewDisplayContext> {

	@Override
	public String getCollectionKey() {
		return "sections";
	}

	@Override
	public String getLabel(Locale locale) {
		return language.get(locale, "on-page-view");
	}

	@Override
	protected OnPageViewDisplayContext getDisplayContext(
		HttpServletRequest httpServletRequest) {

		ObjectEntry currentScan = _fetchCurrentScan(httpServletRequest);

		Long currentScanId = null;
		String lastScanDate = null;

		if (currentScan != null) {
			currentScanId = currentScan.getId();

			Map<String, Object> properties = currentScan.getProperties();

			lastScanDate = GetterUtil.getString(properties.get("requestDate"));
		}

		JSONArray viewsJSONArray = fdsSerializer.serializeViews(
			SEOStudioFDSNames.INSIGHT_TYPE_SECTION, httpServletRequest);

		return new OnPageViewDisplayContext(
			currentScanId, httpServletRequest, language, lastScanDate,
			viewsJSONArray);
	}

	@Override
	protected String getJSPPath() {
		return "/on_page_view.jsp";
	}

	private ObjectEntry _fetchCurrentScan(
		HttpServletRequest httpServletRequest) {

		try {
			long companyId = portal.getCompanyId(httpServletRequest);

			ObjectDefinition objectDefinition =
				objectDefinitionLocalService.
					fetchObjectDefinitionByExternalReferenceCode(
						"L_SEO_STUDIO_SCAN", companyId);

			if (objectDefinition == null) {
				return null;
			}

			Page<ObjectEntry> page = objectEntryManager.getObjectEntries(
				companyId, objectDefinition, null, null,
				getDTOConverterContext(objectDefinition),
				"state eq 'completed'", Pagination.of(1, 1), null,
				new Sort[] {new Sort("requestDate", true)});

			Collection<ObjectEntry> items = page.getItems();

			if (items.isEmpty()) {
				return null;
			}

			Iterator<ObjectEntry> iterator = items.iterator();

			return iterator.next();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OnPageViewFragmentRenderer.class);

}