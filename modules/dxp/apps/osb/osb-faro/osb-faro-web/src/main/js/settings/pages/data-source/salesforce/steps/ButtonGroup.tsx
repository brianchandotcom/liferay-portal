import ClayButton from '@clayui/button';
import React from 'react';

interface IButtonGroupProps {
	nextButtonLabel: string;
	onCancel: () => void;
	prevButtonLabel: string;
}

export const ButtonGroup: React.FC<IButtonGroupProps> = ({
	nextButtonLabel,
	onCancel,
	prevButtonLabel
}) => (
	<div className='mt-5'>
		<ClayButton block type='submit'>
			{nextButtonLabel}
		</ClayButton>

		<ClayButton block borderless displayType='secondary' onClick={onCancel}>
			{prevButtonLabel}
		</ClayButton>
	</div>
);
