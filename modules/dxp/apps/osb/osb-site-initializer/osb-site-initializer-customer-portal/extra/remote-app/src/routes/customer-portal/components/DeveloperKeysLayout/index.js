import React from 'react';
import DeveloperKeysInputs from './Inputs';

const DeveloperKeysLayouts = ({children}) => {
	return (
		<div>
			<h4 className="m-0 py-3">Developer Keys</h4>

			{children}
		</div>
	);
};

DeveloperKeysLayouts.Inputs = DeveloperKeysInputs;

export default DeveloperKeysLayouts;
