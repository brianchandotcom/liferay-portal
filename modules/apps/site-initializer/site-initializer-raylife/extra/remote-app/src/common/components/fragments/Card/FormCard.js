import ClayCard from '@clayui/card';
import React from 'react';

export default function FormCard({children}) {
	return (
		<div>
			<ClayCard className="d-flex flex-column p-6 rounded shadow-lg">
				{children}
			</ClayCard>
		</div>
	);
}
