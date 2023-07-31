/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.info.list.renderer;

import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.info.list.renderer.InfoListRenderer;
import com.liferay.info.taglib.list.renderer.BorderedBasicInfoListRenderer;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = InfoListRenderer.class)
public class BorderedCommerceOrderItemBasicInfoListRenderer
	extends BaseCommerceOrderItemBasicInfoListRenderer
	implements BorderedBasicInfoListRenderer<CommerceOrderItem> {
}