import ClayIcon from '@clayui/icon';
import React from 'react';

export const WarningBadge = ({children, ...props}) => {
	return (
		<div {...props} className="badge badge-error">
			<ClayIcon symbol="exclamation-full" />
			{children}
		</div>
	);
};
