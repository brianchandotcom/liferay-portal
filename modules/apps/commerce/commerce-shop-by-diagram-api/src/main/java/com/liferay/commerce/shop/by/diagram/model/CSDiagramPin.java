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

package com.liferay.commerce.shop.by.diagram.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CSDiagramPin service. Represents a row in the &quot;CSDiagramPin&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CSDiagramPinModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.shop.by.diagram.model.impl.CSDiagramPinImpl"
)
@ProviderType
public interface CSDiagramPin extends CSDiagramPinModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.shop.by.diagram.model.impl.CSDiagramPinImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CSDiagramPin, Long>
		CS_DIAGRAM_PIN_ID_ACCESSOR = new Accessor<CSDiagramPin, Long>() {

			@Override
			public Long get(CSDiagramPin csDiagramPin) {
				return csDiagramPin.getCSDiagramPinId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CSDiagramPin> getTypeClass() {
				return CSDiagramPin.class;
			}

		};

	public com.liferay.commerce.product.model.CPDefinition getCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException;

}