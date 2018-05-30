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

package com.liferay.commerce.product.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CPDisplayLayout service. Represents a row in the &quot;CPDisplayLayout&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPDisplayLayoutModel
 * @see com.liferay.commerce.product.model.impl.CPDisplayLayoutImpl
 * @see com.liferay.commerce.product.model.impl.CPDisplayLayoutModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.product.model.impl.CPDisplayLayoutImpl")
@ProviderType
public interface CPDisplayLayout extends CPDisplayLayoutModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.product.model.impl.CPDisplayLayoutImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPDisplayLayout, Long> CP_DISPLAY_LAYOUT_ID_ACCESSOR =
		new Accessor<CPDisplayLayout, Long>() {
			@Override
			public Long get(CPDisplayLayout cpDisplayLayout) {
				return cpDisplayLayout.getCPDisplayLayoutId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CPDisplayLayout> getTypeClass() {
				return CPDisplayLayout.class;
			}
		};
}