import Breadcrumbs from 'shared/components/Breadcrumbs';
import classNames from 'classnames';
import ClayButton from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ClayNavigationBar from '@clayui/navigation-bar';
import getCN from 'classnames';
import NotificationAlertList, {
	useNotificationsAPI
} from '../NotificationAlertList';
import React from 'react';
import Row from './Row';
import TextTruncate from 'shared/components/TextTruncate';
import {getMatchedRoute, setUriQueryValues, toRoute} from 'shared/util/router';
import {IBreadcrumbArgs} from 'shared/util/breadcrumbs';
import {pickBy} from 'lodash';

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
	routeParams = {},
	routeQueries = {}
}) => {
	const matchedRoute = getMatchedRoute(items);

	return (
		<div className='row'>
			<ClayNavigationBar triggerLabel={matchedRoute}>
				{items.map(({label, route}) => (
					<ClayNavigationBar.Item
						active={matchedRoute === route}
						key={label}
					>
						<ClayLink
							href={setUriQueryValues(
								pickBy(routeQueries),
								toRoute(route, routeParams)
							)}
						>
							{label}
						</ClayLink>
					</ClayNavigationBar.Item>
				))}
			</ClayNavigationBar>
		</div>
	);
};

interface Action extends React.HTMLAttributes<HTMLElement> {
	disabled: boolean;
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
}) => (
	<>
		{actions.length <= actionsDisplayLimit &&
			actions.map(({label, ...props}) => {
				const Button = props.href ? ClayLink : ClayButton;

				return (
					<Button
						button
						className={classNames(
							getCN('button-root', {
								disabled: props.disabled
							})
						)}
						displayType='secondary'
						key={label}
						{...props}
					>
						{label}
					</Button>
				);
			})}

		{actions.length > actionsDisplayLimit && (
			<ClayDropDown
				alignmentPosition={Align.BottomRight}
				trigger={
					<ClayButton
						aria-label={label && Liferay.Language.get('menu')}
						disabled={disabled}
						displayType={label.length ? 'primary' : 'unstyled'}
					>
						{label ? (
							<>
								<span>{label}</span>

								<ClayIcon
									className='icon-root ml-2'
									symbol='caret-bottom'
								/>
							</>
						) : (
							<ClayIcon
								className='icon-root'
								symbol='ellipsis-v'
							/>
						)}
					</ClayButton>
				}
			>
				{actions.map(({label, ...props}) => (
					<ClayDropDown.Item key={label} {...props}>
						{label}
					</ClayDropDown.Item>
				))}
			</ClayDropDown>
		)}
	</>
);

const Section: React.FC<React.HTMLAttributes<HTMLDivElement>> = ({
	children,
	className
}) => <div className={getCN('header-section', className)}>{children}</div>;

interface ITitleSectionProps extends React.HTMLAttributes<HTMLDivElement> {
	subtitle?: React.ReactNode | string;
	title?: string;
}

export interface IActionProps extends React.HTMLAttributes<HTMLDivElement> {
	displayType: string;
	label: string;
	redirectURL?: string;
	onClick?: () => void;
}

interface IActionsProps extends React.HTMLAttributes<HTMLDivElement> {
	actions: IActionProps[];
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

const Actions: React.FC<IActionsProps> = ({actions = []}) => (
	<div className='header-actions'>
		{actions.map(({displayType, label, onClick, redirectURL}, index) =>
			redirectURL ? (
				<a
					className={getCN(`btn btn-${displayType}`, 'ml-2')}
					href={redirectURL}
					key={index}
					target='_blank'
				>
					<ClayIcon className='mr-2' symbol='shortcut' />

					{label}
				</a>
			) : (
				<ClayButton
					className='ml-2'
					displayType={displayType as any}
					key={index}
					onClick={onClick}
				>
					{label}
				</ClayButton>
			)
		)}
	</div>
);

interface IHeaderProps extends React.HTMLAttributes<HTMLDivElement> {
	breadcrumbs: IBreadcrumbArgs[];
	groupId: string;
}

const Header: React.FC<IHeaderProps> & {
	NavBar: typeof NavBar;
	PageActions: typeof PageActions;
	Actions: typeof Actions;
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

Header.Actions = Actions;
Header.NavBar = NavBar;
Header.PageActions = PageActions;
Header.Section = Section;
Header.TitleSection = TitleSection;

export default Header;

export {NavBar, PageActions, Section, TitleSection};
