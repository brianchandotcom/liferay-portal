/* eslint-disable no-console */
import React, {useState} from 'react';

import {CreateAnAccount} from '../components/Steps/CreateAnAccount';
import Panel from '../components/Steps/Panel';
import UploadDocuments from '../components/Steps/UploadDocuments';
import QuoteInfo from '../components/quote-info';

const SelectedQuote = () => {
	const [sections, setSections] = useState(null);
	const [stepChecked, setStepChecked] = useState({
		createAnAccount: false,
		selectPaymentMethod: false,
		uploadDocuments: false,
	});
	const [expanded, setExpanded] = useState({
		createAnAccount: true,
		selectPaymentMethod: false,
		uploadDocuments: false,
	});

	const setState = (state, value) => {
		const stateParam = state;

		for (var item in stateParam) {
			stateParam[item] = false;
			if (item === value) {
				stateParam[item] = true;
			}
		}

		return stateParam;
	};

	const _setExpanded = (property) => {
		const toExpand = setState(expanded, property);
		setExpanded(toExpand);
	};

	const _setStepChecked = (property) => {
		setStepChecked({
			...stepChecked,
			[property]: true,
		});
	};

	const _setSection = (sections) => {
		setSections(sections);
	};

	return (
		<div className="selected-quote">
			<QuoteInfo />

			<div className="selected-quote-right-page">
				<Panel
					defaultExpanded={expanded.createAnAccount}
					stepChecked={stepChecked.createAnAccount}
					title="1. Create an Account"
				>
					<CreateAnAccount
						_setExpanded={_setExpanded}
						_setStepChecked={_setStepChecked}
					/>
				</Panel>

				<Panel
					defaultExpanded={expanded.uploadDocuments}
					sections={sections}
					stepChecked={stepChecked.uploadDocuments}
					title="2. Upload Documents"
				>
					<UploadDocuments
						_setExpanded={_setExpanded}
						_setSection={_setSection}
						_setStepChecked={_setStepChecked}
					/>
				</Panel>

				<Panel
					defaultExpanded={expanded.selectPaymentMethod}
					stepChecked={stepChecked.selectPaymentMethod}
					title="3. Select Payment Method"
				>
					Select Payment Method...
				</Panel>
			</div>
		</div>
	);
};

export default SelectedQuote;
