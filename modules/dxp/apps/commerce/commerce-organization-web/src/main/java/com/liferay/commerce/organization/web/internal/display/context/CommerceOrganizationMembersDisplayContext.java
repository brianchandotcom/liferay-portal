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

package com.liferay.commerce.organization.web.internal.display.context;

import com.liferay.commerce.organization.service.CommerceOrganizationService;
import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.commerce.organization.web.internal.servlet.taglib.ui.CommerceOrganizationScreenNavigationConstants;
import com.liferay.commerce.organization.web.internal.util.CommerceOrganizationPortletUtil;
import com.liferay.commerce.user.constants.CommerceUserPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.LinkedHashMap;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class CommerceOrganizationMembersDisplayContext
	extends BaseCommerceOrganizationDisplayContext {

	public CommerceOrganizationMembersDisplayContext(
		CommerceOrganizationHelper commerceOrganizationHelper,
		CommerceOrganizationService commerceOrganizationService,
		HttpServletRequest httpServletRequest, Portal portal) {

		super(
			commerceOrganizationHelper, commerceOrganizationService,
			httpServletRequest, portal);

		setDefaultOrderByCol("name");
		setDefaultOrderByType("asc");
	}

	public String getEditURL(User user) throws PortalException {
		long groupId = portal.getScopeGroupId(
			commerceOrganizationRequestHelper.getRequest());

		long plid = portal.getPlidFromPortletId(
			groupId, CommerceUserPortletKeys.COMMERCE_USER);

		if (plid <= 0) {
			return StringPool.BLANK;
		}

		PortletURL portletURL = PortletURLFactoryUtil.create(
			commerceOrganizationRequestHelper.getRequest(),
			CommerceUserPortletKeys.COMMERCE_USER, plid,
			PortletRequest.RENDER_PHASE);

		String redirect = portal.getCurrentCompleteURL(
			commerceOrganizationRequestHelper.getRequest());

		portletURL.setParameter("redirect", redirect);

		portletURL.setParameter("userId", String.valueOf(user.getUserId()));

		return portletURL.toString();
	}

	public String getInviteUserHref() throws WindowStateException {
		HttpServletRequest httpServletRequest =
			commerceOrganizationRequestHelper.getRequest();
		LiferayPortletResponse liferayPortletResponse =
			commerceOrganizationRequestHelper.getLiferayPortletResponse();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "inviteUser");
		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		StringBundler sb = new StringBundler(9);

		sb.append("javascript:");
		sb.append(liferayPortletResponse.getNamespace());
		sb.append("inviteUser");
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(StringPool.APOSTROPHE);
		sb.append(portletURL.toString());
		sb.append(StringPool.APOSTROPHE);
		sb.append(StringPool.CLOSE_PARENTHESIS);
		sb.append(StringPool.SEMICOLON);

		return sb.toString();
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(
			commerceOrganizationRequestHelper.getRequest(), "keywords");

		return _keywords;
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter(
			"screenNavigationCategoryKey",
			CommerceOrganizationScreenNavigationConstants.CATEGORY_DETAILS);

		portletURL.setParameter(
			"screenNavigationEntryKey",
			CommerceOrganizationScreenNavigationConstants.
				ENTRY_KEY_ORGANIZATION_MEMBERS);

		return portletURL;
	}

	public SearchContainer<User> getSearchContainer() throws PortalException {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			commerceOrganizationRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, null);

		_searchContainer.setEmptyResultsMessage("no-users-were-found");

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		Organization organization = getCurrentOrganization();

		params.put("usersOrgs", organization.getOrganizationId());

		OrderByComparator<User> orderByComparator =
			CommerceOrganizationPortletUtil.getUserOrderByComparator(
				getOrderByCol(), getOrderByType());

		_searchContainer.setOrderByCol(getOrderByCol());
		_searchContainer.setOrderByComparator(orderByComparator);
		_searchContainer.setOrderByType(getOrderByType());
		_searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(
				commerceOrganizationRequestHelper.getLiferayPortletResponse()));

		Sort sort = CommerceOrganizationPortletUtil.getUserSort(
			getOrderByCol(), getOrderByType());

		BaseModelSearchResult<User> userBaseModelSearchResult =
			UserLocalServiceUtil.searchUsers(
				commerceOrganizationRequestHelper.getCompanyId(), getKeywords(),
				WorkflowConstants.STATUS_ANY, params,
				_searchContainer.getStart(), _searchContainer.getEnd(), sort);

		_searchContainer.setTotal(userBaseModelSearchResult.getLength());
		_searchContainer.setResults(userBaseModelSearchResult.getBaseModels());

		return _searchContainer;
	}

	private String _keywords;
	private SearchContainer<User> _searchContainer;

}