import ConnectDemandbaseAuth from '../ConnectDemandbaseAuth';
import React from 'react';
import {Routes, toRoute} from 'shared/util/router';
import {updateSearchParams} from 'settings/components/base-page/utis';
import {useHistory} from 'react-router-dom';
import {useWizardPage} from '../../base-page/WizardPageContext';

const ConnectDemandbaseStep = ({addAlert, groupId, onNext}) => {
	const {dataSource} = useWizardPage();
	const history = useHistory();

	if (!dataSource) {
		return (
			<ConnectDemandbaseAuth
				addAlert={addAlert}
				buttonProps={{block: true}}
				groupId={groupId}
				onCancel={() => {
					history.push(
						toRoute(Routes.SETTINGS_DATA_SOURCE_LIST, {
							groupId
						})
					);
				}}
				onSubmit={createdDataSource => {
					updateSearchParams(
						history,
						'dataSourceId',
						createdDataSource.id
					);
					onNext();
				}}
			/>
		);
	}

	return (
		<ConnectDemandbaseAuth
			addAlert={addAlert}
			buttonProps={{block: true}}
			dataSource={dataSource}
			groupId={groupId}
			onCancel={() => {
				history.push(
					toRoute(Routes.SETTINGS_DATA_SOURCE_LIST, {
						groupId
					})
				);
			}}
			onSubmit={onNext}
		/>
	);
};

export {ConnectDemandbaseStep};
