/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test.util;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalServiceUtil;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.contributor.util.FragmentCollectionContributorRegistryUtil;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.util.FragmentRendererRegistryUtil;
import com.liferay.fragment.service.FragmentCollectionLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.headless.admin.site.client.dto.v1_0.BackgroundImageValue;
import com.liferay.headless.admin.site.client.dto.v1_0.BasicFragmentInstancePageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.ClassNameReference;
import com.liferay.headless.admin.site.client.dto.v1_0.CollectionDisplayListStyle;
import com.liferay.headless.admin.site.client.dto.v1_0.CollectionDisplayPageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.CollectionDisplayViewport;
import com.liferay.headless.admin.site.client.dto.v1_0.CollectionDisplayViewportDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.CollectionItemExternalReference;
import com.liferay.headless.admin.site.client.dto.v1_0.CollectionItemPageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.CollectionReference;
import com.liferay.headless.admin.site.client.dto.v1_0.CollectionSettings;
import com.liferay.headless.admin.site.client.dto.v1_0.ContainerPageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.ContentPageSpecification;
import com.liferay.headless.admin.site.client.dto.v1_0.DefaultFragmentReference;
import com.liferay.headless.admin.site.client.dto.v1_0.DropZonePageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.FormContainerPageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.FormStepContainerPageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.FormStepPageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentDropZonePageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentEditableElement;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentEditableElementValue;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentInstance;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentItemExternalReference;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentLinkTextValue;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentMappedValue;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentMappedValueItemContextReference;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentReference;
import com.liferay.headless.admin.site.client.dto.v1_0.GridPageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.GridViewport;
import com.liferay.headless.admin.site.client.dto.v1_0.GridViewportDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.Mapping;
import com.liferay.headless.admin.site.client.dto.v1_0.ModulePageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.ModuleViewport;
import com.liferay.headless.admin.site.client.dto.v1_0.ModuleViewportDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.client.dto.v1_0.PageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.PageExperience;
import com.liferay.headless.admin.site.client.dto.v1_0.PageSpecification;
import com.liferay.headless.admin.site.client.dto.v1_0.TemplateListStyle;
import com.liferay.headless.admin.site.client.dto.v1_0.TextFragmentEditableElementValue;
import com.liferay.headless.admin.site.client.dto.v1_0.TextFragmentMappedValue;
import com.liferay.headless.admin.site.client.dto.v1_0.TextFragmentValue;
import com.liferay.headless.admin.site.client.dto.v1_0.WidgetInstance;
import com.liferay.headless.admin.site.client.dto.v1_0.WidgetInstancePageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.WidgetPermission;
import com.liferay.headless.admin.site.client.scope.Scope;
import com.liferay.journal.model.JournalArticle;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.service.TemplateEntryLocalServiceUtil;
import com.liferay.template.test.util.TemplateTestUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;

/**
 * @author Lourdes Fernández Besada
 */
public class PageElementsTestUtil {

	public static FragmentEntry addCompanyGroupFragmentEntryWithTextEditable()
		throws PortalException {

		Company company = CompanyLocalServiceUtil.getCompany(
			TestPropsValues.getCompanyId());

		FragmentCollection fragmentCollection =
			FragmentCollectionLocalServiceUtil.addFragmentCollection(
				null, TestPropsValues.getUserId(), company.getGroupId(),
				StringUtil.randomString(), StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(company.getGroupId()));

		return _addFragmentEntry(
			fragmentCollection.getFragmentCollectionId(), company.getGroupId(),
			"<div data-lfr-editable-id=\"element-text\" " +
				"data-lfr-editable-type=\"text\">Default text</div>");
	}

	public static void assertFieldKeysWithTemplateEntries(
			PageSpecification[] externalPageSpecifications,
			PageSpecification[] internalPageSpecifications)
		throws PortalException {

		Assert.assertEquals(
			Arrays.toString(externalPageSpecifications), 2,
			externalPageSpecifications.length);
		Assert.assertEquals(
			Arrays.toString(internalPageSpecifications),
			externalPageSpecifications.length,
			internalPageSpecifications.length);

		ContentPageSpecification externalPublishedPageSpecification =
			(ContentPageSpecification)externalPageSpecifications[0];
		ContentPageSpecification internalPublishedPageSpecification =
			(ContentPageSpecification)internalPageSpecifications[0];

		_assertFieldKeysWithTemplateEntries(
			externalPublishedPageSpecification.getPageExperiences()[0],
			internalPublishedPageSpecification.getPageExperiences()[0]);

		ContentPageSpecification externalDraftPageSpecification =
			(ContentPageSpecification)externalPageSpecifications[1];
		ContentPageSpecification internalDraftPageSpecification =
			(ContentPageSpecification)internalPageSpecifications[1];

		_assertFieldKeysWithTemplateEntries(
			externalDraftPageSpecification.getPageExperiences()[0],
			internalDraftPageSpecification.getPageExperiences()[0]);
	}

	public static void assertRenderedLayoutHTMLWithTemplateEntries(
		String renderLayoutHTML) {

		Assert.assertNotNull(renderLayoutHTML);

		Assert.assertTrue(
			renderLayoutHTML,
			renderLayoutHTML.contains("companyGroupTemplateEntry1"));
		Assert.assertTrue(
			renderLayoutHTML,
			renderLayoutHTML.contains("companyGroupTemplateEntry2"));
		Assert.assertTrue(
			renderLayoutHTML,
			renderLayoutHTML.contains("companyGroupTemplateEntry3"));
		Assert.assertTrue(
			renderLayoutHTML,
			renderLayoutHTML.contains("companyGroupTemplateEntry4"));
		Assert.assertTrue(
			renderLayoutHTML,
			renderLayoutHTML.contains("companyGroupTemplateEntry5"));
		Assert.assertTrue(
			renderLayoutHTML,
			renderLayoutHTML.contains("companyGroupTemplateEntry6"));
		Assert.assertTrue(
			renderLayoutHTML,
			renderLayoutHTML.contains("scopeGroupTemplateEntry1"));
		Assert.assertTrue(
			renderLayoutHTML,
			renderLayoutHTML.contains("scopeGroupTemplateEntry2"));
		Assert.assertTrue(
			renderLayoutHTML,
			renderLayoutHTML.contains("scopeGroupTemplateEntry3"));
		Assert.assertTrue(
			renderLayoutHTML,
			renderLayoutHTML.contains("scopeGroupTemplateEntry4"));
		Assert.assertTrue(
			renderLayoutHTML,
			renderLayoutHTML.contains("scopeGroupTemplateEntry5"));
		Assert.assertTrue(
			renderLayoutHTML,
			renderLayoutHTML.contains("scopeGroupTemplateEntry6"));
	}

