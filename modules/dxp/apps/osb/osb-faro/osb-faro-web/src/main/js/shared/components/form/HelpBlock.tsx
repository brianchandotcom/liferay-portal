import getCN from 'classnames';
import React from 'react';
import {ErrorMessage} from 'formik';

const HelpBlock: React.FC<React.ComponentProps<typeof ErrorMessage>> = ({
	className,
	...otherProps
}) => {
	const classes = getCN('help-block', 'form-feedback-item', className);

	return (
		<ErrorMessage
			{...otherProps}
			render={message => <div className={classes}>{message}</div>}
		/>
	);
};

export default HelpBlock;
