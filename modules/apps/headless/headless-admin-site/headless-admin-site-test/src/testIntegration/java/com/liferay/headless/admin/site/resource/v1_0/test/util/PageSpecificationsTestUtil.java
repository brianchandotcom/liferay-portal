/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test.util;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoTableLocalServiceUtil;
import com.liferay.headless.admin.site.client.custom.field.CustomField;
import com.liferay.headless.admin.site.client.custom.field.CustomValue;
import com.liferay.headless.admin.site.client.dto.v1_0.ContentPageSpecification;
import com.liferay.headless.admin.site.client.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.client.dto.v1_0.PageExperience;
import com.liferay.headless.admin.site.client.dto.v1_0.PageSpecification;
import com.liferay.headless.admin.site.client.dto.v1_0.Settings;
import com.liferay.headless.admin.site.client.dto.v1_0.WidgetPageSpecification;
import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.layout.constants.LayoutTypeSettingsConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;

/**
 * @author Lourdes Fernández Besada
 */
public class PageSpecificationsTestUtil {

	public static void assertContentPageSpecification(
			PageSpecification pageSpecification, long plid)
		throws Exception {

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		Assert.assertEquals(
			layout.getExternalReferenceCode(),
			pageSpecification.getExternalReferenceCode());

		if (layout.isDraftLayout()) {
			if (layout.isApproved()) {
				Assert.assertEquals(
					PageSpecification.Status.APPROVED,
					pageSpecification.getStatus());
			}
			else {
				Assert.assertEquals(
					PageSpecification.Status.DRAFT,
					pageSpecification.getStatus());
			}
		}
		else if (_isPublished(layout.fetchDraftLayout())) {
			Assert.assertEquals(
				PageSpecification.Status.APPROVED,
				pageSpecification.getStatus());
		}
		else {
			Assert.assertEquals(
				PageSpecification.Status.DRAFT, pageSpecification.getStatus());
		}

		Assert.assertEquals(
			PageSpecification.Type.CONTENT_PAGE_SPECIFICATION,
			pageSpecification.getType());
	}

	public static void assertPageSpecifications(
		ContentPageSpecification expectedDraftContentPageSpecification,
		ContentPageSpecification expectedPublishedContentPageSpecification,
		PageSpecification[] pageSpecifications, Layout layout,
		PageSpecification.Status status) {

		ContentPageSpecification publishedContentPageSpecification =
			(ContentPageSpecification)pageSpecifications[0];

		String expectedPublishedContentPageSpecificationExternalReferenceCode =
			expectedPublishedContentPageSpecification.
				getExternalReferenceCode();

		if (expectedPublishedContentPageSpecificationExternalReferenceCode ==
				null) {

			Assert.assertNotNull(layout.getExternalReferenceCode());
			Assert.assertNotNull(
				publishedContentPageSpecification.getExternalReferenceCode());
		}
		else {
			Assert.assertEquals(
				expectedPublishedContentPageSpecificationExternalReferenceCode,
				layout.getExternalReferenceCode());
			Assert.assertEquals(
				expectedPublishedContentPageSpecificationExternalReferenceCode,
				publishedContentPageSpecification.getExternalReferenceCode());
		}

		Assert.assertEquals(
			expectedPublishedContentPageSpecification.getStatus(),
			publishedContentPageSpecification.getStatus());

		ContentPageSpecification draftContentPageSpecification =
			(ContentPageSpecification)pageSpecifications[1];

		Assert.assertNull(
			draftContentPageSpecification.
				getDraftContentPageSpecificationExternalReferenceCode());
		Assert.assertEquals(
			draftContentPageSpecification.getExternalReferenceCode(),
			publishedContentPageSpecification.
				getDraftContentPageSpecificationExternalReferenceCode());

		if (expectedDraftContentPageSpecification.getExternalReferenceCode() ==
				null) {

			Assert.assertNotNull(
				draftContentPageSpecification.getExternalReferenceCode());
		}
		else {
			Assert.assertEquals(
				expectedDraftContentPageSpecification.
					getExternalReferenceCode(),
				draftContentPageSpecification.getExternalReferenceCode());
		}

		Assert.assertEquals(
			expectedDraftContentPageSpecification.getStatus(),
			draftContentPageSpecification.getStatus());

		Assert.assertEquals(
			status, publishedContentPageSpecification.getStatus());

		PageExperiencesTestUtil.assertPageExperiences(
			expectedPublishedContentPageSpecification.getPageExperiences(),
			layout, publishedContentPageSpecification.getPageExperiences());

		Assert.assertEquals(
			expectedPublishedContentPageSpecification.
				getSiteTemplatePageSpecificationExternalReferenceCode(),
			publishedContentPageSpecification.
				getSiteTemplatePageSpecificationExternalReferenceCode());

		Layout draftLayout = layout.fetchDraftLayout();

		if (Objects.equals(PageSpecification.Status.APPROVED, status)) {
			Assert.assertTrue(_isPublished(draftLayout));
		}
		else {
			Assert.assertFalse(_isPublished(draftLayout));
		}

		Assert.assertEquals(
			draftContentPageSpecification.getExternalReferenceCode(),
			draftLayout.getExternalReferenceCode());

		PageExperiencesTestUtil.assertPageExperiences(
			expectedDraftContentPageSpecification.getPageExperiences(),
			draftLayout, draftContentPageSpecification.getPageExperiences());

		Assert.assertEquals(
			expectedDraftContentPageSpecification.
				getSiteTemplatePageSpecificationExternalReferenceCode(),
			draftContentPageSpecification.
				getSiteTemplatePageSpecificationExternalReferenceCode());

		if (Objects.equals(
				PageSpecification.Status.APPROVED,
				expectedDraftContentPageSpecification.getStatus())) {

			Assert.assertEquals(
				WorkflowConstants.STATUS_APPROVED, draftLayout.getStatus());
		}
		else {
			Assert.assertEquals(
				WorkflowConstants.STATUS_DRAFT, draftLayout.getStatus());
		}
	}

