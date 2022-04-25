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

package com.liferay.object.web.internal.object.definitions.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.web.internal.display.context.helper.ObjectRequestHelper;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Gabriel Albuquerque
 */
public class ObjectDefinitionsViewsDisplayContext
	extends BaseObjectDefinitionsDisplayContext {

	public ObjectDefinitionsViewsDisplayContext(
		HttpServletRequest httpServletRequest,
		ModelResourcePermission<ObjectDefinition>
			objectDefinitionModelResourcePermission) {

		super(httpServletRequest, objectDefinitionModelResourcePermission);

		_objectRequestHelper = new ObjectRequestHelper(httpServletRequest);
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems()
		throws Exception {

		return Arrays.asList(
			new FDSActionDropdownItem(
				PortletURLBuilder.create(
					getPortletURL()
				).setMVCRenderCommandName(
					"/object_definitions/edit_object_view"
				).setParameter(
					"objectViewId", "{id}"
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString(),
				"view", "view",
				LanguageUtil.get(objectRequestHelper.getRequest(), "view"),
				"get", null, "sidePanel"),
			new FDSActionDropdownItem(
				"/o/object-admin/v1.0/object-views/{id}/copy", "copy", "copy",
				LanguageUtil.get(objectRequestHelper.getRequest(), "duplicate"),
				"post", "copy", "async"),
			new FDSActionDropdownItem(
				"/o/object-admin/v1.0/object-views/{id}", "trash", "delete",
				LanguageUtil.get(objectRequestHelper.getRequest(), "delete"),
				"delete", "delete", "async"));
	}

	public JSONArray getWorkflowStatusJSONArray() {
		return JSONUtil.putAll(
			JSONUtil.put(
				"label",
				LanguageUtil.get(
					_objectRequestHelper.getRequest(),
					WorkflowConstants.LABEL_APPROVED)
			).put(
				"value", WorkflowConstants.STATUS_APPROVED
			)
		).put(
			JSONUtil.put(
				"label",
				LanguageUtil.get(
					_objectRequestHelper.getRequest(),
					WorkflowConstants.LABEL_DENIED)
			).put(
				"value", WorkflowConstants.STATUS_DENIED
			)
		).put(
			JSONUtil.put(
				"label",
				LanguageUtil.get(
					_objectRequestHelper.getRequest(),
					WorkflowConstants.LABEL_DRAFT)
			).put(
				"value", WorkflowConstants.STATUS_DRAFT
			)
		).put(
			JSONUtil.put(
				"label",
				LanguageUtil.get(
					_objectRequestHelper.getRequest(),
					WorkflowConstants.LABEL_EXPIRED)
			).put(
				"value", WorkflowConstants.STATUS_EXPIRED
			)
		).put(
			JSONUtil.put(
				"label",
				LanguageUtil.get(
					_objectRequestHelper.getRequest(),
					WorkflowConstants.LABEL_INACTIVE)
			).put(
				"value", WorkflowConstants.STATUS_INACTIVE
			)
		).put(
			JSONUtil.put(
				"label",
				LanguageUtil.get(
					_objectRequestHelper.getRequest(),
					WorkflowConstants.LABEL_INCOMPLETE)
			).put(
				"value", WorkflowConstants.STATUS_INCOMPLETE
			)
		).put(
			JSONUtil.put(
				"label",
				LanguageUtil.get(
					_objectRequestHelper.getRequest(),
					WorkflowConstants.LABEL_IN_TRASH)
			).put(
				"value", WorkflowConstants.STATUS_IN_TRASH
			)
		).put(
			JSONUtil.put(
				"label",
				LanguageUtil.get(
					_objectRequestHelper.getRequest(),
					WorkflowConstants.LABEL_PENDING)
			).put(
				"value", WorkflowConstants.STATUS_PENDING
			)
		).put(
			JSONUtil.put(
				"label",
				LanguageUtil.get(
					_objectRequestHelper.getRequest(),
					WorkflowConstants.LABEL_SCHEDULED)
			).put(
				"value", WorkflowConstants.STATUS_SCHEDULED
			)
		);
	}

	@Override
	protected String getAPIURI() {
		return "/object-views";
	}

	@Override
	protected UnsafeConsumer<DropdownItem, Exception>
		getCreationMenuDropdownItemUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref("addObjectView");
			dropdownItem.setLabel(
				LanguageUtil.get(
					objectRequestHelper.getRequest(), "add-object-view"));
			dropdownItem.setTarget("event");
		};
	}

	private final ObjectRequestHelper _objectRequestHelper;

}