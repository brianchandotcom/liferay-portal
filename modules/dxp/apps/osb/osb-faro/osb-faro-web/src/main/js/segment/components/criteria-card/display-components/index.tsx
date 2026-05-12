import AccountDisplay from './AccountDisplay';
import BehaviorDisplay from './BehaviorDisplay';
import EventDisplay from './EventDisplay';
import IndividualDisplay from './IndividualDisplay';
import InterestDisplay from './InterestDisplay';
import OrganizationDisplay from './OrganizationDisplay';
import React from 'react';
import SessionDisplay from './SessionDisplay';
import VocabularyDisplay from './VocabularyDisplay';
import {IDisplayComponentProps} from '../types';

const DisplayComponent: React.FC<IDisplayComponentProps> = ({
	criterion,
	property,
	segmentType
}) => {
	const getDisplayComponent = (propertyKey: string) => {
		switch (propertyKey) {
			case 'account':
				return AccountDisplay;
			case 'event':
				return EventDisplay;
			case 'session':
				return SessionDisplay;
			case 'interest':
				return InterestDisplay;
			case 'vocabulary':
				return VocabularyDisplay;
			case 'web':
				return BehaviorDisplay;
			case 'organization':
				return OrganizationDisplay;
			case 'individual':
			default:
				return IndividualDisplay;
		}
	};

	const Display = getDisplayComponent(
		property.propertyKey
	) as React.FC<IDisplayComponentProps>;

	return (
		<Display
			criterion={criterion}
			property={property}
			segmentType={segmentType}
		/>
	);
};

export default DisplayComponent;