	public static void assertPageSpecifications(
			Layout layout, PageSpecification[] pageSpecifications)
		throws Exception {

		Assert.assertTrue(ArrayUtil.isNotEmpty(pageSpecifications));

		if (!layout.isTypeAssetDisplay() && !layout.isTypeContent()) {
			Assert.assertEquals(
				Arrays.toString(pageSpecifications), 1,
				pageSpecifications.length);

			PageSpecification pageSpecification = pageSpecifications[0];

			String externalReferenceCode = layout.getExternalReferenceCode();

			LayoutPageTemplateEntry layoutPageTemplateEntry =
				LayoutPageTemplateEntryLocalServiceUtil.
					fetchLayoutPageTemplateEntryByPlid(layout.getPlid());

			if ((layoutPageTemplateEntry != null) &&
				(layoutPageTemplateEntry.getType() ==
					LayoutPageTemplateEntryTypeConstants.WIDGET_PAGE)) {

				externalReferenceCode =
					layoutPageTemplateEntry.getExternalReferenceCode();
			}

			Assert.assertEquals(
				externalReferenceCode,
				pageSpecification.getExternalReferenceCode());

			Assert.assertEquals(
				PageSpecification.Status.APPROVED,
				pageSpecification.getStatus());
			Assert.assertEquals(
				PageSpecification.Type.WIDGET_PAGE_SPECIFICATION,
				pageSpecification.getType());

			return;
		}

		Assert.assertEquals(
			Arrays.toString(pageSpecifications), 2, pageSpecifications.length);

		assertContentPageSpecification(pageSpecifications[0], layout.getPlid());

		Layout draftLayout = layout.fetchDraftLayout();

		assertContentPageSpecification(
			pageSpecifications[1], draftLayout.getPlid());
	}

	public static void assertPostCustomFields(
			long groupId, PageSpecification[] postBodyPageSpecifications,
			PageSpecification[] postPageSpecifications)
		throws Exception {

		Assert.assertTrue(ArrayUtil.isNotEmpty(postPageSpecifications));

		CustomField customField = _getCustomField(
			_EXPANDO_ATTRIBUTE_NAMES[1], _EXPANDO_ATTRIBUTE_DEFAULT_VALUES[1]);

		_assertCustomFields(
			ArrayUtil.append(
				postBodyPageSpecifications[0].getCustomFields(), customField),
			groupId, postPageSpecifications[0]);

		if (postPageSpecifications.length == 2) {
			_assertCustomFields(
				ArrayUtil.append(
					postBodyPageSpecifications[1].getCustomFields(),
					customField),
				groupId, postPageSpecifications[1]);
		}
	}

