/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.tuning.synonyms.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the STSynonymsEntry service. Represents a row in the &quot;STSynonymsEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Bryan Engler
 * @see STSynonymsEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.search.tuning.synonyms.model.impl.STSynonymsEntryImpl"
)
@ProviderType
public interface STSynonymsEntry extends PersistedModel, STSynonymsEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.search.tuning.synonyms.model.impl.STSynonymsEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<STSynonymsEntry, Long>
		ST_SYNONYMS_ENTRY_ID_ACCESSOR = new Accessor<STSynonymsEntry, Long>() {

			@Override
			public Long get(STSynonymsEntry stSynonymsEntry) {
				return stSynonymsEntry.getSTSynonymsEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<STSynonymsEntry> getTypeClass() {
				return STSynonymsEntry.class;
			}

		};

}