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

package com.liferay.portlet.assetcategoryadmin.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.asset.AssetCategoryNameException;
import com.liferay.portlet.asset.DuplicateCategoryException;
import com.liferay.portlet.asset.NoSuchCategoryException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.service.AssetCategoryServiceUtil;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class EditCategoryAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCategory(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCategory(actionRequest);
			}
			else if (cmd.equals(Constants.MOVE)) {
				moveCategory(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchCategoryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.asset_category_admin.error");
			}
			else if (e instanceof AssetCategoryNameException ||
					 e instanceof DuplicateCategoryException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		ActionUtil.getCategory(renderRequest);
		ActionUtil.getVocabularies(renderRequest);

		return actionMapping.findForward(
			getForward(
				renderRequest, "portlet.asset_category_admin.edit_category"));
	}

	protected void deleteCategory(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCategoryIds = null;

		long categoryId = ParamUtil.getLong(actionRequest, "categoryId");

		if (categoryId > 0) {
			deleteCategoryIds = new long[] {categoryId};
		}
		else {
			deleteCategoryIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCategoryIds"), 0L);
		}

		AssetCategoryServiceUtil.deleteCategories(deleteCategoryIds);
	}

	protected String[] getCategoryProperties(ActionRequest actionRequest) {
		int[] categoryPropertiesIndexes = StringUtil.split(
			ParamUtil.getString(actionRequest, "categoryPropertiesIndexes"), 0);

		String[] categoryProperties =
			new String[categoryPropertiesIndexes.length];

		for (int i = 0; i < categoryPropertiesIndexes.length; i++) {
			int categoryPropertiesIndex = categoryPropertiesIndexes[i];

			String key = ParamUtil.getString(
				actionRequest, "key" + categoryPropertiesIndex);

			if (Validator.isNull(key)) {
				continue;
			}

			String value = ParamUtil.getString(
				actionRequest, "value" + categoryPropertiesIndex);

			categoryProperties[i] =
				key + AssetCategoryConstants.PROPERTY_KEY_VALUE_SEPARATOR +
					value;
		}

		return categoryProperties;
	}

	protected void moveCategory(ActionRequest actionRequest) throws Exception {
		long categoryId = ParamUtil.getLong(actionRequest, "categoryId");

		long parentCategoryId = ParamUtil.getLong(
			actionRequest, "parentCategoryId");
		long vocabularyId = ParamUtil.getLong(actionRequest, "vocabularyId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			AssetCategory.class.getName(), actionRequest);

		AssetCategoryServiceUtil.moveCategory(
			categoryId, parentCategoryId, vocabularyId, serviceContext);
	}

	protected void updateCategory(ActionRequest actionRequest)
		throws Exception {

		long categoryId = ParamUtil.getLong(actionRequest, "categoryId");

		long parentCategoryId = ParamUtil.getLong(
			actionRequest, "parentCategoryId");
		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		long vocabularyId = ParamUtil.getLong(actionRequest, "vocabularyId");
		String[] categoryProperties = getCategoryProperties(actionRequest);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			AssetCategory.class.getName(), actionRequest);

		if (categoryId <= 0) {

			// Add category

			AssetCategoryServiceUtil.addCategory(
				parentCategoryId, titleMap, descriptionMap, vocabularyId,
				categoryProperties, serviceContext);
		}
		else {

			// Update category

			AssetCategoryServiceUtil.updateCategory(
				categoryId, parentCategoryId, titleMap, descriptionMap,
				vocabularyId, categoryProperties, serviceContext);
		}
	}

}