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
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemSubtype;
import com.liferay.content.dashboard.web.internal.util.ContentDashboardGroupUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.info.item.InfoItemReference;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
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
import com.liferay.portal.kernel.util.SetUtil;
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
		return PortletURLBuilder.create(
			getPortletURL()
		).setKeywords(
			StringPool.BLANK
		).setParameter(
			"assetCategoryId", (String)null
		).setParameter(
			"assetTagId", (String)null
		).setParameter(
			"authorIds", (String)null
		).setParameter(
			"contentDashboardItemSubtypePayload", (String)null
		).setParameter(
			"fileExtension", (String)null
		).setParameter(
			"scopeId", (String)null
		).setParameter(
			"status", WorkflowConstants.STATUS_ANY
		).buildString();
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(_getFilterDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(httpServletRequest, "filter-by") +
						StringPool.TRIPLE_PERIOD);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					_getFilterAuthorDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(httpServletRequest, "filter-by-author"));
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					_getFilterStatusDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(httpServletRequest, "filter-by-status"));
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
					labelItem.putData(
						"removeLabelURL",
						String.valueOf(
							PortletURLBuilder.create(
								PortletURLUtil.clone(
									currentURLObj, liferayPortletResponse)
							).setParameter(
								"assetCategoryId",
								() -> {
									Stream<Long> stream =
										assetCategoryIds.stream();

									return stream.filter(
										id -> id != assetCategoryId
									).map(
										String::valueOf
									).toArray(
										String[]::new
									);
								}
							).buildString()));
					labelItem.setCloseable(true);
					labelItem.setLabel(
						StringBundler.concat(
							LanguageUtil.get(httpServletRequest, "category"),
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
				labelItem.putData(
					"removeLabelURL",
					PortletURLBuilder.create(
						PortletURLUtil.clone(
							currentURLObj, liferayPortletResponse)
					).setParameter(
						"scopeId", (String)null
					).buildString());
				labelItem.setCloseable(true);
				labelItem.setLabel(
					StringBundler.concat(
						LanguageUtil.get(
							httpServletRequest, "site-or-asset-library"),
						": ", _getScopeLabel(scopeId)));
			});

		List<? extends ContentDashboardItemSubtype>
			contentDashboardItemSubtypes =
				_contentDashboardAdminDisplayContext.
					getContentDashboardItemSubtypes();

		List<String> fileExtensions =
			_contentDashboardAdminDisplayContext.getFileExtensions();

		for (String fileExtension : fileExtensions) {
			labelItemListWrapper.add(
				labelItem -> {
					labelItem.putData(
						"removeLabelURL",
						String.valueOf(
							PortletURLBuilder.create(
								PortletURLUtil.clone(
									currentURLObj, liferayPortletResponse)
							).setParameter(
								"fileExtension",
								() -> {
									Stream<String> stream =
										fileExtensions.stream();

									return stream.filter(
										curFileExtension -> !Objects.equals(
											curFileExtension, fileExtension)
									).toArray(
										String[]::new
									);
								}
							).buildString()));
					labelItem.setCloseable(true);
					labelItem.setLabel(
						StringBundler.concat(
							LanguageUtil.get(httpServletRequest, "extension"),
							": ", fileExtension));
				});
		}

		for (ContentDashboardItemSubtype contentDashboardItemSubtype :
				contentDashboardItemSubtypes) {

			labelItemListWrapper.add(
				labelItem -> {
					labelItem.putData(
						"removeLabelURL",
						String.valueOf(
							PortletURLBuilder.create(
								PortletURLUtil.clone(
									currentURLObj, liferayPortletResponse)
							).setParameter(
								"contentDashboardItemSubtypePayload",
								() -> {
									Stream
										<? extends ContentDashboardItemSubtype>
											stream =
												contentDashboardItemSubtypes.
													stream();

									InfoItemReference infoItemReference =
										contentDashboardItemSubtype.
											getInfoItemReference();

									return stream.filter(
										curContentDashboardItemSubtype -> {
											InfoItemReference
												curInfoItemReference =
													curContentDashboardItemSubtype.
														getInfoItemReference();

											return !Objects.equals(
												curInfoItemReference,
												infoItemReference);
										}
									).map(
										curContentDashboardItemSubtype ->
											curContentDashboardItemSubtype.
												toJSONString(_locale)
									).toArray(
										String[]::new
									);
								}
							).buildString()));
					labelItem.setCloseable(true);
					labelItem.setLabel(
						StringBundler.concat(
							LanguageUtil.get(httpServletRequest, "subtype"),
							": ",
							contentDashboardItemSubtype.getFullLabel(_locale)));
				});
		}

		List<Long> authorIds =
			_contentDashboardAdminDisplayContext.getAuthorIds();

		for (Long authorId : authorIds) {
			labelItemListWrapper.add(
				labelItem -> {
					labelItem.putData(
						"removeLabelURL",
						String.valueOf(
							PortletURLBuilder.create(
								PortletURLUtil.clone(
									currentURLObj, liferayPortletResponse)
							).setParameter(
								"authorIds",
								() -> {
									Stream<Long> stream = authorIds.stream();

									return stream.filter(
										id -> id != authorId
									).map(
										String::valueOf
									).toArray(
										String[]::new
									);
								}
							).buildString()));
					labelItem.setCloseable(true);
					labelItem.setLabel(
						StringBundler.concat(
							LanguageUtil.get(httpServletRequest, "author"),
							StringPool.COLON,
							LanguageUtil.get(
								httpServletRequest,
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
				labelItem.putData(
					"removeLabelURL",
					PortletURLBuilder.create(
						PortletURLUtil.clone(
							currentURLObj, liferayPortletResponse)
					).setParameter(
						"status", (String)null
					).buildString());
				labelItem.setCloseable(true);
				labelItem.setLabel(
					LanguageUtil.get(httpServletRequest, "status") + ": " +
						_getStatusLabel(status));
			});

		Set<String> assetTagIds =
			_contentDashboardAdminDisplayContext.getAssetTagIds();

		for (String assetTagId : assetTagIds) {
			labelItemListWrapper.add(
				labelItem -> {
					labelItem.putData(
						"removeLabelURL",
						String.valueOf(
							PortletURLBuilder.create(
								PortletURLUtil.clone(
									currentURLObj, liferayPortletResponse)
							).setParameter(
								"assetTagId",
								() -> {
									Stream<String> stream =
										assetTagIds.stream();

									return stream.filter(
										id -> !Objects.equals(id, assetTagId)
									).toArray(
										String[]::new
									);
								}
							).buildString()));
					labelItem.setCloseable(true);
					labelItem.setLabel(
						StringBundler.concat(
							LanguageUtil.get(httpServletRequest, "tag"),
							StringPool.COLON, assetTagId));
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
				_log.warn(exception);
			}

			return liferayPortletResponse.createRenderURL();
		}
	}

	@Override
	public String getSearchActionURL() {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		List<Long> assetCategoryIds =
			_contentDashboardAdminDisplayContext.getAssetCategoryIds();

		if (!ListUtil.isEmpty(assetCategoryIds)) {
			Stream<Long> stream = assetCategoryIds.stream();

			portletURL.setParameter(
				"assetCategoryId",
				stream.map(
					String::valueOf
				).toArray(
					String[]::new
				));
		}

		Set<String> assetTagIds =
			_contentDashboardAdminDisplayContext.getAssetTagIds();

		if (!SetUtil.isEmpty(assetTagIds)) {
			Stream<String> stream = assetTagIds.stream();

			portletURL.setParameter(
				"assetTagId", stream.toArray(String[]::new));
		}

		List<Long> authorIds =
			_contentDashboardAdminDisplayContext.getAuthorIds();

		if (!ListUtil.isEmpty(authorIds)) {
			Stream<Long> stream = authorIds.stream();

			portletURL.setParameter(
				"authorIds",
				stream.map(
					String::valueOf
				).toArray(
					String[]::new
				));
		}

		List<? extends ContentDashboardItemSubtype>
			contentDashboardItemSubtypes =
				_contentDashboardAdminDisplayContext.
					getContentDashboardItemSubtypes();

		if (!ListUtil.isEmpty(contentDashboardItemSubtypes)) {
			Stream<? extends ContentDashboardItemSubtype> stream =
				contentDashboardItemSubtypes.stream();

			portletURL.setParameter(
				"contentDashboardItemSubtypePayload",
				stream.map(
					contentDashboardItemSubtype ->
						contentDashboardItemSubtype.toJSONString(_locale)
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

	private PortletURL _getAssetCategorySelectorURL() throws PortalException {
		return PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				_liferayPortletRequest, AssetCategory.class.getName(),
				PortletProvider.Action.BROWSE)
		).setParameter(
			"eventName",
			_liferayPortletResponse.getNamespace() + "selectedAssetCategory"
		).setParameter(
			"selectedCategories",
			() -> {
				List<Long> assetCategoryIds =
					_contentDashboardAdminDisplayContext.getAssetCategoryIds();

				Stream<Long> assetCategoryIdsStream = assetCategoryIds.stream();

				return assetCategoryIdsStream.map(
					String::valueOf
				).collect(
					Collectors.joining(StringPool.COMMA)
				);
			}
		).setParameter(
			"singleSelect", false
		).setParameter(
			"vocabularyIds",
			() -> {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)_liferayPortletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				List<AssetVocabulary> assetVocabularies =
					_assetVocabularyLocalService.getCompanyVocabularies(
						themeDisplay.getCompanyId());

				Stream<AssetVocabulary> assetVocabularyStream =
					assetVocabularies.stream();

				return assetVocabularyStream.map(
					AssetVocabulary::getVocabularyId
				).map(
					String::valueOf
				).collect(
					Collectors.joining(StringPool.COMMA)
				);
			}
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildPortletURL();
	}

	private PortletURL _getAssetTagSelectorURL() throws PortalException {
		return PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				_liferayPortletRequest, AssetTag.class.getName(),
				PortletProvider.Action.BROWSE)
		).setParameter(
			"eventName",
			_liferayPortletResponse.getNamespace() + "selectedAssetTag"
		).setParameter(
			"groupIds",
			() -> {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)_liferayPortletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				List<Long> groupIds = _groupLocalService.getGroupIds(
					themeDisplay.getCompanyId(), true);

				Stream<Long> groupIdsStream = groupIds.stream();

				return groupIdsStream.map(
					String::valueOf
				).collect(
					Collectors.joining(StringPool.COMMA)
				);
			}
		).setParameter(
			"selectedTagNames",
			() -> {
				Set<String> assetTagIds =
					_contentDashboardAdminDisplayContext.getAssetTagIds();

				Stream<String> assetTagIdsStream = assetTagIds.stream();

				return assetTagIdsStream.collect(
					Collectors.joining(StringPool.COMMA));
			}
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildPortletURL();
	}

	private List<DropdownItem> _getFilterAuthorDropdownItems() {
		List<Long> authorIds =
			_contentDashboardAdminDisplayContext.getAuthorIds();

		return DropdownItemList.of(
			() -> DropdownItemBuilder.setActive(
				authorIds.isEmpty()
			).setHref(
				PortletURLBuilder.create(
					getPortletURL()
				).setParameter(
					"authorIds", (String)null
				).buildPortletURL()
			).setLabel(
				LanguageUtil.get(httpServletRequest, "all")
			).build(),
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
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "me"));

				return dropdownItem;
			},
			() -> DropdownItemBuilder.putData(
				"action", "selectAuthor"
			).putData(
				"dialogTitle",
				LanguageUtil.get(httpServletRequest, "select-author")
			).putData(
				"redirectURL",
				PortletURLBuilder.create(
					getPortletURL()
				).setParameter(
					"authorIds", (String)null
				).buildString()
			).putData(
				"selectAuthorURL",
				String.valueOf(
					_contentDashboardAdminDisplayContext.
						getAuthorItemSelectorURL())
			).setActive(
				() -> {
					if (((authorIds.size() == 1) &&
						 !authorIds.contains(
							 _contentDashboardAdminDisplayContext.
								 getUserId())) ||
						(authorIds.size() > 1)) {

						return true;
					}

					return null;
				}
			).setLabel(
				LanguageUtil.get(httpServletRequest, "author") +
					StringPool.TRIPLE_PERIOD
			).build());
	}

	private List<DropdownItem> _getFilterDropdownItems() {
		return DropdownItemList.of(
			() -> DropdownItemBuilder.putData(
				"action", "selectAssetCategory"
			).putData(
				"dialogTitle",
				LanguageUtil.get(httpServletRequest, "select-categories")
			).putData(
				"redirectURL",
				PortletURLBuilder.create(
					getPortletURL()
				).setParameter(
					"assetCategoryId", (String)null
				).buildString()
			).putData(
				"selectAssetCategoryURL",
				String.valueOf(_getAssetCategorySelectorURL())
			).setActive(
				!ListUtil.isEmpty(
					_contentDashboardAdminDisplayContext.getAssetCategoryIds())
			).setLabel(
				LanguageUtil.get(httpServletRequest, "categories") +
					StringPool.TRIPLE_PERIOD
			).build(),
			() -> {
				String label = LanguageUtil.get(
					httpServletRequest, "site-or-asset-library");

				return DropdownItemBuilder.putData(
					"action", "selectScope"
				).putData(
					"dialogTitle",
					LanguageUtil.get(
						httpServletRequest, "select-site-or-asset-library")
				).putData(
					"redirectURL",
					PortletURLBuilder.create(
						getPortletURL()
					).setParameter(
						"scopeId", (String)null
					).buildString()
				).putData(
					"selectScopeURL",
					String.valueOf(
						_contentDashboardAdminDisplayContext.
							getScopeIdItemSelectorURL())
				).setActive(
					_contentDashboardAdminDisplayContext.getScopeId() > 0
				).setLabel(
					label + StringPool.TRIPLE_PERIOD
				).build();
			},
			() -> DropdownItemBuilder.putData(
				"action", "selectContentDashboardItemSubtype"
			).putData(
				"dialogTitle",
				LanguageUtil.get(httpServletRequest, "filter-by-type")
			).putData(
				"redirectURL",
				PortletURLBuilder.create(
					getPortletURL()
				).setParameter(
					"contentDashboardItemSubtypePayload", (String)null
				).buildString()
			).putData(
				"selectContentDashboardItemSubtypeURL",
				String.valueOf(
					_contentDashboardAdminDisplayContext.
						getContentDashboardItemSubtypeItemSelectorURL())
			).setActive(
				!ListUtil.isEmpty(
					_contentDashboardAdminDisplayContext.
						getContentDashboardItemSubtypes())
			).setLabel(
				LanguageUtil.get(httpServletRequest, "type") +
					StringPool.TRIPLE_PERIOD
			).build(),
			() -> DropdownItemBuilder.putData(
				"action", "selectFileExtension"
			).putData(
				"dialogTitle",
				LanguageUtil.get(httpServletRequest, "filter-by-extension")
			).putData(
				"redirectURL",
				PortletURLBuilder.create(
					getPortletURL()
				).setParameter(
					"fileExtension", (String)null
				).buildString()
			).putData(
				"selectFileExtensionURL",
				String.valueOf(
					_contentDashboardAdminDisplayContext.
						getFileExtensionItemSelectorURL())
			).setActive(
				!ListUtil.isEmpty(
					_contentDashboardAdminDisplayContext.getFileExtensions())
			).setLabel(
				LanguageUtil.get(httpServletRequest, "extension") +
					StringPool.TRIPLE_PERIOD
			).build(),
			() -> DropdownItemBuilder.putData(
				"action", "selectAssetTag"
			).putData(
				"dialogTitle",
				LanguageUtil.get(httpServletRequest, "select-tags")
			).putData(
				"redirectURL",
				PortletURLBuilder.create(
					getPortletURL()
				).setParameter(
					"assetTagId", (String)null
				).buildString()
			).putData(
				"selectTagURL", String.valueOf(_getAssetTagSelectorURL())
			).setActive(
				!ListUtil.isEmpty(
					_contentDashboardAdminDisplayContext.getAssetCategoryIds())
			).setLabel(
				LanguageUtil.get(httpServletRequest, "tags") +
					StringPool.TRIPLE_PERIOD
			).build());
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
			group -> ContentDashboardGroupUtil.getGroupName(group, _locale)
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

		return LanguageUtil.get(httpServletRequest, label);
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