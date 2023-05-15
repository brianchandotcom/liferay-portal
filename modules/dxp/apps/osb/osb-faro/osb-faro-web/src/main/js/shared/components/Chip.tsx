import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
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

			<ClayButton
				aria-label={Liferay.Language.get('close')}
				className='button-root remove-button'
				displayType='unstyled'
				onClick={() => onCloseClick()}
			>
				<ClayIcon className='icon-root' symbol='times-circle' />
			</ClayButton>
		</div>
	)
);

export default Chip;
