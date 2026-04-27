import {buildLanguages} from '../buildLanguages';
import {ConnectorConfig} from '../types';
import {DataSourceTypes} from 'shared/util/constants';
import {fetchConnectorEntityCount} from 'shared/api/connector';
import {sub} from 'shared/util/lang';

const SLUG = 'hubspot';

const displayName = Liferay.Language.get('hubspot');

const hubspotConfig: ConnectorConfig = {
	displayName,
	endpointPath: '/api/hubspot_accounts',
	entities: [
		{
			accessor: 'contacts',
			description: sub(
				Liferay.Language.get(
					'represents-fields-from-the-account-object-within-x'
				),
				[displayName]
			) as string,
			fetchCount: params =>
				fetchConnectorEntityCount(SLUG, 'contacts', params),
			icon: 'users',
			label: Liferay.Language.get('contacts')
		}
	],
	languages: buildLanguages(displayName),
	singleton: true,
	slug: SLUG,
	type: DataSourceTypes.Hubspot
};

export default hubspotConfig;
