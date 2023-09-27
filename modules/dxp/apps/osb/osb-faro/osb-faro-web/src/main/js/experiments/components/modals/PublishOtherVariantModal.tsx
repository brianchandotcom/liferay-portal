import BaseModal from 'experiments/components/modals/BaseModal';
import DXPLinkButton from './DXPLinkButton';
import PublishVariant from './PublishVariant';
import React, {useState} from 'react';
import {EXPERIMENT_MUTATION} from 'experiments/queries/ExperimentMutation';
import {getVariantLink, makeAllRefetch} from 'experiments/util/experiments';
import {Option, Picker} from '@clayui/core';
import {useMutation} from '@apollo/react-hooks';
import {useStateValue} from 'experiments/state';

const ChooseVariant = ({dxpVariant, dxpVariants, link, setDxpVariant}) => (
	<>
		<div className='mb-3 font-size-md font-weight-semibold'>
			{Liferay.Language.get('which-variant-do-you-want-to-publish')}
		</div>

		<Picker
			className='mb-3'
			onSelectionChange={value =>
				setDxpVariant(
					dxpVariants.find(({dxpVariantId}) => dxpVariantId === value)
				)
			}
			selectedKey={dxpVariant ? dxpVariant.dxpVariantId : ''}
		>
			<Option key='select-a-variant'>
				{Liferay.Language.get('select-a-variant')}
			</Option>
			{dxpVariants.map(({dxpVariantId, dxpVariantName}) => (
				<Option key={dxpVariantId}>{dxpVariantName}</Option>
			))}
		</Picker>

		<DXPLinkButton disabled={!dxpVariant} href={link} />

		<div className='mt-3'>
			{Liferay.Language.get(
				'this-will-publish-the-variant-as-the-live-experience-for-the-target-segment-that-was-chosen-for-this-test'
			)}
		</div>
	</>
);

const PublishOtherVariantModel = ({
	dxpVariants,
	experimentId,
	observer,
	onClose,
	pageURL,
	title
}) => {
	const [{allRefetch}]: any = useStateValue();
	const [dxpVariant, setDxpVariant] = useState(null);
	const [currentStep, setStep] = useState(1);
	const [mutate] = useMutation(EXPERIMENT_MUTATION);

	const onSubmit =
		currentStep === 1
			? () => setStep(2)
			: () =>
					mutate({
						variables: {
							experimentId,
							publishedDXPVariantId: dxpVariant.dxpVariantId,
							status: 'COMPLETED'
						}
					});
	const closeFn = currentStep === 1 ? () => null : onClose;
	const onCancel = currentStep === 1 ? onClose : () => setStep(1);

	const labelSubmitButton =
		currentStep === 1
			? Liferay.Language.get('next')
			: Liferay.Language.get('publish-as-experience');

	const labelCancelButton =
		currentStep === 1
			? Liferay.Language.get('cancel')
			: Liferay.Language.get('back');

	const onSuccess =
		currentStep === 1 ? () => {} : () => makeAllRefetch(allRefetch);

	const link = dxpVariant
		? getVariantLink({id: dxpVariant.dxpVariantId, pageURL})
		: '';

	return (
		<BaseModal
			cancelMessage={labelCancelButton}
			disabled={!dxpVariant}
			observer={observer}
			onCancel={onCancel}
			onClose={closeFn}
			onSubmit={onSubmit}
			onSuccess={onSuccess}
			submitMessage={labelSubmitButton}
			title={title}
		>
			{currentStep === 1 ? (
				<ChooseVariant
					dxpVariant={dxpVariant}
					dxpVariants={dxpVariants}
					link={link}
					setDxpVariant={setDxpVariant}
				/>
			) : (
				<PublishVariant
					dxpVariantName={dxpVariant.dxpVariantName}
					link={link}
				/>
			)}
		</BaseModal>
	);
};

export default PublishOtherVariantModel;
