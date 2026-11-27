/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.display.context;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.journal.web.internal.configuration.JournalWebConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Lourdes Fernández Besada
 */
public class JournalEditArticleDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		Mockito.when(
			_httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			_themeDisplay
		);

		Mockito.when(
			_httpServletRequest.getParameter("showHeader")
		).thenReturn(
			"false"
		);

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);
	}

	@After
	public void tearDown() {
		_ddmStructureLocalServiceUtilMockedStatic.close();
		_journalArticleLocalServiceUtilMockedStatic.close();
		_journalArticleServiceUtilMockedStatic.close();
	}

	@Test
	public void testFolderNameValueIsCached() {
		String expectedResult = "Home translation";

		Mockito.when(
			_language.get(_httpServletRequest, "home")
		).thenReturn(
			expectedResult
		);

		_journalEditArticleDisplayContext =
			new JournalEditArticleDisplayContext(
				_httpServletRequest, _liferayPortletResponse, null, null, null,
				null);

		Assert.assertEquals(
			_UNEXPECTED_FOLDER_NAME_MESSAGE, expectedResult,
			_journalEditArticleDisplayContext.getFolderName());
		Assert.assertEquals(
			_UNEXPECTED_FOLDER_NAME_MESSAGE, expectedResult,
			_journalEditArticleDisplayContext.getFolderName());

		Mockito.verify(
			_language, Mockito.times(1)
		).get(
			_httpServletRequest, "home"
		);
	}

	@Test
	public void testGetDDMFormValues() throws PortalException {
		_testGetDDMFormValuesWithDifferentDefaultLanguage();
		_testGetDDMFormValuesWithSameDefaultLanguage();
	}

	@Test
	public void testGetDefaultArticleLanguageId() throws PortalException {
		_testGetDefaultArticleLanguageIdFromArticle();
		_testGetDefaultArticleLanguageIdWithChangeableDefaultLanguageAndAvailableLocale(
			LocaleUtil.US, LocaleUtil.UK);
		_testGetDefaultArticleLanguageIdWithChangeableDefaultLanguageAndAvailableLocale(
			LocaleUtil.CHINA, LocaleUtil.CHINA);
		_testGetDefaultArticleLanguageIdWithParameter();
		_testGetDefaultArticleLanguageIdWithUnavailableLocale();
	}

	@Test
	public void testGetFolderNameDefaultFolderId() {
		String expectedResult = "Home translation";

		Mockito.when(
			_language.get(_httpServletRequest, "home")
		).thenReturn(
			expectedResult
		);

		_journalEditArticleDisplayContext =
			new JournalEditArticleDisplayContext(
				_httpServletRequest, _liferayPortletResponse, null, null, null,
				null);

		Assert.assertEquals(
			_UNEXPECTED_FOLDER_NAME_MESSAGE, expectedResult,
			_journalEditArticleDisplayContext.getFolderName());

		Mockito.verify(
			_language
		).get(
			_httpServletRequest, "home"
		);

		Mockito.verifyNoInteractions(_journalFolderLocalService);
	}

	@Test
	public void testGetFolderNameSpecificFolderId() {
		long folderId = 42;

		Mockito.when(
			_httpServletRequest.getParameter("folderId")
		).thenReturn(
			String.valueOf(folderId)
		);

		ReflectionTestUtil.setFieldValue(
			JournalFolderLocalServiceUtil.class, "_serviceSnapshot",
			new Snapshot<JournalFolderLocalService>(
				JournalFolderLocalServiceUtil.class,
				JournalFolderLocalService.class) {

				@Override
				public JournalFolderLocalService get() {
					return _journalFolderLocalService;
				}

			});

		JournalFolder folder = Mockito.mock(JournalFolder.class);

		String expectedResult = "Folder name";

		Mockito.when(
			folder.getName()
		).thenReturn(
			expectedResult
		);

		Mockito.when(
			_journalFolderLocalService.fetchJournalFolder(folderId)
		).thenReturn(
			folder
		);

		_journalEditArticleDisplayContext =
			new JournalEditArticleDisplayContext(
				_httpServletRequest, _liferayPortletResponse, null, null, null,
				null);

		Assert.assertEquals(
			_UNEXPECTED_FOLDER_NAME_MESSAGE, expectedResult,
			_journalEditArticleDisplayContext.getFolderName());

		Mockito.verify(
			_journalFolderLocalService
		).fetchJournalFolder(
			folderId
		);

		Mockito.verifyNoInteractions(_language);
	}

	@Test
	public void testGetFolderNameSpecificFolderIdFolderNotFound() {
		String expectedResult = "Home translation";

		Mockito.when(
			_language.get(_httpServletRequest, "home")
		).thenReturn(
			expectedResult
		);

		long folderId = 42;

		Mockito.when(
			_httpServletRequest.getParameter("folderId")
		).thenReturn(
			String.valueOf(folderId)
		);

		ReflectionTestUtil.setFieldValue(
			JournalFolderLocalServiceUtil.class, "_serviceSnapshot",
			new Snapshot<JournalFolderLocalService>(
				JournalFolderLocalServiceUtil.class,
				JournalFolderLocalService.class) {

				@Override
				public JournalFolderLocalService get() {
					return _journalFolderLocalService;
				}

			});

		_journalEditArticleDisplayContext =
			new JournalEditArticleDisplayContext(
				_httpServletRequest, _liferayPortletResponse, null, null, null,
				null);

		Assert.assertEquals(
			_UNEXPECTED_FOLDER_NAME_MESSAGE, expectedResult,
			_journalEditArticleDisplayContext.getFolderName());

		Mockito.verify(
			_journalFolderLocalService
		).fetchJournalFolder(
			folderId
		);

		Mockito.verify(
			_language
		).get(
			_httpServletRequest, "home"
		);
	}

	@Test
	public void testGetTimeZoneMap() {
		TimeZone timeZone = Mockito.mock(TimeZone.class);

		String timeZoneName = RandomTestUtil.randomString();

		Mockito.when(
			timeZone.getDisplayName(false, TimeZone.SHORT)
		).thenReturn(
			timeZoneName
		);

		String timeZoneId = RandomTestUtil.randomString();

		Mockito.when(
			timeZone.getID()
		).thenReturn(
			timeZoneId
		);

		Mockito.when(
			_themeDisplay.getTimeZone()
		).thenReturn(
			timeZone
		);

		_journalEditArticleDisplayContext =
			new JournalEditArticleDisplayContext(
				_httpServletRequest, _liferayPortletResponse, null, null, null,
				null);

		Map<String, Object> timeZoneMap =
			_journalEditArticleDisplayContext.getTimeZoneMap();

		Assert.assertEquals(timeZoneId, timeZoneMap.get("id"));
		Assert.assertEquals(timeZoneName, timeZoneMap.get("name"));
	}

	@Test
	public void testIsShowSelectFolderAddActionFalseParamValue() {
		_journalEditArticleDisplayContext =
			new JournalEditArticleDisplayContext(
				_httpServletRequest, _liferayPortletResponse, null, null, null,
				null);

		Mockito.when(
			_httpServletRequest.getParameter("showSelectFolder")
		).thenReturn(
			"false"
		);

		Assert.assertFalse(
			_journalEditArticleDisplayContext.isShowSelectFolder());

		Mockito.verify(
			_httpServletRequest
		).getParameter(
			"showSelectFolder"
		);
	}

	@Test
	public void testIsShowSelectFolderAddActionMissingParam() {
		_journalEditArticleDisplayContext =
			new JournalEditArticleDisplayContext(
				_httpServletRequest, _liferayPortletResponse, null, null, null,
				null);

		Mockito.when(
			_httpServletRequest.getParameter("showSelectFolder")
		).thenReturn(
			null
		);

		Assert.assertTrue(
			_journalEditArticleDisplayContext.isShowSelectFolder());

		Mockito.verify(
			_httpServletRequest
		).getParameter(
			"showSelectFolder"
		);
	}

	@Test
	public void testIsShowSelectFolderAddActionTrueParamValue() {
		_journalEditArticleDisplayContext =
			new JournalEditArticleDisplayContext(
				_httpServletRequest, _liferayPortletResponse, null, null, null,
				null);

		Mockito.when(
			_httpServletRequest.getParameter("showSelectFolder")
		).thenReturn(
			"true"
		);

		Assert.assertTrue(
			_journalEditArticleDisplayContext.isShowSelectFolder());

		Mockito.verify(
			_httpServletRequest
		).getParameter(
			"showSelectFolder"
		);
	}

	@Test
	public void testIsShowSelectFolderEditActionDoesntMatterParamValue() {
		_journalEditArticleDisplayContext =
			new JournalEditArticleDisplayContext(
				_httpServletRequest, _liferayPortletResponse, _journalArticle,
				null, null, null);

		Assert.assertFalse(
			_journalEditArticleDisplayContext.isShowSelectFolder());

		Mockito.verify(
			_httpServletRequest, Mockito.never()
		).getParameter(
			"showSelectFolder"
		);
	}

	@Test
	public void testShowSelectFolderValueIsCached() {
		_journalEditArticleDisplayContext =
			new JournalEditArticleDisplayContext(
				_httpServletRequest, _liferayPortletResponse, null, null, null,
				null);

		Mockito.when(
			_httpServletRequest.getParameter("showSelectFolder")
		).thenReturn(
			"true"
		);

		Assert.assertTrue(
			_journalEditArticleDisplayContext.isShowSelectFolder());
		Assert.assertTrue(
			_journalEditArticleDisplayContext.isShowSelectFolder());

		Mockito.verify(
			_httpServletRequest, Mockito.times(1)
		).getParameter(
			"showSelectFolder"
		);
	}

	private DDMFormFieldValue _createDDMFormFieldValue(
		Locale locale, String string) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName(RandomTestUtil.randomString());

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(locale, string);

		ddmFormFieldValue.setValue(localizedValue);

		return ddmFormFieldValue;
	}

	private DDMFormValues _createDDMFormValues(
		DDMFormFieldValue... ddmFormFieldValues) {

		DDMFormValues ddmFormValues = new DDMFormValues(new DDMForm());

		ddmFormValues.addAvailableLocale(LocaleUtil.US);

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
		}

		ddmFormValues.setDefaultLocale(LocaleUtil.US);

		return ddmFormValues;
	}

	private JournalEditArticleDisplayContext
			_getDDMStructureArticleJournalEditArticleDisplayContext(
				DDMFormValues ddmFormValues, Locale defaultArticleLocale,
				Map<Locale, String> descriptionMap,
				Map<Locale, String> titleMap)
		throws PortalException {

		Mockito.when(
			_httpServletRequest.getParameterNames()
		).thenReturn(
			Collections.emptyEnumeration()
		);

		long ddmStructureId = RandomTestUtil.randomLong();

		Mockito.when(
			_httpServletRequest.getParameter("ddmStructureId")
		).thenReturn(
			String.valueOf(ddmStructureId)
		);

		String defaultArticleLanguageId = LocaleUtil.toLanguageId(
			defaultArticleLocale);

		Mockito.when(
			_httpServletRequest.getParameter("defaultLanguageId")
		).thenReturn(
			defaultArticleLanguageId
		);

		Mockito.when(
			_language.isAvailableLocale(0, defaultArticleLanguageId)
		).thenReturn(
			true
		);

		Mockito.when(
			_language.isAvailableLocale(defaultArticleLocale)
		).thenReturn(
			true
		);

		Mockito.when(
			_language.isAvailableLocale(LocaleUtil.US)
		).thenReturn(
			true
		);

		DDMStructure ddmStructure = Mockito.mock(DDMStructure.class);

		long groupId = RandomTestUtil.randomLong();

		Mockito.when(
			ddmStructure.getGroupId()
		).thenReturn(
			groupId
		);

		Mockito.when(
			ddmStructure.getStructureId()
		).thenReturn(
			ddmStructureId
		);

		Mockito.when(
			DDMStructureLocalServiceUtil.fetchStructure(ddmStructureId)
		).thenReturn(
			ddmStructure
		);

		JournalArticle ddmStructureArticle = Mockito.mock(JournalArticle.class);

		Mockito.when(
			ddmStructureArticle.getDDMFormValues()
		).thenReturn(
			ddmFormValues
		);

		Mockito.when(
			JournalArticleLocalServiceUtil.getArticle(
				groupId, DDMStructure.class.getName(), ddmStructureId)
		).thenReturn(
			ddmStructureArticle
		);

		JournalArticle journalArticle = Mockito.mock(JournalArticle.class);

		Mockito.when(
			journalArticle.getDefaultLanguageId()
		).thenReturn(
			LocaleUtil.toLanguageId(LocaleUtil.US)
		);

		Mockito.when(
			journalArticle.getDescriptionMap()
		).thenReturn(
			descriptionMap
		);

		Mockito.when(
			journalArticle.getTitleMap()
		).thenReturn(
			titleMap
		);

		Mockito.when(
			journalArticle.isNew()
		).thenReturn(
			true
		);

		return new JournalEditArticleDisplayContext(
			_httpServletRequest, _liferayPortletResponse, journalArticle, null,
			null, null);
	}

	private void _testGetDDMFormValuesWithDifferentDefaultLanguage()
		throws PortalException {

		DDMFormFieldValue blankDDMFormFieldValue = _createDDMFormFieldValue(
			LocaleUtil.US, StringPool.BLANK);

		String string = RandomTestUtil.randomString();

		DDMFormFieldValue fieldSetDDMFormFieldValue = new DDMFormFieldValue();

		fieldSetDDMFormFieldValue.addNestedDDMFormFieldValue(
			_createDDMFormFieldValue(LocaleUtil.US, string));
		fieldSetDDMFormFieldValue.setName(RandomTestUtil.randomString());

		DDMFormFieldValue japaneseDDMFormFieldValue = _createDDMFormFieldValue(
			LocaleUtil.US, RandomTestUtil.randomString());

		Value japaneseValue = japaneseDDMFormFieldValue.getValue();

		String japaneseString = RandomTestUtil.randomString();

		japaneseValue.addString(LocaleUtil.JAPAN, japaneseString);

		DDMFormFieldValue spanishDDMFormFieldValue = _createDDMFormFieldValue(
			LocaleUtil.US, string);

		Value spanishValue = spanishDDMFormFieldValue.getValue();

		String spanishString = RandomTestUtil.randomString();

		spanishValue.addString(LocaleUtil.SPAIN, spanishString);

		String description = RandomTestUtil.randomString();
		String spanishDescription = RandomTestUtil.randomString();

		Map<Locale, String> descriptionMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, spanishDescription
		).put(
			LocaleUtil.US, description
		).build();

		String title = RandomTestUtil.randomString();

		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.US, title
		).build();

		DDMFormFieldValue unlocalizedDDMFormFieldValue =
			new DDMFormFieldValue();

		unlocalizedDDMFormFieldValue.setName(RandomTestUtil.randomString());
		unlocalizedDDMFormFieldValue.setValue(
			new UnlocalizedValue(Boolean.TRUE.toString()));

		JournalEditArticleDisplayContext journalEditArticleDisplayContext =
			_getDDMStructureArticleJournalEditArticleDisplayContext(
				_createDDMFormValues(
					blankDDMFormFieldValue, fieldSetDDMFormFieldValue,
					japaneseDDMFormFieldValue, spanishDDMFormFieldValue,
					unlocalizedDDMFormFieldValue),
				LocaleUtil.JAPAN, descriptionMap, titleMap);

		Assert.assertEquals(
			HashMapBuilder.put(
				LocaleUtil.JAPAN, description
			).put(
				LocaleUtil.SPAIN, spanishDescription
			).build(),
			descriptionMap);
		Assert.assertEquals(
			HashMapBuilder.put(
				LocaleUtil.JAPAN, title
			).build(),
			titleMap);

		DDMFormValues ddmFormValues =
			journalEditArticleDisplayContext.getDDMFormValues();

		Assert.assertEquals(
			Collections.singleton(LocaleUtil.JAPAN),
			ddmFormValues.getAvailableLocales());
		Assert.assertEquals(LocaleUtil.JAPAN, ddmFormValues.getDefaultLocale());

		Value blankValue = blankDDMFormFieldValue.getValue();

		Assert.assertEquals(
			HashMapBuilder.put(
				LocaleUtil.JAPAN, StringPool.BLANK
			).build(),
			blankValue.getValues());
		Assert.assertEquals(LocaleUtil.JAPAN, blankValue.getDefaultLocale());

		List<DDMFormFieldValue> nestedDDMFormFieldValues =
			fieldSetDDMFormFieldValue.getNestedDDMFormFieldValues();

		DDMFormFieldValue nestedDDMFormFieldValue =
			nestedDDMFormFieldValues.get(0);

		Value nestedValue = nestedDDMFormFieldValue.getValue();

		Assert.assertEquals(
			HashMapBuilder.put(
				LocaleUtil.JAPAN, string
			).build(),
			nestedValue.getValues());

		Assert.assertEquals(
			HashMapBuilder.put(
				LocaleUtil.JAPAN, japaneseString
			).build(),
			japaneseValue.getValues());
		Assert.assertEquals(
			HashMapBuilder.put(
				LocaleUtil.JAPAN, string
			).put(
				LocaleUtil.SPAIN, spanishString
			).build(),
			spanishValue.getValues());

		Value unlocalizedValue = unlocalizedDDMFormFieldValue.getValue();

		Assert.assertEquals(
			Boolean.TRUE.toString(), unlocalizedValue.getString(null));
	}

	private void _testGetDDMFormValuesWithSameDefaultLanguage()
		throws PortalException {

		String string = RandomTestUtil.randomString();

		DDMFormFieldValue ddmFormFieldValue = _createDDMFormFieldValue(
			LocaleUtil.US, string);

		String title = RandomTestUtil.randomString();

		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.US, title
		).build();

		JournalEditArticleDisplayContext journalEditArticleDisplayContext =
			_getDDMStructureArticleJournalEditArticleDisplayContext(
				_createDDMFormValues(ddmFormFieldValue), LocaleUtil.US,
				new HashMap<>(), titleMap);

		Assert.assertEquals(
			HashMapBuilder.put(
				LocaleUtil.US, title
			).build(),
			titleMap);

		DDMFormValues ddmFormValues =
			journalEditArticleDisplayContext.getDDMFormValues();

		Assert.assertEquals(LocaleUtil.US, ddmFormValues.getDefaultLocale());

		Value value = ddmFormFieldValue.getValue();

		Assert.assertEquals(
			HashMapBuilder.put(
				LocaleUtil.US, string
			).build(),
			value.getValues());
	}

	private void _testGetDefaultArticleLanguageIdFromArticle()
		throws PortalException {

		String defaultLanguageId = RandomTestUtil.randomString();

		Mockito.when(
			_httpServletRequest.getParameter("defaultLanguageId")
		).thenReturn(
			defaultLanguageId
		);

		Mockito.when(
			_language.isAvailableLocale(
				0, LocaleUtil.toLanguageId(LocaleUtil.SPAIN))
		).thenReturn(
			true
		);

		Mockito.when(
			_portal.getSiteDefaultLocale(0)
		).thenReturn(
			LocaleUtil.UK
		);

		DDMFormValues ddmFormValues = Mockito.mock(DDMFormValues.class);

		Mockito.when(
			ddmFormValues.getDefaultLocale()
		).thenReturn(
			LocaleUtil.SPAIN
		);

		JournalArticle journalArticle = Mockito.mock(JournalArticle.class);

		Mockito.when(
			journalArticle.getArticleId()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			journalArticle.getDDMFormValues()
		).thenReturn(
			ddmFormValues
		);

		_journalEditArticleDisplayContext =
			new JournalEditArticleDisplayContext(
				_httpServletRequest, _liferayPortletResponse, journalArticle,
				null, null, null);

		Assert.assertEquals(
			LocaleUtil.toLanguageId(LocaleUtil.SPAIN),
			_journalEditArticleDisplayContext.getDefaultArticleLanguageId());
	}

	private void
			_testGetDefaultArticleLanguageIdWithChangeableDefaultLanguageAndAvailableLocale(
				Locale availableLocale, Locale expectedLocale)
		throws PortalException {

		JournalArticle journalArticle = Mockito.mock(JournalArticle.class);

		Mockito.when(
			journalArticle.getArticleId()
		).thenReturn(
			null
		);

		Mockito.when(
			journalArticle.getDefaultLanguageId()
		).thenReturn(
			LocaleUtil.toLanguageId(LocaleUtil.CHINA)
		);

		long ddmStructureId = RandomTestUtil.randomLong();

		DDMStructure ddmStructure = Mockito.mock(DDMStructure.class);

		Mockito.when(
			ddmStructure.getStructureId()
		).thenReturn(
			ddmStructureId
		);

		Mockito.when(
			DDMStructureLocalServiceUtil.fetchStructure(ddmStructureId)
		).thenReturn(
			ddmStructure
		);

		Mockito.when(
			JournalArticleServiceUtil.getArticle(
				ddmStructure.getGroupId(), DDMStructure.class.getName(),
				ddmStructure.getStructureId())
		).thenReturn(
			journalArticle
		);

		JournalWebConfiguration journalWebConfiguration = Mockito.mock(
			JournalWebConfiguration.class);

		Mockito.when(
			journalWebConfiguration.changeableDefaultLanguage()
		).thenReturn(
			true
		);

		Mockito.when(
			_httpServletRequest.getAttribute(
				JournalWebConfiguration.class.getName())
		).thenReturn(
			journalWebConfiguration
		);

		Mockito.when(
			_httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			_themeDisplay
		);

		Mockito.when(
			_httpServletRequest.getParameter("ddmStructureId")
		).thenReturn(
			String.valueOf(ddmStructureId)
		);

		Mockito.when(
			_httpServletRequest.getParameter("defaultLanguageId")
		).thenReturn(
			null
		);

		Set<Locale> availableLocales = new HashSet<>();

		availableLocales.add(availableLocale);

		Mockito.when(
			_language.getAvailableLocales(Mockito.anyLong())
		).thenReturn(
			availableLocales
		);

		Mockito.when(
			_language.isAvailableLocale(
				0, LocaleUtil.toLanguageId(LocaleUtil.CHINA))
		).thenReturn(
			true
		);

		_journalEditArticleDisplayContext =
			new JournalEditArticleDisplayContext(
				_httpServletRequest, _liferayPortletResponse, journalArticle,
				null, null, null);

		Assert.assertEquals(
			LocaleUtil.toLanguageId(expectedLocale),
			_journalEditArticleDisplayContext.getDefaultArticleLanguageId());
	}

	private void _testGetDefaultArticleLanguageIdWithParameter() {
		String defaultLanguageId = RandomTestUtil.randomString();

		Mockito.when(
			_httpServletRequest.getParameter("defaultLanguageId")
		).thenReturn(
			defaultLanguageId
		);

		Mockito.when(
			_language.isAvailableLocale(0, defaultLanguageId)
		).thenReturn(
			true
		);

		_journalEditArticleDisplayContext =
			new JournalEditArticleDisplayContext(
				_httpServletRequest, _liferayPortletResponse, null, null, null,
				null);

		Assert.assertEquals(
			defaultLanguageId,
			_journalEditArticleDisplayContext.getDefaultArticleLanguageId());
	}

	private void _testGetDefaultArticleLanguageIdWithUnavailableLocale()
		throws PortalException {

		String defaultLanguageId = RandomTestUtil.randomString();

		Mockito.when(
			_httpServletRequest.getParameter("defaultLanguageId")
		).thenReturn(
			defaultLanguageId
		);

		Mockito.when(
			_language.isAvailableLocale(0, defaultLanguageId)
		).thenReturn(
			false
		);

		Mockito.when(
			_portal.getSiteDefaultLocale(0)
		).thenReturn(
			LocaleUtil.UK
		);

		_journalEditArticleDisplayContext =
			new JournalEditArticleDisplayContext(
				_httpServletRequest, _liferayPortletResponse, null, null, null,
				null);

		Assert.assertEquals(
			LocaleUtil.toLanguageId(LocaleUtil.UK),
			_journalEditArticleDisplayContext.getDefaultArticleLanguageId());
	}

	private static final String _UNEXPECTED_FOLDER_NAME_MESSAGE =
		"Unexpected folder name";

	private final MockedStatic<DDMStructureLocalServiceUtil>
		_ddmStructureLocalServiceUtilMockedStatic = Mockito.mockStatic(
			DDMStructureLocalServiceUtil.class);
	private final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private final JournalArticle _journalArticle = Mockito.mock(
		JournalArticle.class);
	private final MockedStatic<JournalArticleLocalServiceUtil>
		_journalArticleLocalServiceUtilMockedStatic = Mockito.mockStatic(
			JournalArticleLocalServiceUtil.class);
	private final MockedStatic<JournalArticleServiceUtil>
		_journalArticleServiceUtilMockedStatic = Mockito.mockStatic(
			JournalArticleServiceUtil.class);
	private JournalEditArticleDisplayContext _journalEditArticleDisplayContext;
	private final JournalFolderLocalService _journalFolderLocalService =
		Mockito.mock(JournalFolderLocalService.class);
	private final Language _language = Mockito.mock(Language.class);
	private final LiferayPortletResponse _liferayPortletResponse = Mockito.mock(
		LiferayPortletResponse.class);
	private final Portal _portal = Mockito.mock(Portal.class);
	private final ThemeDisplay _themeDisplay = Mockito.mock(ThemeDisplay.class);

}