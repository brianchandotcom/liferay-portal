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

package com.liferay.commerce.shop.by.diagram.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CPDefinitionDiagramEntry service. Represents a row in the &quot;CPDefinitionDiagramEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionDiagramEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramEntryImpl"
)
@ProviderType
public interface CPDefinitionDiagramEntry
	extends CPDefinitionDiagramEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPDefinitionDiagramEntry, Long>
		CP_DEFINITION_DIAGRAM_ENTRY_ID_ACCESSOR =
			new Accessor<CPDefinitionDiagramEntry, Long>() {

				@Override
				public Long get(
					CPDefinitionDiagramEntry cpDefinitionDiagramEntry) {

					return cpDefinitionDiagramEntry.
						getCPDefinitionDiagramEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CPDefinitionDiagramEntry> getTypeClass() {
					return CPDefinitionDiagramEntry.class;
				}

			};

	public CPDefinitionDiagramSettings getCPDefinitionDiagramSettings()
		throws com.liferay.portal.kernel.exception.PortalException;

}