import * as API from 'shared/api';
import BaseDetails from 'contacts/pages/BaseDetails';
import React from 'react';
import {Individual} from 'shared/util/records';

const fetchIndividualDetails = ({groupId, id}) =>
	API.individuals
		.fetchDetails({groupId, individualId: id})
		.then(({custom, demographics}) => {
			const retVal = {...demographics};

			Object.values(custom).forEach(([fieldMapping, ...others]) => {
				retVal[`custom-${fieldMapping.name}`] = [
					{
						...fieldMapping,
						sourceName: `[${Liferay.Language.get(
							'custom-field'
						)}] ${fieldMapping.sourceName}`
					},
					...others
				];
			});

			return retVal;
		});

interface IDetailsProps {
	groupId: string;
	individual: Individual;
}

const Details: React.FC<IDetailsProps> = ({groupId, individual}) => (
	<BaseDetails
		dataSourceFn={fetchIndividualDetails}
		groupId={groupId}
		id={individual.id}
		title={Liferay.Language.get('individual-attributes')}
	/>
);

export default Details;