	public static BasicFragmentInstancePageElementDefinition
		getBasicFragmentInstancePageElementDefinition(
			BackgroundImageValue backgroundImageValue,
			Map<String, Object> configurationValuesMap,
			FragmentEditableElement[] fragmentEditableElements,
			FragmentEntry fragmentEntry, Boolean hidden, long scopeGroupId) {

		return getBasicFragmentInstancePageElementDefinition(
			backgroundImageValue, configurationValuesMap,
			fragmentEditableElements, fragmentEntry,
			RandomTestUtil.randomString(), hidden,
			RandomTestUtil.randomString(), scopeGroupId,
			RandomTestUtil.randomString(), null);
	}

	public static BasicFragmentInstancePageElementDefinition
		getBasicFragmentInstancePageElementDefinition(
			BackgroundImageValue backgroundImageValue,
			Map<String, Object> configurationValuesMap,
			FragmentEditableElement[] fragmentEditableElements,
			FragmentEntry fragmentEntry, long scopeGroupId) {

		return getBasicFragmentInstancePageElementDefinition(
			backgroundImageValue, configurationValuesMap,
			fragmentEditableElements, fragmentEntry,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			scopeGroupId, RandomTestUtil.randomString(), null);
	}

	public static BasicFragmentInstancePageElementDefinition
		getBasicFragmentInstancePageElementDefinition(
			BackgroundImageValue backgroundImageValue,
			Map<String, Object> configurationValuesMap,
			FragmentEditableElement[] fragmentEditableElements,
			FragmentEntry fragmentEntry,
			String fragmentInstanceExternalReferenceCode, Boolean hidden,
			String namespace, long scopeGroupId, String uuid,
			WidgetInstance[] widgetInstances) {

		return new BasicFragmentInstancePageElementDefinition() {
			{
				setFragmentInstance(
					_getFragmentInstance(
						configurationValuesMap, fragmentEditableElements,
						fragmentEntry, backgroundImageValue,
						fragmentInstanceExternalReferenceCode, hidden,
						namespace, scopeGroupId, uuid, widgetInstances));
				setType(() -> Type.BASIC_FRAGMENT);
			}
		};
	}

	public static BasicFragmentInstancePageElementDefinition
		getBasicFragmentInstancePageElementDefinition(
			BackgroundImageValue backgroundImageValue,
			Map<String, Object> configurationValuesMap,
			FragmentEditableElement[] fragmentEditableElements,
			FragmentEntry fragmentEntry,
			String fragmentInstanceExternalReferenceCode, String namespace,
			long scopeGroupId, String uuid, WidgetInstance[] widgetInstances) {

		return getBasicFragmentInstancePageElementDefinition(
			backgroundImageValue, configurationValuesMap,
			fragmentEditableElements, fragmentEntry,
			fragmentInstanceExternalReferenceCode,
			RandomTestUtil.randomBoolean(), namespace, scopeGroupId, uuid,
			widgetInstances);
	}

	public static BasicFragmentInstancePageElementDefinition
		getBasicFragmentInstancePageElementDefinition(
			BackgroundImageValue backgroundImageValue,
			Map<String, Object> configurationValuesMap,
			FragmentEditableElement[] fragmentEditableElements,
			FragmentRenderer fragmentRenderer, long scopeGroupId) {

		return new BasicFragmentInstancePageElementDefinition() {
			{
				setFragmentInstance(
					_getFragmentInstance(
						configurationValuesMap, fragmentEditableElements,
						backgroundImageValue, fragmentRenderer, scopeGroupId));
				setType(() -> Type.BASIC_FRAGMENT);
			}
		};
	}

	public static BasicFragmentInstancePageElementDefinition
		getBasicFragmentInstancePageElementDefinition(
			BackgroundImageValue backgroundImageValue,
			Map<String, Object> configurationValuesMap, String key,
			FragmentEditableElement[] fragmentEditableElements, Boolean hidden,
			long scopeGroupId) {

		FragmentEntry fragmentEntry =
			FragmentCollectionContributorRegistryUtil.getFragmentEntry(key);

		if (fragmentEntry == null) {
			Company company = null;

			try {
				company = CompanyLocalServiceUtil.getCompany(
					TestPropsValues.getCompanyId());
			}
			catch (Exception exception) {
			}

			if (company != null) {
				fragmentEntry =
					FragmentEntryLocalServiceUtil.fetchFragmentEntry(
						company.getGroupId(), key);
			}
		}

		if (fragmentEntry != null) {
			return getBasicFragmentInstancePageElementDefinition(
				backgroundImageValue, configurationValuesMap,
				fragmentEditableElements, fragmentEntry, hidden, scopeGroupId);
		}

		FragmentRenderer fragmentRenderer =
			FragmentRendererRegistryUtil.getFragmentRenderer(key);

		if (fragmentRenderer != null) {
			return getBasicFragmentInstancePageElementDefinition(
				backgroundImageValue, configurationValuesMap,
				fragmentEditableElements, fragmentRenderer, scopeGroupId);
		}

		return null;
	}

	public static BasicFragmentInstancePageElementDefinition
		getBasicFragmentInstancePageElementDefinition(
			BackgroundImageValue backgroundImageValue,
			Map<String, Object> configurationValuesMap, String key,
			long scopeGroupId) {

		return getBasicFragmentInstancePageElementDefinition(
			backgroundImageValue, configurationValuesMap, key,
			new FragmentEditableElement[0], RandomTestUtil.randomBoolean(),
			scopeGroupId);
	}

	public static PageElement getDropZonePageElement(
			String externalReferenceCode, long groupId)
		throws PortalException {

		DropZonePageElementDefinition dropZonePageElementDefinition =
			new DropZonePageElementDefinition();

		dropZonePageElementDefinition.setAddNewFragmentEntries(true);
		dropZonePageElementDefinition.setAllowedFragmentReferences(
			_getFragmentReferences(groupId));
		dropZonePageElementDefinition.setType(
			PageElementDefinition.Type.DROP_ZONE);

		return _getPageElement(
			externalReferenceCode, dropZonePageElementDefinition);
	}

