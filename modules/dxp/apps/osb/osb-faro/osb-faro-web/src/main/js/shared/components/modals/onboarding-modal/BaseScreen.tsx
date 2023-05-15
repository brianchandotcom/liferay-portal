import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import React from 'react';

interface IBaseScreenProps extends React.HTMLAttributes<HTMLElement> {
	onClose: () => void;
	title?: string;
}

const BaseScreen: React.FC<IBaseScreenProps> = ({
	children,
	className,
	onClose,
	title
}) => (
	<div
		className={getCN(
			'onboarding-base-screen-root d-flex flex-column flex-grow-1 justify-content-between',
			className
		)}
	>
		<div className='header'>
			<div className='d-flex justify-content-end'>
				<ClayButton
					aria-label={Liferay.Language.get('close')}
					className='button-root close'
					displayType='secondary'
					onClick={onClose}
				>
					<ClayIcon className='icon-root' symbol='times' />
				</ClayButton>
			</div>

			{title && (
				<span className='title d-flex justify-content-center'>
					{title}
				</span>
			)}
		</div>

		{children}
	</div>
);

export default BaseScreen;