	public static void assertUpdateCustomFields(
			long groupId, PageSpecification[] pageSpecifications,
			CustomField[][] postCustomFields,
			CustomField[][] updateBodyCustomFields)
		throws Exception {

		Assert.assertTrue(ArrayUtil.isNotEmpty(pageSpecifications));

		_assertCustomFields(
			new CustomField[] {
				_getCustomField(
					_EXPANDO_ATTRIBUTE_NAMES[0], postCustomFields[0]),
				_getCustomField(
					_EXPANDO_ATTRIBUTE_NAMES[1], updateBodyCustomFields[0]),
				_getCustomField(
					_EXPANDO_ATTRIBUTE_NAMES[2], postCustomFields[0])
			},
			groupId, pageSpecifications[0]);

		if (pageSpecifications.length == 2) {
			_assertCustomFields(
				new CustomField[] {
					_getCustomField(
						_EXPANDO_ATTRIBUTE_NAMES[0], postCustomFields[1]),
					_getCustomField(
						_EXPANDO_ATTRIBUTE_NAMES[1], updateBodyCustomFields[1]),
					_getCustomField(
						_EXPANDO_ATTRIBUTE_NAMES[2], postCustomFields[1])
				},
				groupId, pageSpecifications[1]);
		}
	}

	public static void assertWidgetPageSpecification(
		WidgetPageSpecification expectedWidgetPageSpecification,
		WidgetPageSpecification actualWidgetPageSpecification) {

		SettingsTestUtil.assertSettings(
			expectedWidgetPageSpecification.getSettings(),
			actualWidgetPageSpecification.getSettings());

		Assert.assertTrue(
			Objects.deepEquals(
				expectedWidgetPageSpecification.getWidgetPageSections(),
				actualWidgetPageSpecification.getWidgetPageSections()));
	}

	public static ContentPageSpecification getContentPageSpecification(
		String draftContentPageSpecificationExternalReferenceCode,
		PageSpecification.Status status) {

		return getContentPageSpecification(
			RandomTestUtil.randomString(),
			draftContentPageSpecificationExternalReferenceCode, status);
	}

	public static ContentPageSpecification getContentPageSpecification(
		String contentPageSpecificationExternalReferenceCode,
		String draftContentPageSpecificationExternalReferenceCode,
		PageSpecification.Status status) {

		ContentPageSpecification contentPageSpecification =
			new ContentPageSpecification() {
				{
					setType(() -> Type.CONTENT_PAGE_SPECIFICATION);
				}
			};

		contentPageSpecification.
			setDraftContentPageSpecificationExternalReferenceCode(
				draftContentPageSpecificationExternalReferenceCode);
		contentPageSpecification.setExternalReferenceCode(
			contentPageSpecificationExternalReferenceCode);
		contentPageSpecification.setPageExperiences(
			() -> {
				PageExperience pageExperience = new PageExperience();

				pageExperience.setExternalReferenceCode(
					RandomTestUtil::randomString);
				pageExperience.setKey(SegmentsExperienceConstants.KEY_DEFAULT);
				pageExperience.setName_i18n(
					Collections.singletonMap(
						"en-US", RandomTestUtil.randomString()));
				pageExperience.setPageElements(new PageElement[0]);
				pageExperience.setPageSpecificationExternalReferenceCode(
					contentPageSpecification.getExternalReferenceCode());

				return new PageExperience[] {pageExperience};
			});
		contentPageSpecification.setStatus(status);

		return contentPageSpecification;
	}

	public static ExpandoTableAutocloseable getExpandoTableAutoCloseable() {
		return new ExpandoTableAutocloseable();
	}

