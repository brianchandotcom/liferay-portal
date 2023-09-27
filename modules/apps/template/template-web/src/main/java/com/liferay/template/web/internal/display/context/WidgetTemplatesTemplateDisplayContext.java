/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.web.internal.display.context;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateServiceUtil;
import com.liferay.dynamic.data.mapping.util.comparator.TemplateIdComparator;
import com.liferay.dynamic.data.mapping.util.comparator.TemplateModifiedDateComparator;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.template.web.internal.security.permissions.resource.DDMTemplatePermission;
import com.liferay.template.web.internal.util.DDMTemplateActionDropdownItemsProvider;

import java.util.List;
import java.util.Objects;

/**
 * @author Lourdes Fernández Besada
 */
public class WidgetTemplatesTemplateDisplayContext
	extends BaseTemplateDisplayContext {

	public WidgetTemplatesTemplateDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(liferayPortletRequest, liferayPortletResponse);

		_portletDisplayTemplate =
			(PortletDisplayTemplate)liferayPortletRequest.getAttribute(
				PortletDisplayTemplate.class.getName());
	}

	public long[] getClassNameIds() {
		if (_classNameIds != null) {
			return _classNameIds;
		}

		_classNameIds = TransformUtil.transformToLongArray(
			_portletDisplayTemplate.getPortletDisplayTemplateHandlers(),
			templateHandler -> PortalUtil.getClassNameId(
				templateHandler.getClassName()));

		return _classNameIds;
	}

	public List<DropdownItem> getDDMTemplateActionDropdownItems(
		DDMTemplate ddmTemplate) {

		DDMTemplateActionDropdownItemsProvider
			ddmTemplateActionDropdownItemsProvider =
				new DDMTemplateActionDropdownItemsProvider(
					isAddButtonEnabled(), ddmTemplate,
					PortalUtil.getHttpServletRequest(liferayPortletRequest),
					liferayPortletResponse, getTabs1());

		return ddmTemplateActionDropdownItemsProvider.getActionDropdownItems();
	}

	public String getDDMTemplateEditURL(DDMTemplate ddmTemplate)
		throws PortalException {

		if (!isStagingGroup() ||
			!DDMTemplatePermission.contains(
				themeDisplay.getPermissionChecker(), ddmTemplate,
				ActionKeys.UPDATE)) {

			return StringPool.BLANK;
		}

		return PortletURLBuilder.createRenderURL(
			liferayPortletResponse
		).setMVCRenderCommandName(
			"/template/edit_ddm_template"
		).setRedirect(
			themeDisplay.getURLCurrent()
		).setTabs1(
			getTabs1()
		).setParameter(
			"ddmTemplateId", ddmTemplate.getTemplateId()
		).buildString();
	}

	public String getDDMTemplateScope(DDMTemplate ddmTemplate)
		throws PortalException {

		Group group = GroupLocalServiceUtil.getGroup(ddmTemplate.getGroupId());

		return LanguageUtil.get(
			themeDisplay.getLocale(), group.getScopeLabel(themeDisplay));
	}

	public long getResourceClassNameId() {
		if (_resourceClassNameId != null) {
			return _resourceClassNameId;
		}

		_resourceClassNameId = PortalUtil.getClassNameId(
			PortletDisplayTemplate.class);

		return _resourceClassNameId;
	}

	public SearchContainer<DDMTemplate> getTemplateSearchContainer() {
		if (_ddmTemplateSearchContainer != null) {
			return _ddmTemplateSearchContainer;
		}

		SearchContainer<DDMTemplate> ddmTemplateSearchContainer =
			new SearchContainer<>(
				liferayPortletRequest, getPortletURL(), null,
				"there-are-no-templates");

		ddmTemplateSearchContainer.setOrderByCol(getOrderByCol());
		ddmTemplateSearchContainer.setOrderByComparator(
			_getTemplateOrderByComparator());
		ddmTemplateSearchContainer.setOrderByType(getOrderByType());

		if (Validator.isNull(getKeywords())) {
			ddmTemplateSearchContainer.setResultsAndTotal(
				() -> DDMTemplateServiceUtil.getTemplates(
					themeDisplay.getCompanyId(),
					new long[] {themeDisplay.getScopeGroupId()},
					getClassNameIds(), null, getResourceClassNameId(),
					ddmTemplateSearchContainer.getStart(),
					ddmTemplateSearchContainer.getEnd(),
					ddmTemplateSearchContainer.getOrderByComparator()),
				DDMTemplateServiceUtil.getTemplatesCount(
					themeDisplay.getCompanyId(),
					new long[] {themeDisplay.getScopeGroupId()},
					getClassNameIds(), null, getResourceClassNameId()));
		}
		else {
			ddmTemplateSearchContainer.setResultsAndTotal(
				() -> DDMTemplateServiceUtil.search(
					themeDisplay.getCompanyId(),
					new long[] {themeDisplay.getScopeGroupId()},
					getClassNameIds(), null, getResourceClassNameId(),
					getKeywords(), StringPool.BLANK, StringPool.BLANK,
					WorkflowConstants.STATUS_ANY,
					ddmTemplateSearchContainer.getStart(),
					ddmTemplateSearchContainer.getEnd(),
					ddmTemplateSearchContainer.getOrderByComparator()),
				DDMTemplateServiceUtil.searchCount(
					themeDisplay.getCompanyId(),
					new long[] {themeDisplay.getScopeGroupId()},
					getClassNameIds(), null, getResourceClassNameId(),
					getKeywords(), StringPool.BLANK, StringPool.BLANK,
					WorkflowConstants.STATUS_ANY));
		}

		ddmTemplateSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(liferayPortletResponse));

		_ddmTemplateSearchContainer = ddmTemplateSearchContainer;

		return _ddmTemplateSearchContainer;
	}

	public String getTemplateTypeLabel(long classNameId) {
		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(classNameId);

		return templateHandler.getName(themeDisplay.getLocale());
	}

	private OrderByComparator<DDMTemplate> _getTemplateOrderByComparator() {
		OrderByComparator<DDMTemplate> orderByComparator = null;

		boolean orderByAsc = false;

		if (Objects.equals(getOrderByType(), "asc")) {
			orderByAsc = true;
		}

		if (Objects.equals(getOrderByCol(), "id")) {
			orderByComparator = new TemplateIdComparator(orderByAsc);
		}
		else if (Objects.equals(getOrderByCol(), "modified-date")) {
			orderByComparator = new TemplateModifiedDateComparator(orderByAsc);
		}

		return orderByComparator;
	}

	private long[] _classNameIds;
	private SearchContainer<DDMTemplate> _ddmTemplateSearchContainer;
	private final PortletDisplayTemplate _portletDisplayTemplate;
	private Long _resourceClassNameId;

}