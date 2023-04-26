import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import Dropdown from 'shared/components/Dropdown';
import React from 'react';

interface IRowActionsProps {
	actions?: (React.ComponentProps<typeof Dropdown> & {label: string})[];
	quickActions?: {
		iconSymbol: string;
		label: string;
		href?: string;
		onClick?: () => void;
	}[];
}

const RowActions: React.FC<IRowActionsProps> = ({
	actions = [],
	quickActions = []
}) => (
	<>
		{!!quickActions?.length && (
			<div className='quick-action-menu'>
				{quickActions.map(({href, iconSymbol, label, ...props}) =>
					href ? (
						<ClayLink
							aria-label={label}
							button
							className='button-root component-action quick-action-item'
							data-tooltip
							displayType='unstyled'
							href={href}
							key={label}
							title={label}
							{...props}
						>
							<ClayIcon
								className='icon-root'
								symbol={iconSymbol}
							/>
						</ClayLink>
					) : (
						<ClayButton
							aria-label={label}
							className='button-root component-action quick-action-item'
							data-tooltip
							displayType='unstyled'
							key={label}
							title={label}
							{...props}
						>
							<ClayIcon
								className='icon-root'
								symbol={iconSymbol}
							/>
						</ClayButton>
					)
				)}
			</div>
		)}

		{!!actions?.length && (
			<Dropdown
				align='bottomRight'
				buttonProps={{
					className: 'component-action',
					displayType: 'unstyled'
				}}
				className='dropdown-action'
				icon='ellipsis-v'
				showCaret={false}
			>
				{actions.map(({label, ...props}) => (
					<Dropdown.Item hideOnClick key={label} {...props}>
						{label}
					</Dropdown.Item>
				))}
			</Dropdown>
		)}
	</>
);

export default RowActions;
