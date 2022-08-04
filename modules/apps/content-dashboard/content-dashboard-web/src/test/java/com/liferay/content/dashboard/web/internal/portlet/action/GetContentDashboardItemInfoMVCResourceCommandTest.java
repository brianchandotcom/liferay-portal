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

package com.liferay.content.dashboard.web.internal.portlet.action;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItem;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactory;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactoryTracker;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemSubtype;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemSubtypeFactory;
import com.liferay.info.item.InfoItemReference;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.portal.servlet.BrowserSnifferImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PortalImpl;

import java.io.ByteArrayOutputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Cristina González
 */
public class GetContentDashboardItemInfoMVCResourceCommandTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		BrowserSnifferUtil browserSnifferUtil = new BrowserSnifferUtil();

		browserSnifferUtil.setBrowserSniffer(new BrowserSnifferImpl());

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(new LanguageImpl());

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());
	}

	@Test
	public void testServeResource() throws Exception {
		User user = Mockito.mock(User.class);

		Mockito.when(
			user.getPortraitId()
		).thenReturn(
			12345L
		);

		Mockito.when(
			user.getPortraitURL(Mockito.any(ThemeDisplay.class))
		).thenReturn(
			"portraitURL"
		);

		ContentDashboardItem<?> contentDashboardItem =
			new ContentDashboardItemBuilder(
				"className", 12345L
			).withContentDashboardItemAction(
				new ContentDashboardItemAction() {

					@Override
					public String getIcon() {
						return "sharing";
					}

					@Override
					public String getLabel(Locale locale) {
						return "sharing";
					}

					@Override
					public String getName() {
						return "sharing";
					}

					@Override
					public Type getType() {
						return Type.SHARING_BUTTON;
					}

					@Override
					public String getURL() {
						return "http://sharing_button";
					}

					@Override
					public String getURL(Locale locale) {
						return getURL();
					}

				}
			).withContentDashboardItemAction(
				new ContentDashboardItemAction() {

					@Override
					public String getIcon() {
						return "collaborators";
					}

					@Override
					public String getLabel(Locale locale) {
						return "collaborators";
					}

					@Override
					public String getName() {
						return "collaborators";
					}

					@Override
					public Type getType() {
						return Type.SHARING_COLLABORATORS;
					}

					@Override
					public String getURL() {
						return "http://sharing_collaborators";
					}

					@Override
					public String getURL(Locale locale) {
						return getURL();
					}

				}
			).withSubtype(
				"subType"
			).withUser(
				user
			).build();

		_initGetContentDashboardItemInfoMVCResourceCommand(
			contentDashboardItem, user);

		MockLiferayResourceRequest mockLiferayResourceRequest =
			_getMockLiferayResourceRequest(contentDashboardItem);

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		_getContentDashboardItemInfoMVCResourceCommand.serveResource(
			mockLiferayResourceRequest, mockLiferayResourceResponse);

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			new String(byteArrayOutputStream.toByteArray()));

		JSONObject vocabulariesJSONObject = jsonObject.getJSONObject(
			"vocabularies");

		List<AssetCategory> assetCategories =
			contentDashboardItem.getAssetCategories();

		for (AssetCategory assetCategory : assetCategories) {
			JSONObject vocabularyDataJSONObject =
				vocabulariesJSONObject.getJSONObject(
					String.valueOf(assetCategory.getVocabularyId()));

			JSONArray categoriesJSONArray =
				vocabularyDataJSONObject.getJSONArray("categories");

			Assert.assertEquals(1, categoriesJSONArray.length());

			Assert.assertEquals(
				assetCategory.getTitle(LocaleUtil.getSiteDefault()),
				categoriesJSONArray.getString(0));
		}

		InfoItemReference infoItemReference =
			contentDashboardItem.getInfoItemReference();

		Assert.assertEquals(
			infoItemReference.getClassName(),
			jsonObject.getString("className"));
		Assert.assertEquals(
			infoItemReference.getClassPK(), jsonObject.getLong("classPK"), 0);

		Assert.assertEquals(
			contentDashboardItem.getDescription(LocaleUtil.US),
			jsonObject.getString("description"));
		Assert.assertNotNull(jsonObject.getString("fetchSharingButtonURL"));
		Assert.assertNotNull(
			jsonObject.getString("fetchSharingCollaboratorsURL"));

		JSONArray tagsJSONArray = jsonObject.getJSONArray("tags");

		List<AssetTag> assetTags = contentDashboardItem.getAssetTags();

		Stream<AssetTag> stream = assetTags.stream();

		Assert.assertEquals(
			JSONUtil.putAll(
				stream.map(
					AssetTag::getName
				).collect(
					Collectors.toList()
				).toArray(
					new String[0]
				)
			).toString(),
			tagsJSONArray.toString());

		Assert.assertEquals(
			contentDashboardItem.getTitle(LocaleUtil.US),
			jsonObject.getString("title"));

		ContentDashboardItemSubtype contentDashboardItemSubtype =
			contentDashboardItem.getContentDashboardItemSubtype();

		Assert.assertEquals(
			contentDashboardItemSubtype.getLabel(LocaleUtil.US),
			jsonObject.getString("subType"));

		Map<String, Object> specificInformation =
			contentDashboardItem.getSpecificInformation(LocaleUtil.US);

		Assert.assertEquals(
			String.valueOf(specificInformation), 2, specificInformation.size());

		JSONObject specificFieldsJSONObject = jsonObject.getJSONObject(
			"specificFields");

		for (Map.Entry<String, Object> entry : specificInformation.entrySet()) {
			JSONObject specificFieldJSONObject =
				specificFieldsJSONObject.getJSONObject(entry.getKey());

			Assert.assertEquals(
				specificFieldJSONObject.getString("title"), entry.getKey());
			Assert.assertEquals(
				specificFieldJSONObject.getString("value"), entry.getValue());
		}

		JSONObject userJSONObject = jsonObject.getJSONObject("user");

		Assert.assertEquals(
			contentDashboardItem.getUserName(),
			userJSONObject.getString("name"));
		Assert.assertEquals(
			contentDashboardItem.getUserId(), userJSONObject.getLong("userId"));
		Assert.assertEquals("portraitURL", userJSONObject.getString("url"));

		List<ContentDashboardItem.Version> versions =
			contentDashboardItem.getVersions(LocaleUtil.US);

		ContentDashboardItem.Version version = versions.get(0);

		JSONObject expectedVersionJSONObject = version.toJSONObject();

		JSONArray versionsJSONArray = jsonObject.getJSONArray("versions");

		JSONObject actualVersionJSONObject = versionsJSONArray.getJSONObject(0);

		Assert.assertEquals(
			expectedVersionJSONObject.toString(),
			actualVersionJSONObject.toString());
	}

	@Test
	public void testServeResourceWithoutSharingButtonAction() throws Exception {
		ContentDashboardItem<?> contentDashboardItem =
			new ContentDashboardItemBuilder(
				"className", 12345L
			).build();

		_initGetContentDashboardItemInfoMVCResourceCommand(
			contentDashboardItem, null);

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		_getContentDashboardItemInfoMVCResourceCommand.serveResource(
			_getMockLiferayResourceRequest(contentDashboardItem),
			mockLiferayResourceResponse);

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			new String(byteArrayOutputStream.toByteArray()));

		Assert.assertEquals(
			StringPool.BLANK, jsonObject.getString("fetchSharingButtonURL"));
	}

	@Test
	public void testServeResourceWithoutSharingCollaboratorsAction()
		throws Exception {

		ContentDashboardItem<?> contentDashboardItem =
			new ContentDashboardItemBuilder(
				"className", 12345L
			).build();

		_initGetContentDashboardItemInfoMVCResourceCommand(
			contentDashboardItem, null);

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		_getContentDashboardItemInfoMVCResourceCommand.serveResource(
			_getMockLiferayResourceRequest(contentDashboardItem),
			mockLiferayResourceResponse);

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			new String(byteArrayOutputStream.toByteArray()));

		Assert.assertEquals(
			StringPool.BLANK,
			jsonObject.getString("fetchSharingCollaboratorsURL"));
	}

	@Test
	public void testServeResourceWithoutSubtype() throws Exception {
		ContentDashboardItem<?> contentDashboardItem =
			new ContentDashboardItemBuilder(
				"className", 12345L
			).build();

		_initGetContentDashboardItemInfoMVCResourceCommand(
			contentDashboardItem, null);

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		_getContentDashboardItemInfoMVCResourceCommand.serveResource(
			_getMockLiferayResourceRequest(contentDashboardItem),
			mockLiferayResourceResponse);

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			new String(byteArrayOutputStream.toByteArray()));

		Assert.assertEquals(StringPool.BLANK, jsonObject.getString("subType"));
	}

	@Test
	public void testServeResourceWithoutUser() throws Exception {
		ContentDashboardItem<?> contentDashboardItem =
			new ContentDashboardItemBuilder(
				"className", 12345L
			).build();

		_initGetContentDashboardItemInfoMVCResourceCommand(
			contentDashboardItem, null);

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		_getContentDashboardItemInfoMVCResourceCommand.serveResource(
			_getMockLiferayResourceRequest(contentDashboardItem),
			mockLiferayResourceResponse);

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			new String(byteArrayOutputStream.toByteArray()));

		JSONObject userJSONObject = jsonObject.getJSONObject("user");

		Assert.assertEquals(
			contentDashboardItem.getUserName(),
			userJSONObject.getString("name"));
		Assert.assertEquals(
			contentDashboardItem.getUserId(), userJSONObject.getLong("userId"));
		Assert.assertEquals(StringPool.BLANK, userJSONObject.getString("url"));
	}

	private MockLiferayResourceRequest _getMockLiferayResourceRequest(
		ContentDashboardItem<?> contentDashboardItem) {

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.setAttribute(WebKeys.LOCALE, LocaleUtil.US);

		InfoItemReference infoItemReference =
			contentDashboardItem.getInfoItemReference();

		mockLiferayResourceRequest.addParameter(
			"className", infoItemReference.getClassName());
		mockLiferayResourceRequest.addParameter(
			"classPK", String.valueOf(infoItemReference.getClassPK()));

		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY, new ThemeDisplay());

		return mockLiferayResourceRequest;
	}

	private void _initGetContentDashboardItemInfoMVCResourceCommand(
		ContentDashboardItem<?> contentDashboardItem, User user) {

		_getContentDashboardItemInfoMVCResourceCommand =
			new GetContentDashboardItemInfoMVCResourceCommand();

		AssetVocabularyLocalService assetVocabularyLocalService = Mockito.mock(
			AssetVocabularyLocalService.class);

		for (AssetCategory assetCategory :
				contentDashboardItem.getAssetCategories()) {

			AssetVocabulary assetVocabulary = Mockito.mock(
				AssetVocabulary.class);

			Mockito.when(
				assetVocabulary.getTitle(Mockito.any(Locale.class))
			).thenReturn(
				RandomTestUtil.randomString()
			);

			Mockito.when(
				assetVocabularyLocalService.fetchAssetVocabulary(
					assetCategory.getVocabularyId())
			).thenReturn(
				assetVocabulary
			);
		}

		ReflectionTestUtil.setFieldValue(
			_getContentDashboardItemInfoMVCResourceCommand,
			"_assetVocabularyLocalService", assetVocabularyLocalService);
		ReflectionTestUtil.setFieldValue(
			_getContentDashboardItemInfoMVCResourceCommand,
			"_contentDashboardItemFactoryTracker",
			new ContentDashboardItemFactoryTracker() {

				public ContentDashboardItemFactory<?>
					getContentDashboardItemFactory(String className) {

					return new ContentDashboardItemFactory() {

						@Override
						public ContentDashboardItem create(long classPK) {
							InfoItemReference infoItemReference =
								contentDashboardItem.getInfoItemReference();

							if (Objects.equals(
									className,
									infoItemReference.getClassName()) &&
								Objects.equals(
									classPK, infoItemReference.getClassPK())) {

								return contentDashboardItem;
							}

							return null;
						}

						@Override
						public Optional<ContentDashboardItemSubtypeFactory>
							getContentDashboardItemSubtypeFactoryOptional() {

							return Optional.empty();
						}

					};
				}

			});
		ReflectionTestUtil.setFieldValue(
			_getContentDashboardItemInfoMVCResourceCommand,
			"_groupLocalService", Mockito.mock(GroupLocalService.class));
		ReflectionTestUtil.setFieldValue(
			_getContentDashboardItemInfoMVCResourceCommand, "_language",
			new LanguageImpl());
		ReflectionTestUtil.setFieldValue(
			_getContentDashboardItemInfoMVCResourceCommand, "_portal",
			new PortalImpl());

		UserLocalService userLocalService = Mockito.mock(
			UserLocalService.class);

		Mockito.when(
			userLocalService.fetchUser(contentDashboardItem.getUserId())
		).thenReturn(
			user
		);

		ReflectionTestUtil.setFieldValue(
			_getContentDashboardItemInfoMVCResourceCommand, "_userLocalService",
			userLocalService);
	}

	private GetContentDashboardItemInfoMVCResourceCommand
		_getContentDashboardItemInfoMVCResourceCommand;

	private static class ContentDashboardItemBuilder {

		public ContentDashboardItemBuilder(String className, long classPK) {
			_className = className;
			_classPK = classPK;
		}

		public ContentDashboardItem build() {
			String userName = Optional.ofNullable(
				_user
			).map(
				user -> user.getFirstName()
			).orElseGet(
				RandomTestUtil::randomString
			);

			long userId = Optional.ofNullable(
				_user
			).map(
				user -> user.getUserId()
			).orElseGet(
				RandomTestUtil::randomLong
			);

			AssetCategory assetCategory1 = Mockito.mock(AssetCategory.class);

			Mockito.when(
				assetCategory1.getName()
			).thenReturn(
				RandomTestUtil.randomString()
			);

			Mockito.when(
				assetCategory1.getTitle(Mockito.any(Locale.class))
			).thenReturn(
				RandomTestUtil.randomString()
			);

			Mockito.when(
				assetCategory1.getVocabularyId()
			).thenReturn(
				RandomTestUtil.randomLong()
			);

			AssetCategory assetCategory2 = Mockito.mock(AssetCategory.class);

			Mockito.when(
				assetCategory2.getName()
			).thenReturn(
				RandomTestUtil.randomString()
			);

			Mockito.when(
				assetCategory2.getTitle(Mockito.any(Locale.class))
			).thenReturn(
				RandomTestUtil.randomString()
			);

			Mockito.when(
				assetCategory2.getVocabularyId()
			).thenReturn(
				RandomTestUtil.randomLong()
			);

			AssetTag assetTag = Mockito.mock(AssetTag.class);

			Mockito.when(
				assetTag.getName()
			).thenReturn(
				RandomTestUtil.randomString()
			);

			return new ContentDashboardItem() {

				@Override
				public List<AssetCategory> getAssetCategories() {
					return Arrays.asList(assetCategory1, assetCategory2);
				}

				@Override
				public List<AssetCategory> getAssetCategories(
					long vocabularyId) {

					return Collections.emptyList();
				}

				@Override
				public List<AssetTag> getAssetTags() {
					return Collections.singletonList(assetTag);
				}

				@Override
				public List<Locale> getAvailableLocales() {
					return Collections.singletonList(LocaleUtil.US);
				}

				@Override
				public Clipboard getClipboard() {
					return new Clipboard(
						"name", "www.previewURL.url.com/previewURL");
				}

				@Override
				public List<ContentDashboardItemAction>
					getContentDashboardItemActions(
						HttpServletRequest httpServletRequest,
						ContentDashboardItemAction.Type... types) {

					Stream<ContentDashboardItemAction> stream =
						_contentDashboardItemActions.stream();

					return stream.filter(
						contentDashboardItemAction -> ArrayUtil.contains(
							types, contentDashboardItemAction.getType())
					).collect(
						Collectors.toList()
					);
				}

				@Override
				public ContentDashboardItemSubtype
					getContentDashboardItemSubtype() {

					ContentDashboardItemSubtype contentDashboardItemSubtype =
						Mockito.mock(ContentDashboardItemSubtype.class);

					Mockito.when(
						contentDashboardItemSubtype.getLabel(
							Mockito.any(Locale.class))
					).thenReturn(
						_subtype
					);

					return contentDashboardItemSubtype;
				}

				@Override
				public Date getCreateDate() {
					return new Date();
				}

				@Override
				public ContentDashboardItemAction
					getDefaultContentDashboardItemAction(
						HttpServletRequest httpServletRequest) {

					return null;
				}

				@Override
				public Locale getDefaultLocale() {
					return LocaleUtil.US;
				}

				@Override
				public String getDescription(Locale locale) {
					return "My very important description";
				}

				@Override
				public InfoItemReference getInfoItemReference() {
					return new InfoItemReference(_className, _classPK);
				}

				@Override
				public Date getModifiedDate() {
					return new Date();
				}

				@Override
				public Preview getPreview() {
					return new Preview(
						"www.preview.com/imageURL",
						"www.viewURL.url.com/viewURL");
				}

				@Override
				public String getScopeName(Locale locale) {
					return RandomTestUtil.randomString();
				}

				@Override
				public Map<String, Object> getSpecificInformation(
					Locale locale) {

					return HashMapBuilder.<String, Object>put(
						"extension", ".pdf"
					).put(
						"size", "5"
					).build();
				}

				@Override
				public String getTitle(Locale locale) {
					return "title";
				}

				@Override
				public String getTypeLabel(Locale locale) {
					return "Web Content";
				}

				@Override
				public long getUserId() {
					return userId;
				}

				@Override
				public String getUserName() {
					return userName;
				}

				@Override
				public List<Version> getVersions(Locale locale) {
					return Collections.singletonList(
						new Version("version", "style", "0.1"));
				}

				@Override
				public boolean isViewable(
					HttpServletRequest httpServletRequest) {

					return true;
				}

			};
		}

		public ContentDashboardItemBuilder withContentDashboardItemAction(
			ContentDashboardItemAction contentDashboardAction) {

			_contentDashboardItemActions.add(contentDashboardAction);

			return this;
		}

		public ContentDashboardItemBuilder withSubtype(String subtype) {
			_subtype = subtype;

			return this;
		}

		public ContentDashboardItemBuilder withUser(User user) {
			_user = user;

			return this;
		}

		private final String _className;
		private final long _classPK;
		private List<ContentDashboardItemAction> _contentDashboardItemActions =
			new ArrayList<>();
		private String _subtype;
		private User _user;

	}

}