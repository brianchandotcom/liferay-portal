import React from 'react';

import Panel from '../components/Steps/Panel';
import QuoteInfo from '../components/quote-info';

const SelectedQuote = () => {
	return (
		<div className="selected-quote">
			<QuoteInfo />
			<div className="selected-quote-right-page">
				<Panel title="1. Create an Account">Create an Account...</Panel>
				<Panel title="2. Upload Documents">Upload Documents...</Panel>
				<Panel title="3. Select Payment Method">
					Select Payment Method...
				</Panel>
			</div>
		</div>
	);
};

export default SelectedQuote;
