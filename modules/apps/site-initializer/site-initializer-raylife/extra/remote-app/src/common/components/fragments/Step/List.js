import React from 'react';

export function StepList({children}) {
	return (
		<div className="c-mr-6 c-mt-10 d-flex flex-column step-list">
			{children}
		</div>
	);
}
