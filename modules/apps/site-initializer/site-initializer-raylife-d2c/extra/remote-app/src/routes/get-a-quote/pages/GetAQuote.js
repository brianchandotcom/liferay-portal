/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext, useEffect} from 'react';
import {useWatch} from 'react-hook-form';

import Providers from '../Providers';
import {FormLayout} from '../components/containers/Forms';
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

const adaptRaylifeLayout = (isMobile) => {
	if (isMobile) {
		document
			.querySelector('section#content')
			?.setAttribute('class', 'raylife-mobile');
	}

	document
		.querySelector('.get-a-quote-structure .step-list')
		?.setAttribute(
			'style',
			isMobile
				? 'overflow-x: auto; overflow-y: hidden; height: 39px;'
				: 'justify-content-center'
		);
};

const QuoteApp = () => {
	const form = useWatch();
	const {selectedStep} = useStepWizard();
	const {updateState} = useTriggerContext();
	const {
		state: {
			dimensions: {
				device: {isMobile},
			},
		},
	} = useContext(AppContext);

	useEffect(() => {
		updateState('');
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedStep.section, selectedStep.subsection]);

	useEffect(() => {
		adaptRaylifeLayout(isMobile);
	}, [isMobile]);

	return (
		<div className="d-flex get-a-quote-structure justify-content-between">
			<Steps />

			<main className="d-flex flex-wrap justify-content-lg-start justify-content-md-center">
				<h2 className="display-4 mb-6 mx-6 step-title">
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

				<FormLayout form={form} />
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