	public static PageElementDefinition getPageElementDefinition(
		Boolean curHidden, PageElementDefinition.Type type, long scopeGroupId) {

		if (Objects.equals(
				type, PageElementDefinition.Type.COLLECTION_DISPLAY)) {

			ClassNameReference classNameReference = new ClassNameReference();

			classNameReference.setClassName(
				"com.liferay.asset.internal.info.collection.provider." +
					"RecentContentInfoCollectionProvider");
			classNameReference.setCollectionType(
				CollectionReference.CollectionType.COLLECTION_PROVIDER);

			return new CollectionDisplayPageElementDefinition() {
				{
					setCollectionDisplayListStyle(
						_getCollectionDisplayListStyle());
					setCollectionDisplayViewports(
						new CollectionDisplayViewport[] {
							new CollectionDisplayViewport() {
								{
									setCollectionDisplayViewportDefinition(
										() ->
											new CollectionDisplayViewportDefinition() {
												{
													setHidden(curHidden);
													setNumberOfColumns(1);
												}
											});
									setId(Id.DESKTOP);
								}
							}
						});
					setCollectionSettings(
						() -> new CollectionSettings() {
							{
								setCollectionReference(
									() -> classNameReference);
							}
						});
					setDisplayAllItems(Boolean.FALSE);
					setDisplayAllPages(Boolean.TRUE);
					setNumberOfItems(5);
					setNumberOfItemsPerPage(5);
					setNumberOfPages(20);
					setPaginationType(PaginationType.NONE);
					setType(Type.COLLECTION_DISPLAY);
				}
			};
		}

		if (Objects.equals(type, PageElementDefinition.Type.COLLECTION_ITEM)) {
			return new CollectionItemPageElementDefinition() {
				{
					setType(Type.COLLECTION_ITEM);
				}
			};
		}

		if (Objects.equals(type, PageElementDefinition.Type.CONTAINER)) {
			return new ContainerPageElementDefinition() {
				{
					setContentVisibility(ContentVisibility.AUTO);
					setIndexed(Boolean.FALSE);
					setType(Type.CONTAINER);
				}
			};
		}

		if (Objects.equals(type, PageElementDefinition.Type.DROP_ZONE)) {
			return new DropZonePageElementDefinition() {
				{
					setType(Type.DROP_ZONE);
				}
			};
		}

		if (Objects.equals(type, PageElementDefinition.Type.FORM_CONTAINER)) {
			return new FormContainerPageElementDefinition() {
				{
					setIndexed(Boolean.TRUE);
					setType(Type.FORM_CONTAINER);
				}
			};
		}

		if (Objects.equals(type, PageElementDefinition.Type.FORM_STEP)) {
			return new FormStepPageElementDefinition() {
				{
					setType(Type.FORM_STEP);
				}
			};
		}

		if (Objects.equals(
				type, PageElementDefinition.Type.FORM_STEP_CONTAINER)) {

			return new FormStepContainerPageElementDefinition() {
				{
					setType(Type.FORM_STEP_CONTAINER);
				}
			};
		}

		if (Objects.equals(type, PageElementDefinition.Type.BASIC_FRAGMENT)) {
			return getBasicFragmentInstancePageElementDefinition(
				null, Collections.emptyMap(), "BASIC_COMPONENT-heading",
				scopeGroupId);
		}

		if (Objects.equals(
				type, PageElementDefinition.Type.FRAGMENT_DROP_ZONE)) {

			return new FragmentDropZonePageElementDefinition() {
				{
					setType(Type.FRAGMENT_DROP_ZONE);
				}
			};
		}

		if (Objects.equals(type, PageElementDefinition.Type.MODULE)) {
			return new ModulePageElementDefinition() {
				{
					setType(Type.MODULE);
				}
			};
		}

		if (Objects.equals(type, PageElementDefinition.Type.GRID)) {
			return new GridPageElementDefinition() {
				{
					setGutters(Boolean.TRUE);
					setIndexed(Boolean.TRUE);
					setNumberOfModules(0);
					setReverseOrder(Boolean.FALSE);
					setType(Type.GRID);
				}
			};
		}

		if (Objects.equals(type, PageElementDefinition.Type.WIDGET)) {
			return new WidgetInstancePageElementDefinition() {
				{
					setIndexed(true);
					setName(RandomTestUtil.randomString());
					setType(PageElementDefinition.Type.WIDGET);
					setWidgetInstance(PageElementsTestUtil::_getWidgetInstance);
					setWidgetInstanceExternalReferenceCode(
						RandomTestUtil.randomString());
				}
			};
		}

		return null;
	}

	public static PageElementDefinition getPageElementDefinition(
		PageElementDefinition.Type type, long scopeGroupId) {

		return getPageElementDefinition(
			RandomTestUtil.randomBoolean(), type, scopeGroupId);
	}

	public static PageElement[] getPageElements(
		int count, String parentExternalReferenceCode, long scopeGroupId) {

		PageElement[] pageElements = new PageElement[count];

		for (int i = 0; i < count; i++) {
			PageElement pageElement = new PageElement();

			pageElement.setExternalReferenceCode(RandomTestUtil::randomString);
			pageElement.setPageElementDefinition(
				getPageElementDefinition(_getRandomType(), scopeGroupId));
			pageElement.setPosition(i);

			if (_isParentablePageElementDefinitionType(
					pageElement.getPageElementDefinition()) &&
				RandomTestUtil.randomBoolean()) {

				pageElement.setPageElements(
					getPageElements(
						RandomTestUtil.randomInt(1, 2),
						pageElement.getExternalReferenceCode(), scopeGroupId));
			}
			else {
				pageElement.setPageElements(new PageElement[0]);
			}

			pageElement.setParentExternalReferenceCode(
				parentExternalReferenceCode);

			pageElements[i] = pageElement;
		}

		return pageElements;
	}

