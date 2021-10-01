import React, {useEffect} from 'react';
import {useWatch} from 'react-hook-form';

import {Forms} from '~/apps/get-a-quote/components/containers/Forms';
import {Steps} from '~/apps/get-a-quote/components/containers/Steps';
import {useStepWizard} from '~/apps/get-a-quote/hooks/useStepWizard';
import {useTriggerContext} from '~/apps/get-a-quote/hooks/useTriggerContext';
import {AVAILABLE_STEPS} from '~/apps/get-a-quote/utils/constants';
import Style from '~/shared/styles/provider.scss';
import {Providers} from '../Providers';

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
		updateState('');
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedStep.section, selectedStep.subsection]);

	return (
		<div className="form-area">
			<Steps />

			<div>
				<h2 className="title title-area">
					<FormTitle />
				</h2>

				<Forms form={form} />
			</div>

			<div className="info-area"></div>
		</div>
	);
};

const GetAQuote = () => (
	<Providers>
		<style>{Style}</style>

		<QuoteApp />
	</Providers>
);

export default GetAQuote;
