import React from 'react';
import {IBreakdownTableProps} from '..';

const WithEmptyState = (Component: React.FC<IBreakdownTableProps>) => ({
	event,
	...otherProps
}) => {
	if (!event) {
		return (
			<div className='breakdown-empty'>
				{Liferay.Language.get('add-an-event-to-analyze')}
			</div>
		);
	}

	return (
		<Component event={event} {...(otherProps as IBreakdownTableProps)} />
	);
};

export default WithEmptyState;
