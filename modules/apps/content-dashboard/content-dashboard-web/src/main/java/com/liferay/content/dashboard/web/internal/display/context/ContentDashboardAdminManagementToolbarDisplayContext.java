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

package com.liferay.content.dashboard.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemType;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.info.item.InfoItemReference;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González Castellano
 */
public class ContentDashboardAdminManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public ContentDashboardAdminManagementToolbarDisplayContext(
		AssetCategoryLocalService assetCategoryLocalService,
		AssetVocabularyLocalService assetVocabularyLocalService,
		ContentDashboardAdminDisplayContext contentDashboardAdminDisplayContext,
		GroupLocalService groupLocalService,
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, Locale locale,
		UserLocalService userLocalService) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			contentDashboardAdminDisplayContext.getSearchContainer());

		_assetCategoryLocalService = assetCategoryLocalService;
		_assetVocabularyLocalService = assetVocabularyLocalService;
		_contentDashboardAdminDisplayContext =
			contentDashboardAdminDisplayContext;
		_groupLocalService = groupLocalService;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_locale = locale;
		_userLocalService = userLocalService;
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("assetCategoryId", (String)null);
		clearResultsURL.setParameter("assetTagId", (String)null);
		clearResultsURL.setParameter("authorIds", (String)null);
		clearResultsURL.setParameter(
			"contentDashboardItemTypePayload", (String)null);
		clearResultsURL.setParameter("keywords", StringPool.BLANK);
		clearResultsURL.setParameter("scopeId", (String)null);
		clearResultsURL.setParameter(
			"status", String.valueOf(WorkflowConstants.STATUS_ANY));

		return String.valueOf(clearResultsURL);
	}

	@Override
	public String getDefaultEventHandler() {
		return "contentDashboardManagementToolbarDefaultEventHandler";
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(_getFilterDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(request, "filter-by") +
						StringPool.TRIPLE_PERIOD);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					_getFilterAuthorDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(request, "filter-by-author"));
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					_getFilterStatusDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(request, "filter-by-status"));
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(getOrderByDropdownItems());
				dropdownGroupItem.setLabel(getOrderByDropdownItemsLabel());
			}
		).build();
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		List<Long> assetCategoryIds =
			_contentDashboardAdminDisplayContext.getAssetCategoryIds();

		LabelItemListBuilder.LabelItemListWrapper labelItemListWrapper =
			new LabelItemListBuilder.LabelItemListWrapper();

		for (Long assetCategoryId : assetCategoryIds) {
			labelItemListWrapper.add(
				labelItem -> {
					PortletURL portletURL = PortletURLUtil.clone(
						currentURLObj, liferayPortletResponse);

					Stream<Long> stream = assetCategoryIds.stream();

					portletURL.setParameter(
						"assetCategoryId",
						stream.filter(
							id -> id != assetCategoryId
						).map(
							String::valueOf
						).toArray(
							String[]::new
						));

					labelItem.putData(
						"removeLabelURL",
						String.valueOf(portletURL.toString()));

					labelItem.setCloseable(true);
					labelItem.setLabel(
						StringBundler.concat(
							LanguageUtil.get(request, "category"),
							StringPool.COLON,
							Optional.ofNullable(
								_assetCategoryLocalService.fetchAssetCategory(
									assetCategoryId)
							).map(
								assetCategory -> assetCategory.getTitle(_locale)
							).orElse(
								StringPool.BLANK
							)));
				});
		}

		long scopeId = _contentDashboardAdminDisplayContext.getScopeId();

		labelItemListWrapper.add(
			() -> scopeId > 0,
			labelItem -> {
				PortletURL removeLabelURL = PortletURLUtil.clone(
					currentURLObj, liferayPortletResponse);

				removeLabelURL.setParameter("scopeId", (String)null);

				labelItem.putData("removeLabelURL", removeLabelURL.toString());

				labelItem.setCloseable(true);
				labelItem.setLabel(
					LanguageUtil.get(request, "site-or-asset-library") + ": " +
						_getScopeLabel(scopeId));
			});

		List<? extends ContentDashboardItemType> contentDashboardItemTypes =
			_contentDashboardAdminDisplayContext.getContentDashboardItemTypes();

		for (ContentDashboardItemType contentDashboardItemType :
				contentDashboardItemTypes) {

			labelItemListWrapper.add(
				labelItem -> {
					PortletURL portletURL = PortletURLUtil.clone(
						currentURLObj, liferayPortletResponse);

					InfoItemReference infoItemReference =
						contentDashboardItemType.getInfoItemReference();

					Stream<? extends ContentDashboardItemType> stream =
						contentDashboardItemTypes.stream();

					portletURL.setParameter(
						"contentDashboardItemTypePayload",
						stream.filter(
							curContentDashboardItemType -> {
								InfoItemReference curInfoItemReference =
									curContentDashboardItemType.
										getInfoItemReference();

								return !Objects.equals(
									curInfoItemReference.getClassPK(),
									infoItemReference.getClassPK());
							}
						).map(
							curContentDashboardItemType ->
								curContentDashboardItemType.toJSONString(
									_locale)
						).toArray(
							String[]::new
						));

					labelItem.putData(
						"removeLabelURL",
						String.valueOf(portletURL.toString()));

					labelItem.setCloseable(true);
					labelItem.setLabel(
						StringBundler.concat(
							LanguageUtil.get(request, "subtype"), ": ",
							contentDashboardItemType.getFullLabel(_locale)));
				});
		}

		List<Long> authorIds =
			_contentDashboardAdminDisplayContext.getAuthorIds();

		for (Long authorId : authorIds) {
			labelItemListWrapper.add(
				labelItem -> {
					PortletURL portletURL = PortletURLUtil.clone(
						currentURLObj, liferayPortletResponse);

					Stream<Long> stream = authorIds.stream();

					portletURL.setParameter(
						"authorIds",
						stream.filter(
							id -> id != authorId
						).map(
							String::valueOf
						).toArray(
							String[]::new
						));

					labelItem.putData(
						"removeLabelURL",
						String.valueOf(portletURL.toString()));

					labelItem.setCloseable(true);
					labelItem.setLabel(
						StringBundler.concat(
							LanguageUtil.get(request, "author"),
							StringPool.COLON,
							LanguageUtil.get(
								request,
								Optional.ofNullable(
									_userLocalService.fetchUser(authorId)
								).map(
									User::getFullName
								).orElse(
									StringPool.BLANK
								))));
				});
		}

		int status = _contentDashboardAdminDisplayContext.getStatus();

		labelItemListWrapper.add(
			() -> status != WorkflowConstants.STATUS_ANY,
			labelItem -> {
				PortletURL removeLabelURL = PortletURLUtil.clone(
					currentURLObj, liferayPortletResponse);

				removeLabelURL.setParameter("status", (String)null);

				labelItem.putData("removeLabelURL", removeLabelURL.toString());

				labelItem.setCloseable(true);
				labelItem.setLabel(
					LanguageUtil.get(request, "status") + ": " +
						_getStatusLabel(status));
			});

		Set<String> assetTagIds =
			_contentDashboardAdminDisplayContext.getAssetTagIds();

		for (String assetTagId : assetTagIds) {
			labelItemListWrapper.add(
				labelItem -> {
					PortletURL portletURL = PortletURLUtil.clone(
						currentURLObj, liferayPortletResponse);

					Stream<String> stream = assetTagIds.stream();

					portletURL.setParameter(
						"assetTagId",
						stream.filter(
							id -> !Objects.equals(id, assetTagId)
						).toArray(
							String[]::new
						));

					labelItem.putData(
						"removeLabelURL",
						String.valueOf(portletURL.toString()));

					labelItem.setCloseable(true);
					labelItem.setLabel(
						StringBundler.concat(
							LanguageUtil.get(request, "tag"), StringPool.COLON,
							assetTagId));
				});
		}

		return labelItemListWrapper.build();
	}

	@Override
	public PortletURL getPortletURL() {
		try {
			return PortletURLUtil.clone(currentURLObj, liferayPortletResponse);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}

			return liferayPortletResponse.createRenderURL();
		}
	}

	@Override
	public String getSearchActionURL() {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		List<? extends ContentDashboardItemType> contentDashboardItemTypes =
			_contentDashboardAdminDisplayContext.getContentDashboardItemTypes();

		if (!ListUtil.isEmpty(contentDashboardItemTypes)) {
			Stream<? extends ContentDashboardItemType> stream =
				contentDashboardItemTypes.stream();

			portletURL.setParameter(
				"contentDashboardItemTypePayload",
				stream.map(
					contentDashboardItemType ->
						contentDashboardItemType.toJSONString(_locale)
				).toArray(
					String[]::new
				));
		}

		portletURL.setParameter("orderByCol", getOrderByCol());
		portletURL.setParameter("orderByType", getOrderByType());
		portletURL.setParameter(
			"scopeId",
			String.valueOf(_contentDashboardAdminDisplayContext.getScopeId()));
		portletURL.setParameter(
			"status",
			String.valueOf(_contentDashboardAdminDisplayContext.getStatus()));

		return String.valueOf(portletURL);
	}

	@Override
	public String getSearchContainerId() {
		return "content";
	}

	@Override
	public Boolean isDisabled() {
		return false;
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"title", "modified-date"};
	}

	private PortletURL _getAssetCategorySelectorURL()
		throws PortalException, WindowStateException {

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			_liferayPortletRequest, AssetCategory.class.getName(),
			PortletProvider.Action.BROWSE);

		portletURL.setParameter(
			"eventName",
			_liferayPortletResponse.getNamespace() + "selectedAssetCategory");

		List<Long> assetCategoryIds =
			_contentDashboardAdminDisplayContext.getAssetCategoryIds();

		Stream<Long> assetCategoryIdsStream = assetCategoryIds.stream();

		portletURL.setParameter(
			"selectedCategories",
			assetCategoryIdsStream.map(
				String::valueOf
			).collect(
				Collectors.joining(StringPool.COMMA)
			));

		portletURL.setParameter("singleSelect", Boolean.FALSE.toString());

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<AssetVocabulary> assetVocabularies =
			_assetVocabularyLocalService.getCompanyVocabularies(
				themeDisplay.getCompanyId());

		Stream<AssetVocabulary> assetVocabularyStream =
			assetVocabularies.stream();

		portletURL.setParameter(
			"vocabularyIds",
			assetVocabularyStream.map(
				AssetVocabulary::getVocabularyId
			).map(
				String::valueOf
			).collect(
				Collectors.joining(StringPool.COMMA)
			));

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL;
	}

	private PortletURL _getAssetTagSelectorURL()
		throws PortalException, WindowStateException {

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			_liferayPortletRequest, AssetTag.class.getName(),
			PortletProvider.Action.BROWSE);

		portletURL.setParameter(
			"eventName",
			_liferayPortletResponse.getNamespace() + "selectedAssetTag");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<Long> groupIds = _groupLocalService.getGroupIds(
			themeDisplay.getCompanyId(), true);

		Stream<Long> groupIdsStream = groupIds.stream();

		portletURL.setParameter(
			"groupIds",
			groupIdsStream.map(
				String::valueOf
			).collect(
				Collectors.joining(StringPool.COMMA)
			));

		Set<String> assetTagIds =
			_contentDashboardAdminDisplayContext.getAssetTagIds();

		Stream<String> assetTagIdsStream = assetTagIds.stream();

		portletURL.setParameter(
			"selectedTagNames",
			assetTagIdsStream.collect(Collectors.joining(StringPool.COMMA)));

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL;
	}

	private List<DropdownItem> _getFilterAuthorDropdownItems() {
		List<Long> authorIds =
			_contentDashboardAdminDisplayContext.getAuthorIds();

		return DropdownItemList.of(
			() -> {
				DropdownItem dropdownItem = new DropdownItem();

				dropdownItem.setActive(authorIds.isEmpty());

				PortletURL portletURL = getPortletURL();

				portletURL.setParameter("authorIds", (String)null);

				dropdownItem.setHref(portletURL);

				dropdownItem.setLabel(LanguageUtil.get(request, "all"));

				return dropdownItem;
			},
			() -> {
				DropdownItem dropdownItem = new DropdownItem();

				if ((authorIds.size() == 1) &&
					authorIds.contains(
						_contentDashboardAdminDisplayContext.getUserId())) {

					dropdownItem.setActive(true);
				}

				dropdownItem.setHref(
					getPortletURL(), "authorIds",
					_contentDashboardAdminDisplayContext.getUserId());
				dropdownItem.setLabel(LanguageUtil.get(request, "me"));

				return dropdownItem;
			},
			() -> {
				DropdownItem dropdownItem = new DropdownItem();

				if (((authorIds.size() == 1) &&
					 !authorIds.contains(
						 _contentDashboardAdminDisplayContext.getUserId())) ||
					(authorIds.size() > 1)) {

					dropdownItem.setActive(true);
				}

				dropdownItem.putData("action", "selectAuthor");
				dropdownItem.putData(
					"dialogTitle", LanguageUtil.get(request, "select-author"));

				PortletURL portletURL = getPortletURL();

				portletURL.setParameter("authorIds", (String)null);

				dropdownItem.putData("redirectURL", String.valueOf(portletURL));

				dropdownItem.putData(
					"selectAuthorURL",
					String.valueOf(
						_contentDashboardAdminDisplayContext.
							getAuthorItemSelectorURL()));
				dropdownItem.setLabel(
					LanguageUtil.get(request, "author") +
						StringPool.TRIPLE_PERIOD);

				return dropdownItem;
			});
	}

	private List<DropdownItem> _getFilterDropdownItems() {
		return DropdownItemList.of(
			() -> {
				DropdownItem dropdownItem = new DropdownItem();

				dropdownItem.setActive(
					!ListUtil.isEmpty(
						_contentDashboardAdminDisplayContext.
							getAssetCategoryIds()));

				dropdownItem.putData("action", "selectAssetCategory");
				dropdownItem.putData(
					"dialogTitle",
					LanguageUtil.get(request, "select-categories"));

				PortletURL portletURL = getPortletURL();

				portletURL.setParameter("assetCategoryId", (String)null);

				dropdownItem.putData("redirectURL", String.valueOf(portletURL));

				dropdownItem.putData(
					"selectAssetCategoryURL",
					String.valueOf(_getAssetCategorySelectorURL()));
				dropdownItem.setLabel(
					LanguageUtil.get(request, "categories") +
						StringPool.TRIPLE_PERIOD);

				return dropdownItem;
			},
			() -> {
				long scopeId =
					_contentDashboardAdminDisplayContext.getScopeId();

				DropdownItem dropdownItem = new DropdownItem();

				dropdownItem.setActive(scopeId > 0);

				dropdownItem.putData("action", "selectScope");
				dropdownItem.putData(
					"dialogTitle",
					LanguageUtil.get(request, "select-site-or-asset-library"));

				PortletURL portletURL = getPortletURL();

				portletURL.setParameter("scopeId", (String)null);

				dropdownItem.putData("redirectURL", String.valueOf(portletURL));

				dropdownItem.putData(
					"selectScopeURL",
					String.valueOf(
						_contentDashboardAdminDisplayContext.
							getScopeIdItemSelectorURL()));

				dropdownItem.setLabel(
					LanguageUtil.get(request, "site-or-asset-library") +
						StringPool.TRIPLE_PERIOD);

				return dropdownItem;
			},
			() -> {
				List<? extends ContentDashboardItemType>
					contentDashboardItemTypes =
						_contentDashboardAdminDisplayContext.
							getContentDashboardItemTypes();

				DropdownItem dropdownItem = new DropdownItem();

				dropdownItem.setActive(
					!ListUtil.isEmpty(contentDashboardItemTypes));

				dropdownItem.putData(
					"action", "selectContentDashboardItemType");
				dropdownItem.putData(
					"dialogTitle", LanguageUtil.get(request, "select-subtype"));

				PortletURL portletURL = getPortletURL();

				portletURL.setParameter(
					"contentDashboardItemTypePayload", (String)null);

				dropdownItem.putData("redirectURL", String.valueOf(portletURL));

				dropdownItem.putData(
					"selectContentDashboardItemTypeURL",
					String.valueOf(
						_contentDashboardAdminDisplayContext.
							getContentDashboardItemTypeItemSelectorURL()));

				dropdownItem.setLabel(
					LanguageUtil.get(request, "subtype") +
						StringPool.TRIPLE_PERIOD);

				return dropdownItem;
			},
			() -> {
				DropdownItem dropdownItem = new DropdownItem();

				dropdownItem.putData("action", "selectAssetTag");
				dropdownItem.putData(
					"dialogTitle", LanguageUtil.get(request, "select-tags"));

				PortletURL portletURL = getPortletURL();

				portletURL.setParameter("assetTagId", (String)null);

				dropdownItem.putData("redirectURL", String.valueOf(portletURL));

				dropdownItem.putData(
					"selectTagURL", String.valueOf(_getAssetTagSelectorURL()));
				dropdownItem.setActive(
					!ListUtil.isEmpty(
						_contentDashboardAdminDisplayContext.
							getAssetCategoryIds()));
				dropdownItem.setLabel(
					LanguageUtil.get(request, "tags") +
						StringPool.TRIPLE_PERIOD);

				return dropdownItem;
			});
	}

	private List<DropdownItem> _getFilterStatusDropdownItems() {
		return new DropdownItemList() {
			{
				Integer curStatus =
					_contentDashboardAdminDisplayContext.getStatus();

				for (int status : _getStatuses()) {
					add(
						dropdownItem -> {
							dropdownItem.setActive(curStatus == status);
							dropdownItem.setHref(
								getPortletURL(), "status",
								String.valueOf(status));

							dropdownItem.setLabel(_getStatusLabel(status));
						});
				}
			}
		};
	}

	private String _getScopeLabel(long scopeId) {
		return Optional.ofNullable(
			_groupLocalService.fetchGroup(scopeId)
		).map(
			group -> {
				try {
					return group.getDescriptiveName(_locale);
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);

					return StringPool.BLANK;
				}
			}
		).orElse(
			StringPool.BLANK
		);
	}

	private List<Integer> _getStatuses() {
		return Arrays.asList(
			WorkflowConstants.STATUS_ANY, WorkflowConstants.STATUS_DRAFT,
			WorkflowConstants.STATUS_SCHEDULED,
			WorkflowConstants.STATUS_APPROVED);
	}

	private String _getStatusLabel(int status) {
		String label = WorkflowConstants.getStatusLabel(status);

		return LanguageUtil.get(request, label);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentDashboardAdminManagementToolbarDisplayContext.class);

	private final AssetCategoryLocalService _assetCategoryLocalService;
	private final AssetVocabularyLocalService _assetVocabularyLocalService;
	private final ContentDashboardAdminDisplayContext
		_contentDashboardAdminDisplayContext;
	private final GroupLocalService _groupLocalService;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final Locale _locale;
	private final UserLocalService _userLocalService;

}