/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.TreeNodeView;
import com.liferay.portal.kernel.util.TreeView;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletCategory;
import com.liferay.portal.model.User;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.comparator.PortletCategoryComparator;
import com.liferay.portal.util.comparator.PortletTitleComparator;
import com.liferay.portlet.PortletConfigFactoryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.PortletConfig;

import javax.servlet.ServletContext;

/**
 * @author Jorge Ferrer
 * @author Dennis Ju
 * @author Brian Wing Shun Chan
 */
public class PortletListerImpl implements PortletLister {

	@Override
	public TreeView getTreeView() throws PortalException, SystemException {
		_nodeId = 1;

		_list = new ArrayList<TreeNodeView>();

		TreeNodeView rootNodeView = null;

		if (_rootNodeName != null) {
			rootNodeView = new TreeNodeView(_nodeId);

			rootNodeView.setLeaf(false);
			rootNodeView.setName(_rootNodeName);

			_list.add(rootNodeView);
		}

		PortletCategory portletCategory = (PortletCategory)WebAppPool.get(
			_user.getCompanyId(), WebKeys.PORTLET_CATEGORY);

		List<PortletCategory> portletCategories = ListUtil.fromCollection(
			portletCategory.getCategories());

		iteratePortletCategories(rootNodeView, portletCategories, _nodeId, 0);

		return new TreeView(_list, _depth);
	}

	@Override
	public void setHierarchicalTree(boolean hierarchicalTree) {
		_hierarchicalTree = hierarchicalTree;
	}

	@Override
	public void setIncludeInstanceablePortlets(
		boolean includeInstanceablePortlets) {

		_includeInstanceablePortlets = includeInstanceablePortlets;
	}

	@Override
	public void setIteratePortlets(boolean iteratePortlets) {
		_iteratePortlets = iteratePortlets;
	}

	@Override
	public void setLayoutTypePortlet(LayoutTypePortlet layoutTypePortlet) {
		_layoutTypePortlet = layoutTypePortlet;
	}

	@Override
	public void setRootNodeName(String rootNodeName) {
		_rootNodeName = rootNodeName;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Override
	public void setThemeDisplay(ThemeDisplay themeDisplay) {
		_themeDisplay = themeDisplay;
	}

	@Override
	public void setUser(User user) {
		_user = user;
	}

	protected void iteratePortletCategories(
			TreeNodeView parentNodeView,
			List<PortletCategory> portletCategories, long parentId, int depth)
		throws PortalException, SystemException {

		portletCategories = ListUtil.sort(
			portletCategories,
			new PortletCategoryComparator(_themeDisplay.getLocale()));

		for (int i = 0; i < portletCategories.size(); i++) {
			PortletCategory portletCategory = portletCategories.get(i);

			if (portletCategory.isHidden()) {
				continue;
			}

			if (i == 0) {
				depth++;

				if (depth > _depth) {
					_depth = depth;
				}
			}

			TreeNodeView nodeView = new TreeNodeView(++_nodeId);

			nodeView.setDepth(depth);
			nodeView.setLeaf(false);

			if ((i + 1) == portletCategories.size()) {
				nodeView.setLs("1");
			}
			else {
				nodeView.setLs("0");
			}

			nodeView.setName(
				LanguageUtil.get(
					_themeDisplay.getLocale(), portletCategory.getName()));
			nodeView.setObjId(portletCategory.getPath());
			nodeView.setParentId(parentId);

			if (_hierarchicalTree) {
				if (parentNodeView != null) {
					parentNodeView.addChild(nodeView);
				}
			}
			else {
				_list.add(nodeView);
			}

			int nodeId = _nodeId;

			List<PortletCategory> subCategories = ListUtil.fromCollection(
				portletCategory.getCategories());

			iteratePortletCategories(nodeView, subCategories, nodeId, depth);

			if (_iteratePortlets) {
				iteratePortlets(
					nodeView, portletCategory, portletCategory.getPortletIds(),
					nodeId, depth + 1);
			}
		}
	}

	protected void iteratePortlets(
			TreeNodeView parentNodeView, PortletCategory portletCategory,
			Set<String> portletIds, int parentNodeId, int depth)
		throws PortalException, SystemException {

		List<Portlet> portlets = new ArrayList<Portlet>();

		String externalPortletCategory = null;

		for (String portletId : portletIds) {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				_user.getCompanyId(), portletId);

			if (portlet != null) {
				if (portlet.isSystem()) {
				}
				else if (!portlet.isActive()) {
				}
				else if (portlet.isInstanceable() &&
						 !_includeInstanceablePortlets) {
				}
				else if (!portlet.isInstanceable() &&
						 _layoutTypePortlet.hasPortletId(
							portlet.getPortletId())) {

					portlets.add(portlet);
				}
				else if (!portlet.hasAddPortletPermission(_user.getUserId())) {
				}
				else {
					portlets.add(portlet);
				}

				PortletApp portletApp = portlet.getPortletApp();

				if (portletApp.isWARFile() &&
					Validator.isNull(externalPortletCategory)) {

					PortletConfig portletConfig =
						PortletConfigFactoryUtil.create(
							portlet, _servletContext);

					ResourceBundle resourceBundle =
						portletConfig.getResourceBundle(
							_themeDisplay.getLocale());

					externalPortletCategory = ResourceBundleUtil.getString(
						resourceBundle, portletCategory.getName());
				}
			}
		}

		portlets = ListUtil.sort(
			portlets, new PortletTitleComparator(_themeDisplay.getLocale()));

		for (int i = 0; i < portlets.size(); i++) {
			Portlet portlet = portlets.get(i);

			TreeNodeView nodeView = new TreeNodeView(++_nodeId);

			nodeView.setDepth(depth);
			nodeView.setLeaf(true);

			if ((i + 1) == portlets.size()) {
				nodeView.setLs("1");
			}
			else {
				nodeView.setLs("0");
			}

			nodeView.setName(
				PortalUtil.getPortletTitle(
					portlet, _servletContext, _themeDisplay.getLocale()));
			nodeView.setObjId(portlet.getRootPortletId());
			nodeView.setParentId(parentNodeId);

			if (_hierarchicalTree) {
				parentNodeView.addChild(nodeView);
			}
			else {
				_list.add(nodeView);
			}
		}
	}

	private int _depth;
	private boolean _hierarchicalTree;
	private boolean _includeInstanceablePortlets;
	private boolean _iteratePortlets;
	private LayoutTypePortlet _layoutTypePortlet;
	private List<TreeNodeView> _list;
	private int _nodeId;
	private String _rootNodeName;
	private ServletContext _servletContext;
	private ThemeDisplay _themeDisplay;
	private User _user;

}