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
