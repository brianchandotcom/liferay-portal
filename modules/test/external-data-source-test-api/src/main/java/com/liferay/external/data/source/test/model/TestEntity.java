/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.external.data.source.test.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the TestEntity service. Represents a row in the &quot;TestEntity&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see TestEntityModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.external.data.source.test.model.impl.TestEntityImpl"
)
@ProviderType
public interface TestEntity extends PersistedModel, TestEntityModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.external.data.source.test.model.impl.TestEntityImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<TestEntity, Long> ID_ACCESSOR =
		new Accessor<TestEntity, Long>() {

			@Override
			public Long get(TestEntity testEntity) {
				return testEntity.getId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<TestEntity> getTypeClass() {
				return TestEntity.class;
			}

		};

}