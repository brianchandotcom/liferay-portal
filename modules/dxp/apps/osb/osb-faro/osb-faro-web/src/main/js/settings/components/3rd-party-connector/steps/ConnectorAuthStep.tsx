import ConnectorAuth from '../ConnectorAuth';
import React from 'react';
import {Alert} from 'shared/types';
import {ConnectorConfig} from '../types';
import {Routes, toRoute} from 'shared/util/router';
import {updateSearchParams} from 'settings/components/base-page/utis';
import {useNavigate} from 'react-router-dom';
import {useWizardPage} from '../../base-page/WizardPageContext';

interface IConnectorAuthStepProps {
	addAlert: Alert.AddAlert;
	config: ConnectorConfig;
	groupId: string;
	onNext: () => void;
}

const ConnectorAuthStep = ({
	addAlert,
	config,
	groupId,
	onNext
}: IConnectorAuthStepProps) => {
	const {dataSource} = useWizardPage();
	const navigate = useNavigate();

	const handleCancel = () => {
		navigate(
			toRoute(Routes.SETTINGS_DATA_SOURCE_LIST, {
				groupId
			})
		);
	};

	if (!dataSource) {
		return (
			<ConnectorAuth
				addAlert={addAlert}
				buttonProps={{block: true}}
				config={config}
				groupId={groupId}
				onCancel={handleCancel}
				onSubmit={createdDataSource => {
					updateSearchParams(
						navigate,
						'dataSourceId',
						createdDataSource.id
					);

					onNext();
				}}
			/>
		);
	}

	return (
		<ConnectorAuth
			addAlert={addAlert}
			buttonProps={{block: true}}
			config={config}
			dataSource={dataSource}
			groupId={groupId}
			onCancel={handleCancel}
			onSubmit={onNext}
		/>
	);
};

export {ConnectorAuthStep};