	public static PageSpecification[] getPatchPageSpecifications(
		PageSpecification[] pageSpecifications) {

		if (pageSpecifications.length == 2) {
			ContentPageSpecification draftContentPageSpecification = null;

			ContentPageSpecification publishedContentPageSpecification =
				(ContentPageSpecification)pageSpecifications[0];

			String draftContentPageSpecificationExternalReferenceCode =
				publishedContentPageSpecification.
					getDraftContentPageSpecificationExternalReferenceCode();

			if (draftContentPageSpecificationExternalReferenceCode != null) {
				draftContentPageSpecification =
					(ContentPageSpecification)pageSpecifications[1];
			}
			else {
				draftContentPageSpecification =
					publishedContentPageSpecification;
				publishedContentPageSpecification =
					(ContentPageSpecification)pageSpecifications[1];
			}

			ContentPageSpecification[] updatedPageSpecifications =
				_getContentPageSpecifications(
					draftContentPageSpecification.getExternalReferenceCode(),
					publishedContentPageSpecification.
						getExternalReferenceCode());

			updatedPageSpecifications[0].setPageExperiences(
				publishedContentPageSpecification.getPageExperiences());
			updatedPageSpecifications[1].setPageExperiences(
				draftContentPageSpecification.getPageExperiences());

			return updatedPageSpecifications;
		}

		WidgetPageSpecification widgetPageSpecification =
			(WidgetPageSpecification)pageSpecifications[0];

		return new PageSpecification[] {
			getWidgetPageSpecification(
				widgetPageSpecification.getExternalReferenceCode(), null,
				PageSpecification.Status.APPROVED)
		};
	}

	public static PageSpecification[] getPostPageSpecificationsWithCustomFields(
		String publishedPageSpecificationExternalReferenceCode,
		PageSpecification.Type type) {

		PageSpecification[] pageSpecifications;

		if (type == PageSpecification.Type.CONTENT_PAGE_SPECIFICATION) {
			pageSpecifications = _getContentPageSpecifications(
				RandomTestUtil.randomString(),
				publishedPageSpecificationExternalReferenceCode);
		}
		else {
			pageSpecifications = new PageSpecification[] {
				getWidgetPageSpecification(
					publishedPageSpecificationExternalReferenceCode, null,
					PageSpecification.Status.APPROVED)
			};
		}

		pageSpecifications[0].setCustomFields(
			new CustomField[] {
				_getCustomField(
					_EXPANDO_ATTRIBUTE_NAMES[0], RandomTestUtil.randomString()),
				_getCustomField(
					_EXPANDO_ATTRIBUTE_NAMES[2], RandomTestUtil.randomString())
			});

		if (type == PageSpecification.Type.CONTENT_PAGE_SPECIFICATION) {
			pageSpecifications[1].setCustomFields(
				new CustomField[] {
					_getCustomField(
						_EXPANDO_ATTRIBUTE_NAMES[0],
						RandomTestUtil.randomString()),
					_getCustomField(
						_EXPANDO_ATTRIBUTE_NAMES[2],
						RandomTestUtil.randomString())
				});
		}

		return pageSpecifications;
	}

	public static CustomField[][] getUpdateCustomFields(
		PageSpecification.Type type) {

		CustomField[] publishedCustomFields = {
			_getCustomField(_EXPANDO_ATTRIBUTE_NAMES[0], (String)null),
			_getCustomField(
				_EXPANDO_ATTRIBUTE_NAMES[1], RandomTestUtil.randomString())
		};

		CustomField[] draftCustomFields = null;

		if (type == PageSpecification.Type.CONTENT_PAGE_SPECIFICATION) {
			draftCustomFields = new CustomField[] {
				_getCustomField(_EXPANDO_ATTRIBUTE_NAMES[0], (String)null),
				_getCustomField(
					_EXPANDO_ATTRIBUTE_NAMES[1], RandomTestUtil.randomString())
			};
		}

		return new CustomField[][] {publishedCustomFields, draftCustomFields};
	}

	public static WidgetPageSpecification getWidgetPageSpecification(
		String externalReferenceCode, Settings settings,
		PageSpecification.Status status) {

		WidgetPageSpecification widgetPageSpecification =
			new WidgetPageSpecification() {
				{
					setType(() -> Type.WIDGET_PAGE_SPECIFICATION);
				}
			};

		widgetPageSpecification.setExternalReferenceCode(externalReferenceCode);
		widgetPageSpecification.setSettings(settings);
		widgetPageSpecification.setStatus(status);

		return widgetPageSpecification;
	}

