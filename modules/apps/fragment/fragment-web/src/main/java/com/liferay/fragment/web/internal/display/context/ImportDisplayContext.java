/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.display.context;

import com.liferay.fragment.importer.FragmentsImporterResultEntry;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionLocalServiceUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;
import jakarta.portlet.ResourceURL;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * @author Jürgen Kappler
 */
public class ImportDisplayContext {

	public ImportDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public List<String> getFragmentsImporterResultEntries(
		FragmentsImporterResultEntry.Status status) {

		List<FragmentsImporterResultEntry> fragmentsImporterResultEntries =
			_getFragmentsImporterResultEntries();

		if (ListUtil.isEmpty(fragmentsImporterResultEntries)) {
			return null;
		}

		return TransformUtil.transform(
			fragmentsImporterResultEntries,
			fragmentsImporterResultEntry -> {
				if (fragmentsImporterResultEntry.getStatus() == status) {
					return fragmentsImporterResultEntry.getName();
				}

				return null;
			});
	}

	public Map<String, Object> getProps() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return HashMapBuilder.<String, Object>put(
			"addFragmentCollectionURL",
			() -> {
				LiferayPortletURL addFragmentCollectionURL =
					(LiferayPortletURL)_renderResponse.createResourceURL();

				addFragmentCollectionURL.setCopyCurrentRenderParameters(false);
				addFragmentCollectionURL.setResourceID(
					"/fragment/add_fragment_collection");

				return addFragmentCollectionURL.toString();
			}
		).put(
			"backURL", String.valueOf(_renderResponse.createRenderURL())
		).put(
			"fragmentCollectionId",
			ParamUtil.getLong(_httpServletRequest, "fragmentCollectionId")
		).put(
			"fragmentCollections",
			() -> {
				JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

				for (FragmentCollection fragmentCollection :
						FragmentCollectionLocalServiceUtil.
							getFragmentCollections(
								themeDisplay.getScopeGroupId(),
								QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

					jsonArray.put(
						JSONUtil.put(
							"fragmentCollectionId",
							fragmentCollection.getFragmentCollectionId()
						).put(
							"name", fragmentCollection.getName()
						));
				}

				return jsonArray;
			}
		).put(
			"importURL",
			() -> {
				ResourceURL importURL = _renderResponse.createResourceURL();

				importURL.setParameter(
					"fragmentCollectionId",
					ParamUtil.getString(
						_httpServletRequest, "fragmentCollectionId"));
				importURL.setResourceID("/fragment/import");

				return importURL.toString();
			}
		).build();
	}

	private List<FragmentsImporterResultEntry>
		_getFragmentsImporterResultEntries() {

		if (_fragmentsImporterResultEntries != null) {
			return _fragmentsImporterResultEntries;
		}

		_fragmentsImporterResultEntries =
			(List<FragmentsImporterResultEntry>)SessionMessages.get(
				_renderRequest, "fragmentsImporterResultEntries");

		return _fragmentsImporterResultEntries;
	}

	private List<FragmentsImporterResultEntry> _fragmentsImporterResultEntries;
	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}