/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test.util;

import com.liferay.fragment.contributor.util.FragmentCollectionContributorRegistryUtil;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.util.FragmentRendererRegistryUtil;
import com.liferay.headless.admin.site.client.dto.v1_0.CollectionDisplayPageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.CollectionItemPageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.ContainerPageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.DefaultFragmentReference;
import com.liferay.headless.admin.site.client.dto.v1_0.DropZonePageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.FormPageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.FormStepContainerPageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.FormStepPageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentDropZonePageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentInstancePageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentItemExternalReference;
import com.liferay.headless.admin.site.client.dto.v1_0.GridPageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.HtmlProperties;
import com.liferay.headless.admin.site.client.dto.v1_0.ModulePageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.client.dto.v1_0.PageElementDefinition;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Lourdes Fernández Besada
 */
public class PageElementsTestUtil {

	public static FragmentInstancePageElementDefinition
		getFragmentInstancePageElementDefinition(
			FragmentEntry fragmentEntry, long scopeGroupId) {

		return new FragmentInstancePageElementDefinition() {
			{
				setConfiguration(fragmentEntry::getConfiguration);
				setCss(fragmentEntry::getCss);

				setCssClasses(
					() -> new String[] {RandomTestUtil.randomString()});
				setCustomCSS(RandomTestUtil::randomString);
				setDatePropagated(RandomTestUtil::nextDate);
				setFragmentInstanceExternalReferenceCode(
					RandomTestUtil::randomString);
				setFragmentReference(
					() -> {
						if (fragmentEntry.getFragmentEntryId() == 0) {
							return new DefaultFragmentReference() {
								{
									setDefaultFragmentKey(
										fragmentEntry::getFragmentEntryKey);
									setFragmentReferenceType(
										() ->
											FragmentReferenceType.
												DEFAULT_FRAGMENT_REFERENCE);
								}
							};
						}

						return new FragmentItemExternalReference() {
							{
								setExternalReferenceCode(
									fragmentEntry::getExternalReferenceCode);
								setFragmentReferenceType(
									() ->
										FragmentReferenceType.
											FRAGMENT_ITEM_EXTERNAL_REFERENCE);
								setScope(
									() -> ScopeTestUtil.getItemScope(
										fragmentEntry.getGroupId(),
										scopeGroupId));
							}
						};
					});
				setFragmentType(FragmentType.BASIC);
				setHtml(fragmentEntry::getHtml);
				setIndexed(RandomTestUtil::randomBoolean);
				setJs(fragmentEntry::getJs);
				setName(RandomTestUtil::randomString);
				setNamespace(RandomTestUtil::randomString);
				setType(Type.FRAGMENT);
				setUuid(RandomTestUtil::randomString);
			}
		};
	}

	public static FragmentInstancePageElementDefinition
		getFragmentInstancePageElementDefinition(
			FragmentRenderer fragmentRenderer) {

		return new FragmentInstancePageElementDefinition() {
			{
				setConfiguration(
					() -> JSONFactoryUtil.toString(
						fragmentRenderer.getConfigurationJSONObject(
							new DefaultFragmentRendererContext(null))));

				setCss(() -> StringPool.BLANK);
				setCssClasses(
					() -> new String[] {RandomTestUtil.randomString()});
				setCustomCSS(RandomTestUtil::randomString);
				setDatePropagated(RandomTestUtil::nextDate);
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
				setFragmentType(FragmentType.BASIC);
				setHtml(() -> StringPool.BLANK);
				setIndexed(RandomTestUtil::randomBoolean);
				setJs(() -> StringPool.BLANK);
				setName(RandomTestUtil::randomString);
				setNamespace(RandomTestUtil::randomString);
				setType(Type.FRAGMENT);
				setUuid(RandomTestUtil::randomString);
			}
		};
	}

	public static FragmentInstancePageElementDefinition
		getFragmentInstancePageElementDefinition(String key) {

		FragmentEntry fragmentEntry =
			FragmentCollectionContributorRegistryUtil.getFragmentEntry(key);

		if (fragmentEntry != null) {
			return getFragmentInstancePageElementDefinition(fragmentEntry);
		}

		FragmentRenderer fragmentRenderer =
			FragmentRendererRegistryUtil.getFragmentRenderer(key);

		if (fragmentRenderer != null) {
			return getFragmentInstancePageElementDefinition(fragmentRenderer);
		}

		return null;
	}

	public static PageElementDefinition getPageElementDefinition(
		PageElementDefinition.Type type) {

		if (Objects.equals(
				type, PageElementDefinition.Type.COLLECTION_DISPLAY)) {

			return new CollectionDisplayPageElementDefinition() {
				{
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
					setHtmlProperties(new HtmlProperties());
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

		if (Objects.equals(type, PageElementDefinition.Type.FORM)) {
			return new FormPageElementDefinition() {
				{
					setIndexed(Boolean.TRUE);
					setType(Type.FORM);
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

		if (Objects.equals(type, PageElementDefinition.Type.FRAGMENT)) {
			return getFragmentInstancePageElementDefinition(
				"BASIC_COMPONENT-heading");
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
					setSize(1);
					setType(Type.MODULE);
				}
			};
		}

		if (Objects.equals(type, PageElementDefinition.Type.GRID)) {
			return new GridPageElementDefinition() {
				{
					setGutters(Boolean.TRUE);
					setIndexed(Boolean.TRUE);
					setModulesPerRow(0);
					setNumberOfModules(0);
					setReverseOrder(Boolean.FALSE);
					setType(Type.GRID);
					setVerticalAlignment(VerticalAlignment.TOP);
				}
			};
		}

		return null;
	}

	public static PageElement[] getPageElements(
		int count, String parentExternalReferenceCode) {

		PageElement[] pageElements = new PageElement[count];

		for (int i = 0; i < count; i++) {
			PageElement pageElement = new PageElement();

			pageElement.setExternalReferenceCode(RandomTestUtil::randomString);
			pageElement.setPageElementDefinition(
				getPageElementDefinition(_getRandomType()));
			pageElement.setPosition(i);

			if (_isParentablePageElementDefinitionType(
					pageElement.getPageElementDefinition()) &&
				RandomTestUtil.randomBoolean()) {

				pageElement.setPageElements(
					getPageElements(
						RandomTestUtil.randomInt(1, 2),
						pageElement.getExternalReferenceCode()));
			}

			pageElement.setParentExternalReferenceCode(
				parentExternalReferenceCode);

			pageElements[i] = pageElement;
		}

		return pageElements;
	}

	private static PageElementDefinition.Type _getRandomType() {
		return _types.get(RandomTestUtil.randomInt(0, _types.size() - 1));
	}

	private static boolean _isParentablePageElementDefinitionType(
		PageElementDefinition pageElementDefinition) {

		return Objects.equals(
			pageElementDefinition.getType(),
			PageElementDefinition.Type.CONTAINER);
	}

	private static final List<PageElementDefinition.Type> _types =
		Arrays.asList(
			PageElementDefinition.Type.CONTAINER,
			PageElementDefinition.Type.FRAGMENT);

}