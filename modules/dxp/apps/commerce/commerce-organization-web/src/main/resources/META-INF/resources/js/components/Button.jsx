import React from 'react';

function Button(props) {
	const {
		label,
		onButtonClick
	} = props;

	return (
		<button onClick={onButtonClick}>
			{ label }
		</button>
	);
}

export default Button;
