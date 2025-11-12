import ClayForm from '@clayui/form';
import React from 'react';
import SalesforceAccountsAndIndividuals from 'settings/components/salesforce/SalesforceAccountsAndIndividuals';
import {ButtonGroup} from './ButtonGroup';
import {fetch} from 'shared/api/data-source';
import {Text} from '@clayui/core';
import {useConnectSalesforce} from '../ConnectSalesforceContext';
import {useParams} from 'react-router-dom';

const SyncSalesforceDataStep = ({addAlert, onNext, onPrev}) => {
	const {dataSource, setDataSource} = useConnectSalesforce();
	const {groupId} = useParams();

	return (
		<ClayForm
			onSubmit={async event => {
				event.preventDefault();

				onNext();
			}}
		>
			<div className='mb-2'>
				<Text size={2} weight='semi-bold'>
					{Liferay.Language.get('connection-status').toUpperCase()}
				</Text>
			</div>

			{dataSource && (
				<SalesforceAccountsAndIndividuals
					addAlert={addAlert}
					dataSource={dataSource}
					groupId={groupId}
					onChange={async () => {
						await fetch({groupId, id: dataSource.id});

						setDataSource(dataSource);
					}}
				/>
			)}

			<ButtonGroup
				nextButtonLabel={Liferay.Language.get('continue')}
				onCancel={onPrev}
				prevButtonLabel={Liferay.Language.get('previous')}
			/>
		</ClayForm>
	);
};

export {SyncSalesforceDataStep};
