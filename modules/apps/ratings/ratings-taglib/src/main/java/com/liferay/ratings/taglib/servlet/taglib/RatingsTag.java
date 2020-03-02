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

package com.liferay.ratings.taglib.servlet.taglib;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.ratings.kernel.model.RatingsStats;
import com.liferay.ratings.kernel.service.RatingsStatsLocalServiceUtil;
import com.liferay.ratings.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;
import com.liferay.trash.kernel.util.TrashUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import java.util.Map;

/**
 * @author Ambrín Chaudhary
 */
public class RatingsTag extends IncludeTag {

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public boolean isInTrash() {
		return _inTrash;
	}

	public String getType() {
		return _type;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setUrl(String url) {
		_url = url;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setType(String type) {
		_type = type;
	}


	@Override
	protected void cleanUp() {
		super.cleanUp();

		_className = null;
		_classPK = 0;
		_inTrash = null;
		_type = null;
		_url = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		try {
			httpServletRequest.setAttribute(
				"liferay-ratings:ratings:className", _className);
			httpServletRequest.setAttribute(
				"liferay-ratings:ratings:classPK", String.valueOf(_classPK));

			if (_inTrash != null) {
				httpServletRequest.setAttribute(
					"liferay-ratings:ratings:inTrash", _inTrash);
			}
			httpServletRequest.setAttribute(
				"liferay-ratings:ratings:type", _type);

			httpServletRequest.setAttribute(
				"liferay-ratings:ratings:data", _getData(httpServletRequest));

			httpServletRequest.setAttribute("liferay-ratings:ratings:url", _url);

		} catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	private Map<String, Object> _getData(HttpServletRequest httpServletRequest)
		throws PortalException {

		double totalScore = 0.0;

		RatingsStats ratingsStats = RatingsStatsLocalServiceUtil.fetchStats(_className, _classPK);

		if (ratingsStats != null) {
			totalScore = ratingsStats.getTotalScore();
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Boolean inTrash = _inTrash;

		if (inTrash == null) {
			inTrash = TrashUtil.isInTrash(_className, _classPK);
		}

		boolean enabled = false;

		if (!inTrash) {
			Group group = themeDisplay.getSiteGroup();

			if (!group.isStagingGroup() && !group.isStagedRemotely()) {
				enabled = true;
			}
		}

		String url = _url;

		if (Validator.isNull(url)) {
			url = themeDisplay.getPathMain() + "/portal/rate_entry";
		}

		return HashMapBuilder.<String, Object>put(
			"positiveVotes",
			(int)Math.round(totalScore)
		).put(
			"className", _className
		).put(
			"classPK", _classPK
		).put(
			"enabled", enabled
		).put(
			"inTrash", inTrash
		).put(
			"signedIn", themeDisplay.isSignedIn()
		).put(
			"url", url
		).build();
	}

	private static final String _PAGE = "/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(RatingsTag.class);

	private String _className;
	private long _classPK;
	private Boolean _inTrash;
	private String _type;
	private String _url;
}