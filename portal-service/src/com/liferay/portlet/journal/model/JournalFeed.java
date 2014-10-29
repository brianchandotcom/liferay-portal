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

package com.liferay.portlet.journal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the JournalFeed service. Represents a row in the &quot;JournalFeed&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see JournalFeedModel
 * @see com.liferay.portlet.journal.model.impl.JournalFeedImpl
 * @see com.liferay.portlet.journal.model.impl.JournalFeedModelImpl
 * @generated
 */
@ProviderType
public interface JournalFeed extends JournalFeedModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.journal.model.impl.JournalFeedImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getDDMRendererTemplateKey()}
	*/
	public java.lang.String getRendererTemplateId();

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getDDMStructureKey()}
	*/
	public java.lang.String getStructureId();

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getDDMTemplateKey()}
	*/
	public java.lang.String getTemplateId();

	/**
	* @deprecated As of 7.0.0, replaced by {@link #setRendererTemplateKey()}
	*/
	public void setRendererTemplateId(java.lang.String rendererTemplateKey);

	/**
	* @deprecated As of 7.0.0, replaced by {@link #setDDMStructureKey()}
	*/
	public void setStructureId(java.lang.String structureKey);

	/**
	* @deprecated As of 7.0.0, replaced by {@link #setDDMTemplateKey()}
	*/
	public void setTemplateId(java.lang.String templateKey);
}