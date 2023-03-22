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

package com.liferay.commerce.shop.by.diagram.admin.web.internal.info.list.renderer;

import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
import com.liferay.info.list.renderer.InfoListRenderer;
import com.liferay.info.taglib.list.renderer.UnstyledBasicInfoListRenderer;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mahmoud Azzam
 * @author Alessio Antonio Rendina
 */
@Component(service = InfoListRenderer.class)
public class UnstyledCSDiagramEntryBasicInfoListRenderer
	extends BaseCSDiagramEntryBasicInfoListRenderer
	implements UnstyledBasicInfoListRenderer<CSDiagramEntry> {
}