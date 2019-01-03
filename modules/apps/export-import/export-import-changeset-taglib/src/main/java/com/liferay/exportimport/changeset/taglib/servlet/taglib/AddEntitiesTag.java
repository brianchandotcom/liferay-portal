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
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.taglib.TagSupport;

import java.util.Collection;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

/**
 * @author Máté Thurzó
 */
public class AddEntitiesTag extends TagSupport {

	@Override
	public int doStartTag() throws JspException {
		Tag parentTag = getParent();

		if ((parentTag == null) || !(parentTag instanceof CreateTag)) {
			throw new JspException(
				"The add entities tag must exist under a create tag");
		}

		CreateTag createTag = (CreateTag)parentTag;

		Changeset.RawBuilder rawBuilder = createTag.getRawBuilder();

		rawBuilder.addMultipleStagedModel(_stagedModels);

		return EVAL_BODY_INCLUDE;
	}

	public void setStagedModels(Collection<StagedModel> stagedModels) {
		_stagedModels = stagedModels;
	}

	private Collection<StagedModel> _stagedModels;

}