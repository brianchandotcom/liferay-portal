/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shop.by.diagram.type;

import aQute.bnd.annotation.ProviderType;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
@ProviderType
public interface CSDiagramTypeRegistry {

	public CSDiagramType getCSDiagramType(String key);

	public List<CSDiagramType> getCSDiagramTypes();

}