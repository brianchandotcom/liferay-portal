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

package com.liferay.portal.model;

import com.liferay.portal.NoSuchLayoutBranchException;
import com.liferay.portal.NoSuchLayoutRevisionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.LayoutBranchLocalServiceUtil;
import com.liferay.portal.service.LayoutRevisionLocalServiceUtil;
import com.liferay.portal.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.LayoutTypePortletFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class LayoutStagingHandler implements InvocationHandler, Serializable {

	public LayoutStagingHandler(Layout layout) {
		this(layout, null);
	}

	public Layout getLayout() {
		return _layout;
	}

	public LayoutRevision getLayoutRevision() {
		return _layoutRevision;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] arguments)
		throws Throwable {

		try {
			if (_layoutRevision == null) {
				return method.invoke(_layout, arguments);
			}

			String methodName = method.getName();

			if (methodName.equals("getLayoutType")) {
				return _getLayoutType();
			}
			else if (methodName.equals("toEscapedModel")) {
				if (_layout.isEscapedModel()) {
					return this;
				}

				return _toEscapedModel();
			}

			if (methodName.equals("clone")) {
				return _clone();
			}

			Object bean = _layout;

			if (_layoutRevisionMethodNames.contains(methodName)) {
				try {
					Class<?> layoutRevisionClass = _layoutRevision.getClass();

					method = layoutRevisionClass.getMethod(
						methodName,
						ReflectionUtil.getParameterTypes(arguments));

					bean = _layoutRevision;
				}
				catch (NoSuchMethodException nsme) {
					_log.error(nsme, nsme);
				}
			}

			return method.invoke(bean, arguments);
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}

	public void setLayoutRevision(LayoutRevision layoutRevision) {
		_layoutRevision = layoutRevision;
	}

	private LayoutStagingHandler(Layout layout, LayoutRevision layoutRevision) {
		_layout = layout;

		try {
			_layoutRevision = _getLayoutRevision(layout, layoutRevision);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new IllegalStateException(e);
		}
	}

	private Object _clone() {
		return ProxyUtil.newProxyInstance(
			PortalClassLoaderUtil.getClassLoader(), new Class[] {Layout.class},
			new LayoutStagingHandler(_layout, _layoutRevision));
	}

	private LayoutRevision _getLayoutRevision(
			Layout layout, LayoutRevision layoutRevision)
		throws PortalException, SystemException {

		if (layoutRevision != null) {
			return layoutRevision;
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (!serviceContext.isSignedIn()) {
			LayoutRevision lastLayoutRevision = null;

			lastLayoutRevision =
				LayoutRevisionLocalServiceUtil.fetchLastLayoutRevision(
					layout.getPlid(), true);

			if (lastLayoutRevision == null) {
				lastLayoutRevision =
					LayoutRevisionLocalServiceUtil.fetchLastLayoutRevision(
						layout.getPlid(), false);
			}

			return lastLayoutRevision;
		}

		long layoutSetBranchId = ParamUtil.getLong(
			serviceContext, "layoutSetBranchId");

		LayoutSet layoutSet = layout.getLayoutSet();

		LayoutSetBranch layoutSetBranch =
			LayoutSetBranchLocalServiceUtil.getUserLayoutSetBranch(
				serviceContext.getUserId(), layout.getGroupId(),
				layout.isPrivateLayout(), layoutSet.getLayoutSetId(),
				layoutSetBranchId);

		layoutSetBranchId = layoutSetBranch.getLayoutSetBranchId();

		long layoutRevisionId = ParamUtil.getLong(
			serviceContext, "layoutRevisionId");

		if (layoutSetBranchId > 0) {
			if (layoutRevisionId <= 0) {
				User user = UserLocalServiceUtil.getUser(
					serviceContext.getUserId());

				layoutRevisionId = StagingUtil.getRecentLayoutRevisionId(
					user, layoutSetBranchId, layout.getPlid());

				if (layoutRevisionId > 0) {
					try {
						layoutRevision =
							LayoutRevisionLocalServiceUtil.getLayoutRevision(
								layoutRevisionId);

						if (layoutRevision.getStatus() !=
								WorkflowConstants.STATUS_INACTIVE) {

							return layoutRevision;
						}

						StagingUtil.setRecentLayoutRevisionId(
							user, layoutSetBranchId, layout.getPlid(),
							LayoutRevisionConstants.
								DEFAULT_PARENT_LAYOUT_REVISION_ID);

						layoutRevisionId =
							StagingUtil.getRecentLayoutRevisionId(
								user, layoutSetBranchId, layout.getPlid());
					}
					catch (NoSuchLayoutRevisionException nslre) {
					}
				}
			}

			if (layoutRevisionId > 0) {
				try {
					return LayoutRevisionLocalServiceUtil.getLayoutRevision(
						layoutRevisionId);
				}
				catch (NoSuchLayoutRevisionException nslre) {
				}
			}

			try {
				return LayoutRevisionLocalServiceUtil.getLayoutRevision(
					layoutSetBranchId, layout.getPlid(), true);
			}
			catch (NoSuchLayoutRevisionException nslre) {
				List<LayoutRevision> layoutRevisions =
					LayoutRevisionLocalServiceUtil.getChildLayoutRevisions(
						layoutSetBranchId,
						LayoutRevisionConstants.
							DEFAULT_PARENT_LAYOUT_REVISION_ID,
						layout.getPlid());

				if (!layoutRevisions.isEmpty()) {
					return layoutRevisions.get(0);
				}
			}
		}

		LayoutBranch layoutBranch = null;

		try {
			layoutBranch = LayoutBranchLocalServiceUtil.getMasterLayoutBranch(
				layoutSetBranchId, layout.getPlid());
		}
		catch (NoSuchLayoutBranchException nslbe) {
			layoutBranch = LayoutBranchLocalServiceUtil.addLayoutBranch(
				layoutSetBranchId, layout.getPlid(),
				LayoutBranchConstants.MASTER_BRANCH_NAME,
				LayoutBranchConstants.MASTER_BRANCH_DESCRIPTION, true,
				serviceContext);
		}

		if (!MergeLayoutPrototypesThreadLocal.isInProgress()) {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}

		layoutRevision = LayoutRevisionLocalServiceUtil.addLayoutRevision(
			serviceContext.getUserId(), layoutSetBranchId,
			layoutBranch.getLayoutBranchId(),
			LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID, false,
			layout.getPlid(), LayoutConstants.DEFAULT_PLID,
			layout.isPrivateLayout(), layout.getName(), layout.getTitle(),
			layout.getDescription(), layout.getKeywords(), layout.getRobots(),
			layout.getTypeSettings(), layout.getIconImage(),
			layout.getIconImageId(), layout.getThemeId(),
			layout.getColorSchemeId(), layout.getWapThemeId(),
			layout.getWapColorSchemeId(), layout.getCss(), serviceContext);

		boolean explicitCreation = ParamUtil.getBoolean(
			serviceContext, "explicitCreation");

		if (!explicitCreation) {
			LayoutRevisionLocalServiceUtil.updateStatus(
				serviceContext.getUserId(),
				layoutRevision.getLayoutRevisionId(),
				WorkflowConstants.STATUS_INCOMPLETE, serviceContext);
		}

		return layoutRevision;
	}

	private LayoutType _getLayoutType() {
		return LayoutTypePortletFactoryUtil.create(
			(Layout)ProxyUtil.newProxyInstance(
				PortalClassLoaderUtil.getClassLoader(),
				new Class[] {Layout.class},
				new LayoutStagingHandler(_layout, _layoutRevision)));
	}

	private Object _toEscapedModel() {
		return ProxyUtil.newProxyInstance(
			PortalClassLoaderUtil.getClassLoader(), new Class[] {Layout.class},
			new LayoutStagingHandler(
				_layout.toEscapedModel(), _layoutRevision.toEscapedModel()));
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutStagingHandler.class);

	private static Set<String> _layoutRevisionMethodNames =
		new HashSet<String>();

	static {
		_layoutRevisionMethodNames.add("getColorScheme");
		_layoutRevisionMethodNames.add("getColorSchemeId");
		_layoutRevisionMethodNames.add("getCss");
		_layoutRevisionMethodNames.add("getCssText");
		_layoutRevisionMethodNames.add("getDescription");
		_layoutRevisionMethodNames.add("getHTMLTitle");
		_layoutRevisionMethodNames.add("getIconImage");
		_layoutRevisionMethodNames.add("getIconImageId");
		_layoutRevisionMethodNames.add("getKeywords");
		_layoutRevisionMethodNames.add("getName");
		_layoutRevisionMethodNames.add("getRobots");
		_layoutRevisionMethodNames.add("getTheme");
		_layoutRevisionMethodNames.add("getThemeId");
		_layoutRevisionMethodNames.add("getTitle");
		_layoutRevisionMethodNames.add("getTypeSettings");
		_layoutRevisionMethodNames.add("getTypeSettingsProperties");
		_layoutRevisionMethodNames.add("getWapColorScheme");
		_layoutRevisionMethodNames.add("getWapColorSchemeId");
		_layoutRevisionMethodNames.add("getWapTheme");
		_layoutRevisionMethodNames.add("getWapThemeId");
		_layoutRevisionMethodNames.add("isEscapedModel");
		_layoutRevisionMethodNames.add("isIconImage");
		_layoutRevisionMethodNames.add("isInheritLookAndFeel");
		_layoutRevisionMethodNames.add("isInheritWapLookAndFeel");
		_layoutRevisionMethodNames.add("setColorSchemeId");
		_layoutRevisionMethodNames.add("setCss");
		_layoutRevisionMethodNames.add("setDescription");
		_layoutRevisionMethodNames.add("setDescriptionMap");
		_layoutRevisionMethodNames.add("setEscapedModel");
		_layoutRevisionMethodNames.add("setIconImage");
		_layoutRevisionMethodNames.add("setIconImageId");
		_layoutRevisionMethodNames.add("setKeywords");
		_layoutRevisionMethodNames.add("setKeywordsMap");
		_layoutRevisionMethodNames.add("setName");
		_layoutRevisionMethodNames.add("setNameMap");
		_layoutRevisionMethodNames.add("setRobots");
		_layoutRevisionMethodNames.add("setRobotsMap");
		_layoutRevisionMethodNames.add("setThemeId");
		_layoutRevisionMethodNames.add("setTitle");
		_layoutRevisionMethodNames.add("setTitleMap");
		_layoutRevisionMethodNames.add("setTypeSettings");
		_layoutRevisionMethodNames.add("setTypeSettingsProperties");
		_layoutRevisionMethodNames.add("setWapColorSchemeId");
		_layoutRevisionMethodNames.add("setWapThemeId");
	}

	private Layout _layout;
	private LayoutRevision _layoutRevision;

}