import Icon from '../../../shared/components/Icon';
import React from 'react';

const FieldLabel = ({fieldId, required, text}) => (
	<label htmlFor={fieldId}>
		{`${text} `}
		{required && (
			<span className="reference-mark">
				<Icon iconName="asterisk" />
			</span>
		)}
	</label>
);

export default FieldLabel;
