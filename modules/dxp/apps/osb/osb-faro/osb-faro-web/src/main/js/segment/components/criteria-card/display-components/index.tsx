import AccountDisplay from './AccountDisplay';
import BehaviorDisplay from './BehaviorDisplay';
import EventDisplay from './EventDisplay';
import IndividualDisplay from './IndividualDisplay';
import InterestDisplay from './InterestDisplay';
import OrganizationDisplay from './OrganizationDisplay';
import React from 'react';
import SessionDisplay from './SessionDisplay';
import {IDisplayComponentProps} from '../types';

const DisplayComponent: React.FC<IDisplayComponentProps> = ({
	criterion,
	property
}) => {
	const getDisplayComponent = propertyKey => {
		switch (propertyKey) {
			case 'account':
				return AccountDisplay;
			case 'event':
				return EventDisplay;
			case 'session':
				return SessionDisplay;
			case 'interest':
				return InterestDisplay;
			case 'web':
				return BehaviorDisplay;
			case 'organization':
				return OrganizationDisplay;
			case 'individual':
			default:
				return IndividualDisplay;
		}
	};

	const Display: React.FC<IDisplayComponentProps> = getDisplayComponent(
		property.propertyKey
	);

	return <Display criterion={criterion} property={property} />;
};

export default DisplayComponent;
