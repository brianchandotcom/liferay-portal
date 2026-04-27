import {buildLanguages} from '../buildLanguages';
import {ConnectorConfig} from '../types';
import {DataSourceTypes} from 'shared/util/constants';
import {fetchConnectorEntityCount} from 'shared/api/connector';
import {sub} from 'shared/util/lang';

const SLUG = 'demandbase';

const displayName = Liferay.Language.get('demandbase');

const demandbaseConfig: ConnectorConfig = {
	displayName,
	endpointPath: '/api/demandbase_accounts',
	entities: [
		{
			accessor: 'accounts',
			description: sub(
				Liferay.Language.get(
					'represents-fields-from-the-account-object-within-x'
				),
				[displayName]
			) as string,
			fetchCount: params =>
				fetchConnectorEntityCount(SLUG, 'accounts', params),
			icon: 'briefcase',
			label: Liferay.Language.get('accounts')
		}
	],
	languages: buildLanguages(displayName),
	singleton: true,
	slug: SLUG,
	type: DataSourceTypes.Demandbase
};

export default demandbaseConfig;
