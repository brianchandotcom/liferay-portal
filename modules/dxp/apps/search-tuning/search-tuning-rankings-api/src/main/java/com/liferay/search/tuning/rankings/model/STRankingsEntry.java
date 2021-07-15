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

package com.liferay.search.tuning.rankings.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the STRankingsEntry service. Represents a row in the &quot;STRankingsEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Bryan Engler
 * @see STRankingsEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.search.tuning.rankings.model.impl.STRankingsEntryImpl"
)
@ProviderType
public interface STRankingsEntry extends PersistedModel, STRankingsEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.search.tuning.rankings.model.impl.STRankingsEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<STRankingsEntry, Long>
		ST_RANKINGS_ENTRY_ID_ACCESSOR = new Accessor<STRankingsEntry, Long>() {

			@Override
			public Long get(STRankingsEntry stRankingsEntry) {
				return stRankingsEntry.getSTRankingsEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<STRankingsEntry> getTypeClass() {
				return STRankingsEntry.class;
			}

		};

	public java.util.List<String> getAliases();

	public java.util.List<String> getHiddenDocumentIds();

	public boolean getInactive();

	public String getIndexName();

	public String getJSON();

	public String getName();

	public java.util.Map<Integer, String> getPinnedDocumentIds();

	public String getQueryString();

	public String getRankingDocumentId();

}