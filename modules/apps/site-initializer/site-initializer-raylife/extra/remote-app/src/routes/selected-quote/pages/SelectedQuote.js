import React, {useContext, useEffect, useState} from 'react';
import Panel from '../components/Panel';
import CheckButton from '../components/Panel/CheckButton';
import ViewFilesPanel from '../components/Panel/ViewFilesPanel';
import QuoteInfo from '../components/QuoteInfo';
import {CreateAnAccount} from '../components/Steps/CreateAnAccount';
import PaymentMethod from '../components/Steps/PaymentMethod';
import UploadDocuments from '../components/Steps/UploadDocuments';
import DiscardChanges from '../components/Steps/UploadDocuments/DiscardChanges';
import {sectionsHasError} from '../components/Steps/UploadDocuments/utils/upload';

import SelectedQuoteContextProvider, {
	SelectedQuoteContext,
} from '../context/SelectedQuoteContextProvider';

const SelectedQuote = () => {
	const [{sections}] = useContext(SelectedQuoteContext);
	const [hasError, setHasError] = useState(false);

	useEffect(() => {
		if (sections) {
			setHasError(sectionsHasError(sections));
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [sections]);

	return (
		<div className="selected-quote">
			<QuoteInfo />

			<div className="selected-quote-right-page">
				<Panel
					PanelRight={CheckButton}
					id="createAnAccount"
					title="1. Create an Account"
				>
					<CreateAnAccount />
				</Panel>

				<Panel
					PanelMiddle={({checked, expanded}) => (
						<div className="panel-middle">
							{!expanded && checked && (
								<ViewFilesPanel sections={sections} />
							)}
						</div>
					)}
					PanelRight={DiscardChanges}
					changeable
					hasError={hasError}
					id="uploadDocuments"
					title="2. Upload Documents"
				>
					<UploadDocuments />
				</Panel>

				<Panel
					id="selectPaymentMethod"
					title="3. Select Payment Method"
				>
					<PaymentMethod />
				</Panel>
			</div>
		</div>
	);
};

const SelectedQuoteWithProvider = () => (
	<SelectedQuoteContextProvider>
		<SelectedQuote />
	</SelectedQuoteContextProvider>
);

export default SelectedQuoteWithProvider;
