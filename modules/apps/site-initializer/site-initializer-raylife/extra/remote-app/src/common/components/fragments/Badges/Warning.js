import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import React from 'react';

export function WarningBadge({children}) {
	return (
		<ClayLabel className="label-inverse-danger mt-6 rounded">
			<div className="align-items-center badge d-flex ml-2 warning">
				<span className="inline-item inline-item-before">
					<ClayIcon symbol="exclamation-full" />
				</span>

				<span className="text-paragraph-sm">{children}</span>
			</div>
		</ClayLabel>
	);
}