	public static PageElement[] getPageElements(long scopeGroupId) {
		List<PageElement> pageElements = new ArrayList<>();

		int position = 0;

		pageElements.add(
			_getCollectionDisplayPageElement(position++, scopeGroupId));
		pageElements.add(
			_getPageElement(
				getPageElementDefinition(
					PageElementDefinition.Type.CONTAINER, scopeGroupId),
				StringPool.BLANK, position++));
		pageElements.add(_getGridPageElement(position++));

		pageElements.add(
			_getPageElement(
				getPageElementDefinition(
					PageElementDefinition.Type.WIDGET, scopeGroupId),
				StringPool.BLANK, position));

		return pageElements.toArray(new PageElement[0]);
	}

	public static PageElement[] getPageElementsWithTemplateEntries(
			String fragmentKey, JournalArticle journalArticle,
			int layoutPageTemplateEntryType, long scopeGroupId)
		throws Exception {

		List<PageElement> pageElements = new ArrayList<>();

		Company company = CompanyLocalServiceUtil.getCompany(
			TestPropsValues.getCompanyId());

		ServiceContext companyGroupServiceContext =
			ServiceContextTestUtil.getServiceContext(company.getGroupId());

		ServiceContext scopeGroupServiceContext =
			ServiceContextTestUtil.getServiceContext(scopeGroupId);

		int position = 0;

		pageElements.add(
			_getBasicFragmentPageElement(
				_getExternalGlobalGroupTemplateFieldKey(
					_getTemplateEntry(
						journalArticle, "companyGroupTemplateEntry1",
						companyGroupServiceContext)),
				fragmentKey, journalArticle, position++, scopeGroupId));
		pageElements.add(
			_getBasicFragmentPageElement(
				_getExternalScopeGroupTemplateFieldKey(
					_getTemplateEntry(
						journalArticle, "scopeGroupTemplateEntry1",
						scopeGroupServiceContext)),
				fragmentKey, journalArticle, position++, scopeGroupId));
		pageElements.add(
			_getBasicFragmentPageElement(
				_getInternalTemplateFieldKey(
					_getTemplateEntry(
						journalArticle, "companyGroupTemplateEntry2",
						companyGroupServiceContext)),
				fragmentKey, journalArticle, position++, scopeGroupId));
		pageElements.add(
			_getBasicFragmentPageElement(
				_getInternalTemplateFieldKey(
					_getTemplateEntry(
						journalArticle, "scopeGroupTemplateEntry2",
						scopeGroupServiceContext)),
				fragmentKey, journalArticle, position++, scopeGroupId));
		pageElements.add(
			_getCollectionDisplayPageElement(
				_getExternalGlobalGroupTemplateFieldKey(
					_getTemplateEntry(
						journalArticle, "companyGroupTemplateEntry3",
						companyGroupServiceContext)),
				fragmentKey, journalArticle, position++, scopeGroupId));
		pageElements.add(
			_getCollectionDisplayPageElement(
				_getExternalScopeGroupTemplateFieldKey(
					_getTemplateEntry(
						journalArticle, "scopeGroupTemplateEntry3",
						scopeGroupServiceContext)),
				fragmentKey, journalArticle, position++, scopeGroupId));
		pageElements.add(
			_getCollectionDisplayPageElement(
				_getInternalTemplateFieldKey(
					_getTemplateEntry(
						journalArticle, "companyGroupTemplateEntry4",
						companyGroupServiceContext)),
				fragmentKey, journalArticle, position++, scopeGroupId));
		pageElements.add(
			_getCollectionDisplayPageElement(
				_getInternalTemplateFieldKey(
					_getTemplateEntry(
						journalArticle, "scopeGroupTemplateEntry4",
						scopeGroupServiceContext)),
				fragmentKey, journalArticle, position++, scopeGroupId));

		if (layoutPageTemplateEntryType ==
				LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE) {

			pageElements.add(
				_getDisplayPageItemPageElement(
					_getExternalGlobalGroupTemplateFieldKey(
						_getTemplateEntry(
							journalArticle, "companyGroupTemplateEntry5",
							companyGroupServiceContext)),
					fragmentKey, position++, scopeGroupId));
			pageElements.add(
				_getDisplayPageItemPageElement(
					_getExternalScopeGroupTemplateFieldKey(
						_getTemplateEntry(
							journalArticle, "scopeGroupTemplateEntry5",
							scopeGroupServiceContext)),
					fragmentKey, position++, scopeGroupId));
			pageElements.add(
				_getDisplayPageItemPageElement(
					_getInternalTemplateFieldKey(
						_getTemplateEntry(
							journalArticle, "companyGroupTemplateEntry6",
							companyGroupServiceContext)),
					fragmentKey, position++, scopeGroupId));
			pageElements.add(
				_getDisplayPageItemPageElement(
					_getInternalTemplateFieldKey(
						_getTemplateEntry(
							journalArticle, "scopeGroupTemplateEntry6",
							scopeGroupServiceContext)),
					fragmentKey, position, scopeGroupId));
		}

		return pageElements.toArray(new PageElement[0]);
	}

