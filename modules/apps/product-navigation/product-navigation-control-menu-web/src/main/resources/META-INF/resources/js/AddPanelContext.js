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

import React, {useContext} from 'react';

const INITIAL_STATE = {
	addContentsURLs: null,
	contents: null,
	displayGrid: false,
	getContentsURL: null,
	namespace: null,
	portletNamespace: null,
	setDisplayGrid: () => null,
	setWidgets: () => null,
	widgets: null,
};

const AddPanelContext = React.createContext(INITIAL_STATE);

const AddPanelContextProvider = AddPanelContext.Provider;

const useAddContentsURLsContext = () => {
	return useContext(AddPanelContext).addContentsURLs;
};

const useContentsContext = () => {
	return useContext(AddPanelContext).contents;
};

const useDisplayGridContext = () => {
	return useContext(AddPanelContext).displayGrid;
};

const useGetContentsURLContext = () => {
	return useContext(AddPanelContext).getContentsURL;
};

const useNamespaceContext = () => {
	return useContext(AddPanelContext).namespace;
};

const usePortletNamespaceContext = () => {
	return useContext(AddPanelContext).portletNamespace;
};

const useSetDisplayGridContext = () => {
	return useContext(AddPanelContext).setDisplayGrid;
};

const useWidgetsContext = () => {
	return useContext(AddPanelContext).widgets;
};

const useSetWidgetsContext = () => {
	return useContext(AddPanelContext).setWidgets;
};

export {
	AddPanelContext,
	AddPanelContextProvider,
	useAddContentsURLsContext,
	useContentsContext,
	useDisplayGridContext,
	useGetContentsURLContext,
	useNamespaceContext,
	usePortletNamespaceContext,
	useSetDisplayGridContext,
	useWidgetsContext,
	useSetWidgetsContext,
};
