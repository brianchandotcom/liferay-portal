/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

import Email from './Email.es';
import Link from './Link.es';

export function ShareFormModalBody({
	autocompleteUserURL,
	emailContent,
	localizedName,
	url,
}) {
	const [addresses, setAddresses] = useState([]);
	const [message, setMessage] = useState(
		sub(Liferay.Language.get('please-fill-out-this-form-x'), url)
	);
	const [subject, setSubject] = useState(
		localizedName[themeDisplay.getLanguageId()]
	);

	return (
		<div className="share-form-modal-items">
			<div className="share-form-modal-item">
				<div className="popover-header">
					{Liferay.Language.get('link')}
				</div>

				<div className="popover-body">
					<Link url={url} />
				</div>
			</div>

			<div className="share-form-modal-item">
				<div className="popover-header">
					{Liferay.Language.get('email')}
				</div>

				<div className="popover-body">
					<Email
						addresses={addresses}
						autocompleteUserURL={autocompleteUserURL}
						emailContent={emailContent}
						localizedName={localizedName}
						message={message}
						onMessageChanged={setMessage}
						onMultiSelectItemsChanged={setAddresses}
						onSubjectChanged={setSubject}
						subject={subject}
					/>
				</div>
			</div>
		</div>
	);
}
