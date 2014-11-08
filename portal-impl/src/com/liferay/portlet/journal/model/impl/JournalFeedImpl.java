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

package com.liferay.portlet.journal.model.impl;

/**
 * @author Brian Wing Shun Chan
 */
public class JournalFeedImpl extends JournalFeedBaseImpl {

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getDDMRendererTemplateKey()}
	 */
	@Override
	public String getRendererTemplateId() {
		return getDDMRendererTemplateKey();
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getDDMStructureKey()}
	 */
	@Override
	public String getStructureId() {
		return getDDMStructureKey();
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getDDMTemplateKey()}
	 */
	@Override
	public String getTemplateId() {
		return getDDMTemplateKey();
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #setRendererTemplateKey()}
	 */
	@Override
	public void setRendererTemplateId(String rendererTemplateKey) {
		setDDMRendererTemplateKey(rendererTemplateKey);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #setDDMStructureKey()}
	 */
	@Override
	public void setStructureId(String structureKey) {
		setDDMStructureKey(structureKey);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #setDDMTemplateKey()}
	 */
	@Override
	public void setTemplateId(String templateKey) {
		setDDMTemplateKey(templateKey);
	}

}