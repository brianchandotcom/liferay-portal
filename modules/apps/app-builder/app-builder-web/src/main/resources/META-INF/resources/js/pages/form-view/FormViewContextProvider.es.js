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
	const [state, setState] = useState(dataLayoutBuilder.getState());

	useEffect(() => {
		const callback = () => {
			setState(dataLayoutBuilder.getState());
		};

		dataLayoutBuilder.on('contextUpdated', callback);

		return () =>
			dataLayoutBuilder.removeEventListener('contextUpdated', callback);
	}, [dataLayoutBuilder]);

	const dispatch = useCallback(
		action => {
			dataLayoutBuilder.dispatchAction(action);
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
