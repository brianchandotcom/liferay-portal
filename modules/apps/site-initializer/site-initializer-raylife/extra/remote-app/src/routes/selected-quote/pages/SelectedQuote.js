import React, {useState} from 'react';

import {CreateAnAccount} from '../components/Steps/CreateAnAccount';
import Panel from '../components/Steps/Panel';
import UploadDocuments from '../components/Steps/UploadDocuments';
import QuoteInfo from '../components/quote-info';

const SelectedQuote = () => {
	const [panel, setPanel] = useState({
		createAnAccount: {
			checked: false,
			expanded: true,
		},
		selectPaymentMethod: {
			checked: false,
			expanded: false,
		},
		uploadDocuments: {
			checked: false,
			expanded: false,
		},
	});

	const [sections, setSections] = useState(null);

	const _setPanel = (panelKey, panelKeyProperty, value) => {
		const newPanel = {...panel};

		newPanel[panelKey][panelKeyProperty] =
			value ?? !newPanel[panelKey][panelKeyProperty];

		setPanel(newPanel);
	};

	const setExpanded = (panelKey) => {
		_setPanel(panelKey, 'expanded');
	};

	const setStepChecked = (panelKey, value) => {
		_setPanel(panelKey, 'checked', value);
	};

	const hasUploadError = () => {
		const hasError = sections?.some(({error}) => error);

		return hasError;
	};

	return (
		<div className="selected-quote">
			<QuoteInfo />

			<div className="selected-quote-right-page">
				<Panel
					defaultExpanded={panel.createAnAccount.expanded}
					stepChecked={panel.createAnAccount.checked}
					title="1. Create an Account"
				>
					<CreateAnAccount
						setExpanded={setExpanded}
						setStepChecked={setStepChecked}
					/>
				</Panel>

				<Panel
					changeable
					defaultExpanded={panel.uploadDocuments.expanded}
					hasError={hasUploadError()}
					sections={sections}
					stepChecked={panel.uploadDocuments.checked}
					title="2. Upload Documents"
				>
					<UploadDocuments
						setExpanded={setExpanded}
						setSection={(sections) => setSections(sections)}
						setStepChecked={setStepChecked}
					/>
				</Panel>

				<Panel
					defaultExpanded={panel.selectPaymentMethod.expanded}
					stepChecked={panel.selectPaymentMethod.checked}
					title="3. Select Payment Method"
				>
					Select Payment Method...
				</Panel>
			</div>
		</div>
	);
};

export default SelectedQuote;
