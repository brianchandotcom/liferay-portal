import BaseModal from 'experiments/components/modals/BaseModal';
import BasePage from 'shared/components/base-page';
import React, {useContext} from 'react';
import WithHistory from 'shared/hoc/WithHistory';
import {EXPERIMENT_DELETE_MUTATION} from 'experiments/queries/ExperimentMutation';
import {Routes, toRoute} from 'shared/util/router';
import {useMutation} from '@apollo/react-hooks';

const DeleteExperimentModal = ({experimentId, history, observer, onClose}) => {
	const {
		router: {
			params: {channelId, groupId}
		}
	} = useContext(BasePage.Context);
	const [mutate] = useMutation(EXPERIMENT_DELETE_MUTATION);

	const onSubmit = () =>
		mutate({
			variables: {
				experimentId
			}
		});

	const onSuccess = () => {
		history.push(toRoute(Routes.TESTS, {channelId, groupId}));
	};

	return (
		<BaseModal
			observer={observer}
			onClose={onClose}
			onSubmit={onSubmit}
			onSuccess={onSuccess}
			status='warning'
			submitMessage={Liferay.Language.get('delete')}
			title={Liferay.Language.get('deleting-test')}
		>
			<div className='mb-2 text-secondary'>
				{Liferay.Language.get(
					'are-you-sure-you-want-to-delete-this-test'
				)}
			</div>

			<strong>
				{Liferay.Language.get(
					'you-will-permanently-lose-all-test-data-and-results-collected-from-this-test'
				)}{' '}
				{Liferay.Language.get('you-will-not-be-able-to-run-this-test')}{' '}
				{Liferay.Language.get(
					'you-will-not-be-able-to-undo-this-operation'
				)}
			</strong>
		</BaseModal>
	);
};

export default WithHistory(DeleteExperimentModal);
