import React from 'react';
import {useWatch} from 'react-hook-form';

import {Forms} from './components/containers/Forms';
import {Steps} from './components/containers/Steps';
import {useStepWizard} from './hooks/useStepWizard';
import {AVAILABLE_STEPS} from './utils/constants';

export const App = () => {
	const form = useWatch();
	const {selectedStep} = useStepWizard();

	const _renderTitle = () => {
		if (selectedStep.section !== AVAILABLE_STEPS.PROPERTY.section) {
			return selectedStep.title;
		}
		else {
			return (
				<>
					{selectedStep.title}
					<span className="primary">
						{
							form.basics.businessInformation.business.location
								.address
						}
					</span>
				</>
			);
		}
	};

	return (
		<>
			<div className="form-area">
				<Steps />
				<div>
					<h2 className="title title-area">{_renderTitle()}</h2>
					<Forms form={form} />
				</div>
				<div className="info-area"></div>
			</div>
		</>
	);
};
