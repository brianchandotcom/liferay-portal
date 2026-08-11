/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.internal.resource.v1_0;

import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.depot.service.DepotEntryService;
import com.liferay.headless.cms.dto.v1_0.BrokenLinkAsset;
import com.liferay.headless.cms.internal.links.BrokenLinkAssetSearcher;
import com.liferay.headless.cms.resource.v1_0.BrokenLinkAssetResource;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.GroupUtil;
import com.liferay.site.cms.site.initializer.util.CMSOutboundLinksUtil;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Jürgen Kappler
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/broken-link-asset.properties",
	scope = ServiceScope.PROTOTYPE, service = BrokenLinkAssetResource.class
)
public class BrokenLinkAssetResourceImpl
	extends BaseBrokenLinkAssetResourceImpl {

	@Override
	public Page<BrokenLinkAsset> getBrokenLinkAssetsPage(
			Long assetLibraryId, String search, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-82226")) {

			throw new UnsupportedOperationException();
		}

		Long[] groupIds = _getGroupIds(assetLibraryId);

		if (ArrayUtil.isEmpty(groupIds)) {
			return Page.of(Collections.emptyList());
		}

		Long[] objectDefinitionIds = transformToArray(
			_objectDefinitionService.getCMSObjectDefinitions(
				contextCompany.getCompanyId(),
				new String[] {
					ObjectFolderConstants.
						EXTERNAL_REFERENCE_CODE_CONTENT_STRUCTURES,
					ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_FILE_TYPES
				}),
			ObjectDefinition::getObjectDefinitionId, Long.class);

		if (ArrayUtil.isEmpty(objectDefinitionIds)) {
			return Page.of(Collections.emptyList());
		}

		Map<String, String> expiredAssetTitles =
			_brokenLinkAssetSearcher.getExpiredAssetTitles(
				contextCompany.getCompanyId(),
				contextAcceptLanguage.getPreferredLanguageId(),
				objectDefinitionIds);

		if (expiredAssetTitles.isEmpty()) {
			return Page.of(Collections.emptyList());
		}

		SearchResponse searchResponse = _brokenLinkAssetSearcher.search(
			contextCompany.getCompanyId(), ArrayUtil.toArray(groupIds),
			contextAcceptLanguage.getPreferredLanguageId(),
			expiredAssetTitles.keySet(), pagination, search, sorts);

		return Page.of(
			transform(
				searchResponse.getDocuments(),
				document -> _toBrokenLinkAsset(document, expiredAssetTitles)),
			pagination, searchResponse.getCount());
	}

	private Long[] _getGroupIds(Long assetLibraryId) {
		List<Long> depotEntryGroupIds =
			_depotEntryService.getDepotEntryGroupIds(
				contextCompany.getCompanyId(), contextUser.getUserId(),
				DepotConstants.TYPE_SPACE);

		if (assetLibraryId == null) {
			return depotEntryGroupIds.toArray(new Long[0]);
		}

		Long groupId = GroupUtil.getDepotGroupId(
			String.valueOf(assetLibraryId), contextCompany.getCompanyId(),
			_depotEntryLocalService, groupLocalService);

		if ((groupId == null) || !depotEntryGroupIds.contains(groupId)) {
			return new Long[0];
		}

		return new Long[] {groupId};
	}

	private BrokenLinkAsset _toBrokenLinkAsset(
		Document document, Map<String, String> expiredAssetTitles) {

		Set<String> brokenLinkTitles = new LinkedHashSet<>();

		for (String outboundLink :
				document.getStrings(CMSOutboundLinksUtil.FIELD_NAME)) {

			String brokenLinkTitle = expiredAssetTitles.get(outboundLink);

			if (brokenLinkTitle != null) {
				brokenLinkTitles.add(brokenLinkTitle);
			}
		}

		long objectEntryId = GetterUtil.getLong(
			document.getString(Field.ENTRY_CLASS_PK));

		return new BrokenLinkAsset() {
			{
				setBrokenLinkCount(() -> (long)brokenLinkTitles.size());
				setBrokenLinkTitle(
					() -> {
						if (brokenLinkTitles.isEmpty()) {
							return null;
						}

						return brokenLinkTitles.iterator(
						).next();
					});
				setHref(
					() -> StringBundler.concat(
						_portal.getPortalURL(contextHttpServletRequest),
						_portal.getPathMain(),
						GroupConstants.CMS_FRIENDLY_URL,
						"/edit_content_item?p_l_mode=read&p_p_state=",
						LiferayWindowState.POP_UP, "&objectEntryId=",
						objectEntryId));
				setId(() -> objectEntryId);
				setTitle(
					() -> document.getString(
						Field.getLocalizedName(
							contextAcceptLanguage.getPreferredLocale(),
							_FIELD_NAME_LOCALIZED_TITLE)));
			}
		};
	}

	private static final String _FIELD_NAME_LOCALIZED_TITLE =
		"localized_title";

	@Reference
	private BrokenLinkAssetSearcher _brokenLinkAssetSearcher;

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private DepotEntryService _depotEntryService;

	@Reference
	private ObjectDefinitionService _objectDefinitionService;

	@Reference
	private Portal _portal;

}
