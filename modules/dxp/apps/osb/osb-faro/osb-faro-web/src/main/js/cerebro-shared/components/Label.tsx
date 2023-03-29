import Button from 'shared/components/Button';
import getCN from 'classnames';
import React from 'react';

interface ILabelProps extends React.HTMLAttributes<HTMLSpanElement> {
	className?: string;
	closeable: boolean;
	display: 'danger' | 'info' | 'secondary' | 'success' | 'warning';
	label: string;
	onRemove?: () => void;
	size?: 'lg';
}

const Label: React.FC<ILabelProps> = ({
	className,
	closeable = false,
	display = 'secondary',
	label,
	onRemove,
	size
}) => {
	const classes = getCN('label', `label-${display}`, className, {
		['label-dismissible']: closeable,
		[`label-${size}`]: size
	});

	return (
		<span className={classes}>
			<span className='label-item label-item-expand'>{label}</span>

			{closeable && (
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
			)}
		</span>
	);
};

export default Label;
