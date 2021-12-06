import React, {useEffect} from 'react';
import {useWatch} from 'react-hook-form';
import {createExitAlert} from '../../../common/utils/exitAlert';

import Providers from '../Providers';
import {Forms} from '../components/containers/Forms';
import {Steps} from '../components/containers/Steps';
import {useStepWizard} from '../hooks/useStepWizard';
import {useTriggerContext} from '../hooks/useTriggerContext';
import {AVAILABLE_STEPS} from '../utils/constants';

const QuoteApp = () => {
	const form = useWatch();
	const {selectedStep} = useStepWizard();
	const {updateState} = useTriggerContext();

	const FormTitle = () => {
		if (selectedStep.section !== AVAILABLE_STEPS.PROPERTY.section) {
			return selectedStep.title;
		}

		return (
			<>
				{selectedStep.title}
				<span className="primary">
					{form.basics.businessInformation.business.location.address}
				</span>
			</>
		);
	};

	useEffect(() => {
		createExitAlert();
	});

	useEffect(() => {
		updateState('');
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedStep.section, selectedStep.subsection]);

	return (
		<div className="d-flex justify-content-between">
			<Steps />

			<main>
				<h2 className="display-4 mb-6 mx-6">
					<FormTitle />
				</h2>

				<Forms form={form} />
			</main>
		</div>
	);
};

const GetAQuote = () => (
	<Providers>
		<QuoteApp />
	</Providers>
);

export default GetAQuote;
