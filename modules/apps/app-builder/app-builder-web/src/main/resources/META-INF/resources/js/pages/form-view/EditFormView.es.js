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

import {withDragAndDropContext} from 'data-engine-taglib';
import React, {useContext, useState} from 'react';
import {createPortal} from 'react-dom';

import {AppContext} from '../../AppContext.es';
import {ControlMenuBase} from '../../components/control-menu/ControlMenu.es';
import CustomObjectSidebar from './CustomObjectSidebar.es';
import FormViewContextProvider from './FormViewContextProvider.es';
import FormViewUpperToolbar from './FormViewUpperToolbar.es';

const parseProps = ({dataDefinitionId, dataLayoutId, ...props}) => ({
	...props,
	dataDefinitionId: Number(dataDefinitionId),
	dataLayoutId: Number(dataLayoutId)
});

const FormViewControlMenu = ({backURL, dataLayoutId}) => {
	let title = Liferay.Language.get('new-form-view');

	if (dataLayoutId > 0) {
		title = Liferay.Language.get('edit-form-view');
	}

	return (
		<ControlMenuBase backURL={backURL} title={title} url={location.href} />
	);
};

const EditFormView = withDragAndDropContext(props => {
	const {
		customObjectSidebarElementId,
		dataDefinitionId,
		dataLayoutBuilder,
		dataLayoutId,
		newCustomObject
	} = parseProps(props);
	const {basePortletURL} = useContext(AppContext);

	let backURL = `${basePortletURL}/#/custom-object/${dataDefinitionId}/form-views`;

	if (newCustomObject) {
		backURL = basePortletURL;
	}

	return (
		<FormViewContextProvider
			dataDefinitionId={dataDefinitionId}
			dataLayoutBuilder={dataLayoutBuilder}
			dataLayoutId={dataLayoutId}
		>
			<FormViewControlMenu
				backURL={backURL}
				dataLayoutId={dataLayoutId}
			/>

			<FormViewUpperToolbar newCustomObject={newCustomObject} />

			{createPortal(
				<CustomObjectSidebar />,
				document.querySelector(`#${customObjectSidebarElementId}`)
			)}
		</FormViewContextProvider>
	);
});

export default ({dataLayoutBuilderId, ...props}) => {
	const [dataLayoutBuilder, setDataLayoutBuilder] = useState();

	if (!dataLayoutBuilder) {
		Liferay.componentReady(dataLayoutBuilderId).then(setDataLayoutBuilder);
	}

	return dataLayoutBuilder ? (
		<EditFormView dataLayoutBuilder={dataLayoutBuilder} {...props} />
	) : null;
};
