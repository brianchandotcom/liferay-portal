import Breadcrumbs from 'shared/components/Breadcrumbs';
import ClayButton from '@clayui/button';
import ClayLink from '@clayui/link';
import Dropdown from 'shared/components/Dropdown';
import getCN from 'classnames';
import Nav from 'shared/components/Nav';
import NotificationAlertList, {
	useNotificationsAPI
} from '../NotificationAlertList';
import React from 'react';
import Row from './Row';
import TextTruncate from 'shared/components/TextTruncate';
import {getMatchedRoute, setUriQueryValues, toRoute} from 'shared/util/router';
import {noop, pickBy} from 'lodash';

type NavBarItem = {
	exact: boolean;
	label: string;
	route: string;
};

interface INavBarProps extends React.HTMLAttributes<HTMLDivElement> {
	items: NavBarItem[];
	routeParams?: object;
	routeQueries?: object;
}

const NavBar: React.FC<INavBarProps> = ({
	items,
	onClick = noop,
	routeParams = {},
	routeQueries = {}
}) => {
	const matchedRoute = getMatchedRoute(items);

	return (
		<Nav className='page-subnav' display='underline'>
			{items.map(({label, route}) => (
				<Nav.Item
					active={matchedRoute === route}
					href={setUriQueryValues(
						pickBy(routeQueries),
						toRoute(route, routeParams)
					)}
					key={label}
					onClick={onClick}
				>
					<span className='title'>{label}</span>
				</Nav.Item>
			))}
		</Nav>
	);
};

interface Action extends React.HTMLAttributes<HTMLElement> {
	label: string;
	href: string;
}

interface IPageActionsProps {
	actions?: Action[];
	actionsDisplayLimit?: number;
	disabled?: boolean;
	label?: string;
}

const PageActions: React.FC<IPageActionsProps> = ({
	actions = [],
	actionsDisplayLimit = 1,
	disabled = false,
	label = ''
}) => {
	const triggerDisplayProps = label.length
		? {
				label
		  }
		: {icon: 'ellipsis-v'};

	return (
		<>
			{actions.length <= actionsDisplayLimit &&
				actions.map(({label, ...props}) => {
					const Button = props.href ? ClayLink : ClayButton;

					return (
						<Button
							button
							className='button-root'
							displayType='secondary'
							key={label}
							{...props}
						>
							{label}
						</Button>
					);
				})}

			{actions.length > actionsDisplayLimit && (
				<Dropdown
					{...triggerDisplayProps}
					align='bottomRight'
					buttonProps={{
						displayType: label.length ? 'primary' : 'unstyled'
					}}
					disabled={disabled}
					showCaret={false}
				>
					{actions.map(({label, ...props}) => (
						<Dropdown.Item key={label} {...props}>
							{label}
						</Dropdown.Item>
					))}
				</Dropdown>
			)}
		</>
	);
};

const Section: React.FC<React.HTMLAttributes<HTMLDivElement>> = ({
	children,
	className
}) => <div className={getCN('header-section', className)}>{children}</div>;

interface ITitleSectionProps extends React.HTMLAttributes<HTMLDivElement> {
	subtitle?: React.ReactNode | string;
	title?: string;
}

const TitleSection: React.FC<ITitleSectionProps> = ({
	children,
	className,
	subtitle,
	title
}) => (
	<Section className={getCN('title-section', className, {subtitle})}>
		<span className='align-items-center d-flex'>
			<h1 className='title text-truncate'>
				<TextTruncate title={title} />
			</h1>

			{children}
		</span>

		{subtitle && <div className='subtitle'>{subtitle}</div>}
	</Section>
);

type Breadcrumb = {
	active?: boolean;
	href?: string;
	label: string;
	id?: string;
};

interface IHeaderProps extends React.HTMLAttributes<HTMLDivElement> {
	breadcrumbs: Breadcrumb[];
	groupId: string;
}

const Header: React.FC<IHeaderProps> & {
	NavBar: typeof NavBar;
	PageActions: typeof PageActions;
	Section: typeof Section;
	TitleSection: typeof TitleSection;
} = ({breadcrumbs, children, groupId}) => {
	const notificationResponse = useNotificationsAPI(groupId);

	return (
		<header className='header-root'>
			<div className='header-container'>
				{breadcrumbs && (
					<Row>
						<Breadcrumbs items={breadcrumbs} />
					</Row>
				)}

				{children}
			</div>

			<NotificationAlertList
				{...notificationResponse}
				groupId={groupId}
				stripe
			/>
		</header>
	);
};

Header.NavBar = NavBar;
Header.PageActions = PageActions;
Header.Section = Section;
Header.TitleSection = TitleSection;

export default Header;

export {NavBar, PageActions, Section, TitleSection};
