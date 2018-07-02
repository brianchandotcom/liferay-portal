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

package com.liferay.journal.model.impl;

/**
 * @author Brian Wing Shun Chan
 */
public class JournalFeedImpl extends JournalFeedBaseImpl {

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #getDDMRendererTemplateKey()}
	 */
	@Deprecated
	@Override
	public String getRendererTemplateId() {
		return getDDMRendererTemplateKey();
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #getDDMStructureKey()}
	 */
	@Deprecated
	@Override
	public String getStructureId() {
		return getDDMStructureKey();
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #getDDMTemplateKey()}
	 */
	@Deprecated
	@Override
	public String getTemplateId() {
		return getDDMTemplateKey();
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #setDDMRendererTemplateKey(String)}
	 */
	@Deprecated
	@Override
	public void setRendererTemplateId(String rendererTemplateKey) {
		setDDMRendererTemplateKey(rendererTemplateKey);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #setDDMStructureKey(String)}
	 */
	@Deprecated
	@Override
	public void setStructureId(String structureKey) {
		setDDMStructureKey(structureKey);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #setDDMTemplateKey(String)}
	 */
	@Deprecated
	@Override
	public void setTemplateId(String templateKey) {
		setDDMTemplateKey(templateKey);
	}

}