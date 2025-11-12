import ClayButton from '@clayui/button';
import React from 'react';

interface IButtonGroupProps {
	nextButtonLoading?: boolean;
	nextButtonLabel: string;
	onCancel: () => void;
	prevButtonLabel: string;
}

export const ButtonGroup: React.FC<IButtonGroupProps> = ({
	nextButtonLabel,
	nextButtonLoading,
	onCancel,
	prevButtonLabel
}) => (
	<div className='mt-5'>
		<ClayButton
			block
			disabled={nextButtonLoading}
			loading={nextButtonLoading}
			type='submit'
		>
			{nextButtonLabel}
		</ClayButton>

		<ClayButton block borderless displayType='secondary' onClick={onCancel}>
			{prevButtonLabel}
		</ClayButton>
	</div>
);
