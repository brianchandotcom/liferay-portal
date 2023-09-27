/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the TemplateEntry service. Represents a row in the &quot;TemplateEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see TemplateEntryModel
 * @generated
 */
@ImplementationClassName("com.liferay.template.model.impl.TemplateEntryImpl")
@ProviderType
public interface TemplateEntry extends PersistedModel, TemplateEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.template.model.impl.TemplateEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<TemplateEntry, Long>
		TEMPLATE_ENTRY_ID_ACCESSOR = new Accessor<TemplateEntry, Long>() {

			@Override
			public Long get(TemplateEntry templateEntry) {
				return templateEntry.getTemplateEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<TemplateEntry> getTypeClass() {
				return TemplateEntry.class;
			}

		};

}