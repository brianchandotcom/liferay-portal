import BaseModal from 'experiments/components/modals/BaseModal';
import React from 'react';
import {EXPERIMENT_MUTATION} from 'experiments/queries/ExperimentMutation';
import {makeAllRefetch} from 'experiments/util/experiments';
import {Status as ModalStatus, Observer} from '@clayui/modal/lib/types';
import {Status} from 'experiments/util/types';
import {useMutation} from '@apollo/react-hooks';
import {useStateValue} from 'experiments/state';

interface IUpdateExperimentStatusModalProps
	extends React.HTMLAttributes<HTMLElement> {
	experimentId: string;
	modalBody: React.ReactNode;
	modalStatus: ModalStatus;
	nextStatus: Status;
	observer: Observer;
	onClose: () => void;
	publishedDXPVariantId?: string;
	submitMessage: string;
	title: string;
}

const UpdateExperimentStatusModal: React.FC<IUpdateExperimentStatusModalProps> = ({
	experimentId,
	modalBody,
	modalStatus,
	nextStatus,
	observer,
	onClose,
	publishedDXPVariantId,
	submitMessage,
	title
}) => {
	const [{allRefetch}]: any = useStateValue();
	const [mutate] = useMutation(EXPERIMENT_MUTATION);

	return (
		<BaseModal
			observer={observer}
			onClose={onClose}
			onSubmit={() =>
				mutate({
					variables: {
						experimentId,
						publishedDXPVariantId,
						status: nextStatus
					}
				})
			}
			onSuccess={() => makeAllRefetch(allRefetch)}
			status={modalStatus}
			submitMessage={submitMessage}
			title={title}
		>
			{modalBody}
		</BaseModal>
	);
};

export default UpdateExperimentStatusModal;
