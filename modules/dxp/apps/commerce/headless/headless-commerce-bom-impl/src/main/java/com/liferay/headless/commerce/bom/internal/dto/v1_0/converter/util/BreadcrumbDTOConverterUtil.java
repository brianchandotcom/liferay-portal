/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.headless.commerce.bom.internal.dto.v1_0.converter.util;

import com.liferay.commerce.bom.model.CommerceBOMFolder;
import com.liferay.headless.commerce.bom.dto.v1_0.Breadcrumb;
import com.liferay.headless.commerce.bom.internal.dto.v1_0.converter.BreadcrumbDTOConverter;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

/**
 * @author Alessio Antonio Rendina
 */
public class BreadcrumbDTOConverterUtil {

	public static Breadcrumb[] getBreadcrumbs(
			BreadcrumbDTOConverter breadcrumbDTOConverter,
			CommerceBOMFolder commerceBOMFolder, Locale locale)
		throws Exception {

		List<Breadcrumb> breadcrumbs = new ArrayList<>();

		breadcrumbs.add(
			new Breadcrumb() {
				{
					label = LanguageUtil.get(locale, "home");
					url = "/folders/0";
				}
			});

		if (commerceBOMFolder != null) {
			List<CommerceBOMFolder> ancestorCommerceBOMFolders =
				commerceBOMFolder.getAncestors();

			Collections.reverse(ancestorCommerceBOMFolders);

			for (CommerceBOMFolder ancestorCommerceBOMFolder :
					ancestorCommerceBOMFolders) {

				breadcrumbs.add(
					breadcrumbDTOConverter.toDTO(
						new DefaultDTOConverterContext(
							ancestorCommerceBOMFolder.getCommerceBOMFolderId(),
							locale)));
			}
		}

		Stream<Breadcrumb> stream = breadcrumbs.stream();

		return stream.toArray(Breadcrumb[]::new);
	}

}