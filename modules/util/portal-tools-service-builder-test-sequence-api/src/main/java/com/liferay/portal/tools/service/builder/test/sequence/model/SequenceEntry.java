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

package com.liferay.portal.tools.service.builder.test.sequence.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the SequenceEntry service. Represents a row in the &quot;SequenceEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see SequenceEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.tools.service.builder.test.sequence.model.impl.SequenceEntryImpl"
)
@ProviderType
public interface SequenceEntry extends PersistedModel, SequenceEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.tools.service.builder.test.sequence.model.impl.SequenceEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SequenceEntry, Long>
		SEQUENCE_ENTRY_ID_ACCESSOR = new Accessor<SequenceEntry, Long>() {

			@Override
			public Long get(SequenceEntry sequenceEntry) {
				return sequenceEntry.getSequenceEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<SequenceEntry> getTypeClass() {
				return SequenceEntry.class;
			}

		};

}