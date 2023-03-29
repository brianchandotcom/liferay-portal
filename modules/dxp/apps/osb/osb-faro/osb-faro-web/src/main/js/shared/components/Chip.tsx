import Button from 'shared/components/Button';
import getCN from 'classnames';
import React from 'react';

export interface IChipProps extends React.HTMLAttributes<HTMLDivElement> {
	className?: string;
	onCloseClick: () => void;
}

const Chip = React.forwardRef<HTMLDivElement, IChipProps>(
	({children, className, onCloseClick}, ref) => (
		<div
			className={getCN(
				'chip-root d-flex align-items-center justify-content-between',
				className
			)}
			ref={ref}
		>
			{children}

			<Button
				className='remove-button'
				display='unstyled'
				icon='times-circle'
				iconAlignment='left'
				onClick={() => onCloseClick()}
			/>
		</div>
	)
);

export default Chip;
