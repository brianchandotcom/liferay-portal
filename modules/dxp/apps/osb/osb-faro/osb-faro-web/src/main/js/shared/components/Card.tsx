import getCN from 'classnames';
import React from 'react';

interface ICardBodyProps extends React.HTMLAttributes<HTMLElement> {
	alignCenter?: boolean;
	noPadding?: boolean;
}

const Body: React.FC<ICardBodyProps> = ({
	alignCenter = false,
	children,
	className,
	noPadding
}) => (
	<div
		className={getCN('card-body', className, {
			'align-center': alignCenter,
			'no-padding': noPadding
		})}
	>
		{alignCenter ? <div>{children}</div> : children}
	</div>
);

const Footer: React.FC<React.HTMLAttributes<HTMLElement>> = ({
	children,
	className
}) => <div className={getCN('card-footer', className)}>{children}</div>;

const Header: React.FC<React.HTMLAttributes<HTMLElement>> = ({
	children,
	className
}) => <div className={getCN('card-header', className)}>{children}</div>;

const Title: React.FC<React.HTMLAttributes<HTMLElement>> = ({
	children,
	className
}) => <h5 className={getCN('card-title', className)}>{children}</h5>;

interface ICardProps extends React.HTMLAttributes<HTMLElement> {
	horizontal?: boolean;
	minHeight?: number;
	pageDisplay?: boolean;
	testId?: string;
}

const Card: React.FC<ICardProps> & {
	Body: typeof Body;
	Footer: typeof Footer;
	Header: typeof Header;
	Title: typeof Title;
} = ({
	children,
	className,
	horizontal = false,
	id,
	minHeight,
	pageDisplay = false,
	testId
}) => {
	const classes = getCN('card', 'card-root', className, {
		horizontal,
		'page-display': pageDisplay
	});

	return (
		<div
			className={classes}
			data-testid={testId}
			id={id}
			style={minHeight && {minHeight: `${minHeight}px`}}
		>
			{children}
		</div>
	);
};

Card.Body = Body;
Card.Footer = Footer;
Card.Header = Header;
Card.Title = Title;

export default Card;
