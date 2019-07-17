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

package com.liferay.exportimport.changeset.taglib.servlet.taglib;

import com.liferay.exportimport.changeset.Changeset;
import com.liferay.exportimport.changeset.ChangesetManager;
import com.liferay.exportimport.changeset.ChangesetManagerUtil;
import com.liferay.exportimport.changeset.taglib.internal.servlet.ServletContextUtil;
import com.liferay.exportimport.kernel.lar.ExportImportClassedModelUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Máté Thurzó
 */
public class PublishModelMenuItemTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		Changeset.Builder builder = Changeset.create();

		Changeset changeset = builder.addStagedModel(
			() -> _stagedModel
		).build();

		ChangesetManager changesetManager =
			ChangesetManagerUtil.getChangesetManager();

		changesetManager.addChangeset(changeset);

		return EVAL_BODY_INCLUDE;
	}

	public StagedModel getStagedModel() {
		return _stagedModel;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setStagedModel(StagedModel stagedModel) {
		_stagedModel = stagedModel;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_changesetUuid = StringPool.BLANK;
		_stagedModel = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-export-import-changeset:publish-model-menu-item:" +
				"changesetUuid",
			_changesetUuid);

		if (_stagedModel == null) {
			return;
		}

		httpServletRequest.setAttribute(
			"liferay-export-import-changeset:publish-model-menu-item:className",
			ExportImportClassedModelUtil.getClassName(_stagedModel));

		httpServletRequest.setAttribute(
			"liferay-export-import-changeset:publish-model-menu-item:uuid",
			_stagedModel.getUuid());
	}

	private static final String _PAGE = "/publish_model_menu_item/page.jsp";

	private String _changesetUuid = StringPool.BLANK;
	private StagedModel _stagedModel;

}