/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.similar.results.web.internal.contributor.document.library;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.similar.results.web.internal.helper.HttpHelper;
import com.liferay.portal.search.similar.results.web.internal.util.SearchStringUtil;
import com.liferay.portal.search.similar.results.web.spi.contributor.SimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.CriteriaBuilder;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.CriteriaHelper;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.DestinationBuilder;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.DestinationHelper;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.RouteBuilder;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.RouteHelper;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
@Component(service = SimilarResultsContributor.class)
public class DocumentLibrarySimilarResultsContributor
	implements SimilarResultsContributor {

	@Override
	public void detectRoute(
		RouteBuilder routeBuilder, RouteHelper routeHelper) {

		String[] parameters = _httpHelper.getFriendlyURLParameters(
			routeHelper.getURLString());

		SearchStringUtil.requireEquals("document_library", parameters[0]);

		routeBuilder.addAttribute("id", Long.valueOf(parameters[3]));
	}

	@Override
	public void resolveCriteria(
		CriteriaBuilder criteriaBuilder, CriteriaHelper criteriaHelper) {

		Long id = (Long)criteriaHelper.getRouteParameter("id");

		List<?> list = _getDLFileEntryData(id);

		if (list == null) {
			list = _getDLFolderData(id);
		}

		if (list == null) {
			return;
		}

		Long classPK = (Long)list.get(0);

		String uuid = (String)list.get(1);

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			criteriaHelper.getGroupId(), uuid);

		if (assetEntry == null) {
			return;
		}

		criteriaBuilder.type(
			assetEntry.getClassName()
		).uid(
			Field.getUID(assetEntry.getClassName(), String.valueOf(classPK))
		);
	}

	@Reference(unbind = "-")
	public void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	@Reference(unbind = "-")
	public void setDLFileEntryLocalService(
		DLFileEntryLocalService dlFileEntryLocalService) {

		_dlFileEntryLocalService = dlFileEntryLocalService;
	}

	@Reference(unbind = "-")
	public void setDLFolderLocalService(
		DLFolderLocalService dlFolderLocalService) {

		_dlFolderLocalService = dlFolderLocalService;
	}

	@Reference(unbind = "-")
	public void setHttpHelper(HttpHelper httpHelper) {
		_httpHelper = httpHelper;
	}

	@Override
	public void writeDestination(
		DestinationBuilder destinationBuilder,
		DestinationHelper destinationHelper) {

		AssetRenderer<?> assetRenderer = destinationHelper.getAssetRenderer();

		Long id1 = _getId(
			destinationHelper.getClassName(), assetRenderer.getAssetObject());

		if (id1 == null) {
			return;
		}

		Long id2 = (Long)destinationHelper.getRouteParameter("id");

		destinationBuilder.replace(String.valueOf(id2), String.valueOf(id1));
	}

	private List<?> _getDLFileEntryData(Long id) {
		DLFileEntry dlFileEntry = _dlFileEntryLocalService.fetchDLFileEntry(id);

		if (dlFileEntry != null) {
			return Arrays.asList(
				dlFileEntry.getFileEntryId(), dlFileEntry.getUuid());
		}

		return null;
	}

	private List<?> _getDLFolderData(Long id) {
		DLFolder dlFolder = _dlFolderLocalService.fetchDLFolder(id);

		if (dlFolder != null) {
			return Arrays.asList(dlFolder.getFolderId(), dlFolder.getUuid());
		}

		return null;
	}

	private long _getFileEntryId(Object assetObject) {
		if (assetObject instanceof FileEntry) {
			FileEntry fileEntry = (FileEntry)assetObject;

			return fileEntry.getFileEntryId();
		}

		DLFileEntry dlFileEntry = (DLFileEntry)assetObject;

		return dlFileEntry.getFileEntryId();
	}

	private long _getFolderId(Object assetObject) {
		if (assetObject instanceof Folder) {
			Folder folder = (Folder)assetObject;

			return folder.getFolderId();
		}

		DLFolder dlFolder = (DLFolder)assetObject;

		return dlFolder.getFolderId();
	}

	private Long _getId(String className, Object assetObject) {
		if (className.equals(DLFileEntry.class.getName())) {
			return _getFileEntryId(assetObject);
		}

		if (className.equals(DLFolder.class.getName())) {
			return _getFolderId(assetObject);
		}

		return null;
	}

	private AssetEntryLocalService _assetEntryLocalService;
	private DLFileEntryLocalService _dlFileEntryLocalService;
	private DLFolderLocalService _dlFolderLocalService;
	private HttpHelper _httpHelper;

}