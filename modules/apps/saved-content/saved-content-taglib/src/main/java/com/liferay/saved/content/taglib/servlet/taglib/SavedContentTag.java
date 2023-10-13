/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saved.content.taglib.servlet.taglib;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.saved.content.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Alicia Garcia
 */
public class SavedContentTag extends IncludeTag {

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public long getGroupId() {
		return _groupId;
	}

	public boolean isInTrash() {
		return _inTrash;
	}

	public void setClassName(String className) {
		if (className.equals(DLFileEntry.class.getName()) ||
			className.equals(FileEntry.class.getName()) ||
			className.equals(LiferayFileEntry.class.getName())) {

			className = DLFileEntryConstants.getClassName();
		}

		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setInTrash(boolean inTrash) {
		_inTrash = inTrash;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_className = null;
		_classPK = 0;
		_groupId = 0;
		_inTrash = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		try {
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private static final String _PAGE = "/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		SavedContentTag.class);

	private String _className;
	private long _classPK;
	private long _groupId;
	private Boolean _inTrash;

}