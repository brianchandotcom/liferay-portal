/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test.util;

import com.liferay.headless.admin.site.client.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.client.dto.v1_0.PageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.PageExperience;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.junit.Assert;

/**
 * @author Lourdes Fernández Besada
 */
public class PageExperiencesTestUtil {

	public static void assertPageExperiences(
		PageExperience[] expectedPageExperiences, Layout layout,
		PageExperience[] pageExperiences) {

		PageExperience expectedPageExperience = expectedPageExperiences[0];

		Assert.assertEquals(
			pageExperiences.toString(), 1, pageExperiences.length);

		PageExperience pageExperience = pageExperiences[0];

		Assert.assertEquals(
			expectedPageExperience.getExternalReferenceCode(),
			pageExperience.getExternalReferenceCode());
		Assert.assertEquals(
			layout.getExternalReferenceCode(),
			pageExperience.getPageSpecificationExternalReferenceCode());

		SegmentsExperience segmentsExperience =
			SegmentsExperienceLocalServiceUtil.fetchDefaultSegmentsExperience(
				layout.getPlid());

		Assert.assertEquals(
			segmentsExperience.getExternalReferenceCode(),
			pageExperience.getExternalReferenceCode());
	}

	public static void assertPageExperiences(
		PageExperience[] actualPageExperiences,
		PageExperience[] expectedPageExperiences) {

		for (int i = 0; i < expectedPageExperiences.length; i++) {
			Assert.assertEquals(
				expectedPageExperiences[i], actualPageExperiences[i]);
		}
	}

	public static PageExperience getDefaultPageExperience(
		PageExperience[] pageExperiences) {

		for (PageExperience pageExperience : pageExperiences) {
			if (Objects.equals(
					pageExperience.getKey(),
					SegmentsExperienceConstants.KEY_DEFAULT)) {

				return pageExperience;
			}
		}

		return null;
	}

	public static PageExperience[] getDefaultPageExperiences(
		String contentPageSpecificationExternalReferenceCode,
		long scopeGroupId) {

		return getDefaultPageExperiences(
			contentPageSpecificationExternalReferenceCode,
			PageElementsTestUtil.getPageElements(
				RandomTestUtil.randomInt(1, 3), StringPool.BLANK,
				scopeGroupId));
	}

	public static PageExperience[] getDefaultPageExperiences(
		String contentPageSpecificationExternalReferenceCode,
		PageElement[] pageElements) {

		PageExperience pageExperience = new PageExperience();

		pageExperience.setExternalReferenceCode(RandomTestUtil::randomString);
		pageExperience.setKey(SegmentsExperienceConstants.KEY_DEFAULT);
		pageExperience.setName_i18n(
			Collections.singletonMap("en-US", RandomTestUtil.randomString()));
		pageExperience.setPageElements(pageElements);
		pageExperience.setPageSpecificationExternalReferenceCode(
			contentPageSpecificationExternalReferenceCode);

		return new PageExperience[] {pageExperience};
	}

	public static PageExperience getPageExperience() {
		PageExperience pageExperience = new PageExperience();

		pageExperience.setExternalReferenceCode(RandomTestUtil::randomString);
		pageExperience.setKey(RandomTestUtil.randomString());
		pageExperience.setName_i18n(
			Collections.singletonMap("en-US", RandomTestUtil.randomString()));

		return pageExperience;
	}

	public static PageExperience getPageExperience(
		String pageSpecificationExternalReferenceCode, int priority,
		long scopeGroupId, SegmentsEntry segmentsEntry) {

		PageExperience pageExperience = getPageExperience();

		pageExperience.setPageElements(
			PageElementsTestUtil.getPageElements(scopeGroupId));
		pageExperience.setPageSpecificationExternalReferenceCode(
			pageSpecificationExternalReferenceCode);
		pageExperience.setPriority(priority);

		if (segmentsEntry != null) {
			pageExperience.setSegmentItemExternalReference(
				() -> ReferencesTestUtil.getItemExternalReference(
					segmentsEntry, scopeGroupId));
		}

		return pageExperience;
	}

	public static PageExperience getPageExperience(
		String contentPageSpecificationExternalReferenceCode,
		PageElement[] pageElements, int priority) {

		PageExperience pageExperience = getPageExperience();

		pageExperience.setPageElements(pageElements);
		pageExperience.setPageSpecificationExternalReferenceCode(
			contentPageSpecificationExternalReferenceCode);
		pageExperience.setPriority(priority);

		return pageExperience;
	}

	public static PageExperience[] getPageExperiences(
			long companyGroupId,
			String contentPageSpecificationExternalReferenceCode, long groupId)
		throws PortalException {

		return new PageExperience[] {
			getPageExperience(
				contentPageSpecificationExternalReferenceCode, 3, groupId,
				SegmentsTestUtil.addSegmentsEntry(groupId)),
			getPageExperience(
				contentPageSpecificationExternalReferenceCode, 2, groupId,
				null),
			getPageExperience(
				contentPageSpecificationExternalReferenceCode, 1, groupId,
				SegmentsTestUtil.addSegmentsEntry(companyGroupId))
		};
	}

	public static void modifyPageExperiences(
		PageExperience[] pageExperiences, long scopeGroupId) {

		for (PageExperience pageExperience : pageExperiences) {
			List<PageElement> dropZonePageElements =
				TransformUtil.transformToList(
					pageExperience.getPageElements(),
					pageElement -> {
						PageElementDefinition pageElementDefinition =
							pageElement.getPageElementDefinition();

						if (Objects.equals(
								pageElementDefinition.getType(),
								PageElementDefinition.Type.DROP_ZONE)) {

							return pageElement;
						}

						return null;
					});

			pageExperience.setPageElements(
				() -> {
					PageElement[] pageElements =
						PageElementsTestUtil.getPageElements(
							RandomTestUtil.randomInt(1, 3), StringPool.BLANK,
							scopeGroupId);

					if (ListUtil.isEmpty(dropZonePageElements)) {
						return pageElements;
					}

					for (int i = 0; i < dropZonePageElements.size(); i++) {
						PageElement pageElement = dropZonePageElements.get(i);

						pageElement.setPosition(pageElements.length + i);
					}

					return ArrayUtil.append(
						pageElements,
						dropZonePageElements.toArray(new PageElement[0]));
				});
		}
	}

}