	public static void testPostSiteSiteByExternalReferenceCodePageSpecification(
			Layout layout, PageSpecification[] pageSpecifications,
			ServiceContext serviceContext,
			UnsafeFunction
				<ContentPageSpecification, ContentPageSpecification, Exception>
					unsafeFunction)
		throws Exception {

		assertPageSpecifications(layout, pageSpecifications);

		Layout draftLayout = layout.fetchDraftLayout();

		Assert.assertFalse(_isPublished(draftLayout));

		assertPageSpecifications(layout, pageSpecifications);

		ContentPageSpecification publishedContentPageSpecification =
			(ContentPageSpecification)pageSpecifications[0];

		Assert.assertEquals(
			publishedContentPageSpecification.getStatus(),
			PageSpecification.Status.DRAFT);

		publishedContentPageSpecification.setExternalReferenceCode(
			layout.getExternalReferenceCode());

		_assertProblemException(
			() -> unsafeFunction.apply(publishedContentPageSpecification));

		Assert.assertEquals(
			draftLayout.getStatus(), WorkflowConstants.STATUS_APPROVED);

		ContentPageSpecification draftContentPageSpecification =
			(ContentPageSpecification)pageSpecifications[1];

		draftContentPageSpecification.setExternalReferenceCode(
			draftLayout.getExternalReferenceCode());
		draftContentPageSpecification.setStatus(PageSpecification.Status.DRAFT);

		assertContentPageSpecification(
			unsafeFunction.apply(draftContentPageSpecification),
			draftLayout.getPlid());

		draftLayout = LayoutLocalServiceUtil.getLayout(draftLayout.getPlid());

		Assert.assertEquals(
			draftLayout.getStatus(), WorkflowConstants.STATUS_DRAFT);

		_assertProblemException(
			() -> unsafeFunction.apply(draftContentPageSpecification));

		ContentLayoutTestUtil.publishLayout(layout.fetchDraftLayout(), layout);

		draftLayout = layout.fetchDraftLayout();

		Assert.assertEquals(
			draftLayout.getStatus(), WorkflowConstants.STATUS_APPROVED);

		publishedContentPageSpecification.setExternalReferenceCode(
			draftLayout.getExternalReferenceCode());

		publishedContentPageSpecification.setStatus(
			PageSpecification.Status.APPROVED);

		_assertProblemException(
			() -> unsafeFunction.apply(publishedContentPageSpecification));

		publishedContentPageSpecification.setExternalReferenceCode(
			layout.getExternalReferenceCode());

		_assertProblemException(
			() -> unsafeFunction.apply(publishedContentPageSpecification));

		draftContentPageSpecification.setExternalReferenceCode(
			draftLayout.getExternalReferenceCode());
		draftContentPageSpecification.setStatus(PageSpecification.Status.DRAFT);

		draftLayout = LayoutLocalServiceUtil.getLayout(draftLayout.getPlid());

		Assert.assertEquals(
			draftLayout.getStatus(), WorkflowConstants.STATUS_APPROVED);

		assertContentPageSpecification(
			unsafeFunction.apply(draftContentPageSpecification),
			draftLayout.getPlid());

		_assertProblemException(
			() -> unsafeFunction.apply(draftContentPageSpecification));

		draftLayout = LayoutLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), draftLayout.getPlid(),
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		draftContentPageSpecification.setExternalReferenceCode((String)null);
		draftContentPageSpecification.setStatus((PageSpecification.Status)null);

		assertContentPageSpecification(
			unsafeFunction.apply(draftContentPageSpecification),
			draftLayout.getPlid());

		draftLayout = LayoutLocalServiceUtil.getLayout(draftLayout.getPlid());

		Assert.assertEquals(
			draftLayout.getStatus(), WorkflowConstants.STATUS_DRAFT);

