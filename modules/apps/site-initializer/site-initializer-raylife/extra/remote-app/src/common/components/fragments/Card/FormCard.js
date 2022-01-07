import ClayCard from '@clayui/card';
import React from 'react';

export default function FormCard({children}) {
	return (
		<div className="col-12">
			<ClayCard className="d-flex flex-column m-auto p-6 rounded shadow-lg">
				{children}
			</ClayCard>
		</div>
	);
}
