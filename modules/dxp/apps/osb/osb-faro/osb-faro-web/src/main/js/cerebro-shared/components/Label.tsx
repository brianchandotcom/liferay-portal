import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
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
					<ClayButton
						aria-label={Liferay.Language.get('close')}
						className='close button-root'
						displayType='unstyled'
						onClick={onRemove}
					>
						<ClayIcon className='icon-root' symbol='times' />
					</ClayButton>
				</span>
			)}
		</span>
	);
};

export default Label;
