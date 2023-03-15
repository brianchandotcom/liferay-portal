import Button from 'shared/components/Button';
import Dropdown from 'shared/components/Dropdown';
import React from 'react';

interface IRowActionsProps {
	actions?: (React.ComponentProps<typeof Dropdown> & {label: string})[];
	quickActions?: (React.ComponentProps<typeof Button> & {
		iconSymbol: string;
		label: string;
	})[];
}

const RowActions: React.FC<IRowActionsProps> = ({
	actions = [],
	quickActions = []
}) => (
	<>
		{!!quickActions?.length && (
			<div className='quick-action-menu'>
				{quickActions.map(
					({iconAlignment = 'left', iconSymbol, label, ...props}) => (
						<Button
							alt={label}
							aria-label={label}
							className='component-action quick-action-item'
							data-tooltip
							display='unstyled'
							icon={iconSymbol}
							iconAlignment={iconAlignment}
							key={label}
							title={label}
							{...props}
						/>
					)
				)}
			</div>
		)}

		{!!actions?.length && (
			<Dropdown
				align='bottomRight'
				buttonProps={{
					className: 'component-action',
					display: 'unstyled'
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
