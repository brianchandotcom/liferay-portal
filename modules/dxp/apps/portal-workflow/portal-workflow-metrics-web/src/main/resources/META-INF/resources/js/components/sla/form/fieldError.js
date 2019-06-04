import Icon from '../../../shared/components/Icon';
import React from 'react';

const FieldError = ({error}) => (
	<div className='form-feedback-group'>
		<div className='form-feedback-item'>
			<span className='form-feedback-indicator'>
				<Icon iconName='exclamation-full' />
			</span>
			{error}
		</div>
	</div>
);

export default FieldError;