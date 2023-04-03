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

package com.liferay.osb.faro.contacts.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ContactsLayoutTemplate service. Represents a row in the &quot;OSBFaro_ContactsLayoutTemplate&quot; database table, with each column mapped to a property of this class.
 *
 * @author Shinn Lok
 * @see ContactsLayoutTemplateModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.osb.faro.contacts.model.impl.ContactsLayoutTemplateImpl"
)
@ProviderType
public interface ContactsLayoutTemplate
	extends ContactsLayoutTemplateModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.osb.faro.contacts.model.impl.ContactsLayoutTemplateImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ContactsLayoutTemplate, Long>
		CONTACTS_LAYOUT_TEMPLATE_ID_ACCESSOR =
			new Accessor<ContactsLayoutTemplate, Long>() {

				@Override
				public Long get(ContactsLayoutTemplate contactsLayoutTemplate) {
					return contactsLayoutTemplate.getContactsLayoutTemplateId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<ContactsLayoutTemplate> getTypeClass() {
					return ContactsLayoutTemplate.class;
				}

			};

}