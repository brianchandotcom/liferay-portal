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

package com.liferay.portlet.messageboards.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the MBMailingList service. Represents a row in the &quot;MBMailingList&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see MBMailingListModel
 * @see com.liferay.portlet.messageboards.model.impl.MBMailingListImpl
 * @see com.liferay.portlet.messageboards.model.impl.MBMailingListModelImpl
 * @generated
 */
@ProviderType
public interface MBMailingList extends MBMailingListModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.messageboards.model.impl.MBMailingListImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<MBMailingList, Long> MAILING_LIST_ID_ACCESSOR = new Accessor<MBMailingList, Long>() {
			@Override
			public Long get(MBMailingList mbMailingList) {
				return mbMailingList.getMailingListId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<MBMailingList> getTypeClass() {
				return MBMailingList.class;
			}
		};
}