import React, {useCallback} from 'react';
import {Alert} from 'shared/types';
import {disconnect} from 'shared/api/data-source';
import {modalTypes} from 'shared/actions/modals';
import {Text} from '@clayui/core';

const useDisconnectDataSource = ({
	addAlert,
	close,
	groupId,
	id,
	onSubmit,
	open
}) => {
	const handleDisconnect = useCallback(() => {
		open(modalTypes.CONFIRMATION_MODAL, {
			message: (
				<Text as='p' size={4}>
					{Liferay.Language.get(
						'this-action-will-stop-syncing-data-from-salesforce-to-this-analytics-cloud-workspace.-the-data-that-was-already-synced-will-remain-available-in-the-properties-the-data-source-was-connected-to.-are-you-sure-you-want-to-continue'
					)}
				</Text>
			),
			modalVariant: 'modal-warning',
			onClose: close,
			onSubmit: async () => {
				try {
					await disconnect({groupId, id});

					await onSubmit();

					addAlert({
						alertType: Alert.Types.Success,
						message: Liferay.Language.get(
							'data-source-disconnected'
						)
					});

					close();
				} catch (error) {
					addAlert({
						alertType: Alert.Types.Error,
						message: Liferay.Language.get(
							'there-was-an-error-processing-your-request.-try-again.-if-the-problem-persists,-please-contact-support'
						)
					});
				}
			},
			submitButtonDisplay: 'warning',
			submitMessage: Liferay.Language.get('disconnect'),
			title: Liferay.Language.get('disconnect-data-source'),
			titleIcon: 'warning-full'
		});
	}, [addAlert, close, groupId, id, open]);

	return {handleDisconnect};
};

export {useDisconnectDataSource};
