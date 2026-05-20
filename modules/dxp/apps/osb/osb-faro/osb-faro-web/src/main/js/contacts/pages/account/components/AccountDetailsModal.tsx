import * as API from 'shared/api';
import ClayModal, {useModal} from '@clayui/modal';
import React, {useMemo} from 'react';
import {columns} from 'shared/util/frontend-data-set';
import {Routes} from 'shared/util/router';
import {sub} from 'shared/util/lang';
import {useFDSState} from 'shared/hooks/useFDSState';
import {useFrontendDataSet} from 'shared/hooks/useFrontendDataSet';
import {useParams} from 'react-router-dom';
import {useRequest} from 'shared/hooks/useRequest';

const FDS_ID = 'account-details-attributes-dataset';

interface IAccountDetailsField {
	dataSourceId?: string;
	dataSourceName?: string;
	lastModified?: string;
	name: string;
	sourceName?: string;
	value?: string;
}

interface IAccountDetailsModalProps {
	accountId: string;
	accountName?: string;
	onClose: () => void;
}

const AccountDetailsModal: React.FC<IAccountDetailsModalProps> = ({
	accountId,
	accountName,
	onClose
}) => {
	const {channelId, groupId} = useParams<{
		channelId: string;
		groupId: string;
	}>();
	const {observer} = useModal({onClose});
	const FrontendDataSet = useFrontendDataSet();
	const {search} = useFDSState(FDS_ID);

	const {data} = useRequest({
		dataSourceFn: API.accounts.fetchDetails,
		variables: {accountId, channelId, groupId}
	});

	const items: IAccountDetailsField[] = data?.fields ?? [];

	const filteredItems = useMemo(() => {
		if (!search) {
			return items;
		}

		const query = search.toLowerCase();

		return items.filter(item => item.name.toLowerCase().includes(query));
	}, [items, search]);

	return (
		<ClayModal observer={observer} size='lg'>
			<ClayModal.Header>
				{sub(Liferay.Language.get('xs-attributes'), [
					accountName ?? ''
				])}
			</ClayModal.Header>

			<ClayModal.Body className='px-0'>
				{FrontendDataSet && (
					<FrontendDataSet
						configInURLBehavior='off'
						customDataRenderers={{
							attributeNameAndValueRenderer: ({
								itemData,
								value
							}: {
								itemData: {value?: string};
								value: string;
							}) =>
								columns.attributeNameAndValue({
									attributeName: value,
									value: itemData.value ?? ''
								}),
							dataSourceRenderer: ({
								itemData,
								value
							}: {
								itemData: {dataSourceId?: string};
								value: string;
							}) =>
								columns.nameAndLinkRenderer({
									channelId,
									groupId,
									itemData: {
										id: itemData.dataSourceId ?? ''
									},
									route: Routes.SETTINGS_DATA_SOURCE,
									value
								}),
							lastModifiedRenderer: ({value}: {value: string}) =>
								columns.dateRenderer({
									itemData: {},
									value
								})
						}}
						id={FDS_ID}
						items={filteredItems}
						views={[
							{
								contentRenderer: 'table',
								default: true,
								label: Liferay.Language.get('default-view'),
								name: 'table',
								schema: {
									fields: [
										{
											_key: 'name',
											contentRenderer:
												'attributeNameAndValueRenderer',
											fieldName: 'name',
											label: `${Liferay.Language.get(
												'attribute-name'
											)} | ${Liferay.Language.get(
												'value'
											)}`
										},
										{
											_key: 'sourceName',
											fieldName: 'sourceName',
											label: Liferay.Language.get(
												'source-name'
											)
										},
										{
											_key: 'dataSourceName',
											contentRenderer:
												'dataSourceRenderer',
											fieldName: 'dataSourceName',
											label: Liferay.Language.get(
												'data-source'
											),
											sortable: true
										},
										{
											_key: 'lastModified',
											contentRenderer:
												'lastModifiedRenderer',
											fieldName: 'lastModified',
											label: Liferay.Language.get(
												'last-modified'
											)
										}
									]
								},
								thumbnail: 'table'
							}
						]}
					/>
				)}
			</ClayModal.Body>
		</ClayModal>
	);
};

export default AccountDetailsModal;
