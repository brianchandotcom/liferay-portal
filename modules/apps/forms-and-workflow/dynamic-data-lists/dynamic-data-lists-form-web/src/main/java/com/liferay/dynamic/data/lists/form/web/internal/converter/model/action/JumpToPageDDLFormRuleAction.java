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

package com.liferay.dynamic.data.lists.form.web.internal.converter.model.action;

import com.liferay.dynamic.data.lists.form.web.internal.converter.serializer.DDLFormRuleActionSerializer;
import com.liferay.dynamic.data.lists.form.web.internal.converter.serializer.JumpToPageDDLFormRuleActionSerializer;
import com.liferay.portal.kernel.util.HashUtil;

import java.util.Objects;

/**
 * @author Leonardo Barros
 */
public class JumpToPageDDLFormRuleAction extends DefaultDDLFormRuleAction {

	public JumpToPageDDLFormRuleAction() {
	}

	public JumpToPageDDLFormRuleAction(String source, String target) {
		super("jump-to-page", target);

		_source = source;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof JumpToPageDDLFormRuleAction)) {
			return false;
		}

		JumpToPageDDLFormRuleAction ddlFormRuleAction =
			(JumpToPageDDLFormRuleAction)obj;

		if (super.equals(obj) &&
			Objects.equals(_source, ddlFormRuleAction._source)) {

			return true;
		}

		return false;
	}

	public String getSource() {
		return _source;
	}

	@Override
	public int hashCode() {
		int hash = super.hashCode();

		return HashUtil.hash(hash, _source);
	}

	@Override
	public String serialize() {
		return _ddlFormRuleActionSerializer.serialize(this);
	}

	public void setSource(String source) {
		_source = source;
	}

	private static final
		DDLFormRuleActionSerializer<JumpToPageDDLFormRuleAction>
			_ddlFormRuleActionSerializer =
				new JumpToPageDDLFormRuleActionSerializer();

	private String _source;

}