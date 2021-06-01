/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {createResourceURL, fetch, openToast} from 'frontend-js-web';
import React, {useContext, useEffect, useState} from 'react';

import {AppContext} from '../../AppContext';
import DocumentPreviewer from '../../components/document-previewer/DocumentPreviewer';
import EmptyState from '../../components/table/EmptyState';
import {DOCUSIGN_STATUS} from '../../utils/contants';
import {concatValues} from '../../utils/utils';

const QuestionLine = ({children, colon = true, question}) => (
	<div>
		<b>{`${question}${colon ? ':' : ''}`}</b>
		<span className="ml-1">{children}</span>
	</div>
);

const EnvelopeDetail = ({
	envelope: {emailBlurb, emailSubject, recipients, senderEmailAddress},
	envelopeId,
}) => (
	<div className="envelope-view__details">
		<div>
			<b>{Liferay.Language.get('envelope-id')}</b>: {envelopeId}
		</div>
		<hr />

		<QuestionLine colon={false} question={emailSubject} />
		<QuestionLine question={Liferay.Language.get('to')}>
			{concatValues(recipients?.signers.map(({email}) => email))}
		</QuestionLine>
		<QuestionLine question={Liferay.Language.get('from')}>
			{senderEmailAddress}
		</QuestionLine>

		<p className="mt-2">{emailBlurb}</p>
	</div>
);

const EnvelopeHeader = ({docusignStatus, emailSubject, envelopeId}) => {
	const {baseResourceURL} = useContext(AppContext);

	return (
		<div className="envelope-view__header">
			<div>
				<span className="envelope-view__header__title">
					{emailSubject}
				</span>
				<ClayLabel className="ml-2" displayType={docusignStatus.color}>
					{docusignStatus.label}
				</ClayLabel>
			</div>
			<ClayButton
				onClick={() =>
					window.open(
						createResourceURL(baseResourceURL, {
							envelopeId,
							p_p_resource_id:
								'/digital_signature/get_ds_documents_as_bytes',
						}),
						'_blank'
					)
				}
			>
				<ClayIcon symbol="download" />
				<span className="ml-1">{Liferay.Language.get('download')}</span>
			</ClayButton>
		</div>
	);
};

function EnvelopeView({
	match: {
		params: {envelopeId},
	},
}) {
	const {baseResourceURL} = useContext(AppContext);

	const [{envelope, fileEntries = [], isLoading}, setEnvelope] = useState({
		envelope: {},
		isLoading: true,
	});

	const fileEntry = fileEntries[0] || {};

	const docusignStatus = DOCUSIGN_STATUS[envelope?.status] || {
		...DOCUSIGN_STATUS.other,
		label: envelope?.status,
	};

	const getEnvelope = async () => {
		try {
			const response = await fetch(
				createResourceURL(baseResourceURL, {
					envelopeId,
					p_p_resource_id: '/digital_signature/get_ds_envelope',
				})
			);

			const data = await response.json();

			setEnvelope({...data, isLoading: false});
		}
		catch (e) {
			openToast({
				message: Liferay.Language.get('an-unexpected-error-occurred'),
				title: Liferay.Language.get('error'),
				type: 'danger',
			});
		}
	};

	useEffect(() => {
		if (envelopeId) {
			getEnvelope();
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [envelopeId]);

	if (isLoading) {
		return <ClayLoadingIndicator />;
	}

	return (
		<div className="envelope-view">
			<EnvelopeHeader
				docusignStatus={docusignStatus}
				emailSubject={envelope.emailSubject}
				envelopeId={envelopeId}
			/>

			{fileEntries.length === 0 ? (
				<EmptyState
					className="mb-2 mt-4"
					description={Liferay.Language.get(
						'hmm-looks-like-this-item-doesnt-have-a-preview-we-can-show-you'
					)}
					title={Liferay.Language.get('no-preview-available')}
				/>
			) : (
				<DocumentPreviewer
					baseImageURL={fileEntry.previewFileURL}
					initialPage={fileEntry.initialPage}
					totalPages={fileEntry.previewFileCount}
				/>
			)}

			<EnvelopeDetail envelope={envelope} envelopeId={envelopeId} />
		</div>
	);
}

export default EnvelopeView;
