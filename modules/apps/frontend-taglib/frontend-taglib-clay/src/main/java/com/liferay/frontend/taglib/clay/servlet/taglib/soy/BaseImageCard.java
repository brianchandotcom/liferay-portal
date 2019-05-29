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

package com.liferay.frontend.taglib.clay.servlet.taglib.soy;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.model.AuditedModel;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.WorkflowedModel;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;

import javax.portlet.RenderRequest;

/**
 * @author Eudaldo Alonso
 */
public abstract class BaseImageCard
	extends BaseBaseClayCard implements ImageCard {

	public BaseImageCard(
		BaseModel<?> baseModel, RenderRequest renderRequest,
		RowChecker rowChecker) {

		super(baseModel, rowChecker);

		this.renderRequest = renderRequest;

		themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public List<LabelItem> getLabels() {
		if (!(baseModel instanceof WorkflowedModel)) {
			return Collections.emptyList();
		}

		WorkflowedModel workflowedModel = (WorkflowedModel)baseModel;

		return new LabelItemList() {
			{
				add(
					labelItem -> labelItem.setStatus(
						workflowedModel.getStatus()));
			}
		};
	}

	@Override
	public String getStickerImageSrc() {
		if (!(baseModel instanceof AuditedModel)) {
			return StringPool.BLANK;
		}

		AuditedModel auditedModel = (AuditedModel)baseModel;

		User user = UserLocalServiceUtil.fetchUser(auditedModel.getUserId());

		if (user.getPortraitId() > 0) {
			try {
				return user.getPortraitURL(themeDisplay);
			}
			catch (Exception e) {
			}
		}

		return StringPool.BLANK;
	}

	@Override
	public String getStickerLabel() {
		if (!(baseModel instanceof AuditedModel)) {
			return StringPool.BLANK;
		}

		AuditedModel auditedModel = (AuditedModel)baseModel;

		User user = UserLocalServiceUtil.fetchUser(auditedModel.getUserId());

		if (user == null) {
			return StringPool.BLANK;
		}

		return user.getInitials();
	}

	@Override
	public String getStickerShape() {
		return "circle";
	}

	protected final RenderRequest renderRequest;
	protected final ThemeDisplay themeDisplay;

}