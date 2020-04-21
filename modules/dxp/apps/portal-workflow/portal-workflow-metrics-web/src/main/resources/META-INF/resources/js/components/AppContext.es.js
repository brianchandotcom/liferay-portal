/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React, {useState} from 'react';

import ToasterProvider from '../shared/components/toaster/ToasterProvider.es';

const AppContext = React.createContext();

const AppContextProvider = ({children, ...props}) => {
	const [reindexStatuses, setReindexStatuses] = useState([]);
	const [title, setTitle] = useState(Liferay.Language.get('metrics'));

	const state = {
		...props,
		reindexStatuses,
		setReindexStatuses,
		setTitle,
		title,
	};

	return (
		<AppContext.Provider value={state}>
			<ToasterProvider>{children}</ToasterProvider>
		</AppContext.Provider>
	);
};

export {AppContext, AppContextProvider};