		_assertProblemException(
			() -> unsafeFunction.apply(draftContentPageSpecification));
	}

	public static class ExpandoTableAutocloseable implements AutoCloseable {

		public ExpandoTableAutocloseable() {
			_originalPermissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			try {
				PermissionThreadLocal.setPermissionChecker(
					PermissionCheckerFactoryUtil.create(
						TestPropsValues.getUser()));

				_expandoTable = ExpandoTableLocalServiceUtil.addDefaultTable(
					PortalUtil.getDefaultCompanyId(), Layout.class.getName());

				for (int i = 0; i < _EXPANDO_ATTRIBUTE_NAMES.length; i++) {
					ExpandoColumnLocalServiceUtil.addColumn(
						_expandoTable.getTableId(), _EXPANDO_ATTRIBUTE_NAMES[i],
						ExpandoColumnConstants.STRING,
						_EXPANDO_ATTRIBUTE_DEFAULT_VALUES[i]);
				}
			}
			catch (PortalException portalException) {
				throw new RuntimeException(portalException);
			}
		}

		@Override
		public void close() {
			try {
				if (_expandoTable != null) {
					ExpandoTableLocalServiceUtil.deleteTable(_expandoTable);
				}
			}
			catch (PortalException portalException) {
				throw new RuntimeException(portalException);
			}
			finally {
				PermissionThreadLocal.setPermissionChecker(
					_originalPermissionChecker);
			}
		}

		private final ExpandoTable _expandoTable;
		private final PermissionChecker _originalPermissionChecker;

	}

	private static void _assertCustomFields(
			CustomField[] expectedCustomFields, long groupId,
			PageSpecification pageSpecification)
		throws Exception {

		CustomField[] customFields = pageSpecification.getCustomFields();

		Assert.assertTrue(
			Arrays.toString(customFields) +
				" does not contain all custom fields in " +
					Arrays.toString(expectedCustomFields),
			ArrayUtil.containsAll(customFields, expectedCustomFields));

		Layout layout = LayoutLocalServiceUtil.getLayoutByExternalReferenceCode(
			pageSpecification.getExternalReferenceCode(), groupId);

		ExpandoBridge expandoBridge = layout.getExpandoBridge();

		Map<String, Serializable> attributes = expandoBridge.getAttributes();

		Assert.assertFalse(attributes.isEmpty());

		for (CustomField customField : expectedCustomFields) {
			CustomValue customValue = customField.getCustomValue();

			Assert.assertEquals(
				customValue.getData(), attributes.get(customField.getName()));
		}
	}

	private static void _assertProblemException(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try {
			unsafeRunnable.run();
			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}
	}

	private static ContentPageSpecification[] _getContentPageSpecifications(
		String draftPageSpecificationExternalReferenceCode,
		String publishedPageSpecificationExternalReferenceCode) {

		ContentPageSpecification draftContentPageSpecification =
			getContentPageSpecification(
				draftPageSpecificationExternalReferenceCode, null,
				PageSpecification.Status.DRAFT);

		ContentPageSpecification publishedContentPageSpecification =
			getContentPageSpecification(
				publishedPageSpecificationExternalReferenceCode,
				draftContentPageSpecification.getExternalReferenceCode(),
				PageSpecification.Status.APPROVED);

		return new ContentPageSpecification[] {
			publishedContentPageSpecification, draftContentPageSpecification
		};
	}

	private static CustomField _getCustomField(
		String attributeName, CustomField[] customFields) {

		CustomField[] filteredCustomFields = ArrayUtil.filter(
			customFields,
			customField -> Objects.equals(
				customField.getName(), attributeName));

		return filteredCustomFields[0];
	}

	private static CustomField _getCustomField(String curName, String curData) {
		return new CustomField() {
			{
				customValue = new CustomValue() {
					{
						data = curData;
						dataType = "Text";
					}
				};
				name = curName;
			}
		};
	}

	private static boolean _isPublished(Layout draftLayout) {
		return GetterUtil.getBoolean(
			draftLayout.getTypeSettingsProperty(
				LayoutTypeSettingsConstants.KEY_PUBLISHED));
	}

	private static final String[] _EXPANDO_ATTRIBUTE_DEFAULT_VALUES = {
		RandomTestUtil.randomString(), RandomTestUtil.randomString(), null
	};

	private static final String[] _EXPANDO_ATTRIBUTE_NAMES = {
		RandomTestUtil.randomString(), RandomTestUtil.randomString(),
		RandomTestUtil.randomString()
	};

}