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

import React, {useCallback, useEffect, useState} from 'react';

import DataLayoutBuilderInstanceProvider from './DataLayoutBuilderInstanceProvider.es';
import FormViewContext from './FormViewContext.es';

export default ({children, dataLayoutBuilder}) => {
	const {
		appContext: [appState],
	} = dataLayoutBuilder.props;
	const [state, setState] = useState(appState);

	useEffect(() => {
		const callback = () => {
			const {
				appContext: [state],
			} = dataLayoutBuilder.props;
			setState(state);
		};

		dataLayoutBuilder.on('contextUpdated', callback);

		return () =>
			dataLayoutBuilder.removeEventListener('contextUpdated', callback);
	}, [dataLayoutBuilder]);

	const dispatch = useCallback(
		(action) => {
			const [, dispatch] = dataLayoutBuilder.props.appContext;

			dispatch?.(action);
		},
		[dataLayoutBuilder]
	);

	return (
		<FormViewContext.Provider value={[state, dispatch]}>
			<DataLayoutBuilderInstanceProvider
				dataLayoutBuilder={dataLayoutBuilder}
			>
				{children}
			</DataLayoutBuilderInstanceProvider>
		</FormViewContext.Provider>
	);
};
