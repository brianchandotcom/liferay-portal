import BackButton from 'contacts/components/BackButton';
import BasePage from 'shared/components/base-page';
import InterestDetails from 'shared/components/InterestDetails';
import React from 'react';
import {isNil, pickBy} from 'lodash';
import {Router} from 'shared/types';
import {Routes, setUriQueryValues, toRoute} from 'shared/util/router';

interface IInterestDetailsProps extends React.HTMLAttributes<HTMLDivElement> {
	router: Router;
}

const InterestDetailsPage: React.FC<IInterestDetailsProps> = ({router}) => {
	const {
		params: {channelId, groupId},
		query: {rangeKey}
	} = router;

	return (
		<BasePage.Body
			className='individuals-dashboard-interest-details-root'
			pageContainer
		>
			<BackButton
				href={setUriQueryValues(
					pickBy({rangeKey}, param => !isNil(param)),

					toRoute(Routes.CONTACTS_INDIVIDUALS_INTERESTS, {
						channelId,
						groupId
					})
				)}
				label={Liferay.Language.get('back-to-interests')}
			/>

			<InterestDetails router={router} />
		</BasePage.Body>
	);
};

export default InterestDetailsPage;
