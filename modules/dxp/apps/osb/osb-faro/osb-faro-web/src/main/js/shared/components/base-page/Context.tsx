import React from 'react';
import {IBasePageContext} from 'shared/types';

const Context: React.Context<IBasePageContext> = React.createContext({
	filters: {},
	router: {
		params: {
			groupId: ''
		},
		query: {}
	}
} as IBasePageContext);

Context.displayName = 'BasePageContext';

export default Context;
