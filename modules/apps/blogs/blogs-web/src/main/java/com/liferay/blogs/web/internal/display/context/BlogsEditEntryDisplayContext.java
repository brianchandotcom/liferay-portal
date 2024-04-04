/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.web.internal.display.context;

import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfiguration;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.blogs.configuration.BlogsFileUploadsConfiguration;
import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.blogs.item.selector.criterion.BlogsItemSelectorCriterion;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.settings.BlogsGroupServiceSettings;
import com.liferay.blogs.web.internal.util.BlogsEntryUtil;
import com.liferay.depot.group.provider.SiteConnectedGroupGroupProvider;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.DownloadFileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;

import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class BlogsEditEntryDisplayContext {

	public BlogsEditEntryDisplayContext(
		AssetAutoTaggerConfiguration assetAutoTaggerConfiguration,
		BlogsEntry blogsEntry,
		BlogsFileUploadsConfiguration blogsFileUploadsConfiguration,
		BlogsGroupServiceSettings blogsGroupServiceSettings,
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_assetAutoTaggerConfiguration = assetAutoTaggerConfiguration;
		_blogsEntry = blogsEntry;
		_blogsFileUploadsConfiguration = blogsFileUploadsConfiguration;
		_blogsGroupServiceSettings = blogsGroupServiceSettings;
		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_assetVocabularyLocalService =
			(AssetVocabularyLocalService)httpServletRequest.getAttribute(
				AssetVocabularyLocalService.class.getName());
		_itemSelector = (ItemSelector)httpServletRequest.getAttribute(
			ItemSelector.class.getName());
		_siteConnectedGroupGroupProvider =
			(SiteConnectedGroupGroupProvider)httpServletRequest.getAttribute(
				SiteConnectedGroupGroupProvider.class.getName());
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getAssetCategorySelectorURL() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(_httpServletRequest);

		InfoItemItemSelectorCriterion itemSelectorCriterion =
			new InfoItemItemSelectorCriterion();

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new InfoItemItemSelectorReturnType());
		itemSelectorCriterion.setItemType(AssetCategory.class.getName());
		itemSelectorCriterion.setMultiSelection(true);

		return PortletURLBuilder.create(
			_itemSelector.getItemSelectorURL(
				requestBackedPortletURLFactory, _themeDisplay.getScopeGroup(),
				_themeDisplay.getScopeGroupId(),
				_liferayPortletResponse.getNamespace() +
					"selectedAssetCategory",
				itemSelectorCriterion)
		).setParameter(
			"selectedCategoryIds",
			StringUtil.merge(_getAssetCategoryIds(), StringPool.COMMA)
		).setParameter(
			"vocabularyIds",
			() -> ListUtil.toString(
				_assetVocabularyLocalService.getGroupsVocabularies(
					_getGroupIds()),
				AssetVocabulary.VOCABULARY_ID_ACCESSOR)
		).buildString();
	}

	public BlogsEntry getBlogsEntry() {
		return _blogsEntry;
	}

	public String getContent() {
		if (_content != null) {
			return _content;
		}

		_content = BeanParamUtil.getString(
			getBlogsEntry(), _httpServletRequest, "content");

		return _content;
	}

	public String getCoverImageCaption() {
		if (_coverImageCaption != null) {
			return _coverImageCaption;
		}

		_coverImageCaption = BeanParamUtil.getString(
			getBlogsEntry(), _httpServletRequest, "coverImageCaption",
			LanguageUtil.get(_httpServletRequest, "caption"));

		return _coverImageCaption;
	}

	public long getCoverImageFileEntryId() {
		if (_coverImageFileEntryId != null) {
			return _coverImageFileEntryId;
		}

		_coverImageFileEntryId = BeanParamUtil.getLong(
			getBlogsEntry(), _httpServletRequest, "coverImageFileEntryId");

		return _coverImageFileEntryId;
	}

	public String getCoverImageItemSelectorEventName() {
		return _liferayPortletResponse.getNamespace() +
			"coverImageSelectedItem";
	}

	public String getCoverImageItemSelectorURL() {
		return _getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_httpServletRequest),
			getCoverImageItemSelectorEventName());
	}

	public String getDescription() {
		if (_description != null) {
			return _description;
		}

		String description = BeanParamUtil.getString(
			getBlogsEntry(), _httpServletRequest, "description");

		if (!isCustomAbstract()) {
			description = StringUtil.shorten(
				getContent(), PropsValues.BLOGS_PAGE_ABSTRACT_LENGTH);
		}

		_description = description;

		return _description;
	}

	public String getEditEntryURL() {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/blogs/edit_entry"
		).setRedirect(
			getRedirect()
		).setPortletResource(
			() -> {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)_httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				PortletDisplay portletDisplay =
					themeDisplay.getPortletDisplay();

				if (Objects.equals(
						portletDisplay.getPortletName(),
						BlogsPortletKeys.BLOGS_ADMIN)) {

					return null;
				}

				return portletDisplay.getPortletResource();
			}
		).buildString();
	}

	public long getEntryId() {
		if (_entryId != null) {
			return _entryId;
		}

		_entryId = BeanParamUtil.getLong(
			getBlogsEntry(), _httpServletRequest, "entryId");

		return _entryId;
	}

	public String[] getImageExtensions() throws ConfigurationException {
		return _blogsFileUploadsConfiguration.imageExtensions();
	}

	public long getImageMaxSize() throws ConfigurationException {
		return _blogsFileUploadsConfiguration.imageMaxSize();
	}

	public String getPageTitle(ResourceBundle resourceBundle) {
		BlogsEntry entry = getBlogsEntry();

		if (entry != null) {
			return BlogsEntryUtil.getDisplayTitle(resourceBundle, entry);
		}

		return LanguageUtil.get(_httpServletRequest, "new-blog-entry");
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		return _redirect;
	}

	public String getReferringPortletResource() {
		return ParamUtil.getString(
			_httpServletRequest, "referringPortletResource");
	}

	public long getSmallImageFileEntryId() {
		if (_smallImageFileEntryId != null) {
			return _smallImageFileEntryId;
		}

		_smallImageFileEntryId = BeanParamUtil.getLong(
			getBlogsEntry(), _httpServletRequest, "smallImageFileEntryId");

		return _smallImageFileEntryId;
	}

	public String getSmallImageItemSelectorEventName() {
		return _liferayPortletResponse.getNamespace() +
			"smallImageSelectedItem";
	}

	public String getSmallImageItemSelectorURL() {
		return _getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_httpServletRequest),
			getSmallImageItemSelectorEventName());
	}

	public Map<String, Object> getTaglibContext() throws PortalException {
		return HashMapBuilder.<String, Object>put(
			"constants",
			HashMapBuilder.<String, Object>put(
				"ACTION_PUBLISH", WorkflowConstants.ACTION_PUBLISH
			).put(
				"ACTION_SAVE_DRAFT", WorkflowConstants.ACTION_SAVE_DRAFT
			).put(
				"ADD", Constants.ADD
			).put(
				"CMD", Constants.CMD
			).put(
				"STATUS_DRAFT", WorkflowConstants.STATUS_DRAFT
			).put(
				"UPDATE", Constants.UPDATE
			).build()
		).put(
			"descriptionLength", PropsValues.BLOGS_PAGE_ABSTRACT_LENGTH
		).put(
			"editEntryURL",
			PortletURLBuilder.createActionURL(
				_liferayPortletResponse
			).setActionName(
				"/blogs/edit_entry"
			).setParameter(
				"ajax", true
			).setWindowState(
				LiferayWindowState.EXCLUSIVE
			).buildString()
		).put(
			"emailEntryUpdatedEnabled", isEmailEntryUpdatedEnabled()
		).put(
			"entry",
			() -> {
				BlogsEntry blogsEntry = getBlogsEntry();

				if (blogsEntry != null) {
					return HashMapBuilder.<String, Object>put(
						"content", UnicodeFormatter.toString(getContent())
					).put(
						"customDescription", isCustomAbstract()
					).put(
						"description", getDescription()
					).put(
						"pending", blogsEntry.isPending()
					).put(
						"status", blogsEntry.getStatus()
					).put(
						"subtitle",
						BeanParamUtil.getString(
							getBlogsEntry(), _httpServletRequest, "subtitle")
					).put(
						"title", getTitle()
					).put(
						"userId", blogsEntry.getUserId()
					).build();
				}

				return null;
			}
		).build();
	}

	public String getTitle() {
		if (_title != null) {
			return _title;
		}

		_title = BeanParamUtil.getString(
			getBlogsEntry(), _httpServletRequest, "title");

		return _title;
	}

	public String getUploadCoverImageURL() {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/blogs/upload_cover_image"
		).buildString();
	}

	public String getUploadSmallImageURL() {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/blogs/upload_small_image"
		).buildString();
	}

	public String getURLTitle() {
		if (_urlTitle != null) {
			return _urlTitle;
		}

		_urlTitle = BeanParamUtil.getString(
			getBlogsEntry(), _httpServletRequest, "urlTitle");

		return _urlTitle;
	}

	public String getViewEntryURL() {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			"/blogs/view_entry"
		).setParameter(
			"entryId", getEntryId()
		).buildString();
	}

	public boolean isAllowPingbacks() {
		if (_allowPingbacks != null) {
			return _allowPingbacks;
		}

		if (PropsValues.BLOGS_PINGBACK_ENABLED &&
			BeanParamUtil.getBoolean(
				getBlogsEntry(), _httpServletRequest, "allowPingbacks", true)) {

			_allowPingbacks = true;
		}
		else {
			_allowPingbacks = false;
		}

		return _allowPingbacks;
	}

	public boolean isAllowTrackbacks() {
		if (_allowTrackbacks != null) {
			return _allowTrackbacks;
		}

		if (PropsValues.BLOGS_TRACKBACK_ENABLED &&
			BeanParamUtil.getBoolean(
				getBlogsEntry(), _httpServletRequest, "allowTrackbacks",
				true)) {

			_allowTrackbacks = true;
		}
		else {
			_allowTrackbacks = false;
		}

		return _allowTrackbacks;
	}

	public boolean isAutoTaggingEnabled() {
		if (getBlogsEntry() == null) {
			return false;
		}

		return _assetAutoTaggerConfiguration.isEnabled();
	}

	public boolean isCustomAbstract() {
		if (_customAbstract != null) {
			return _customAbstract;
		}

		BlogsEntry entry = getBlogsEntry();

		boolean defaultValue = false;

		if ((entry != null) && Validator.isNotNull(entry.getDescription())) {
			defaultValue = true;
		}

		_customAbstract = ParamUtil.getBoolean(
			_httpServletRequest, "customAbstract", defaultValue);

		return _customAbstract;
	}

	public boolean isEmailEntryUpdatedEnabled() throws PortalException {
		if (_emailEntryUpdatedEnabled != null) {
			return _emailEntryUpdatedEnabled;
		}

		if ((getBlogsEntry() != null) &&
			_blogsGroupServiceSettings.isEmailEntryUpdatedEnabled()) {

			_emailEntryUpdatedEnabled = true;
		}
		else {
			_emailEntryUpdatedEnabled = false;
		}

		return _emailEntryUpdatedEnabled;
	}

	public boolean isUpdateAutoTags() {
		return _assetAutoTaggerConfiguration.isUpdateAutoTags();
	}

	private long[] _getAssetCategoryIds() {
		if (_assetCategoryIds == null) {
			_assetCategoryIds = ParamUtil.getLongValues(
				_httpServletRequest, "assetCategoryId");
		}

		return _assetCategoryIds;
	}

	private long[] _getGroupIds() {
		if (_groupIds != null) {
			return _groupIds;
		}

		try {
			_groupIds =
				_siteConnectedGroupGroupProvider.
					getCurrentAndAncestorSiteAndDepotGroupIds(
						_themeDisplay.getScopeGroupId());
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return _groupIds;
	}

	private String _getItemSelectorURL(
		RequestBackedPortletURLFactory requestBackedPortletURLFactory,
		String itemSelectedEventName) {

		ItemSelector itemSelector = _itemSelectorSnapshot.get();

		if (itemSelector == null) {
			return null;
		}

		ItemSelectorCriterion blogsItemSelectorCriterion =
			new BlogsItemSelectorCriterion();

		blogsItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FileEntryItemSelectorReturnType());

		ImageItemSelectorCriterion imageItemSelectorCriterion =
			new ImageItemSelectorCriterion();

		imageItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new DownloadFileEntryItemSelectorReturnType());

		return String.valueOf(
			itemSelector.getItemSelectorURL(
				requestBackedPortletURLFactory, itemSelectedEventName,
				blogsItemSelectorCriterion, imageItemSelectorCriterion));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BlogsEditEntryDisplayContext.class);

	private static final Snapshot<ItemSelector> _itemSelectorSnapshot =
		new Snapshot<>(
			BlogsEditEntryDisplayContext.class, ItemSelector.class, null, true);

	private Boolean _allowPingbacks;
	private Boolean _allowTrackbacks;
	private final AssetAutoTaggerConfiguration _assetAutoTaggerConfiguration;
	private long[] _assetCategoryIds;
	private final AssetVocabularyLocalService _assetVocabularyLocalService;
	private final BlogsEntry _blogsEntry;
	private final BlogsFileUploadsConfiguration _blogsFileUploadsConfiguration;
	private final BlogsGroupServiceSettings _blogsGroupServiceSettings;
	private String _content;
	private String _coverImageCaption;
	private Long _coverImageFileEntryId;
	private Boolean _customAbstract;
	private String _description;
	private Boolean _emailEntryUpdatedEnabled;
	private Long _entryId;
	private long[] _groupIds;
	private final HttpServletRequest _httpServletRequest;
	private final ItemSelector _itemSelector;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _redirect;
	private final SiteConnectedGroupGroupProvider
		_siteConnectedGroupGroupProvider;
	private Long _smallImageFileEntryId;
	private final ThemeDisplay _themeDisplay;
	private String _title;
	private String _urlTitle;

}