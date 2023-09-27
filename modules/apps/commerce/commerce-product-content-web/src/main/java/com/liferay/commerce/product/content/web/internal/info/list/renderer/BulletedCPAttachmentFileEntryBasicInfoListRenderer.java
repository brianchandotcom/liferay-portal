/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.web.internal.info.list.renderer;

import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.info.list.renderer.InfoListRenderer;
import com.liferay.info.taglib.list.renderer.BulletedBasicInfoListRenderer;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = InfoListRenderer.class)
public class BulletedCPAttachmentFileEntryBasicInfoListRenderer
	extends BaseCPAttachmentFileEntryBasicInfoListRenderer
	implements BulletedBasicInfoListRenderer<CPAttachmentFileEntry> {
}