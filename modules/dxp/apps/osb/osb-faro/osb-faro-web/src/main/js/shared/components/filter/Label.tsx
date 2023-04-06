import Button from 'shared/components/Button';
import React from 'react';

interface ILabelProps extends React.HTMLAttributes<HTMLSpanElement> {
	label: string;
	onRemove: () => void;
}

export const Label: React.FC<ILabelProps> = ({label, onRemove}) => (
	<span className='label label-secondary label-dismissible label-lg'>
		<span className='label-item label-item-expand'>{label}</span>

		<span className='label-item label-item-after'>
			<Button
				aria-label={Liferay.Language.get('close')}
				className='close'
				display='unstyled'
				icon='times'
				iconAlignment='right'
				onClick={onRemove}
			/>
		</span>
	</span>
);
