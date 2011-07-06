/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.SaveableModel;

/**
 * The extended model interface for the JournalStructure service. Represents a row in the &quot;JournalStructure&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see JournalStructureModel
 * @see com.liferay.portlet.journal.model.impl.JournalStructureImpl
 * @see com.liferay.portlet.journal.model.impl.JournalStructureModelImpl
 * @generated
 */
public interface JournalStructure extends JournalStructureModel, SaveableModel {
	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.journal.model.impl.JournalStructureImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public java.lang.String getMergedXsd();
}