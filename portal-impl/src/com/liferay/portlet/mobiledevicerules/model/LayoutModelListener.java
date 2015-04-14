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

package com.liferay.portlet.mobiledevicerules.model;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.Layout;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceLocalServiceUtil;

import java.util.List;

/**
 * @author Eduardo Garcia
 */
public class LayoutModelListener extends BaseModelListener<Layout> {

	@Override
	public void onBeforeRemove(Layout layout) throws ModelListenerException {
		try {
			List<MDRRuleGroupInstance> mdrRuleGroupInstances =
				MDRRuleGroupInstanceLocalServiceUtil.getRuleGroupInstances(
					Layout.class.getName(), layout.getPlid());

			for (MDRRuleGroupInstance mdrRuleGroupInstance :
					mdrRuleGroupInstances) {

				MDRRuleGroupInstanceLocalServiceUtil.deleteMDRRuleGroupInstance(
					mdrRuleGroupInstance);
			}
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

}