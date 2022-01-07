import React, {useContext, useEffect} from 'react';
import {useWatch} from 'react-hook-form';
import {DEVICES} from '../../../common/utils/constants';

import {createExitAlert} from '../../../common/utils/exitAlert';
import {getWebDavUrl} from '../../../common/utils/webdav';
import Providers from '../Providers';
import {Forms} from '../components/containers/Forms';
import {Steps} from '../components/containers/Steps';
import {AppContext} from '../context/AppContextProvider';
import {useStepWizard} from '../hooks/useStepWizard';
import {useTriggerContext} from '../hooks/useTriggerContext';

import {AVAILABLE_STEPS} from '../utils/constants';

/**
 * @description Since Raylife contains fragments and elements out of our scope
 * We need to rewrite some behavior for this elements, according to layout size
 * @param {Boolean} isMobileDevice
 */

const adaptRaylifeLayout = (isMobileDevice) => {
	if (isMobileDevice) {
		document
			.querySelector('section#content')
			?.setAttribute('class', 'raylife-mobile');
	}

	document
		.querySelector('.quote-site-navbar .container img.navbar-logo')
		?.setAttribute(
			'src',
			`${getWebDavUrl()}/${
				isMobileDevice ? 'raylife_logo_mobile.svg' : 'raylife_logo.svg'
			}`
		);

	document
		.querySelector('.get-a-quote-structure .step-list')
		?.setAttribute(
			'style',
			isMobileDevice
				? 'overflow-x: auto; overflow-y: hidden; height: 39px;'
				: 'justify-content-center'
		);
};

const QuoteApp = () => {
	const form = useWatch();
	const {selectedStep} = useStepWizard();
	const {updateState} = useTriggerContext();
	const {
		state: {dimensions},
	} = useContext(AppContext);

	const isMobileDevice = dimensions.deviceSize === DEVICES.PHONE;

	useEffect(() => {
		createExitAlert();
	});

	useEffect(() => {
		updateState('');
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedStep.section, selectedStep.subsection]);

	useEffect(() => {
		adaptRaylifeLayout(isMobileDevice);
	}, [isMobileDevice]);

	return (
		<div className="d-flex get-a-quote-structure justify-content-between">
			<Steps />

			<main className="d-flex flex-wrap justify-content-lg-start justify-content-md-center">
				<h2 className="display-4 mb-6 mx-6">
					{selectedStep.title}

					{AVAILABLE_STEPS.PROPERTY.section ===
						selectedStep.section && (
						<span className="primary">
							{
								form.basics.businessInformation.business
									.location.address
							}
						</span>
					)}
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
