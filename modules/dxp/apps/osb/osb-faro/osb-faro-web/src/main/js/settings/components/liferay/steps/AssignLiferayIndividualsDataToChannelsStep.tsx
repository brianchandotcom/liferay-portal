import DataSourceQuery, {
	DataSourceSyncData
} from 'shared/queries/DataSourceQuery';
import React from 'react';
import {Alert} from 'shared/types';
import {AssignIndividualsDataToPropertiesStep} from 'settings/components/salesforce/steps/AssignIndividualsDataToChannelsStep';
import {CREATE_DATE} from 'shared/util/pagination';
import {
	CredentialTypes,
	DataSourceTypes,
	OrderByDirections
} from 'shared/util/constants';
import {updateLiferay} from 'shared/api/data-source';
import {useQuery} from '@apollo/react-hooks';

const AssignLiferayIndividualsDataToChannelsStep = props => {
	const {data, loading} = useQuery<DataSourceSyncData>(DataSourceQuery, {
		fetchPolicy: 'network-only',
		variables: {
			credentialsType: CredentialTypes.Token,
			size: 1,
			sort: {
				column: CREATE_DATE,
				type: OrderByDirections.Descending
			},
			type: DataSourceTypes.Liferay
		}
	});

	if (loading) {
		return null;
	}

	const dataSource = data.dataSources[0];

	return (
		<AssignIndividualsDataToPropertiesStep
			{...props}
			onSubmit={() => {
				if (
					dataSource?.sitesSyncDetails?.selected ||
					dataSource?.contactsSyncDetails?.selected
				) {
					props.addAlert({
						alertType: Alert.Types.Success,
						message: Liferay.Language.get(
							'the-data-source-setup-is-now-complete,-and-you-will-begin-to-see-data-as-activities-occur-on-your-sites'
						)
					});
				} else {
					props.addAlert({
						alertType: Alert.Types.Success,
						message: Liferay.Language.get(
							'the-data-source-setup-has-finished'
						)
					});
				}
			}}
			updateDataSourceFn={updateLiferay}
		/>
	);
};

export {AssignLiferayIndividualsDataToChannelsStep};
