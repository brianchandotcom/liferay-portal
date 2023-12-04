import Card from 'shared/components/Card';
import getCN from 'classnames';
import React from 'react';
import {Status} from './types';
import {SummaryTitle} from './SummaryTitle';

interface SummaryBaseCardIProps extends React.HTMLAttributes<HTMLElement> {
	status?: Status;
}

const Body: React.FC<React.HTMLAttributes<HTMLElement>> = ({children}) => (
	<Card.Body>{children}</Card.Body>
);

const Footer: React.FC<React.HTMLAttributes<HTMLElement>> = ({children}) => (
	<Card.Footer>{children}</Card.Footer>
);

const Header = ({Description, title}) => (
	<Card.Header>
		<SummaryTitle className='mb-2' label={title} />

		{Description && (
			<span className='font-size-sm font-weight-normal'>
				<Description />
			</span>
		)}
	</Card.Header>
);

export const SummaryBaseCard: React.FC<SummaryBaseCardIProps> & {
	Body: typeof Body;
	Footer: typeof Footer;
	Header: typeof Header;
} = ({children, status}) => (
	<Card
		className={getCN(
			'analytics-summary-card analytics-summary-card-status',
			{
				[` analytics-summary-card-status-${status}`]: status
			}
		)}
	>
		{children}
	</Card>
);

SummaryBaseCard.Body = Body;
SummaryBaseCard.Footer = Footer;
SummaryBaseCard.Header = Header;
