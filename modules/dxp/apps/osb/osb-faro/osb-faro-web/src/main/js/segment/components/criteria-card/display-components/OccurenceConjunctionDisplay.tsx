import React from 'react';
import {
	OCCURENCE_OPTIONS,
	RelationalOperators
} from 'segment/segment-editor/dynamic/utils/constants';

interface IOccurenceConjunctionDisplayProps {
	operatorName: RelationalOperators;
	value: number;
}

const OccurenceConjunctionDisplay: React.FC<IOccurenceConjunctionDisplayProps> = ({
	operatorName,
	value
}) => {
	const {label = ''} =
		OCCURENCE_OPTIONS.find(({value}) => value === operatorName) || {};

	return (
		<>
			<span>{label}</span>

			<b>{value}</b>

			<span>{Liferay.Language.get('times')}</span>
		</>
	);
};

export default OccurenceConjunctionDisplay;
