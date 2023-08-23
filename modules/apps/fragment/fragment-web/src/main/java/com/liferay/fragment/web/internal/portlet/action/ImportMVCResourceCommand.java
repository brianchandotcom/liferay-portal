/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.portlet.action;

import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.importer.FragmentsImporter;
import com.liferay.fragment.importer.FragmentsImporterResultEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.File;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"javax.portlet.name=" + FragmentPortletKeys.FRAGMENT,
		"mvc.command.name=/fragment/import"
	},
	service = MVCResourceCommand.class
)
public class ImportMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(resourceRequest);

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			_importFragmentEntries(
				uploadPortletRequest.getFile("file"),
				ParamUtil.getLong(resourceRequest, "fragmentCollectionId"),
				themeDisplay.getScopeGroupId(), themeDisplay.getLocale(),
				ParamUtil.getBoolean(resourceRequest, "overwrite"),
				themeDisplay.getUserId()));
	}

	private String _getKey(FragmentsImporterResultEntry.Status status) {
		if (status == FragmentsImporterResultEntry.Status.IMPORTED) {
			return "success";
		}

		if (status == FragmentsImporterResultEntry.Status.IMPORTED_DRAFT) {
			return "warning";
		}

		if (status == FragmentsImporterResultEntry.Status.INVALID) {
			return "error";
		}

		return StringPool.BLANK;
	}

	private JSONObject _importFragmentEntries(
		File file, long fragmentCollectionId, long groupId, Locale locale,
		boolean overwrite, long userId) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		try {
			List<FragmentsImporterResultEntry> fragmentsImporterResultEntries =
				_fragmentsImporter.importFragmentEntries(
					userId, groupId, fragmentCollectionId, file, overwrite);

			JSONObject importResultsJSONObject =
				_jsonFactory.createJSONObject();

			for (FragmentsImporterResultEntry fragmentsImporterResultEntry :
					fragmentsImporterResultEntries) {

				String key = _getKey(fragmentsImporterResultEntry.getStatus());

				JSONArray jsonArray = importResultsJSONObject.getJSONArray(key);

				if (jsonArray == null) {
					jsonArray = _jsonFactory.createJSONArray();
				}

				jsonArray.put(
					JSONUtil.put(
						"messages",
						() -> {
							if (Validator.isNotNull(
									fragmentsImporterResultEntry.
										getErrorMessage())) {

								return Collections.singletonList(
									fragmentsImporterResultEntry.
										getErrorMessage());
							}

							return Collections.emptyList();
						}
					).put(
						"name", fragmentsImporterResultEntry.getName()
					));

				importResultsJSONObject.put(key, jsonArray);
			}

			jsonObject.put("importResults", importResultsJSONObject);
		}
		catch (Exception exception) {
			_log.error(exception);

			jsonObject.put(
				"error", _language.get(locale, "an-unexpected-error-occurred"));
		}

		return jsonObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImportMVCResourceCommand.class);

	@Reference
	private FragmentsImporter _fragmentsImporter;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}