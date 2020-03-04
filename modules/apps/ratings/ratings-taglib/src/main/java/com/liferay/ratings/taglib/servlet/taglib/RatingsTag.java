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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.ratings.kernel.model.RatingsStats;
import com.liferay.ratings.kernel.service.RatingsEntryLocalServiceUtil;
import com.liferay.ratings.kernel.service.RatingsStatsLocalServiceUtil;
import com.liferay.ratings.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;
import com.liferay.trash.kernel.util.TrashUtil;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

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

	public RatingsEntry getRatingsEntry() {
		return _ratingsEntry;
	}

	public RatingsStats getRatingsStats() {
		return _ratingsStats;
	}

	public String getType() {
		return _type;
	}

	public String getUrl() {
		return _url;
	}

	public boolean isInTrash() {
		return _inTrash;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setInTrash(Boolean inTrash) {
		_inTrash = inTrash;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setRatingsEntry(RatingsEntry ratingsEntry) {
		_ratingsEntry = ratingsEntry;

		_setRatingsEntry = true;
	}

	public void setRatingsStats(RatingsStats ratingsStats) {
		_ratingsStats = ratingsStats;

		_setRatingsStats = true;
	}

	public void setType(String type) {
		_type = type;
	}

	public void setUrl(String url) {
		_url = url;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_className = null;
		_classPK = 0;
		_inTrash = null;
		_ratingsEntry = null;
		_ratingsStats = null;
		_setRatingsEntry = false;
		_setRatingsStats = false;
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

			boolean inTrash = _isInTrash();

			RatingsStats ratingsStats = _getRatingsStats();

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			RatingsEntry ratingsEntry = _getRatingsEntry(
				ratingsStats, themeDisplay);

			String url = _getURL(themeDisplay);

			httpServletRequest.setAttribute(
				"liferay-ratings:ratings:data",
				_getData(inTrash, ratingsEntry, themeDisplay, url));

			httpServletRequest.setAttribute(
				"liferay-ratings:ratings:inTrash", inTrash);
			httpServletRequest.setAttribute(
				"liferay-ratings:ratings:type", _type);
			httpServletRequest.setAttribute("liferay-ratings:ratings:url", url);
			httpServletRequest.setAttribute(
				"liferay:ratings:ratings:ratingsEntry", ratingsEntry);
			httpServletRequest.setAttribute(
				"liferay:ratings:ratings:ratingsStats", ratingsStats);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	private Map<String, Object> _getData(
		boolean inTrash, RatingsEntry ratingsEntry, ThemeDisplay themeDisplay,
		String url) {

		return HashMapBuilder.<String, Object>put(
			"className", _className
		).put(
			"classPK", _classPK
		).put(
			"enabled", _isEnabled(themeDisplay, inTrash)
		).put(
			"inTrash", inTrash
		).put(
			"isLiked", _isLiked(ratingsEntry)
		).put(
			"positiveVotes", (int)Math.round(_getTotalScore())
		).put(
			"signedIn", themeDisplay.isSignedIn()
		).put(
			"url", url
		).build();
	}

	private RatingsEntry _getRatingsEntry(
		RatingsStats ratingsStats, ThemeDisplay themeDisplay) {

		if (!_setRatingsEntry && (ratingsStats != null)) {
			return RatingsEntryLocalServiceUtil.fetchEntry(
				themeDisplay.getUserId(), _className, _classPK);
		}

		return _ratingsEntry;
	}

	private RatingsStats _getRatingsStats() {
		if (!_setRatingsStats) {
			return RatingsStatsLocalServiceUtil.fetchStats(
				_className, _classPK);
		}

		return _ratingsStats;
	}

	private double _getTotalScore() {
		RatingsStats ratingsStats = RatingsStatsLocalServiceUtil.fetchStats(
			_className, _classPK);

		if (ratingsStats != null) {
			return ratingsStats.getTotalScore();
		}

		return 0.0;
	}

	private String _getURL(ThemeDisplay themeDisplay) {
		if (Validator.isNull(_url)) {
			return themeDisplay.getPathMain() + "/portal/rate_entry";
		}

		return _url;
	}

	private boolean _isEnabled(ThemeDisplay themeDisplay, boolean inTrash) {
		if (!inTrash) {
			Group group = themeDisplay.getSiteGroup();

			if (!group.isStagingGroup() && !group.isStagedRemotely()) {
				return true;
			}
		}

		return false;
	}

	private boolean _isInTrash() throws PortalException {
		if (_inTrash == null) {
			return TrashUtil.isInTrash(_className, _classPK);
		}

		return _inTrash;
	}

	private Object _isLiked(RatingsEntry ratingsEntry) {
		double yourScore = -1.0;

		if (ratingsEntry != null) {
			yourScore = ratingsEntry.getScore();
		}

		return (yourScore != -1.0) && (yourScore >= 0.5);
	}

	private static final String _PAGE = "/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(RatingsTag.class);

	private String _className;
	private long _classPK;
	private Boolean _inTrash;
	private RatingsEntry _ratingsEntry;
	private RatingsStats _ratingsStats;
	private boolean _setRatingsEntry;
	private boolean _setRatingsStats;
	private String _type;
	private String _url;

}