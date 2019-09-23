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

import React, {useState} from 'react';
import CustomObjectSidebar from './CustomObjectSidebar.es';
import LayoutBuilderManager from './LayoutBuilderManager.es';
import LayoutBuilderSidebar from './LayoutBuilderSidebar.es';
import {createPortal} from 'react-dom';
import FormViewUpperToolbar from './FormViewUpperToolbar.es';
import FormViewContextProvider from './FormViewContextProvider.es';

const parseProps = ({dataDefinitionId, dataLayoutId, ...props}) => ({
	...props,
	dataDefinitionId: Number(dataDefinitionId),
	dataLayoutId: Number(dataLayoutId)
});

const EditFormView = props => {
	const {
		customObjectSidebarElementId,
		dataDefinitionId,
		dataLayoutBuilder,
		dataLayoutBuilderElementId,
		dataLayoutId,
		newCustomObject
	} = parseProps(props);

	return (
		<FormViewContextProvider
			dataDefinitionId={dataDefinitionId}
			dataLayoutBuilder={dataLayoutBuilder}
			dataLayoutId={dataLayoutId}
		>
			<FormViewUpperToolbar newCustomObject={newCustomObject} />

			{createPortal(
				<CustomObjectSidebar
					customObjectSidebarElementId={customObjectSidebarElementId}
				/>,
				document.querySelector(`#${customObjectSidebarElementId}`)
			)}

			<LayoutBuilderSidebar
				dataLayoutBuilder={dataLayoutBuilder}
				dataLayoutBuilderElementId={dataLayoutBuilderElementId}
			/>

			<LayoutBuilderManager dataLayoutBuilder={dataLayoutBuilder} />
		</FormViewContextProvider>
	);
};

export default ({dataLayoutBuilderId, ...props}) => {
	const [dataLayoutBuilder, setDataLayoutBuilder] = useState();

	if (!dataLayoutBuilder) {
		Liferay.componentReady(dataLayoutBuilderId).then(setDataLayoutBuilder);
	}

	return dataLayoutBuilder ? (
		<EditFormView dataLayoutBuilder={dataLayoutBuilder} {...props} />
	) : null;
};
