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

package com.liferay.portal.reports.engine.console.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the Source service. Represents a row in the &quot;Reports_Source&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see SourceModel
 * @see com.liferay.portal.reports.engine.console.model.impl.SourceImpl
 * @see com.liferay.portal.reports.engine.console.model.impl.SourceModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.portal.reports.engine.console.model.impl.SourceImpl")
@ProviderType
public interface Source extends SourceModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.reports.engine.console.model.impl.SourceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<Source, Long> SOURCE_ID_ACCESSOR = new Accessor<Source, Long>() {
			@Override
			public Long get(Source source) {
				return source.getSourceId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<Source> getTypeClass() {
				return Source.class;
			}
		};

	public String getAttachmentsDir();

	public String[] getAttachmentsFiles()
		throws com.liferay.portal.kernel.exception.PortalException;
}