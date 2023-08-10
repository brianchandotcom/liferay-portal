import BaseModal from 'experiments/components/modals/BaseModal';
import PublishVariant from './PublishVariant';
import React from 'react';
import {EXPERIMENT_MUTATION} from 'experiments/queries/ExperimentMutation';
import {getVariantLink, makeAllRefetch} from 'experiments/util/experiments';
import {useMutation} from '@apollo/react-hooks';
import {useStateValue} from 'experiments/state';

const PublishVariantModal = ({
	dxpVariantId,
	dxpVariantName,
	experimentId,
	observer,
	onClose,
	pageURL,
	title
}) => {
	const [mutate] = useMutation(EXPERIMENT_MUTATION);
	const [{allRefetch}]: any = useStateValue();

	const onSubmit = () =>
		mutate({
			variables: {
				experimentId,
				publishedDXPVariantId: dxpVariantId,
				status: 'COMPLETED'
			}
		});

	const variantLink = getVariantLink({id: dxpVariantId, pageURL});

	return (
		<BaseModal
			observer={observer}
			onClose={onClose}
			onSubmit={onSubmit}
			onSuccess={() => makeAllRefetch(allRefetch)}
			submitMessage={Liferay.Language.get('publish-as-experience')}
			title={title}
		>
			<PublishVariant
				dxpVariantName={dxpVariantName}
				link={variantLink}
			/>
		</BaseModal>
	);
};

export default PublishVariantModal;
