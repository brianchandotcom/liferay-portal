import ClayCard from '@clayui/card';
import React from 'react';

export default function FormCard({children}) {
	return (
		<div className="col-12">
			<ClayCard className="d-flex flex-column m-auto px-3 px-lg-6 px-md-6 px-sm-3 py-5 py-lg-6 py-md-6 py-sm-5 rounded shadow-lg">
				{children}
			</ClayCard>
		</div>
	);
}
