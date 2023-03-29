import getCN from 'classnames';
import Icon from 'shared/components/Icon';
import React from 'react';
import Spinner from 'shared/components/Spinner';
import {Link} from 'react-router-dom';

export type Displays =
	| 'danger'
	| 'dark'
	| 'default'
	| 'info'
	| 'light'
	| 'link'
	| 'primary'
	| 'profile'
	| 'secondary'
	| 'success'
	| 'unstyled'
	| 'warning';

export type Sizes = 'sm' | 'lg';

export type Alignments = 'left' | 'right';

interface IButtonGroupProps extends React.HTMLAttributes<HTMLDivElement> {
	size?: Sizes;
	vertical?: boolean;
}

const ButtonGroup: React.FC<IButtonGroupProps> = ({
	children,
	className,
	size,
	vertical = false
}) => {
	const classes = getCN('btn-group', className, {
		[`btn-group-${size}`]: size,
		['btn-group-vertical']: vertical
	});

	return (
		<div className={classes} role='group'>
			{children}
		</div>
	);
};

const ButtonGroupItem = ({
	children,
	className
}: React.HTMLAttributes<HTMLDivElement>) => {
	const classes = getCN('btn-group-item', className);

	return <div className={classes}>{children}</div>;
};

interface IButtonProps
	extends React.HTMLAttributes<HTMLButtonElement | HTMLAnchorElement> {
	active?: boolean;
	block?: boolean;
	borderless?: boolean;
	disabled?: boolean;
	display?: Displays;
	externalLink?: boolean;
	href?: string;
	icon?: string;
	iconAlignment?: Alignments;
	loading?: boolean;
	monospaced?: boolean;
	outline?: boolean;
	size?: Sizes;
	type?: 'button' | 'submit' | 'reset';
	[propName: string]: any;
}

class Button extends React.Component<IButtonProps> {
	renderIcon() {
		const {children, icon, iconAlignment} = this.props;

		const classes = getCN('inline-item', {
			'inline-item-after': iconAlignment == 'right',
			'inline-item-before': iconAlignment == 'left'
		});

		if (children) {
			return (
				<span className={classes}>
					<Icon symbol={icon} />
				</span>
			);
		} else {
			return <Icon symbol={icon} />;
		}
	}

	render() {
		const {
			active,
			block,
			borderless = false,
			children,
			className,
			disabled = false,
			display = 'secondary',
			externalLink = false,
			forwardedRef,
			href,
			icon,
			iconAlignment,
			loading = false,
			monospaced = false,
			onClick,
			outline,
			size,
			type = 'button',
			...otherProps
		} = this.props;

		const classes = getCN(
			'button-root',
			'btn',
			`btn${outline ? '-outline' : ''}-${display}`,
			className,
			{
				active,
				'btn-block': block,
				'btn-monospaced': monospaced,
				'btn-outline-borderless': borderless,
				[`btn-${size}`]: size,
				'link-disabled': href && disabled
			}
		);

		const iconAndChildren = (
			<>
				{icon && iconAlignment === 'left' && this.renderIcon()}

				{children}

				{icon && iconAlignment === 'right' && this.renderIcon()}
			</>
		);

		if ((disabled || externalLink) && href) {
			/* eslint-disable */
			return (
				<a
					{...otherProps}
					className={classes}
					href={disabled ? undefined : href}
					onClick={onClick}
					ref={forwardedRef}
				>
					{iconAndChildren}
				</a>
			);
			/* eslint-enable */
		} else if (!disabled && !externalLink && href) {
			return (
				<Link
					{...otherProps}
					className={classes}
					onClick={onClick}
					ref={forwardedRef}
					to={href}
				>
					{iconAndChildren}
				</Link>
			);
		} else {
			return (
				<button
					{...otherProps}
					className={classes}
					disabled={disabled || loading}
					onClick={onClick}
					ref={forwardedRef}
					type={type}
				>
					{loading && (
						<span className='inline-item inline-item-before'>
							<Spinner />
						</span>
					)}

					{iconAndChildren}
				</button>
			);
		}
	}
}

export default Object.assign(
	React.forwardRef<HTMLButtonElement, IButtonProps>((props, ref) => (
		<Button forwardedRef={ref} {...props} />
	)),
	{Group: ButtonGroup, GroupItem: ButtonGroupItem}
);