	private static AssetListEntry _addAssetListEntry(
			long groupId, JournalArticle journalArticle)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		AssetListEntry assetListEntry =
			AssetListEntryLocalServiceUtil.addAssetListEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				groupId, RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_MANUAL, serviceContext);

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				JournalArticle.class.getName());

		AssetEntry assetEntry = assetRendererFactory.getAssetEntry(
			JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey());

		AssetListEntryLocalServiceUtil.addAssetEntrySelections(
			assetListEntry.getAssetListEntryId(),
			new long[] {assetEntry.getEntryId()},
			SegmentsEntryConstants.ID_DEFAULT, serviceContext);

		return assetListEntry;
	}

	private static DefaultFragmentReference _addDefaultFragmentReference(
		String fragmentEntryKey) {

		DefaultFragmentReference defaultFragmentReference =
			new DefaultFragmentReference();

		defaultFragmentReference.setDefaultFragmentKey(
			() -> {
				FragmentEntry fragmentEntry =
					FragmentCollectionContributorRegistryUtil.getFragmentEntry(
						fragmentEntryKey);

				return fragmentEntry.getFragmentEntryKey();
			});
		defaultFragmentReference.setFragmentReferenceType(
			FragmentReference.FragmentReferenceType.DEFAULT_FRAGMENT_REFERENCE);

		return defaultFragmentReference;
	}

	private static FragmentEntry _addFragmentEntry(
			long fragmentCollectionId, long groupId)
		throws PortalException {

		return _addFragmentEntry(
			fragmentCollectionId, groupId, "Fragment Entry HTML");
	}

	private static FragmentEntry _addFragmentEntry(
			long fragmentCollectionId, long groupId, String html)
		throws PortalException {

		return FragmentEntryLocalServiceUtil.addFragmentEntry(
			null, TestPropsValues.getUserId(), groupId, fragmentCollectionId,
			null, RandomTestUtil.randomString(), StringPool.BLANK, html,
			StringPool.BLANK, false, null, null, 0, false, false,
			FragmentConstants.TYPE_COMPONENT, null,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(groupId));
	}

	private static FragmentItemExternalReference
		_addFragmentItemExternalReference(
			FragmentEntry fragmentEntry, Scope scope) {

		FragmentItemExternalReference fragmentItemExternalReference =
			new FragmentItemExternalReference();

		fragmentItemExternalReference.setExternalReferenceCode(
			fragmentEntry.getExternalReferenceCode());
		fragmentItemExternalReference.setFragmentReferenceType(
			FragmentReference.FragmentReferenceType.
				FRAGMENT_ITEM_EXTERNAL_REFERENCE);
		fragmentItemExternalReference.setScope(scope);

		return fragmentItemExternalReference;
	}

	private static void _assertFieldKeysWithTemplateEntries(
			PageElement externalPageElement, PageElement internalPageElement)
		throws PortalException {

		PageElementDefinition externalPageElementDefinition =
			externalPageElement.getPageElementDefinition();

		if (externalPageElementDefinition instanceof
				BasicFragmentInstancePageElementDefinition) {

			PageElementDefinition internalPageElementDefinition =
				internalPageElement.getPageElementDefinition();

			BasicFragmentInstancePageElementDefinition
				externalBasicFragmentInstancePageElementDefinition =
					(BasicFragmentInstancePageElementDefinition)
						externalPageElementDefinition;
			BasicFragmentInstancePageElementDefinition
				internalBasicFragmentInstancePageElementDefinition =
					(BasicFragmentInstancePageElementDefinition)
						internalPageElementDefinition;

			FragmentInstance externalFragmentInstance =
				externalBasicFragmentInstancePageElementDefinition.
					getFragmentInstance();
			FragmentInstance internalFragmentInstance =
				internalBasicFragmentInstancePageElementDefinition.
					getFragmentInstance();

			FragmentEditableElement[] externalFragmentEditableElements =
				externalFragmentInstance.getFragmentEditableElements();
			FragmentEditableElement[] internalFragmentEditableElements =
				internalFragmentInstance.getFragmentEditableElements();

			FragmentEditableElementValue externalFragmentEditableElementValue =
				externalFragmentEditableElements[0].
					getFragmentEditableElementValue();
			FragmentEditableElementValue internalFragmentEditableElementValue =
				internalFragmentEditableElements[0].
					getFragmentEditableElementValue();

			Assert.assertTrue(
				externalFragmentEditableElementValue instanceof
					TextFragmentEditableElementValue);
			Assert.assertTrue(
				internalFragmentEditableElementValue instanceof
					TextFragmentEditableElementValue);

			TextFragmentEditableElementValue
				externalTextFragmentEditableElementValue =
					(TextFragmentEditableElementValue)
						externalFragmentEditableElementValue;
			TextFragmentEditableElementValue
				internalTextFragmentEditableElementValue =
					(TextFragmentEditableElementValue)
						internalFragmentEditableElementValue;

			FragmentLinkTextValue externalFragmentLinkTextValue =
				externalTextFragmentEditableElementValue.
					getFragmentLinkTextValue();
			FragmentLinkTextValue internalFragmentLinkTextValue =
				internalTextFragmentEditableElementValue.
					getFragmentLinkTextValue();

			TextFragmentValue externalTextFragmentValue =
				externalFragmentLinkTextValue.getTextFragmentValue();
			TextFragmentValue internalTextFragmentValue =
				internalFragmentLinkTextValue.getTextFragmentValue();

			Assert.assertTrue(
				externalTextFragmentValue instanceof TextFragmentMappedValue);
			Assert.assertTrue(
				internalTextFragmentValue instanceof TextFragmentMappedValue);

			TextFragmentMappedValue externalTextFragmentMappedValue =
				(TextFragmentMappedValue)externalTextFragmentValue;
			TextFragmentMappedValue internalTextFragmentMappedValue =
				(TextFragmentMappedValue)internalTextFragmentValue;

			FragmentMappedValue externalFragmentMappedValue =
				externalTextFragmentMappedValue.getFragmentMappedValue();
			FragmentMappedValue internalFragmentMappedValue =
				internalTextFragmentMappedValue.getFragmentMappedValue();

			Mapping externalMapping = externalFragmentMappedValue.getMapping();
			Mapping internalMapping = internalFragmentMappedValue.getMapping();

			String externalFieldKey = externalMapping.getFieldKey();
			String internalFieldKey = internalMapping.getFieldKey();

			if (internalFieldKey.contains("__ERC__")) {
				Assert.assertEquals(externalFieldKey, internalFieldKey);
			}
			else {
				long templateEntryId = GetterUtil.getLong(
					internalFieldKey.substring(
						"ddmTemplate__ddmTemplate_".length()));

				TemplateEntry templateEntry =
					TemplateEntryLocalServiceUtil.getTemplateEntry(
						templateEntryId);

				Company company = CompanyLocalServiceUtil.getCompany(
					TestPropsValues.getCompanyId());

				if (templateEntry.getGroupId() == company.getGroupId()) {
					Assert.assertEquals(
						externalFieldKey,
						_getExternalGlobalGroupTemplateFieldKey(templateEntry));
				}
				else {
					Assert.assertEquals(
						externalFieldKey,
						_getExternalScopeGroupTemplateFieldKey(templateEntry));
				}
			}
		}

		if (externalPageElementDefinition instanceof
				CollectionDisplayPageElementDefinition ||
			externalPageElementDefinition instanceof
				CollectionItemPageElementDefinition) {

			_assertFieldKeysWithTemplateEntries(
				externalPageElement.getPageElements()[0],
				internalPageElement.getPageElements()[0]);
		}
	}

	private static void _assertFieldKeysWithTemplateEntries(
			PageExperience externalPageExperience,
			PageExperience internalPageExperience)
		throws PortalException {

		PageElement[] externalPageElements =
			externalPageExperience.getPageElements();
		PageElement[] internalPageElements =
			internalPageExperience.getPageElements();

		Assert.assertEquals(
			Arrays.toString(externalPageElements), 12,
			externalPageElements.length);
		Assert.assertEquals(
			Arrays.toString(internalPageElements), externalPageElements.length,
			internalPageElements.length);

		for (int i = 0; i < internalPageElements.length; i++) {
			_assertFieldKeysWithTemplateEntries(
				externalPageElements[i], internalPageElements[i]);
		}
	}

	private static PageElement _getBasicFragmentPageElement(
		String className,
		FragmentMappedValueItemContextReference.ContextSource contextSource,
		String externalReferenceCode, String fieldKey, String fragmentKey,
		String scopeExternalReferenceCode, long scopeGroupId) {

		FragmentEditableElement[] fragmentEditableElements = {
			FragmentEditableElementTestUtil.getTextFragmentEditableElement(
				className, contextSource, externalReferenceCode, fieldKey,
				scopeExternalReferenceCode)
		};

		return _getPageElement(
			RandomTestUtil.randomString(),
			getBasicFragmentInstancePageElementDefinition(
				null, Collections.emptyMap(), fragmentKey,
				fragmentEditableElements, Boolean.FALSE, scopeGroupId));
	}

	private static PageElement _getBasicFragmentPageElement(
		String fieldKey, String fragmentKey, JournalArticle journalArticle,
		int position, long scopeGroupId) {

		PageElement pageElement = _getBasicFragmentPageElement(
			"com.liferay.journal.model.JournalArticle", null,
			journalArticle.getExternalReferenceCode(), fieldKey, fragmentKey,
			"L_GLOBAL", scopeGroupId);

		pageElement.setPosition(position);

		return pageElement;
	}

	private static CollectionDisplayListStyle _getCollectionDisplayListStyle() {
		TemplateListStyle templateListStyle = new TemplateListStyle();

		templateListStyle.setCollectionDisplayListStyleType(
			CollectionDisplayListStyle.CollectionDisplayListStyleType.TEMPLATE);
		templateListStyle.setListItemStyleClassName(
			"com.liferay.asset.internal.info.renderer." +
				"AssetEntryFullContentInfoItemRenderer");
		templateListStyle.setListStyleClassName(
			"com.liferay.asset.info.internal.list.renderer." +
				"NumberedAssetEntryBasicInfoListRenderer");
		templateListStyle.setTemplateKey(RandomTestUtil.randomString());

		return templateListStyle;
	}

	private static PageElement _getCollectionDisplayPageElement(
		int position, long scopeGroupId) {

		PageElement collectionDisplayPageElement = _getPageElement(
			getPageElementDefinition(
				PageElementDefinition.Type.COLLECTION_DISPLAY, scopeGroupId),
			StringPool.BLANK, position);

		collectionDisplayPageElement.setPageElements(
			new PageElement[] {
				_getPageElement(
					getPageElementDefinition(
						PageElementDefinition.Type.COLLECTION_ITEM,
						scopeGroupId),
					collectionDisplayPageElement.getExternalReferenceCode(), 0)
			});

		return collectionDisplayPageElement;
	}

	private static PageElement _getCollectionDisplayPageElement(
			String fieldKey, String fragmentKey, JournalArticle journalArticle,
			int position, long scopeGroupId)
		throws Exception {

		PageElement[] pageElements = {
			_getBasicFragmentPageElement(
				null,
				FragmentMappedValueItemContextReference.ContextSource.
					COLLECTION_ITEM,
				null, fieldKey, fragmentKey, null, scopeGroupId)
		};

		Company company = CompanyLocalServiceUtil.getCompany(
			TestPropsValues.getCompanyId());

		AssetListEntry assetListEntry = _addAssetListEntry(
			company.getGroupId(), journalArticle);

		CollectionItemExternalReference collectionItemExternalReference =
			new CollectionItemExternalReference();

		collectionItemExternalReference.setCollectionType(
			CollectionReference.CollectionType.COLLECTION);
		collectionItemExternalReference.setExternalReferenceCode(
			assetListEntry.getExternalReferenceCode());
		collectionItemExternalReference.setScope(
			new Scope() {
				{
					setExternalReferenceCode("L_GLOBAL");
					setType(Type.SITE);
				}
			});

		PageElement pageElement = _getPageElement(
			_getCollectionDisplayPageElementDefinition(
				collectionItemExternalReference, Boolean.FALSE),
			StringPool.BLANK, position);

		pageElement.setPageElements(
			new PageElement[] {
				_getPageElement(
					RandomTestUtil.randomString(),
					getPageElementDefinition(
						Boolean.FALSE,
						PageElementDefinition.Type.COLLECTION_ITEM,
						scopeGroupId),
					pageElements, pageElement.getExternalReferenceCode(), 0)
			});

		pageElement.setPosition(position);

		return pageElement;
	}

	private static CollectionDisplayPageElementDefinition
		_getCollectionDisplayPageElementDefinition(
			CollectionReference curCollectionReference, Boolean curHidden) {

		return new CollectionDisplayPageElementDefinition() {
			{
				setCollectionDisplayListStyle(_getCollectionDisplayListStyle());
				setCollectionDisplayViewports(
					new CollectionDisplayViewport[] {
						new CollectionDisplayViewport() {
							{
								setCollectionDisplayViewportDefinition(
									new CollectionDisplayViewportDefinition() {
										{
											setHidden(curHidden);
											setNumberOfColumns(1);
										}
									});
								setId(Id.DESKTOP);
							}
						}
					});
				setCollectionSettings(
					new CollectionSettings() {
						{
							setCollectionReference(curCollectionReference);
						}
					});
				setDisplayAllItems(Boolean.FALSE);
				setDisplayAllPages(Boolean.TRUE);
				setNumberOfItems(5);
				setNumberOfItemsPerPage(5);
				setNumberOfPages(20);
				setPaginationType(PaginationType.NONE);
				setType(Type.COLLECTION_DISPLAY);
			}
		};
	}

	private static PageElement _getDisplayPageItemPageElement(
		String fieldKey, String fragmentKey, int position, long scopeGroupId) {

		PageElement pageElement = _getBasicFragmentPageElement(
			null,
			FragmentMappedValueItemContextReference.ContextSource.
				DISPLAY_PAGE_ITEM,
			null, fieldKey, fragmentKey, null, scopeGroupId);

		pageElement.setPosition(position);

		return pageElement;
	}

	private static String _getExternalGlobalGroupTemplateFieldKey(
		TemplateEntry templateEntry) {

		return "ddmTemplate__ddmTemplate__ERC__" +
			templateEntry.getExternalReferenceCode() + "__SERC__L_GLOBAL";
	}

	private static String _getExternalScopeGroupTemplateFieldKey(
		TemplateEntry templateEntry) {

		return "ddmTemplate__ddmTemplate__ERC__" +
			templateEntry.getExternalReferenceCode();
	}

	private static FragmentInstance _getFragmentInstance(
		Map<String, Object> configurationValuesMap,
		FragmentEditableElement[] curFragmentEditableElements,
		BackgroundImageValue fragmentInstanceBackgroundImageValue,
		FragmentRenderer fragmentRenderer, long scopeGroupId) {

		JSONObject configurationJSONObject =
			fragmentRenderer.getConfigurationJSONObject(
				new DefaultFragmentRendererContext(null));

		return new FragmentInstance() {
			{
				setBackgroundImageValue(
					() -> fragmentInstanceBackgroundImageValue);
				setConfiguration(
					() -> GetterUtil.getString(
						JSONFactoryUtil.toString(configurationJSONObject)));
				setCss(() -> StringPool.BLANK);
				setCssClasses(
					() -> new String[] {RandomTestUtil.randomString()});
				setDatePropagated(RandomTestUtil::nextDate);
				setFragmentConfigurationFieldValues(
					() ->
						FragmentConfigurationFieldValueTestUtil.
							getFragmentConfigurationFieldValuesMap(
								configurationJSONObject, configurationValuesMap,
								scopeGroupId));
				setFragmentEditableElements(() -> curFragmentEditableElements);
				setFragmentInstanceExternalReferenceCode(
					RandomTestUtil::randomString);
				setFragmentReference(
					() -> new DefaultFragmentReference() {
						{
							setDefaultFragmentKey(fragmentRenderer::getKey);
							setFragmentReferenceType(
								() ->
									FragmentReferenceType.
										DEFAULT_FRAGMENT_REFERENCE);
						}
					});
				setHtml(() -> StringPool.BLANK);
				setIndexed(RandomTestUtil::randomBoolean);
				setJs(() -> StringPool.BLANK);
				setName(RandomTestUtil::randomString);
				setNamespace(RandomTestUtil::randomString);
				setUuid(RandomTestUtil::randomString);
			}
		};
	}

	private static FragmentInstance _getFragmentInstance(
		Map<String, Object> configurationValuesMap,
		FragmentEditableElement[] fragmentEditableElements,
		FragmentEntry fragmentEntry,
		BackgroundImageValue fragmentInstanceBackgroundImageValue,
		String fragmentInstanceExternalReferenceCode, Boolean hidden,
		String namespace, long scopeGroupId, String uuid,
		WidgetInstance[] widgetInstances) {

		FragmentInstance fragmentInstance = new FragmentInstance();

		fragmentInstance.setBackgroundImageValue(
			() -> fragmentInstanceBackgroundImageValue);
		fragmentInstance.setConfiguration(fragmentEntry::getConfiguration);
		fragmentInstance.setCss(fragmentEntry::getCss);
		fragmentInstance.setCssClasses(
			() -> new String[] {RandomTestUtil.randomString()});
		fragmentInstance.setDatePropagated(RandomTestUtil::nextDate);
		fragmentInstance.setFragmentConfigurationFieldValues(
			() ->
				FragmentConfigurationFieldValueTestUtil.
					getFragmentConfigurationFieldValuesMap(
						JSONFactoryUtil.createJSONObject(
							fragmentEntry.getConfiguration()),
						configurationValuesMap, scopeGroupId));
		fragmentInstance.setFragmentEditableElements(
			() -> fragmentEditableElements);
		fragmentInstance.setFragmentInstanceExternalReferenceCode(
			fragmentInstanceExternalReferenceCode);
		fragmentInstance.setFragmentReference(
			() -> {
				if (fragmentEntry.getFragmentEntryId() == 0) {
					return _addDefaultFragmentReference(
						fragmentEntry.getFragmentEntryKey());
				}

				return _addFragmentItemExternalReference(
					fragmentEntry,
					ScopeTestUtil.getItemScope(
						fragmentEntry.getGroupId(), scopeGroupId));
			});
		fragmentInstance.setFragmentViewports(
			FragmentViewportTestUtil.getFragmentViewports(hidden));
		fragmentInstance.setHtml(fragmentEntry::getHtml);
		fragmentInstance.setIndexed(RandomTestUtil::randomBoolean);
		fragmentInstance.setJs(fragmentEntry::getJs);
		fragmentInstance.setName(RandomTestUtil::randomString);
		fragmentInstance.setNamespace(namespace);
		fragmentInstance.setUuid(uuid);
		fragmentInstance.setWidgetInstances(() -> widgetInstances);

		return fragmentInstance;
	}

	private static FragmentReference[] _getFragmentReferences(long groupId)
		throws PortalException {

		List<FragmentReference> fragmentReferences = new ArrayList<>();

		fragmentReferences.add(
			_addDefaultFragmentReference("BASIC_COMPONENT-button"));
		fragmentReferences.add(
			_addDefaultFragmentReference("INPUTS-date-input"));

		FragmentCollection fragmentCollection =
			FragmentCollectionLocalServiceUtil.addFragmentCollection(
				null, TestPropsValues.getUserId(), groupId,
				StringUtil.randomString(), StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(groupId));

		for (int i = 0; i < 3; i++) {
			fragmentReferences.add(
				_addFragmentItemExternalReference(
					_addFragmentEntry(
						fragmentCollection.getFragmentCollectionId(),
						fragmentCollection.getGroupId()),
					null));
		}

		return fragmentReferences.toArray(new FragmentReference[0]);
	}

	private static PageElement _getGridPageElement(int position) {
		String externalReferenceCode = RandomTestUtil.randomString();

		ModuleViewport[] moduleViewports = {
			new ModuleViewport() {
				{
					setId(Id.DESKTOP);
					setModuleViewportDefinition(
						() -> new ModuleViewportDefinition() {
							{
								setSize(4);
							}
						});
				}
			},
			new ModuleViewport() {
				{
					setId(Id.LANDSCAPE_MOBILE);
					setModuleViewportDefinition(
						() -> new ModuleViewportDefinition() {
							{
								setSize(12);
							}
						});
				}
			}
		};

		return _getPageElement(
			externalReferenceCode,
			new GridPageElementDefinition() {
				{
					setGridViewports(
						new GridViewport[] {
							_getGridViewport(
								GridViewportDefinition.VerticalAlignment.BOTTOM,
								GridViewport.Id.DESKTOP),
							_getGridViewport(
								GridViewportDefinition.VerticalAlignment.BOTTOM,
								GridViewport.Id.LANDSCAPE_MOBILE),
							_getGridViewport(
								GridViewportDefinition.VerticalAlignment.TOP,
								GridViewport.Id.PORTRAIT_MOBILE),
							_getGridViewport(
								GridViewportDefinition.VerticalAlignment.MIDDLE,
								GridViewport.Id.TABLET)
						});
					setGutters(Boolean.TRUE);
					setIndexed(Boolean.TRUE);
					setNumberOfModules(1);
					setReverseOrder(Boolean.FALSE);
					setType(Type.GRID);
				}
			},
			new PageElement[] {
				_getPageElement(
					_getModulePageElementDefinition(moduleViewports),
					externalReferenceCode, 0),
				_getPageElement(
					_getModulePageElementDefinition(moduleViewports),
					externalReferenceCode, 1),
				_getPageElement(
					_getModulePageElementDefinition(moduleViewports),
					externalReferenceCode, 2)
			},
			StringPool.BLANK, position);
	}

	private static GridViewport _getGridViewport(
		GridViewportDefinition.VerticalAlignment verticalAlignment,
		GridViewport.Id id) {

		GridViewport gridViewport = new GridViewport();

		gridViewport.setCustomCSS(RandomTestUtil.randomString());

		GridViewportDefinition gridViewportDefinition =
			new GridViewportDefinition();

		gridViewportDefinition.setModulesPerRow(RandomTestUtil.randomInt());
		gridViewportDefinition.setVerticalAlignment(verticalAlignment);

		gridViewport.setGridViewportDefinition(() -> gridViewportDefinition);

		gridViewport.setId(id);

		return gridViewport;
	}

	private static String _getInternalTemplateFieldKey(
		TemplateEntry templateEntry) {

		return "ddmTemplate__ddmTemplate_" + templateEntry.getTemplateEntryId();
	}

	private static ModulePageElementDefinition _getModulePageElementDefinition(
		ModuleViewport[] moduleViewports) {

		ModulePageElementDefinition modulePageElementDefinition =
			new ModulePageElementDefinition();

		modulePageElementDefinition.setModuleViewports(moduleViewports);
		modulePageElementDefinition.setType(PageElementDefinition.Type.MODULE);

		return modulePageElementDefinition;
	}

	private static PageElement _getPageElement(
		PageElementDefinition pageElementDefinition,
		String parentExternalReferenceCode, int position) {

		return _getPageElement(
			RandomTestUtil.randomString(), pageElementDefinition,
			new PageElement[0], parentExternalReferenceCode, position);
	}

	private static PageElement _getPageElement(
		String externalReferenceCode,
		PageElementDefinition pageElementDefinition) {

		PageElement pageElement = new PageElement();

		pageElement.setExternalReferenceCode(externalReferenceCode);
		pageElement.setPageElementDefinition(pageElementDefinition);
		pageElement.setPageElements(new PageElement[0]);
		pageElement.setParentExternalReferenceCode(StringPool.BLANK);
		pageElement.setPosition(0);

		return pageElement;
	}

	private static PageElement _getPageElement(
		String externalReferenceCode,
		PageElementDefinition pageElementDefinition, PageElement[] pageElements,
		String parentExternalReferenceCode, int position) {

		PageElement pageElement = new PageElement();

		pageElement.setExternalReferenceCode(externalReferenceCode);
		pageElement.setPageElementDefinition(pageElementDefinition);
		pageElement.setPageElements(pageElements);
		pageElement.setParentExternalReferenceCode(parentExternalReferenceCode);
		pageElement.setPosition(position);

		for (PageElement childPageElement : pageElements) {
			childPageElement.setParentExternalReferenceCode(
				externalReferenceCode);
		}

		return pageElement;
	}

	private static PageElementDefinition.Type _getRandomType() {
		return _types.get(RandomTestUtil.randomInt(0, _types.size() - 1));
	}

	private static TemplateEntry _getTemplateEntry(
			JournalArticle journalArticle, String text,
			ServiceContext serviceContext)
		throws Exception {

		return TemplateTestUtil.addTemplateEntry(
			JournalArticle.class.getName(),
			String.valueOf(journalArticle.getDDMStructureId()), text,
			RandomTestUtil.randomString(),
			TemplateTestUtil.getSampleScriptFTL("JournalArticle_title", text),
			serviceContext);
	}

	private static WidgetInstance _getWidgetInstance() {
		WidgetInstance widgetInstance = new WidgetInstance();

		widgetInstance.setWidgetConfig(new HashMap<>());
		widgetInstance.setWidgetInstanceId(RandomTestUtil.randomString());
		widgetInstance.setWidgetName(AssetPublisherPortletKeys.ASSET_PUBLISHER);
		widgetInstance.setWidgetPermissions(new WidgetPermission[0]);

		return widgetInstance;
	}

	private static boolean _isParentablePageElementDefinitionType(
		PageElementDefinition pageElementDefinition) {

		if (Objects.equals(
				pageElementDefinition.getType(),
				PageElementDefinition.Type.COLLECTION_ITEM)) {

			return true;
		}
		else if (Objects.equals(
					pageElementDefinition.getType(),
					PageElementDefinition.Type.CONTAINER)) {

			return true;
		}
		else if (Objects.equals(
					pageElementDefinition.getType(),
					PageElementDefinition.Type.MODULE)) {

			return true;
		}

		return false;
	}

	private static final List<PageElementDefinition.Type> _types =
		Arrays.asList(
			PageElementDefinition.Type.BASIC_FRAGMENT,
			PageElementDefinition.Type.CONTAINER);

}