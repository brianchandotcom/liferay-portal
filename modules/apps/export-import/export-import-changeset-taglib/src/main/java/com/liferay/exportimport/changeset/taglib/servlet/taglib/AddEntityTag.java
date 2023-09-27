/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.changeset.taglib.servlet.taglib;

import com.liferay.exportimport.changeset.Changeset;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.taglib.TagSupport;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

/**
 * @author Máté Thurzó
 */
public class AddEntityTag extends TagSupport {

	@Override
	public int doStartTag() throws JspException {
		Tag parentTag = getParent();

		if ((parentTag == null) || !(parentTag instanceof CreateTag)) {
			throw new JspException(
				"The add entity tag must exist under a create tag");
		}

		CreateTag createTag = (CreateTag)parentTag;

		Changeset.RawBuilder rawBuilder = createTag.getRawBuilder();

		rawBuilder.addStagedModel(_stagedModel);

		return EVAL_BODY_INCLUDE;
	}

	public void setStagedModel(StagedModel stagedModel) {
		_stagedModel = stagedModel;
	}

	private StagedModel _stagedModel;

}