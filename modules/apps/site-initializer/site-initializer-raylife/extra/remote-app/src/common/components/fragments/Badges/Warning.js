import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import React from 'react';

export function WarningBadge({children}) {
	return (
		<ClayLabel className="label-inverse-danger p-0 rounded w-100">
			<div className="align-items-center badge d-flex m-0 warning">
				<span className="inline-item inline-item-before">
					<ClayIcon
						className="c-ml-4 c-mr-2"
						symbol="exclamation-full"
					/>
				</span>

				<span className="font-weight-normal text-paragraph">
					{children}
				</span>
			</div>
		</ClayLabel>
	);
}
