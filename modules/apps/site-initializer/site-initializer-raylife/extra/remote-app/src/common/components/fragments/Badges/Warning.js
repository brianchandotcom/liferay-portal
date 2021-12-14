import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import React from 'react';

export function WarningBadge({children}) {
	return (
		<ClayLabel className="d-block label-inverse-danger mt-1 rounded">
			<div className="align-items-start badge d-flex ml-2 warning">
				<span className="inline-item inline-item-before mr-1">
					<ClayIcon symbol="exclamation-full" />
				</span>

				<span className="text-paragraph-sm text-start">{children}</span>
			</div>
		</ClayLabel>
	);
}
