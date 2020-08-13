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

import React from 'react';

const DataSetDisplayContext = React.createContext({
	actionParameterName: null,
	formId: null,
	formRef: null,
	id: null,
	loadData: () => {},
	modalId: null,
	namespace: null,
	openModal: () => {},
	openSidePanel: () => {},
	selectItems: () => {},
	selectable: false,
	selectedItemsValue: [],
	sidePanelId: null,
	sorting: [],
	updateSorting: () => {},
});

export default DataSetDisplayContext;
