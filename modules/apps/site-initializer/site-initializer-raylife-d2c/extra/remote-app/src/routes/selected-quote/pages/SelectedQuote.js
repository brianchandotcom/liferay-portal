/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
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
	const [createAnAccount, setCreateAnAccount] = useState(false);

	useEffect(() => {
		const quoteElements = document.querySelector(
			'section#content #main-content .container-fluid'
		);

		quoteElements.classList.add('selected-quote-content');
	}, []);

	useEffect(() => {
		if (sections) {
			setHasError(sectionsHasError(sections));
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [sections]);

	return (
		<div className="container d-flex flex-column flex-lg-row mb-10 w-100">
			<QuoteInfo />

			<div className="ml-0 ml-lg-5 w-100">
				<Panel
					Right={CheckButton}
					id="createAnAccount"
					title={
						createAnAccount
							? 'Create an Account'
							: 'Login or Create an Account'
					}
					titleNumber="1"
				>
					<CreateAnAccount
						createAnAccount={createAnAccount}
						setCreateAnAccount={setCreateAnAccount}
					/>
				</Panel>

				<Panel
					Middle={({checked, expanded}) => {
						const displayViewFiles = !expanded && checked;

						return (
							<div
								className={classNames(
									'order-last order-lg-3 order-md-2 order-xl-2 panel-middle',
									{
										'd-none': !displayViewFiles,
									}
								)}
							>
								{displayViewFiles && (
									<ViewFilesPanel sections={sections} />
								)}
							</div>
						);
					}}
					Right={DiscardChanges}
					changeable
					hasError={hasError}
					id="uploadDocuments"
					title="Upload Documents"
					titleNumber="2"
				>
					<UploadDocuments />
				</Panel>

				<Panel
					Right={CheckButton}
					id="selectPaymentMethod"
					title="Select Payment Method"
					titleNumber="3"
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
