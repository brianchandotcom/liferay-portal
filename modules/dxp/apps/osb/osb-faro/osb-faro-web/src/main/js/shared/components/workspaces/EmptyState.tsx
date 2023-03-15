import Button from 'shared/components/Button';
import getCN from 'classnames';
import Icon from 'shared/components/Icon';
import React from 'react';
import {close, modalTypes, open} from 'shared/actions/modals';
import {connect} from 'react-redux';
import {DisplayType} from 'shared/types';
import {PROD_MODE} from 'shared/util/constants';
import {Routes, toRoute} from 'shared/util/router';

interface IEmptyStateProps extends React.HTMLAttributes<HTMLElement> {
	close: () => void;
	open: (string, object) => void;
}

const EmptyState: React.FC<IEmptyStateProps> = ({
	className,
	close,
	open,
	...otherProps
}) => (
	<div className={getCN('empty-container row', className)} {...otherProps}>
		<div className={PROD_MODE ? 'col-xl-12' : 'col-xl-6'}>
			<CardEmpty
				buttonProps={{
					label: Liferay.Language.get('contact-sales'),
					onClick: () =>
						open(modalTypes.CONTACT_SALES_MODAL, {
							onClose: close
						})
				}}
				description={Liferay.Language.get(
					'do-more-with-our-business-&-enterprise-plans'
				)}
				icon='ac-integration'
			/>
		</div>

		{!PROD_MODE && (
			<div className='col-xl-6'>
				<CardEmpty
					buttonProps={{
						display: 'secondary',
						href: toRoute(Routes.WORKSPACE_ADD_TRIAL),
						label: Liferay.Language.get('start-free-trial')
					}}
					description={Liferay.Language.get(
						'90-day-full-feature-trial'
					)}
					icon='ac-page-analytics'
				/>
			</div>
		)}
	</div>
);

interface ICardItemProps extends React.HTMLAttributes<HTMLElement> {
	buttonProps: {
		display?: DisplayType;
		href?: string;
		label: string;
		onClick?: () => void;
	};
	description: string;
	icon: string;
}

export const CardEmpty: React.FC<ICardItemProps> = ({
	buttonProps,
	description,
	icon
}) => {
	const {display = 'primary', label, ...otherButtonProps} = buttonProps;

	return (
		<div className='empty-card'>
			<Icon symbol={icon} />
			<p>{description}</p>
			<Button display={display} {...otherButtonProps}>
				{label}
			</Button>
		</div>
	);
};

export default connect(null, {close, open})(EmptyState);
