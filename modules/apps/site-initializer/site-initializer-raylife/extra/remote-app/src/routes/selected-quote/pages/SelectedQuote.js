import React, {useState} from 'react';

import {CreateAnAccount} from '../components/Steps/CreateAnAccount';
import Panel from '../components/Steps/Panel';
import UploadDocuments from '../components/Steps/UploadDocuments';
import QuoteInfo from '../components/quote-info';

const SelectedQuote = () => {
	const [sections, setSections] = useState(null);
	const [stepChecked, setStepChecked] = useState(false);

	const _setSection = (sections) => {
		setSections(sections);
	};

	const _setStepChecked = (value) => {
		setStepChecked(value);
	};

	return (
		<div className="selected-quote">
			<QuoteInfo />

			<div className="selected-quote-right-page">
				<Panel title="1. Create an Account">
					<CreateAnAccount />
				</Panel>

				<Panel
					defaultExpanded
					sections={sections}
					stepChecked={stepChecked}
					title="2. Upload Documents"
				>
					<UploadDocuments
						_setSection={_setSection}
						_setStepChecked={_setStepChecked}
					/>
				</Panel>

				<Panel title="3. Select Payment Method">
					Select Payment Method...
				</Panel>
			</div>
		</div>
	);
};

export default SelectedQuote;
