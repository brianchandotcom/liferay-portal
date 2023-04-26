import Card from 'shared/components/Card';
import ClayLink from '@clayui/link';
import NoResultsDisplay from 'shared/components/NoResultsDisplay';
import React from 'react';
import {Routes, toRoute} from 'shared/util/router';

interface IAddDataSourceProps {
	groupId: string;
}

const AddDataSource: React.FC<IAddDataSourceProps> = ({groupId}) => (
	<>
		<Card.Header>
			<Card.Title>{Liferay.Language.get('distribution')}</Card.Title>
		</Card.Header>

		<Card.Body>
			<NoResultsDisplay
				description={Liferay.Language.get(
					'convert-and-enrich-anonymous-individuals-to-known-individuals-with-attributes-from-another-data-source'
				)}
				primary
				title=''
			>
				<ClayLink
					button
					className='button-root'
					displayType='primary'
					href={toRoute(Routes.SETTINGS_ADD_DATA_SOURCE, {groupId})}
				>
					{Liferay.Language.get('add-data-source')}
				</ClayLink>
			</NoResultsDisplay>
		</Card.Body>
	</>
);

export default AddDataSource;
